package frostdev.frostdev.Listeners;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.PlayerDataCommit.PlayerDataCommit;
import frostdev.frostdev.PlayerWallet.PlayerWalletCommit;
import net.ess3.api.events.UserBalanceUpdateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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
        PlayerDataCommit commit = main.playerDataCommit();
        PlayerWalletCommit wallet = main.playerWalletCommit();
        commit.PlayerEconChange(e.getPlayer().getUniqueId().toString(), e.getNewBalance().doubleValue());
        wallet.PlayerWalletChange(e.getPlayer().getName(), e.getPlayer().getUniqueId().toString(), this.dest, e.getOldBalance().toEngineeringString(), e.getNewBalance().toEngineeringString());
    }
    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void EconResp(AsyncPlayerChatEvent e){
        String paymessage = e.getMessage();
            if(paymessage.startsWith("/pay")){
                this.dest = paymessage.replace("/pay ", "").replaceAll("\\d","").replaceAll(" ", "");

            }

    }
}