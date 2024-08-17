package ca.tylerfisher.minecraft.auditor.tracing;

import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerMetadata implements TraceObject {
  public UUID id;
  public String name;

  public PlayerMetadata(Player player) {
    this.id = player.getUniqueId();
    this.name = player.getName();
  }

  @Override
  public String getType() {
    return "player";
  }
}
