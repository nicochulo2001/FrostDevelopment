package frostdev.frostdev.PlayerDataCommit;

import frostdev.frostdev.HMDB;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDataGet {
    private String result;
    private HMDB main;
    private String pname;
    private String UUID;
    private ResultSet results;
    private String sql;
    private boolean valid = true;
    private Player e;
    private Connection connection;
    public PlayerDataGet(HMDB as) {
        this.main = as;
        this.connection = as.GetConnection();


    }
    private void GetPlayerDataName(String name){
        e = main.getServer().getPlayer(name);
        if(!main.getServer().getOnlinePlayers().contains(e)){
            this.sql = "SELECT UUID, username, econbal, companymember FROM userdata WHERE username='" + name + "';";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                this.results = stmt.executeQuery(sql);
            }catch (SQLException x){
                this.valid = false;
            }

        }else if (this.main.getServer().getOnlinePlayers().contains(e)) {
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

    public String ReturnPlayerBalance(String n){
        this.GetPlayerDataName(n);
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

    public String ReturnPlayerUUID(String n){
        this.GetPlayerDataName(n);
        try {
            while (results.next()) {
                this.result = results.getString("UUID");
            }
        }catch (SQLException e){
            this.valid = false;
        }
        return this.result;
    }

    public String ReturnPlayerName(String UUID){
        this.sql = "SELECT UUID, username, econbal, companymember FROM userdata WHERE UUID='" + UUID + "';";
        try {
            PreparedStatement statement = this.connection.prepareStatement(this.sql);
            this.results = statement.executeQuery();
            while (results.next()) {
                this.result = results.getString("username");
            }
        }catch (SQLException e){
            this.valid = false;
        }
        return this.result;
    }

    public String ReturnPlayerCompany(String n){
        this.GetPlayerDataName(n);
        try {
            while (results.next()) {
                this.result = results.getString("companymember");
            }
        }catch (SQLException e){
            this.valid = false;
        }
        return this.result;
    }

    public Boolean isValidPlayer(String n){
        this.sql = "SELECT username FROM userdata WHERE username='" + n + "';";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            this.results = stmt.executeQuery(sql);
            if(this.results.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException x){
            return false;
        }

    }
}
