package frostdev.frostdev.Listeners;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.PlayerDataCommit.PlayerDataCreate;
import frostdev.frostdev.PlayerWallet.PlayerWalletCreate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import net.milkbowl.vault.economy.Economy;

public class LogInListener implements Listener {
    private HMDB main;


    public LogInListener(HMDB as) { this.main = as;}

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onLogIn(PlayerLoginEvent e) {
        PlayerDataCreate user = main.playerDataCreate();
        String UUID = e.getPlayer().getUniqueId().toString();
        String name = e.getPlayer().getDisplayName();
        Economy economy = this.main.getEconomy();
        double econ = economy.getBalance(e.getPlayer());
        String Econ = Double.toString(econ);
        user.CommitPlayer(UUID, name, Econ);
    }
}
