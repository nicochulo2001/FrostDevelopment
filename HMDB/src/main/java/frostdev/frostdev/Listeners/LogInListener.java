package frostdev.frostdev.Listeners;

import frostdev.frostdev.DBCOMMIT.UserDataCommit;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.Util.GetPlayerData;
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
        UserDataCommit user = new UserDataCommit();
        String UUID = e.getPlayer().getUniqueId().toString();
        String name = e.getPlayer().getDisplayName();
        Economy economy = this.main.getEconomy();
        double econ = economy.getBalance(e.getPlayer());
        String Econ = Double.toString(econ);
        user.UserData(name, UUID, Econ, this.main.GetConnection());
        GetPlayerData getPlayerData = new GetPlayerData(main, e.getPlayer().getName());
        main.getLogger().info("Player: " + e.getPlayer().getDisplayName() + " UUID: " + UUID + " Balance: " + getPlayerData.ReturnPlayerBalance());
    }
}
