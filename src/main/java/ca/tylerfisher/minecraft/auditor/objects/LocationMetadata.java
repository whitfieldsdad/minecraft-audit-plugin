package ca.tylerfisher.minecraft.auditor.objects;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;

import ca.tylerfisher.minecraft.auditor.UUID5;

public class LocationMetadata implements WorldObjectInterface {
  public UUID worldId;
  public int x;
  public int y;
  public int z;

  public LocationMetadata(Location location) {
    this.x = location.getBlockX();
    this.y = location.getBlockY();
    this.z = location.getBlockZ();
  }

  @Override
  public String getObjectId() throws NoSuchAlgorithmException, IOException {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("x", this.x);
    map.put("y", this.y);
    map.put("z", this.z);
    UUID uuid = UUID5.fromMap(worldId, map);
    return String.format("location--%s", uuid.toString());
  }
}
