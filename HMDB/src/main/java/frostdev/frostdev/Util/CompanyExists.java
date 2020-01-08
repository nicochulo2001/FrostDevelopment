package frostdev.frostdev.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyExists {
    private Connection connection;
    public CompanyExists(Connection connection){
        this.connection = connection;
    }

    public boolean QueryCompanyName(String comp) {
        PreparedStatement statement;
        ResultSet results;
        try {
            statement = connection.prepareStatement("SELECT * FROM companies WHERE compname = " + comp);
            results = statement.executeQuery();
            return results.next();
        } catch (SQLException e){
            return false;
        }
    }

    public boolean QueryCompanyUUID(String UUID) {
        PreparedStatement statement;
        ResultSet results;
        try {
            statement = connection.prepareStatement("SELECT * FROM companies WHERE UUID = " + UUID);
            results = statement.executeQuery();
            return results.next();
        } catch (SQLException e){
            return false;
        }
    }
}
