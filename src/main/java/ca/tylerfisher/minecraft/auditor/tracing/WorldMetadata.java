package ca.tylerfisher.minecraft.auditor.tracing;

import java.util.UUID;

import org.bukkit.World;

public class WorldMetadata implements TraceObject {
  public UUID id;
  public String name;

  public WorldMetadata(World world) {
    this.id = world.getUID();
    this.name = world.getName();
  }

  @Override
  public String getType() {
    return "world";
  }
}
