package frostdev.frostdev.DBCOMMIT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

 public class TableSetup {
    public void OnTableSetup(String table, String dataname, Connection connection)throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS " + table +"(" + dataname + ");";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
    }
}