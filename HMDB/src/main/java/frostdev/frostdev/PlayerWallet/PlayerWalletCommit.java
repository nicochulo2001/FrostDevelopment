package frostdev.frostdev.PlayerWallet;

import frostdev.frostdev.HMDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlayerWalletCommit {
    private HMDB main;
    private Connection connection;


    public PlayerWalletCommit(HMDB as){
        this.main = as;
        this.connection = this.main.GetConnection();
    }

    public void PlayerWalletChange(String name, String UUID, String dest, String oldbal, String newbal){
        String sql;
        double old, cur;
        old = Double.valueOf(oldbal);
        cur = Double.valueOf(newbal);
        String table = "wallet_" + name + "_" + UUID.replaceAll("-", "");
        String player;
        double amount;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        amount = cur - old;
        player = main.getPlayerData().ReturnPlayerName(UUID);

        try {
            sql = "INSERT INTO " + table +" (amount, destination, balance, date_time) VALUES ('"+  amount + "', '" + dest + "', '" + newbal + "', '" + dtf.format(now) + "');"  ;
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e){
                if(!main.playerWalletCreate().CheckBookCreate(table)){
                    main.getServer().getPlayer(player).sendMessage("Unable to create Player Wallet. Contact an admin!");
                }
                main.getServer().getPlayer(player).sendMessage("Failed to update wallet.");
                e.printStackTrace();
        }
    }
}
