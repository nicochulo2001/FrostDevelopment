package frostdev.frostdev;

import frostdev.frostdev.CompanyDataCommit.*;
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
import java.sql.Connection;
import java.sql.SQLException;

public final class HMDB extends JavaPlugin {
    private FileConfiguration config = this.getConfig();
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
            this.getLogger().info("Failed to open connection to database, check and make sure your config is set up correctly and your database is online. this is a fatal error.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        getLogger().info("Successfully hooked into MySQL Database, initializing...");
        this.tableSetup = new TableSetup();
        getLogger().info("TableSetup instance initialized.");
        this.companyCreate = new CompanyCreate();
        this.companyDataGet = new CompanyDataGet(this.GetConnection());
        this.companyMembersCreate = new CompanyMembersCreate();
        this.companyMembersCommit = new CompanyMembersCommit();
        this.companyExists = new CompanyExists(this, this.GetConnection());
        getLogger().info("Company instances initialized.");
        this.playerDataCommit = new PlayerDataCommit(this);
        this.playerDataCreate = new PlayerDataCreate(this, this.GetConnection());
        getLogger().info("PlayerData instances initialized.");
       // GetItems items = new GetItems();
       // items.Populate(this);
        ItemStack test = new ItemStack(Material.IRON_HELMET);
        GetItemData getItemData = new GetItemData(this, test.getType().toString());
        String result = getItemData.ReturnItemValue();
        getLogger().info(result);
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
        getConfig().addDefault("database-host", "localhost");
        getConfig().addDefault("database-DBname", "HMDB");
        getConfig().addDefault("database-port", 3306);
        getConfig().addDefault("database-username", "root");
        getConfig().addDefault("database-password", "Test123!");
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config not found, spawning a new one.");
                saveDefaultConfig();
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

    public CompanyMembersCommit companyMembersCommit(String UUID, String datacontent, String datatype){
        return this.companyMembersCommit.CommitInstance(UUID, datacontent, datatype, this.GetConnection());
    }

    public void MemberTitleCommit(String title){
        this.companyMembersCommit.TitleCommit(title);
    }

    public void MemberUUIDCommit(String Player_name) {
        this.companyMembersCommit.UUIDCommit(Player_name, this);
    }

    public CompanyDataCommit CompanyCommitData(String UUID) {
        return this.companyDataCommit = new CompanyDataCommit(this, UUID);
    }

    public CompanyDataGet companyDataGet() {
        return this.companyDataGet;
    }

    public PlayerDataGet getPlayerData(String player){
       return this.playerDataGet = new PlayerDataGet(this, player);
    }

    public PlayerDataCommit playerDataCommit(){
        return this.playerDataCommit;
    }

    public PlayerDataCreate playerDataCreate(){
        return this.playerDataCreate;
    }

    public TableExists tableExists(){
        return this.tableExists = new TableExists();

    }

    public CompanyExists getCompanyExists(){
        return this.companyExists;
    }

    public GetItemData getItemData(String item){
        return this.getItemData = new GetItemData(this, item);
    }


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
