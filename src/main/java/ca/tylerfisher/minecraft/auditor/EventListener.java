package ca.tylerfisher.minecraft.auditor;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class EventListener implements Listener {
  private final Logger logger = Bukkit.getLogger();

  public EventListener() {

  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onBlockBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();
    this.logger.info("%s broke a %s".formatted(player.getName(), event.getBlock().getType().name()));
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onBlockPlace(BlockPlaceEvent event) {
    Player player = event.getPlayer();
    this.logger.info("%s placed a %s".formatted(player.getName(), event.getBlock().getType().name()));
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onChunkLoad(ChunkLoadEvent event) {
    Chunk chunk = event.getChunk();
    this.logger.info("Chunk (%d, %d) loaded".formatted(chunk.getX(), chunk.getZ()));
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onChunkUnload(ChunkUnloadEvent event) {
    Chunk chunk = event.getChunk();
    this.logger.info("Chunk (%d, %d) unloaded".formatted(chunk.getX(), chunk.getZ()));
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    // Give the player a bow and a full stack of arrows.
    ensurePlayerHasObjects(player, Material.BOW, 1);
    ensurePlayerHasObjects(player, Material.ARROW, 64);
  }

  private void ensurePlayerHasObjects(Player player, Material material, int amount) {
    Inventory inventory = player.getInventory();
    int count = 0;
    for (ItemStack itemStack : inventory.getContents()) {
      if (itemStack != null && itemStack.getType() == material) {
        count += itemStack.getAmount();
      }
    }
    if (count < amount) {
      int giftAmount = amount - count;
      player.sendMessage("You have been given " + giftAmount + " " + material.name() + "s");
      inventory.addItem(new ItemStack(material, giftAmount));
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuit(PlayerQuitEvent event) {

  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerMove(PlayerQuitEvent event) {

  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onProjectileLaunch(ProjectileLaunchEvent event) {
    Projectile projectile = event.getEntity();
    ProjectileSource source = projectile.getShooter();
    if (source instanceof Player) {
      Player player = (Player) source;
      player.sendMessage("You shot a " + projectile.getType().name());
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onProjectileHit(ProjectileHitEvent event) {
    Projectile projectile = event.getEntity();
    ProjectileSource source = projectile.getShooter();

    if (source instanceof Player) {
      Player player = (Player) source;
      player.sendMessage("Your " + projectile.getType().name() + " hit something");
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onEntityDeath(EntityDeathEvent event) {
    LivingEntity deceased = event.getEntity();
    Player player = deceased.getKiller();
    if (player != null) {
      player.sendMessage("You killed a " + deceased.getType().name());
    }
  }
}
