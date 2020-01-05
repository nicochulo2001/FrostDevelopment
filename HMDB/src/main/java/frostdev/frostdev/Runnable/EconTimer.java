package frostdev.frostdev.Runnable;

import frostdev.frostdev.DBCOMMIT.PlayerEconCommit;
import frostdev.frostdev.HMDB;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.util.HashMap;

public class EconTimer implements Runnable {
    private HMDB main;
    private HashMap<String, Double> balance = new HashMap<String, Double>();
    public EconTimer(HMDB as){this.main = as;}

    public void ToHashMap(Player p, String option) {
        Economy econ = this.main.getEconomy();
        if(option.equals("join")){
            balance.put(p.getName(), econ.getBalance(p));
        }
        else if(option.equals("quit")) {
            if(balance.containsKey(p.getName()))
                balance.remove(p.getName());
        }
    }
    public void run() {
        Economy econ = this.main.getEconomy();
            for (Player p : main.getServer().getOnlinePlayers()) {
                if (econ.getBalance(p) != balance.get(p.getName())) {
                    Connection connection = main.GetConnection();
                    PlayerEconCommit ecommit = new PlayerEconCommit();
                    ecommit.EconCommit(p.getUniqueId().toString(), Double.toString(econ.getBalance(p)), connection);
                    balance.remove(p.getName());
                    balance.put(p.getName(), econ.getBalance(p.getName()));
                }
            }

    }
}
