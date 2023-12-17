package ca.tylerfisher.minecraft.auditor.objects;

import org.bukkit.Location;

public class LocationMetadata {
  public int x;
  public int y;
  public int z;

  public LocationMetadata(Location location) {
    this.x = location.getBlockX();
    this.y = location.getBlockY();
    this.z = location.getBlockZ();
  }
}
