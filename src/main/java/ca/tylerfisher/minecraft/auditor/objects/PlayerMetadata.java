package ca.tylerfisher.minecraft.auditor.objects;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerMetadata implements WorldObjectInterface {
  public UUID worldId;
  public UUID id;
  public String name;

  public PlayerMetadata(Player player) {
    this.id = player.getUniqueId();
    this.name = player.getName();
    this.worldId = player.getWorld().getUID();
  }

  @Override
  public String getObjectId() throws NoSuchAlgorithmException, IOException {
    return String.format("player--%s", this.id.toString());
  }
}
