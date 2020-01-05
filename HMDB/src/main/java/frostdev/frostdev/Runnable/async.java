package frostdev.frostdev.Runnable;

import frostdev.frostdev.DBConnect.MySQLConnect;
import frostdev.frostdev.HMDB;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.sql.Statement;

public class async {
        BukkitRunnable r = new BukkitRunnable() {
            HMDB as = new HMDB();
            @Override
            public void run() {
                try {
                    MySQLConnect connect = new MySQLConnect();
                    connect.OnConnect(as);
                    Statement statement = connect.GetConnection().createStatement();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        };


}