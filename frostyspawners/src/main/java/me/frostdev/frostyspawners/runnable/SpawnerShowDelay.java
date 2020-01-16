package me.frostdev.frostyspawners.runnable;

import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.util.Logger;
import org.bukkit.Bukkit;

public class SpawnerShowDelay implements Runnable {
    private Spawner spawner;
    private int id;

    public SpawnerShowDelay(Spawner s) {
        this.spawner = s;
    }

    public void setTaskId(int i) {
        this.id = i;
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(this.id);
    }

    public void run() {
        Logger.info(String.valueOf(this.spawner.getDelay()));



    }
}
