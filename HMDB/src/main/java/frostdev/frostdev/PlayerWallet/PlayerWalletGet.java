package frostdev.frostdev.PlayerWallet;

import frostdev.frostdev.HMDB;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerWalletGet {
    private HMDB main;
    private boolean valid;
    private ResultSet results;
    public PlayerWalletGet(HMDB as){
        this.main = as;
    }

    public void GetPlayer(String data){
       Player e = main.getServer().getPlayer(data);
        Connection connection = this.main.GetConnection();
        String sql;
        if(!main.getServer().getOnlinePlayers().contains(e)){
            String uuid = e.getUniqueId().toString();
            sql = "SELECT UUID, amount, oldbal, newbal, date_time FROM playerwallet WHERE UUID='" + uuid + "';";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                results = stmt.executeQuery(sql);
            }catch (SQLException x){
                valid = false;
            }

        }else if (this.main.getServer().getOnlinePlayers().contains(e)) {
            String uuid = this.main.getPlayerData().ReturnPlayerUUID(data);
            sql = "SELECT UUID, amount, oldbal, newbal, date_time FROM playerwallet WHERE UUID='" + uuid + "';";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                results = stmt.executeQuery(sql);

            } catch (SQLException x) {
                this.valid = false;
            }
        }else {
            this.valid = false;
        }
    }

    public ResultSet GetSales(String data){
        Connection connection = this.main.GetConnection();
        String sql;
        ResultSet results;
            sql = "SELECT * FROM chest_shop_sales WHERE shop='" + data + "';";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                results = stmt.executeQuery(sql);
            }catch (SQLException x){
                return results = null;
            }
            return results;
    }
    public ResultSet GetOrders(String data){
        Connection connection = this.main.GetConnection();
        String sql;
        sql = "SELECT * FROM chest_shop_sales WHERE player='" + data + "';";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            results = stmt.executeQuery(sql);
        }catch (SQLException x){
            x.printStackTrace();
            return results = null;
        }
        return results;
    }

    public ResultSet GetByName(String name){
        this.GetPlayer(name);
        return this.results;
    }
}
