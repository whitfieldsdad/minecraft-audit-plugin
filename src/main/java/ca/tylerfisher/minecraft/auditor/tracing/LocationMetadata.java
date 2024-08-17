package ca.tylerfisher.minecraft.auditor.tracing;

import org.bukkit.Location;

public class LocationMetadata implements TraceObject {
  public int x;
  public int y;
  public int z;

  public LocationMetadata(Location location) {
    this.x = location.getBlockX();
    this.y = location.getBlockY();
    this.z = location.getBlockZ();
  }

  @Override
  public String getType() {
    return "location";
  }
}
