package frostdev.frostdev.Util;

import frostdev.frostdev.HMDB;
import frostdev.frostdev.PlayerDataCommit.PlayerDataGet;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHMDB implements CommandExecutor {
    private HMDB main;
    private String prefix;
    private String noperm;

    public CommandHMDB(HMDB as) {
        this.main = as;
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
                    PlayerDataGet playerDataGet = new PlayerDataGet(main, args[1]);
                    if(playerDataGet.isValidPlayer()) {
                        sender.sendMessage(this.prefix + " Player " + args[1] + " has a balance of $" + playerDataGet.ReturnPlayerBalance());
                        return true;
                    }else{
                        sender.sendMessage("Player not found!");
                    }
                }
            } if (args[0].equals("company")) {
                    PlayerDataGet playerDataGet = new PlayerDataGet(main, args[1]);
                    if (playerDataGet.isValidPlayer()) {
                        sender.sendMessage(this.prefix + " Player " + args[1] + " is a member of " + playerDataGet.ReturnPlayerCompany());
                        return true;
                    } else {
                        sender.sendMessage("Player not found!");
                    }
            } if (args[0].equals("uuid")) {
                if (args[1].isEmpty()) {
                    sender.sendMessage(this.prefix + " No Player name defined.");
                    return false;
                } else {
                    PlayerDataGet playerDataGet = new PlayerDataGet(main, args[1]);
                    if (playerDataGet.isValidPlayer()) {
                        sender.sendMessage(this.prefix + " Player " + args[1] + "'s UUID is  " + playerDataGet.ReturnPlayerUUID());
                        return true;
                    } else {
                        sender.sendMessage("Player not found!");
                    }
                }
            } if (args[0].equals("populate")){
                GetItems populate = new GetItems();
                populate.Populate(this.main);
                sender.sendMessage("Success.");
            } if (args[0].equals("create")) {
                    boolean res = main.CreateCompany(args[1],args[2],args[3],args[4],args[5],args[6]);
                    if (res){
                        sender.sendMessage("Success");
                        return true;
                    } else {
                        sender.sendMessage("Fail");
                        return false;
                    }
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
