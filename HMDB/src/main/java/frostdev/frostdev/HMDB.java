package frostdev.frostdev;

import frostdev.frostdev.CompanyDataCommit.*;
import frostdev.frostdev.PlayerWallet.PlayerWalletCommit;
import frostdev.frostdev.PlayerWallet.PlayerWalletCreate;
import frostdev.frostdev.PlayerWallet.PlayerWalletGet;
import frostdev.frostdev.Util.TableSetup;
import frostdev.frostdev.DBConnect.MySQLConnect;
import frostdev.frostdev.Listeners.LogInListener;
import frostdev.frostdev.PlayerDataCommit.PlayerDataCommit;
import frostdev.frostdev.PlayerDataCommit.PlayerDataCreate;
import frostdev.frostdev.PlayerDataCommit.PlayerDataGet;
import frostdev.frostdev.Util.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import frostdev.frostdev.Listeners.EconListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public final class HMDB extends JavaPlugin {
    private FileConfiguration config;
    private MySQLConnect connect;
    private Economy econ;
    private CompanyCreate companyCreate;
    private CompanyDataCommit companyDataCommit;
    private CompanyDataGet companyDataGet;
    private CompanyMembersCreate companyMembersCreate;
    private TableSetup tableSetup;
    private GetItemData getItemData;
    private PlayerDataGet playerDataGet;
    private TableExists tableExists;
    private CompanyMembersCommit companyMembersCommit;
    private CompanyExists companyExists;
    private PlayerDataCommit playerDataCommit;
    private PlayerDataCreate playerDataCreate;
    private PlayerWalletCommit playerWalletCommit;
    private PlayerWalletCreate playerWalletCreate;
    private PlayerWalletGet playerWalletGet;
    private GetConfigData getConfigData;

    @Override
    public void onEnable() {
        Config();
        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("Successfully hooked into Vault.");
        getLogger().info("Successfully hooked into Essentials.");
        getLogger().info("Loading config...");
        this.getConfigData = new GetConfigData(this);
        this.connect = new MySQLConnect(this);
        try {
            this.connect.openConnection();
        }catch (SQLException e){
            this.getLogger().info("Ignore SSL Error, we don't need that shit.");

        }
        getLogger().info("Successfully hooked into MySQL Database, initializing...");
        this.tableExists = new TableExists();
        this.tableSetup = new TableSetup();
        this.playerDataCreate = new PlayerDataCreate(this.GetConnection());
        this.companyCreate = new CompanyCreate();
        this.companyMembersCreate = new CompanyMembersCreate();
        this.playerWalletCreate = new PlayerWalletCreate(this, this.GetConnection());
        getLogger().info("TableSetup instance initialized.");
        this.playerWalletCommit = new PlayerWalletCommit(this);
        this.companyDataCommit = new CompanyDataCommit(this.GetConnection());
        this.companyDataGet = new CompanyDataGet(this.GetConnection());
        this.playerWalletGet = new PlayerWalletGet();
        this.companyExists = new CompanyExists(this.GetConnection());
        this.companyMembersCommit = new CompanyMembersCommit(this, this.GetConnection());
        getLogger().info("CompanyCreate instance initialized.");

        getLogger().info("CompanyData instance initialized.");

        getLogger().info("CompanyMemberCreate instance initialized.");

        getLogger().info("CompanyMemberCommit instance initialized.");

        getLogger().info("Company instances initialized.");
        this.playerDataCommit = new PlayerDataCommit(this.GetConnection());
        this.playerDataGet = new PlayerDataGet(this);
        getLogger().info("PlayerData instances initialized.");
        getLogger().info("ItemStack instances initialized.");
        this.getCommand("HMDB").setExecutor(new CommandHMDB(this));
        getLogger().info("Commands instance initialized.");
        this.getServer().getPluginManager().registerEvents(new LogInListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EconListener(this), this);
        getLogger().info("Listeners initialized.");
        getLogger().info("HMDB Fully Operational.");
        // Plugin startup logic
    }
    private void Config() {
        getConfig().addDefault("database-host", "47.22.57.154");
        getConfig().addDefault("database-DBname", "hmdb");
        getConfig().addDefault("database-port", 3306);
        getConfig().addDefault("database-username", "root");
        getConfig().addDefault("database-password", "EmoBeemo6716!");

        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config not found, spawning a new one.");
                saveDefaultConfig();
                file.createNewFile();
            } else {
                getLogger().info("Config found, initializing database configuration");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public GetConfigData getConfigData(){
        return this.getConfigData;
    }

    public Economy getEconomy() {
       return econ;
    }

    public boolean CreateCompany(String user, String UUID, String econ, String stock, String compname, String textcompdata){
        return this.companyCreate.CompanyCreate(user, UUID, econ, stock, compname, textcompdata, this);
    }
    public CompanyMembersCreate companyMembersCreate(){
        return this.companyMembersCreate;
    }

    public CompanyMembersCommit companyMembersCommit(){
        return this.companyMembersCommit;
    }

    public CompanyDataCommit CompanyCommitData(String UUID) {
        return this.companyDataCommit;
    }

    public CompanyDataGet companyDataGet() {
        return this.companyDataGet;
    }

    public PlayerDataGet getPlayerData(){
       return this.playerDataGet;
    }

    public PlayerDataCommit playerDataCommit(){
        return this.playerDataCommit;
    }

    public PlayerDataCreate playerDataCreate(){
        return this.playerDataCreate;
    }

    public PlayerWalletCommit playerWalletCommit(){
        return this.playerWalletCommit;
    }
    public PlayerWalletCreate playerWalletCreate(){
        return this.playerWalletCreate;
    }

    public TableExists tableExists(){
        return this.tableExists;

    }

    public CompanyExists getCompanyExists(){
        return  this.companyExists;
    }

  //  public GetItemData getItemData(String item){
  //      return this.getItemData = new GetItemData(this, item);
 //   }


    public Connection GetConnection() {
        return  this.connect.GetConnection();
    }

    public TableSetup tableSetup() {
        return this.tableSetup;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
