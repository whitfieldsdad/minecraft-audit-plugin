package ca.tylerfisher.minecraft.auditor.objects;

import org.bukkit.block.Block;

public class BlockMetadata {
  public int x;
  public int y;
  public int z;
  public String materialType;

  public BlockMetadata(Block block) {
    this.x = block.getX();
    this.y = block.getY();
    this.z = block.getZ();
    this.materialType = block.getType().name();
  }
}
