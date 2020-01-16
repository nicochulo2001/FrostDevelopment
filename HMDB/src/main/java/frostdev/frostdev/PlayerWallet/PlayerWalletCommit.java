package frostdev.frostdev.PlayerWallet;

import frostdev.frostdev.HMDB;
import frostdev.frostdev.Util.TableExists;
import frostdev.frostdev.Util.TableSetup;
import net.milkbowl.vault.economy.Economy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlayerWalletCommit {
    private HMDB main;
    private Connection connection;
    private String table;
    private TableSetup tableSetup;


    public PlayerWalletCommit(HMDB as){
        this.main = as;
        this.connection = this.main.GetConnection();
        this.tableSetup = this.main.tableSetup();
    }
    public void WalletCommit(String UUID, String oldbal, String newbal, String total){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String sql;
        if(!this.main.tableExists("playerwallet")) {
            try {
                tableSetup.OnTableSetup("playerwallet", "transaction int NOT NULL PRIMARY KEY auto_increment,\n" +
                        "UUID        varchar(255) null,\n" +
                        "amount      int null, \n" +
                        "oldbal       varchar(255) null,\n" +
                        "newbal        varchar(255) null,\n" +
                        "date_time   varchar(50) null", connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            sql = "INSERT INTO playerwallet (UUID, amount, oldbal, newbal, date_time) VALUES ('"+ UUID + "', '" + total +  "', '" + oldbal + "', '"+ newbal +"', '" + dtf.format(now) + "');"  ;
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e){
            main.getLogger().info("Failed to commit transaction to Player Wallet!");
            e.printStackTrace();
        }
    }

    public void MassItemCommit(String amount, String stock, String price, String owner, String client){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String sql;
        if(!this.main.tableExists("chest_shop_sales")) {
            try {
                tableSetup.OnTableSetup("chest_shop_sales", "transaction int NOT NULL PRIMARY KEY auto_increment,\n" +
                        "item        varchar(255) null,\n" +
                        "amount      int null, \n" +
                        "price       varchar(255) null,\n" +
                        "shop        varchar(255) null,\n" +
                        "player      varchar(255) null,\n" +
                        "date_time   varchar(50) null", connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            try {
                sql = "INSERT INTO chest_shop_sales (item, amount, price, shop, player, date_time) VALUES ('"+ stock + "', '" + amount +  "', '" + price + "', '"+ owner +"', '" + client + "', '" + dtf.format(now) + "');"  ;
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.executeUpdate();
            } catch (SQLException e){
                main.getLogger().info("Failed to commit sign shop sale!");
                e.printStackTrace();
            }
        }
}
