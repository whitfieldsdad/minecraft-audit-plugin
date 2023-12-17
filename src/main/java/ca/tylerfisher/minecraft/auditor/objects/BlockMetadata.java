package ca.tylerfisher.minecraft.auditor.objects;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;

import ca.tylerfisher.minecraft.auditor.UUID5;

public class BlockMetadata implements WorldObjectInterface {
  public UUID worldId;
  public int x;
  public int y;
  public int z;
  public String materialType;

  public BlockMetadata(Block block) {
    this.worldId = block.getWorld().getUID();
    this.x = block.getX();
    this.y = block.getY();
    this.z = block.getZ();

    Material material = block.getType();
    this.materialType = material.toString();
  }

  @Override
  public String getObjectId() throws NoSuchAlgorithmException, IOException {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("x", this.x);
    map.put("y", this.y);
    map.put("z", this.z);
    map.put("materialType", this.materialType);
    UUID uuid = UUID5.fromMap(worldId, map);
    return String.format("block--%s", uuid.toString());
  }
}
