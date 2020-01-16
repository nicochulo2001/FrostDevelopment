package frostdev.frostdev.Util;

import frostdev.frostdev.EconBot.MassEconDataGet;
import frostdev.frostdev.GUI.CustomPlayerGUI;
import frostdev.frostdev.GUI.CustomPlayerPurchaseGUI;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.PlayerDataCommit.PlayerDataGet;
import frostdev.frostdev.PlayerWallet.PlayerWalletGet;
import gyurix.inventory.CustomGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandHMDB implements CommandExecutor {
    private HMDB main;
    private String prefix;
    private String noperm;
    private PlayerDataGet playerDataGet;

    public CommandHMDB(HMDB as) {
        this.main = as;
        this.playerDataGet = as.getPlayerData();
        this.prefix = ChatColor.translateAlternateColorCodes('&', "&9[HMDB Manager]");
        this.noperm = "&cYou have no permission to preform this action.";


    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length >= 1) {
            if (args[0].equals("bal")) {
                if (args[1].isEmpty()) {
                    sender.sendMessage(this.prefix + " No Player name defined.");
                    return false;
                } else {
                    if(playerDataGet.isValidPlayer(args[1])) {
                        sender.sendMessage(this.prefix + " Player " + args[1] + " has a balance of $" + playerDataGet.ReturnPlayerBalance(args[1]));
                        return true;
                    }else{
                        sender.sendMessage("Player not found!");
                    }
                }
            } if (args[0].equals("company")) {
                    if (playerDataGet.isValidPlayer(args[1])) {
                        sender.sendMessage(this.prefix + " Player " + args[1] + " is a member of " + playerDataGet.ReturnPlayerCompany(args[1]));
                        return true;
                    } else {
                        sender.sendMessage("Player not found!");
                    }
            } if (args[0].equals("uuid")) {
                if (args[1].isEmpty()) {
                    sender.sendMessage(this.prefix + " No Player name defined.");
                    return false;
                } else {
                    if (playerDataGet.isValidPlayer(args[1])) {
                        sender.sendMessage(this.prefix + " Player " + args[1] + "'s UUID is  " + playerDataGet.ReturnPlayerUUID(args[1]));
                        return true;
                    } else {
                        sender.sendMessage("Player not found!");
                    }
                }
            } if (args[0].equals("populate")){
                GetItems populate = new GetItems();
                populate.Populate(this.main);
                sender.sendMessage("Success.");
            } if (args[0].equals("wallet")){

            }
            if (args[0].equals("create")) {
                    boolean res = main.CreateCompany(args[1],args[2],args[3],args[4],args[5],args[6]);
                    if (res){
                        sender.sendMessage("Success");
                        return true;
                    } else {
                        sender.sendMessage("Fail");
                        return false;
                    }
            }
            if (args[0].equals("value")){
                MassEconDataGet econDataGet = new MassEconDataGet(this.main, this.main.GetConnection());
                try {
                    sender.sendMessage(econDataGet.ReturnAvgValue(args[1]));
                    return true;
                }catch (SQLException  e){
                    sender.sendMessage("Invalid item name or item not found. Replace all white spaces with '_'");
                    return false;
                }

            }
            if (args[0].equals("wallet")){

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        PlayerWalletGet walletGet = new PlayerWalletGet(main);
                        ResultSet resultSet = walletGet.GetByName(sender.getName());
                        try {
                            while (resultSet.next()) {
                                sender.sendMessage("Amount: " + resultSet.getString("amount") + "OldBal: " + resultSet.getString("oldbal") + "NewBal: " + resultSet.getString("newbal") + " Date/Time: " + resultSet.getString("date_time"));

                            }
                        }catch (SQLException e){
                            sender.sendMessage("Error in data get.");
                        }
                        sender.sendMessage("Wallet Fetched.");
                    }
                }.runTaskAsynchronously(this.main);
                return true;
            }
            if (args[0].equals("sales")){

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        PlayerWalletGet walletGet = new PlayerWalletGet(main);
                        ResultSet resultSet = walletGet.GetSales(sender.getName());
                        try {
                            while (resultSet.next()) {
                                sender.sendMessage("Amount: " + resultSet.getString("amount"));
                                sender.sendMessage("Item: " + resultSet.getString("item"));
                                sender.sendMessage("Price: " + resultSet.getString("price"));
                                sender.sendMessage("Client: " + resultSet.getString("player"));
                                sender.sendMessage("Date/Time: " + resultSet.getString("date_time"));
                                sender.sendMessage("---------------------------------------------------");
                            }
                        }catch (SQLException e){
                            sender.sendMessage("Error in data get.");
                        }
                    }
                }.runTaskAsynchronously(this.main);
                return  true;
            }
            if (args[0].equals("purchases")){

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        PlayerWalletGet walletGet = new PlayerWalletGet(main);
                        ResultSet resultSet = walletGet.GetOrders(sender.getName());
                        try {
                            while (resultSet.next()) {
                                sender.sendMessage("Amount: " + resultSet.getString("amount"));
                                sender.sendMessage("Item: " + resultSet.getString("item"));
                                sender.sendMessage("Price: " + resultSet.getString("price"));
                                sender.sendMessage("shop: " + resultSet.getString("shop"));
                                sender.sendMessage("Date/Time: " + resultSet.getString("date_time"));
                                sender.sendMessage("---------------------------------------------------");
                            }
                        }catch (SQLException e){
                            sender.sendMessage("Error in data get.");
                        }
                    }
                }.runTaskAsynchronously(this.main);
                return  true;
            }
            if(args[0].equals("admin") || args[0].equals("Admin") || args[0].equals("ADMIN")){
                if(args[2].equals("purchases")){
                    Player p = this.main.getServer().getPlayer(sender.getName());
                    CustomPlayerPurchaseGUI gui = new CustomPlayerPurchaseGUI(this.main);
                    gui.openInventory(p, args[1]);
                    return true;
                }
                else if(args[2].equals("sales")){
                    Player p = this.main.getServer().getPlayer(sender.getName());
                    CustomPlayerGUI gui = new CustomPlayerGUI(this.main);
                    gui.openInventory(p, args[1]);
                    return true;
                }
            }
            if (args[0].equals("GUI") || args[0].equals("gui")){
                if(args[1].equals("sales")) {
                    Player p = this.main.getServer().getPlayer(sender.getName());
                    CustomPlayerGUI gui = new CustomPlayerGUI(this.main);
                    gui.openInventory(p, p.getName());
                    return true;
                }else if(args[1].equals("purchases")){
                    Player p = this.main.getServer().getPlayer(sender.getName());
                    CustomPlayerPurchaseGUI gui = new CustomPlayerPurchaseGUI(this.main);
                    gui.openInventory(p, p.getName());
                    return true;
                }
                    sender.sendMessage("available sub-commands are 'sales' | 'purchases' ");
                    return false;
            }

            sender.sendMessage(this.prefix + " Unknown subcommand");
            return false;
        } else {
            sender.sendMessage(this.prefix + " Current Sub-Commands:");
            sender.sendMessage(this.prefix + " bal <playername> | Shows a players balance as stored in the database.");
            sender.sendMessage(this.prefix + " Developed for the HelloMiners Minecraft Server by MagnusFrost.");
            return true;
        }

    }
}
