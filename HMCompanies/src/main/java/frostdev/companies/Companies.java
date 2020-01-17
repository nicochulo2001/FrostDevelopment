package frostdev.companies;

import com.sk89q.worldguard.WorldGuard;
import frostdev.companies.Company.CompanyUtil;
import frostdev.companies.DBHandler.CompaniesCreateCompany;
import frostdev.frostdev.HMDB;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public final class Companies extends JavaPlugin {

    private Connection connection;
    private HMDB database;
    private CompaniesCreateCompany companycreate;
    private String returnstmt;
    private CompanyUtil util;
    private WorldGuard worldGuard;

    @Override
    public void onEnable() {
        database = JavaPlugin.getPlugin(HMDB.class);
        this.worldGuard = WorldGuard.getInstance();
        this.connection = database.GetConnection();
        this.companycreate = new CompaniesCreateCompany();
        this.util =  new CompanyUtil(this);
        this.getCommand("comp").setExecutor(new Command(this));
        // Plugin startup logic

    }

    public CompaniesCreateCompany CompanyCreateInstance(){
        return this.companycreate;
        }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public HMDB getDatabase(){
        return this.database;
    }
}
