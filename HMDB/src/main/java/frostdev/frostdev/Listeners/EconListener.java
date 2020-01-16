package frostdev.frostdev.Listeners;
import com.earth2me.essentials.commands.Commandpay;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.PlayerDataCommit.PlayerDataCommit;
import frostdev.frostdev.PlayerWallet.PlayerWalletCommit;
import net.ess3.api.events.UserBalanceUpdateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;

public class EconListener implements Listener {
    private HMDB main;
    private String dest;
    private PlayerWalletCommit playerwallet;

    public EconListener(HMDB as) { this.main = as; this.playerwallet = as.playerWalletCommit();}

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void EconomyUpdateEvent(UserBalanceUpdateEvent e){
        new BukkitRunnable() {

            @Override
                    public void run() {
                    PlayerDataCommit commit = main.playerDataCommit();
                    PlayerWalletCommit wallet = main.playerWalletCommit();
                    String puuid = e.getPlayer().getUniqueId().toString();
                    double oldbal = e.getOldBalance().doubleValue();
                    double newbal = e.getNewBalance().doubleValue();
                    double amount = newbal - oldbal;
                    commit.PlayerEconChange(puuid, newbal);
                    wallet.WalletCommit(puuid, String.valueOf(oldbal), String.valueOf(newbal), String.valueOf(amount));
            }
        }.runTaskAsynchronously(main);
    }

}