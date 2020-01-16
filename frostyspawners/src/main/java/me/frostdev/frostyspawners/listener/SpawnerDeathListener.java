package me.frostdev.frostyspawners.listener;

import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class SpawnerDeathListener implements Listener {
    private Frostyspawners main;

    public SpawnerDeathListener(Frostyspawners as) {
        this.main = as;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onSpawnerDeath(EntityDeathEvent e) {


                Entity entity = e.getEntity();
                String typename = Util.toUserFriendlyString(e.getEntityType());
                typename = ChatColor.GOLD + typename;
                Location location = entity.getLocation();

                if (entity.isCustomNameVisible() && entity.fromMobSpawner() && entity.getMetadata("frosty_ident").get(0).asBoolean()) {
                    if (entity.getCustomName() != null && entity.getCustomName().contains(typename)) {
                        if(e.getEntity().getWorld().getEnvironment().equals(World.Environment.NETHER)){
                            EntityType type = e.getEntityType();
                            if(type == EntityType.WITHER_SKELETON || type == EntityType.BLAZE || type == EntityType.PIG_ZOMBIE || type == EntityType.MAGMA_CUBE){
                                e.setShouldPlayDeathSound(false);
                                e.getEntity().setCustomName("Nether Restricted.");
                                e.getDrops().clear();
                                e.getEntity().setLastDamage(0);
                                e.getEntity().setHealth(10);
                                e.getEntity().remove();
                            }

                        }
                        int temp = entity.getMetadata("frosty_count").get(0).asInt();
                        if (entity.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || entity.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
                            if (temp > 1) {
                                e.setShouldPlayDeathSound(false);
                                List<ItemStack> drops = e.getDrops();
                                temp--;
                                for (int i = 0; i < drops.size(); i++) {
                                    entity.getLocation().getWorld().dropItem(location, drops.get(i));
                                }
                                entity.setMetadata("frosty_count", new FixedMetadataValue(main, temp));
                                ConsoleCommandSender console = getServer().getConsoleSender();
                                entity.setCustomName(typename + " x" + temp);
                                console.sendMessage(temp + " Lives left on Entity: " + entity.getType().toString() + ":" + entity.getUniqueId().toString());
                                console.sendMessage("MetaData: " + entity.getMetadata("frosty_count").get(0).asString());
                                e.setCancelled(true);
                            }
                        } else {
                            e.setShouldPlayDeathSound(false);
                            e.getEntity().setCustomName("No.");
                            e.getDrops().clear();
                            e.getEntity().setLastDamage(0);
                            e.getEntity().setHealth(10);
                            e.getEntity().remove();
                        }
                    }


                }
            }
}
