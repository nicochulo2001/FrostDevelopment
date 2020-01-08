package frostdev.frostdev.CompanyDataCommit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompanyDataCommit {

    private Connection connection;

    public CompanyDataCommit(Connection connection){
        this.connection = connection;
    }

    public String CompanyNameChange(String name, String UUID){
        String sql;
        try {
            sql = "UPDATE companies SET compname ='" + name + "' " + "WHERE UUID = '"+ UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return "Company by the UUID of " + UUID + " has had it's name changed to " + name + ".";
        } catch (SQLException e){
            return "Failed to update company name due to SQL error.";
        }
    }

    public String CompanyStockPrice(String stock, String UUID){
        String sql;
        try {
            sql = "UPDATE companies SET stock ='" + stock + "' " + "WHERE UUID = '"+ UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return "Company by the UUID of " + UUID + " has had it's stock price changed to " + stock + ".";
        } catch (SQLException e){
            return "Failed to update company stock price due to SQL error.";
        }
    }

    public String CompanySetPublic(String pub, String UUID){
        String sql;
        try {
            sql = "UPDATE companies SET public ='" + pub + "' " + "WHERE UUID = '"+ UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return "Company by the UUID of " + UUID + " has had it's Public status changed to: " + pub + ".";
        } catch (SQLException e){
            return "Failed to update company due to SQL error.";
        }
    }

    public String CompanyEconChange(String econ, String UUID){
        String sql;
        try {
            sql = "UPDATE companies SET econ ='" + econ + "' " + "WHERE UUID = '"+ UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return "Company by the UUID of " + UUID + " has had it's Balance changed to: " + econ + ".";
        } catch (SQLException e){
            return "Failed to update company due to SQL error.";
        }
    }

    public String CompanyDescription(String des, String UUID){
        String sql;
        try {
            sql = "UPDATE companies SET textcompdata ='" + des + "' " + "WHERE UUID = '"+ UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
            return "Company by the UUID of " + UUID + " has had it's Description changed to: " + des + ".";
        } catch (SQLException e){
            return "Failed to update company due to SQL error.";
        }
    }

}
