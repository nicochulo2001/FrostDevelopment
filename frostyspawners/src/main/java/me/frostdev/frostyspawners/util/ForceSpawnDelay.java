package me.frostdev.frostyspawners.util;

import me.frostdev.frostyspawners.Frostyspawners;
import me.frostdev.frostyspawners.spawners.Spawner;
import me.frostdev.frostyspawners.util.config.Config;

public class ForceSpawnDelay {

    public void ForceDelay(Spawner spawner){
        spawner.getCreatureSpawner().setDelay(Config.getLevel(spawner.getLevel()).getDelay());
    }

    public void ForceSpawnCount(Spawner spawner) {
        spawner.getCreatureSpawner().setSpawnCount(Config.getLevel(spawner.getLevel()).getSpawnCount());
    }
}
