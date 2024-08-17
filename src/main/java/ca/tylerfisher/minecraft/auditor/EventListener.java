package ca.tylerfisher.minecraft.auditor;

import java.time.Instant;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.tylerfisher.minecraft.auditor.serialization.InstantTypeAdapter;
import ca.tylerfisher.minecraft.auditor.tracing.BlockMetadata;
import ca.tylerfisher.minecraft.auditor.tracing.LocationMetadata;
import ca.tylerfisher.minecraft.auditor.tracing.PlayerMetadata;
import ca.tylerfisher.minecraft.auditor.tracing.TraceEvent;
import ca.tylerfisher.minecraft.auditor.tracing.WorldMetadata;


public class EventListener implements Listener {
  private final Logger logger = Bukkit.getLogger();

  public EventListener() {

  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onBlockBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();

    WorldMetadata world = new WorldMetadata(block.getWorld());
    PlayerMetadata subject = new PlayerMetadata(event.getPlayer());
    BlockMetadata object = new BlockMetadata(block);
    LocationMetadata location = new LocationMetadata(player.getLocation());

    TraceEvent e = new TraceEvent(subject, "broke", object, world, location);
    logTraceEvent(e);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onBlockPlace(BlockPlaceEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();

    WorldMetadata world = new WorldMetadata(block.getWorld());
    PlayerMetadata subject = new PlayerMetadata(event.getPlayer());
    BlockMetadata object = new BlockMetadata(block);
    LocationMetadata location = new LocationMetadata(player.getLocation());

    TraceEvent e = new TraceEvent(subject, "placed", object, world, location);
    logTraceEvent(e);
  }

  public void logTraceEvent(TraceEvent e) {
    Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();

    String blob = gson.toJson(e);
    logger.info(blob);
  }
}
