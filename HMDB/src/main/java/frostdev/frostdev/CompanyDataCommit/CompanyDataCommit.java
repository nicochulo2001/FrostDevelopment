package frostdev.frostdev.CompanyDataCommit;

import frostdev.frostdev.HMDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDataCommit {

    private HMDB main;
    private ResultSet result;
    private CompanyDataGet companyDataGet;
    private String UUID;
    private Connection connection;

    public CompanyDataCommit(HMDB as, String UUID){
        this.main = as;
        this.connection = main.GetConnection();
        this.UUID = UUID;
        this.companyDataGet = main.companyDataGet();
        String sql = "SELECT * FROM companies WHERE UUID ='" + UUID + "';";

        try {
            PreparedStatement update = connection.prepareStatement(sql);
            result = update.executeQuery();
        } catch (SQLException e) {
             return;
        }
    }

    public String CompanyNameChange(String name){
        String sql;
        try {
            sql = "UPDATE companies SET compname ='" + name + "' " + "WHERE UUID = '"+ this.UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return "Company by the UUID of " + this.UUID + " has had it's name changed to " + name + ".";
        } catch (SQLException e){
            return "Failed to update company name due to SQL error.";
        }
    }

    public String CompanyStockPrice(String stock){
        String sql;
        try {
            sql = "UPDATE companies SET stock ='" + stock + "' " + "WHERE UUID = '"+ this.UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return "Company by the UUID of " + this.UUID + " has had it's stock price changed to " + stock + ".";
        } catch (SQLException e){
            return "Failed to update company stock price due to SQL error.";
        }
    }

    public String CompanySetPublic(String pub){
        String sql;
        try {
            sql = "UPDATE companies SET public ='" + pub + "' " + "WHERE UUID = '"+ this.UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return "Company by the UUID of " + this.UUID + " has had it's Public status changed to: " + pub + ".";
        } catch (SQLException e){
            return "Failed to update company due to SQL error.";
        }
    }

    public String CompanyEconChange(String econ){
        String sql;
        try {
            sql = "UPDATE companies SET econ ='" + econ + "' " + "WHERE UUID = '"+ this.UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return "Company by the UUID of " + this.UUID + " has had it's Balance changed to: " + econ + ".";
        } catch (SQLException e){
            return "Failed to update company due to SQL error.";
        }
    }

    public String CompanyDescription(String des){
        String sql;
        try {
            sql = "UPDATE companies SET textcompdata ='" + des + "' " + "WHERE UUID = '"+ this.UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return "Company by the UUID of " + this.UUID + " has had it's Description changed to: " + des + ".";
        } catch (SQLException e){
            return "Failed to update company due to SQL error.";
        }
    }

}
