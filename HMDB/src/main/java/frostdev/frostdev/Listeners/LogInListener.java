package frostdev.frostdev.Listeners;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.PlayerDataCommit.PlayerDataCommit;
import frostdev.frostdev.PlayerDataCommit.PlayerDataCreate;
import frostdev.frostdev.PlayerWallet.PlayerWalletCreate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.scheduler.BukkitRunnable;

public class LogInListener implements Listener {
    private HMDB main;


    public LogInListener(HMDB as) { this.main = as;}

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onLogIn(PlayerLoginEvent e) {
        new BukkitRunnable() {

            @Override
            public void run() {
            PlayerDataCreate user = main.playerDataCreate();
                PlayerDataCommit playerDataCommit = main.playerDataCommit();
            String UUID = e.getPlayer().getUniqueId().toString();
            String name = e.getPlayer().getDisplayName();
            Economy economy = main.getEconomy();
            double econ = economy.getBalance(e.getPlayer());
            String Econ = Double.toString(econ);
        user.CommitPlayer(UUID,name,Econ);
        playerDataCommit.PlayerEconChangeSilent(UUID, econ);

        }
        }.runTaskAsynchronously(this.main);
    }
}
