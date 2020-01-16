package me.frostdev.frostyspawners.runnable;


import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.spawners.Spawner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SpawnerInspect implements Runnable {
    private int id;
    private Player player;
    private Spawner spawner;

    public SpawnerInspect(Spawner s, Player p) {
        this.spawner = s;
        this.player = p;
        Location loc = new Location(this.spawner.getWorld(), (double)this.spawner.getBlock().getX() + 0.5D, (double)(this.spawner.getBlock().getY() + 2), (double)this.spawner.getBlock().getZ() + 0.5D);


    }

    public int getId() {
        return this.id;
    }

    public void setTaskId(int id) {
        this.id = id;
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(this.id);
    }

    public void run() {
        this.spawner.removeInspect(this.player);
    }
}
