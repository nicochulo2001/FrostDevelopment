package frostdev.frostdev.Util;

import frostdev.frostdev.HMDB;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetPlayerData {
    private String result;
    private HMDB main;
    private String pname;
    private String UUID;
    private ResultSet results;
    private String sql;
    private boolean valid = true;
    public GetPlayerData(HMDB as, String player) {
        this.main = as;
        Connection connection;
        connection = as.GetConnection();
        Player e = as.getServer().getPlayer(player);
        if(!as.getServer().getOnlinePlayers().contains(e)){
            this.sql = "SELECT UUID, username, econbal, companymember FROM userdata WHERE username='" + player + "';";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                this.results = stmt.executeQuery(sql);
            }catch (SQLException x){
                this.valid = false;
            }

        }else if (as.getServer().getOnlinePlayers().contains(e)) {
            this.UUID = e.getUniqueId().toString();
            this.pname = e.getDisplayName();
            this.sql = "SELECT UUID, username, econbal, companymember FROM userdata WHERE UUID='" + UUID + "';";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                this.results = stmt.executeQuery(sql);

            } catch (SQLException x) {
                this.valid = false;
            }
        }else {
            this.valid = false;
        }
    }

    public String ReturnPlayerBalance(){
        try {
            while (results.next()) {
                this.result = results.getString("econbal");
                main.getLogger().info("Player: " + pname + " UUID: " + UUID + " Balance: " + this.result);
            }
        }catch (SQLException e){
            this.valid = false;
        }
        return this.result;
    }

    public String ReturnPlayerUUID(){
        try {
            while (results.next()) {
                this.result = results.getString("UUID");
            }
        }catch (SQLException e){
            this.valid = false;
        }
        return this.result;
    }

    public String ReturnPlayerName(){
        try {
            while (results.next()) {
                this.result = results.getString("username");
            }
        }catch (SQLException e){
            this.valid = false;
        }
        return this.result;
    }

    public String ReturnPlayerCompany(){
        try {
            while (results.next()) {
                this.result = results.getString("companymember");
            }
        }catch (SQLException e){
            this.valid = false;
        }
        return this.result;
    }

    public Boolean isValidPlayer(){
        return this.valid;
    }
}
