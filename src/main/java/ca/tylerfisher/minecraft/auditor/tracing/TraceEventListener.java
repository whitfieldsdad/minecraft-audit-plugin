package ca.tylerfisher.minecraft.auditor.tracing;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import java.util.concurrent.locks.Lock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class TraceEventListener implements Listener {
  private final Logger logger = Bukkit.getLogger();
  private final BlockingQueue<TraceEvent> events;
  private final Lock lock;

  public TraceEventListener(Lock lock, BlockingQueue<TraceEvent> events) {
    this.events = events;
    this.lock = lock;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerMove(PlayerMoveEvent event) {
    
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onBlockBreak(BlockBreakEvent event) {
    
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onBlockPlace(BlockPlaceEvent event) {
    
  }

  private void queueEvent(TraceEvent e) {
    this.lock.lock();
    try {
      this.events.put(e);
    } catch (InterruptedException ex) {
      logger.warning("Failed to queue event: " + ex.getMessage());
    } finally {
      this.lock.unlock();
    }
  }
}
