package frostdev.companies;

import frostdev.companies.DBHandler.CompaniesCreateCompany;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.PlayerDataCommit.PlayerDataGet;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Command implements CommandExecutor {
    private HMDB main;
    private Companies comp;
    private String prefix;
    private String noperm;

    public Command(Companies as) {
        this.comp = as;
        this.main = as.getDatabase();
        this.prefix = ChatColor.translateAlternateColorCodes('&', "&9[HMDB Manager]");
        this.noperm = "&cYou have no permission to preform this action.";


    }
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length >= 1) {
            if (args[0].equals("create")) {
                String des = args[4] + " ";
                CompaniesCreateCompany company = this.comp.CompanyCreateInstance();
                if (args[4].contains("<")) {
                    for (int c = 5; c < args.length; c++) {
                        des = des.concat(args[c]) + " ";
                    }
                }
                sender.sendMessage(company.Create(sender.getName(), args[1], args[2], args[3], des, this.comp));
                return true;
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
