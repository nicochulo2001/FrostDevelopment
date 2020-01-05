package frostdev.frostdev;

import com.earth2me.essentials.UserData;
import frostdev.frostdev.CompanyDataCommit.*;
import frostdev.frostdev.DBCOMMIT.PlayerEconCommit;
import frostdev.frostdev.DBCOMMIT.TableSetup;
import frostdev.frostdev.DBCOMMIT.UserDataCommit;
import frostdev.frostdev.DBConnect.MySQLConnect;
import frostdev.frostdev.Listeners.LogInListener;
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

public final class HMDB extends JavaPlugin {
    private MySQLConnect connect = new MySQLConnect();
    private FileConfiguration config = this.getConfig();
    private Economy econ;
    private CompanyCreate companyCreate;
    private CompanyDataCommit companyDataCommit;
    private CompanyDataGet companyDataGet;
    private CompanyMembersCreate companyMembersCreate;
    private PlayerEconCommit playerEconCommit;
    private TableSetup tableSetup;
    private UserDataCommit userDataCommit;
    private GetItemData getItemData;
    private GetPlayerData getPlayerData;
    private TableExists tableExists;
    private CompanyMembersCommit companyMembersCommit;


    @Override
    public void onEnable() {
        Config();
        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        connect.OnConnect(this);
        this.companyCreate = new CompanyCreate();
        this.companyDataGet = new CompanyDataGet();
        this.playerEconCommit = new PlayerEconCommit();
        this.tableSetup = new TableSetup();
        this.userDataCommit = new UserDataCommit();
        this.companyMembersCreate = new CompanyMembersCreate();
        this.companyMembersCommit = new CompanyMembersCommit();
       // GetItems items = new GetItems();
       // items.Populate(this);
        ItemStack test = new ItemStack(Material.IRON_HELMET);
        GetItemData getItemData = new GetItemData(this, test.getType().toString());
        String result = getItemData.ReturnItemValue();
        getLogger().info(result);
        this.getCommand("HMDB").setExecutor(new CommandHMDB(this));
        this.getServer().getPluginManager().registerEvents(new LogInListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EconListener(this), this);

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

    public GetPlayerData getPlayerData(String player){
       return this.getPlayerData = new GetPlayerData(this, player);
    }

    public TableExists tableExists(){
        return this.tableExists = new TableExists();

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
