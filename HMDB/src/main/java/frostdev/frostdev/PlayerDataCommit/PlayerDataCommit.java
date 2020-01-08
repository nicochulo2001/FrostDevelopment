package frostdev.frostdev.PlayerDataCommit;

import frostdev.frostdev.CompanyDataCommit.CompanyDataGet;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.Util.CompanyExists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerDataCommit {
    private Connection connection;


    public PlayerDataCommit(Connection connection){
        this.connection = connection;
    }

    public String PlayerNameChange(String UUID, String name){
        String sql;
        try {
            sql = "UPDATE userdata SET username ='" + name + "' " + "WHERE UUID = '"+ UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return "Player by the UUID of " + UUID + " has had it's name changed to " + name + ".";
        } catch (SQLException e){
            return "Failed to update player name due to SQL error.";
        }
    }

    public String PlayerEconChange(String UUID, double data) {
        String sql;
        try {
            sql = "UPDATE userdata SET econbal ='" + data + "' " + "WHERE UUID = '"+ UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return "Player by the UUID of " + UUID + " has had it's balance changed to " + data + ".";
        } catch (SQLException e){
            return "Failed to update player balance due to SQL error.";
        }
    }

    public boolean PlayerEconChangeSilent(String UUID, double data) {
        String sql;
        try {
            sql = "UPDATE userdata SET econbal ='" + data + "' " + "WHERE UUID = '"+ UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    public boolean PlayerCompanyJoin(String UUID, String company) {
        CompanyExists comp = new CompanyExists(connection);
        if(comp.QueryCompanyName(company)) {
            String sql;
            try {
                sql = "UPDATE userdata SET companymember ='" + company + "' " + "WHERE UUID = '" + UUID + "';";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.executeUpdate();
                return true;
            } catch (SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
