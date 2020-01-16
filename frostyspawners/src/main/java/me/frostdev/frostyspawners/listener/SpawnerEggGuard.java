package me.frostdev.frostyspawners.listener;

import me.frostdev.frostyspawners.Frostyspawners;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class SpawnerEggGuard implements Listener {
    private Frostyspawners main;
    public SpawnerEggGuard(Frostyspawners as){
        this.main = as;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onEggInteract(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType().name().contains("SPAWNER")) {
                Player p = e.getPlayer();
                if (p.getInventory().getItemInMainHand().getType().toString().contains("SPAWN_EGG")) {
                    main.getLogger().info("EGG CHANGE ATTEMPT DETECTED! " + e.getPlayer().getName() + " attempted to change a spawner's type with an egg.");
                    e.getPlayer().sendMessage("Nice Try.");
                    e.setCancelled(true);
                }
            }
        }
    }

}
