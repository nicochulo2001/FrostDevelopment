package frostdev.frostdev.EconBot;

import frostdev.frostdev.HMDB;
import frostdev.frostdev.Util.TableSetup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ItemAverageCommit {
    private HMDB main;
    private Connection connection;

    public ItemAverageCommit(HMDB as, Connection cn){
        this.main = as;
        this.connection = cn;
    }

    public void CommitAvg(String item, String avg){
        String sql;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        if(!this.main.tableExists("item_avg")) {
            try {
                TableSetup tableSetup = this.main.tableSetup();
                tableSetup.OnTableSetup("item_avg", "item VARCHAR(255), avg VARCHAR(255), date_time VARCHAR(255)", this.connection);
            }catch (SQLException e){
                main.getLogger().info("Failed to create item average table!");
            }
        }else{
            try {
                sql = "INSERT INTO item_avg (item, avg, date_time) VALUES ('" + item + "', '" + avg + "', '" + dtf.format(now) + "');";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.executeUpdate();
            } catch (SQLException e) {
                main.getLogger().info("Failed to commit item average!");
                e.printStackTrace();
            }

        }
    }
}
