package ca.tylerfisher.minecraft.auditor;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import ca.tylerfisher.minecraft.auditor.objects.BlockMetadata;
import ca.tylerfisher.minecraft.auditor.objects.PlayerMetadata;

public class EventListener implements Listener {
  private final String broke = "broke";

  public EventListener() {
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onBlockBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();

    PlayerMetadata s = new PlayerMetadata(player);
    String p = broke;
    BlockMetadata o = new BlockMetadata(event.getBlock());

    try {
      player.sendMessage(String.format("%s %s %s", s.getObjectId(), p, o.getObjectId()));
    } catch (NoSuchAlgorithmException | IOException e) {
      e.printStackTrace();
    }
  }
}
