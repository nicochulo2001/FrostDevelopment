package frostdev.frostdev.GUI;

import com.avaje.ebean.validation.NotNull;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.PlayerWallet.PlayerWalletGet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CustomPlayerGUI implements InventoryHolder, Listener {
    // Create a new inventory, with "this" owner for comparison with other inventories, a size of nine, called example
    private final Inventory inv;
    private final Inventory inv2;
    private final Inventory inv3;
    private HMDB main;
    private ResultSet resultSet;
    private int trans;
    int count;
    public CustomPlayerGUI(HMDB as) {
        this.main = as;
        inv = Bukkit.createInventory(this, 54, "WalletHistory - 1");
        inv2 = Bukkit.createInventory(this, 54, "WalletHistory - 2");
        inv3 = Bukkit.createInventory(this, 54, "WalletHistory - 3");
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inv;
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        try {
            resultSet.afterLast();
        while (this.resultSet.previous()) {
            if(inv.firstEmpty() == -1){
                inv2.addItem(this.transconst(Material.PAPER));
                this.trans = this.resultSet.getInt("transaction");
            }
            if(inv2.firstEmpty() == -1) {
                inv3.addItem(this.transconst(Material.PAPER));
                this.trans = this.resultSet.getInt("transaction");
            }
            inv.addItem(this.transconst(Material.PAPER));
            this.trans = this.resultSet.getInt("transaction");


        }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Nice little method to create a gui item with a custom name, and description

    // You can open the inventory with this
    public void openInventory(Player p, String player) {
        this.resultSet = this.main.playerWalletGet().GetSales(player);
        p.openInventory(inv);
        this.initializeItems();
    }


    private ItemStack transconst(Material mat){
        try {
            ItemStack ret = new ItemStack(mat);
            ItemMeta meta = ret.getItemMeta();
            List<String> lore = new ArrayList<>();
            meta.setDisplayName(this.resultSet.getString("date_time"));
            lore.add("Item: " + resultSet.getString("item"));
            lore.add("Amount: " + resultSet.getString("amount"));
            lore.add("Price: " + resultSet.getString("price"));
            lore.add("Client: " + resultSet.getString("player"));
            lore.add("ID#: " + resultSet.getString("transaction"));
            meta.setLore(lore);
            ret.setItemMeta(meta);
            return ret;
        }catch (SQLException e){

            return null;
        }
    }
    private ItemStack createGuiItem(Material material, String name, String...lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metalore = new ArrayList<String>();

        for(String lorecomments : lore) {

            metalore.add(lorecomments);

        }

        meta.setLore(metalore);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack NextPageItem(){
        ItemStack item = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Next Page");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack PreviousPageItem(){
        ItemStack item = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Previous Page");
        item.setItemMeta(meta);
        return item;
    }


    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack air = new ItemStack(Material.AIR);

        if (e.getInventory().getHolder() == this) {

            if (e.getClick().equals(ClickType.NUMBER_KEY)) {
                e.setCancelled(true);
            }
            if (e.getInventory().getTitle().endsWith("1") && e.getRawSlot() == 53) {
                e.getWhoClicked().openInventory(inv2);
                e.setCurrentItem(air);
                e.setCancelled(true);
                return;
            }
            if (e.getInventory().getTitle().endsWith("2") && e.getRawSlot() == 53) {
                e.getWhoClicked().openInventory(inv3);
                e.setCurrentItem(air);
                e.setCancelled(true);
                return;
            }
            if (e.getInventory().getTitle().endsWith("3") && e.getRawSlot() == 45) {
                e.getWhoClicked().openInventory(inv2);
                e.setCurrentItem(air);
                e.setCancelled(true);
                return;
            }
            if (e.getInventory().getTitle().endsWith("2") && e.getRawSlot() == 45) {
                e.getWhoClicked().openInventory(inv);
                e.setCurrentItem(air);
                e.setCancelled(true);
                return;
            }

            if (e.getCurrentItem() == null || e.getCurrentItem() != null) {
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);

            Player p = (Player) e.getWhoClicked();
            ItemStack clickedItem = e.getCurrentItem();

            // verify current item is not null

            // Using slots click is a best option for your inventory click's
            if (e.getRawSlot() == 10) p.sendMessage("You clicked at slot " + 10);
        }
    }
}