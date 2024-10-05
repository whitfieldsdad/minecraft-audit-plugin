package ca.tylerfisher.minecraft.auditor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import ca.tylerfisher.minecraft.auditor.tracing.TraceEvent;
import ca.tylerfisher.minecraft.auditor.tracing.TraceEventListener;
import ca.tylerfisher.minecraft.auditor.tracing.TraceEventSink;

public class App extends JavaPlugin {

  @Override
  public void onEnable() {
    File dataFolder = getDataFolder();
    if (!dataFolder.exists()) {
      dataFolder.mkdirs();
    }
    String path = dataFolder.toPath().resolve("events.jsonl").toAbsolutePath().toString();

    // Events will be written to a queue, and then written to a JSONL (NDJSON) file every 15 seconds.
    BlockingQueue<TraceEvent> events = new LinkedBlockingQueue<TraceEvent>();
    Lock lock = new ReentrantLock();

    Server server = getServer();
    PluginManager pluginManager = server.getPluginManager();

    TraceEventListener listener = new TraceEventListener(lock, events);
    pluginManager.registerEvents(listener, this);

    TraceEventSink sink = new TraceEventSink(path);

    BukkitScheduler scheduler = getServer().getScheduler();
    scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
      @Override
      public void run() {
        lock.lock();
        if (!events.isEmpty()) {
          Object[] objects = events.toArray();
          Logger logger = getLogger();
          
          logger.info(String.format("Writing %d objects to %s", objects.length, path));

          try {
            sink.writeEvents(events.toArray());
            events.clear();
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            lock.unlock();
          }
        }
      }
    }, 0L, 20L * 15);
  }
}
