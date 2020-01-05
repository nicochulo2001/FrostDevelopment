package frostdev.frostdev.Util;

import frostdev.frostdev.HMDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableExists {
    public boolean TableExists(String table, Connection connection){
        try {
            String sql = "SHOW TABLES LIKE '%" + table + "%';";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultset = statement.executeQuery();
            if (resultset.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
