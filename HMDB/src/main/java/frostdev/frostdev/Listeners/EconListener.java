package frostdev.frostdev.Listeners;
import frostdev.frostdev.DBCOMMIT.PlayerEconCommit;
import frostdev.frostdev.HMDB;
import net.ess3.api.events.UserBalanceUpdateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class EconListener implements Listener {
    private HMDB main;
    public EconListener(HMDB as) { this.main = as;}

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void EconomyUpdateEvent(UserBalanceUpdateEvent e){
        PlayerEconCommit commit = new PlayerEconCommit();
        commit.EconCommit(e.getPlayer().getUniqueId().toString(), e.getNewBalance().toString(), main.GetConnection());
    }
}