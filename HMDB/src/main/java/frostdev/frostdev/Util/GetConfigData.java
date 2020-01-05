package frostdev.frostdev.Util;

import frostdev.frostdev.HMDB;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;


public class GetConfigData {
    private HMDB main;
    private File file;
    private FileConfiguration config;
    public GetConfigData(HMDB as) {this.main = as;}




    public String GetConfigString(String path) {
        return this.main.getConfig().getString(path);
    }
    public int GetConfigInt(String path) {
        return this.main.getConfig().getInt(path);

    }

}
