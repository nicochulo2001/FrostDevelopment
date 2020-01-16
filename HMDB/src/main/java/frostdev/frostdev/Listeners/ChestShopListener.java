package frostdev.frostdev.Listeners;

import com.Acrobot.ChestShop.Events.TransactionEvent;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.PlayerWallet.PlayerWalletCommit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestShopListener implements Listener {
    private PlayerWalletCommit playerwallet;
    private HMDB main;
    public ChestShopListener(HMDB as) { this.main = as; this.playerwallet = as.playerWalletCommit();}

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void ChestShopEvent(TransactionEvent e){
        new BukkitRunnable() {

            public void run() {
                String client = e.getClient().getName();
                String owner = e.getOwnerAccount().getName();
                ItemStack[] item = e.getStock();
                String stock = item[0].getType().name().toLowerCase();
                int amount = item[0].getAmount();
                playerwallet.MassItemCommit(String.valueOf(amount), stock, String.valueOf(e.getExactPrice().doubleValue()), owner, client);
            }
        }.runTaskAsynchronously(this.main);
    }
}
