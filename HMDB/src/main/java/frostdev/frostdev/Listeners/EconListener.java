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
        String pname = e.getPlayer().getName();
        String puuid = this.main.getPlayerData().ReturnPlayerUUID(pname);
        commit.PlayerEconChange(puuid, e.getNewBalance().doubleValue());
        wallet.PlayerWalletChange(pname, puuid, this.dest, e.getOldBalance().toString(), e.getNewBalance().toString());
    }

}