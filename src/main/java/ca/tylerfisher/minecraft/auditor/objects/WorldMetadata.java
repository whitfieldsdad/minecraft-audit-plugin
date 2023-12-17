package ca.tylerfisher.minecraft.auditor.objects;

import java.util.UUID;

import org.bukkit.World;

public class WorldMetadata implements WorldObjectInterface {
  public UUID id;
  public String name;
  public boolean allowAnimals;
  public boolean allowMonsters;
  public int animalSpawnLimit;
  public int ambientSpawnLimit;

  public WorldMetadata(World world) {
    this.id = world.getUID();
    this.name = world.getName();
    this.allowAnimals = world.getAllowAnimals();
    this.allowMonsters = world.getAllowMonsters();
    this.animalSpawnLimit = world.getAnimalSpawnLimit();
    this.ambientSpawnLimit = world.getAmbientSpawnLimit();
  }

  @Override
  public String getObjectId() {
    return String.format("world--%s", this.id.toString());
  }
}
