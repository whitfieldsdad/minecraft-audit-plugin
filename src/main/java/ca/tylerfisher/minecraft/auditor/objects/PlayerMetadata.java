package ca.tylerfisher.minecraft.auditor.objects;

import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerMetadata {
  public UUID id;
  public String name;

  public PlayerMetadata(Player player) {
    this.id = player.getUniqueId();
    this.name = player.getName();
  }
}
