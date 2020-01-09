package me.frostdev.frostyspawners.listener;

import me.frostdev.frostyspawners.Frostyspawners;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_14_R1.command.ColouredConsoleSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class ItemSpawnListener implements Listener {
    private Frostyspawners main;

    public ItemSpawnListener(Frostyspawners as) {
        this.main = as;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onItemSpawn(ItemSpawnEvent e) {
        EntityType type = EntityType.CHICKEN;
        Location location = e.getEntity().getLocation();
        double radius = 0.5;
        int mcheck = 0;
        double pdist = 0;
        ConsoleCommandSender console = this.main.getServer().getConsoleSender();
        Entity debug= null;

        if(e.getEntity().getItemStack().getType()==Material.EGG ){
            if(e.getEntity().getVelocity().getY()*10000000==2000000) {
                List<Entity> near = location.getWorld().getEntities();
                for(Entity x : near) {
                        if (x.getType().equals(type)) {
                            if (x.getLocation().distance(location) <= radius) {
                                if (x.getLocation().distance(e.getLocation()) > pdist && x.hasMetadata("frosty_ident")){
                                    int count = x.getMetadata("frosty_count").get(0).asInt();
                                    Random rand = new Random();
                                    int randint = rand.nextInt(50) +1;
                                    count = count - randint;
                                    e.getEntity().getItemStack().setAmount(count);
                                    pdist = x.getLocation().distance(e.getLocation());
                                    console.sendMessage("New Closest Chicken Found!: "+ x.getCustomName());
                                    console.sendMessage("New Egg Ammount: " + count);
                                    debug = x;
                                    }else if(!x.hasMetadata("frosty_ident")){
                                    console.sendMessage("Non-Stacked Chicken Found, Ignoring.");
                                }

                                }
                            }
                        }
                if(debug != null && !debug.hasMetadata("frosty_ident")) {
                    console.sendMessage("Non-Stacked Chicken Egg Spawn Found!: " + debug.getType().toString() + "At Location: " + debug.getLocation().toString());
                }
                    }else{
                console.sendMessage("Non-Chicken Egg Spawn found!");

                }
            }

        }
    }
