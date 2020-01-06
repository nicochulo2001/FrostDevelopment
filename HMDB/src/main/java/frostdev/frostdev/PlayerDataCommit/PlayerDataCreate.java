package frostdev.frostdev.PlayerDataCommit;

import frostdev.frostdev.Util.TableSetup;
import frostdev.frostdev.HMDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDataCreate {

    private Connection connection;
    private TableSetup tableSetup;
    private ResultSet result;

    public PlayerDataCreate(HMDB as, Connection connection) {

        this.tableSetup = as.tableSetup();
        this.connection = connection;
    }

    public boolean CommitPlayer(String UUID, String name, String econ){
        String sql = "SELECT * FROM userdata WHERE UUID='" + UUID + "';";
        try {
            PreparedStatement update = connection.prepareStatement(sql);
            result = update.executeQuery();
        } catch (SQLException e) {
            try {
                tableSetup.OnTableSetup("userdata", "UUID          varchar(150) null,\n" +
                        "    username      varchar(45)  null,\n" +
                        "    econbal       VARCHAR(255) null,\n" +
                        "    companymember varchar(45)  null", connection);
                PreparedStatement update = connection.prepareStatement(sql);
                result = update.executeQuery();
            } catch (SQLException sub1) {
                return false;
            }
        }
        try {
            if (!result.next()) {
                try {
                    sql = "INSERT INTO userdata(username, UUID, econbal) VALUES ('" + name + "', '" + UUID + "', '" + econ + "' );";
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.executeUpdate();
                    return true;
                } catch (SQLException e) {
                    return false;
                }
            } else {
                sql = "UPDATE userdata SET username ='" + name + "' WHERE UUID = '" + UUID + "';";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
