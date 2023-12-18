package ca.tylerfisher.minecraft.auditor;

import java.util.Collection;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
import org.bukkit.event.player.PlayerRespawnEvent;
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
    this.logger.fine("Chunk (%d, %d) loaded".formatted(chunk.getX(), chunk.getZ()));
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onChunkUnload(ChunkUnloadEvent event) {
    Chunk chunk = event.getChunk();
    this.logger.fine("Chunk (%d, %d) unloaded".formatted(chunk.getX(), chunk.getZ()));
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    provisionPlayerEnvironment(player);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerRespawn(PlayerRespawnEvent event) {
    Player player = event.getPlayer();
    provisionPlayerEnvironment(player);
  }

  private void provisionPlayerEnvironment(Player player) {
    restoreMaxHealth(player);

    ensurePlayerHasObjects(player, Material.BOW, 1);
    ensurePlayerHasObjects(player, Material.ARROW, 64);

    ensureEntityNearby(player, EntityType.SHEEP, 5);
    ensureEntityNearby(player, EntityType.PIG, 5);
  }

  private void restoreMaxHealth(Damageable entity) {
    entity.setHealth(entity.getMaxHealth());
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

  private void ensureEntityNearby(Player player, EntityType entityType, int radius) {
    Location location = player.getLocation();
    World world = location.getWorld();

    this.logger.info("Ensuring that a %s is near %s".formatted(entityType.name(), player.getName()));

    if (entityNearPlayer(player, entityType, radius)) {
      this.logger.info("A %s is already nearby".formatted(entityType.name()));
      return;
    }

    Location spawnLocation = getRandomSpawnLocationNearPlayer(player, radius);
    if (spawnLocation == null) {
      this.logger.warning("Could not find a location to spawn a %s".formatted(entityType.name()));
    } else {
      world.spawnEntity(spawnLocation, entityType);
      this.logger.info("Spawned a %s at (%d, %d, %d)".formatted(entityType.name(), spawnLocation.getBlockX(),
          spawnLocation.getBlockY(), spawnLocation.getBlockZ()));
    }
  }

  private boolean entityNearPlayer(Player player, EntityType entityType, int radius) {
    Location location = player.getLocation();
    return entityNearLocation(location, entityType, radius);
  }

  private boolean entityNearLocation(Location location, EntityType entityType, int radius) {
    World world = location.getWorld();

    int cx = location.getBlockX();
    int cy = location.getBlockY();
    int cz = location.getBlockZ();
    int r = radius;

    Collection<Entity> nearbyEntities = world.getNearbyEntities(location, r, r, r);
    for (Entity entity : nearbyEntities) {
      Location entityLocation = entity.getLocation();
      int x = entityLocation.getBlockX();
      int y = entityLocation.getBlockY();
      int z = entityLocation.getBlockZ();

      // Check if the entity is within the spherical radius of the provided location.
      int d = (x - cx) ^ 2 + (y - cy) ^ 2 + (z - cz) ^ 2;
      if (d <= (r ^ 2)) {
        return true;
      }
    }
    return false;
  }

  private Location getRandomSpawnLocationNearPlayer(Player player, int radius) {
    Location playerLocation = player.getLocation();
    int playerX = playerLocation.getBlockX();
    int playerY = playerLocation.getBlockY();
    int playerZ = playerLocation.getBlockZ();

    int minX = playerX - radius;
    int maxX = playerX + radius;

    int minZ = playerZ - radius;
    int maxZ = playerZ + radius;

    int minY = playerY - radius;
    int maxY = playerY + radius;

    World world = player.getWorld();

    for (int x = minX; x <= maxX; x++) {
      for (int z = minZ; z <= maxZ; z++) {
        for (int y = minY; y <= maxY; y++) {
          Block block = world.getBlockAt(x, y, z);
          Block below = world.getBlockAt(x, y - 1, z);
          if (block.isEmpty() && !below.isEmpty()) {
            return block.getLocation();
          }
        }
      }
    }
    return null;
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
      player.sendMessage("You shot a %s".formatted(projectile.getType().name()));
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onProjectileHit(ProjectileHitEvent event) {
    Projectile projectile = event.getEntity();
    ProjectileSource source = projectile.getShooter();

    if (source instanceof Player) {
      Player player = (Player) source;
      player.sendMessage("Your %s hit something".formatted(projectile.getType().name()));
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
