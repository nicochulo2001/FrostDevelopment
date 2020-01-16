package frostdev.frostdev.Util;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.PlayerDataCommit.PlayerDataGet;
import frostdev.frostdev.PlayerWallet.PlayerWalletCommit;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Timer;
import java.util.UUID;

public class CommandPay implements CommandExecutor {
    private HMDB main;
    private String prefix;
    private String noperm;
    private PlayerDataGet playerDataGet;

    public CommandPay(HMDB as) {
        this.main = as;
        this.playerDataGet = as.getPlayerData();
        this.prefix = ChatColor.translateAlternateColorCodes('&', "&6&L[HMDB Manager] &F");
        this.noperm = "&cYou have no permission to preform this action.";


    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 1) {
            if(args[0].equals("?")){
                sender.sendMessage(this.prefix + " Developed for the HelloMiners Minecraft Server by MagnusFrost.");
                sender.sendMessage(this.prefix + " Developed for the HelloMiners Minecraft Server by MagnusFrost.");
            }
            sender.sendMessage("syntax: /pay <player> <amount>");
            return true;
        }

        if(args.length == 2){
            double amount;
            try {
                 amount = Double.valueOf(args[1]);
            } catch (NumberFormatException e){
                sender.sendMessage("Expected Number value after player name.");
                return false;
            }
            String pname = args[0];
            if(this.main.getPlayerData().isValidPlayer(pname)){
                String puuid = this.main.getPlayerData().ReturnPlayerUUID(sender.getName());
                Economy economy = this.main.getEconomy();
                OfflinePlayer send = this.main.getServer().getOfflinePlayer(UUID.fromString(puuid));
                OfflinePlayer rec = this.main.getServer().getOfflinePlayer(UUID.fromString(this.playerDataGet.ReturnPlayerUUID(pname)));
                if(send == null || rec == null){
                    sender.sendMessage("Player not found.");
                    return true;
                }
                if(!economy.hasAccount(send) && !economy.hasAccount(rec)){
                    sender.sendMessage("Somehow this player has been on the server but does not have a vault account. I have no idea if this error will ever appear, but if it does let me know. -Magnus");
                    return true;
                }
                if(economy.getBalance(sender.getName()) < Double.valueOf(args[1])){
                    sender.sendMessage("Insufficient funds to complete transfer.");
                    return true;
                }
                if(economy.withdrawPlayer(this.main.getServer().getOfflinePlayer(UUID.fromString(this.playerDataGet.ReturnPlayerUUID(sender.getName()))), "Payment to " + pname, amount)
                        .transactionSuccess()){
                    if(economy.depositPlayer(this.main.getServer().getOfflinePlayer(UUID.fromString(this.playerDataGet.ReturnPlayerUUID(pname))), "Payment from " + sender.getName(), amount)
                            .transactionSuccess()){
                        double senderbal =Double.valueOf(this.playerDataGet.ReturnPlayerBalance(sender.getName()));
                        double  reciept = Double.valueOf(this.playerDataGet.ReturnPlayerBalance(pname));
                        this.main.playerDataCommit().PlayerEconChangeSilent(this.playerDataGet.ReturnPlayerUUID(sender.getName()), senderbal - amount);
                        this.main.playerDataCommit().PlayerEconChangeSilent(this.playerDataGet.ReturnPlayerUUID(pname), reciept + amount);
                        PlayerWalletCommit walletCommit = this.main.playerWalletCommit();
                        walletCommit.MassItemCommit(String.valueOf(amount), "Payment", String.valueOf(amount), pname, sender.getName());
                        sender.sendMessage("Transaction Success.");
                        try {
                            Player player = this.main.getServer().getPlayer(UUID.fromString(this.playerDataGet.ReturnPlayerUUID(pname)));
                            player.sendMessage(prefix + sender.getName() + " has sent you a payment of " + amount + "F.");
                        }catch (NullPointerException e){
                            sender.sendMessage("Recipient is offline but the payment still went through. Magical.");
                        }

                        sender.sendMessage(prefix + "You sent a payment of " + amount + "F to " + pname);
                        return true;
                    }
                    sender.sendMessage("Failed to deposit into Offline Player Account.");
                    return false;
                }
                sender.sendMessage("Failed to Withdraw from your account.");
                return false;
            }
            else{
                sender.sendMessage("Player not found to have played before on server. Perhaps they have not logged in since the new database?");
                return true;
            }
        }

        else {
            sender.sendMessage("syntax: /pay <player> <amount>");
            return true;
        }

    }
}
