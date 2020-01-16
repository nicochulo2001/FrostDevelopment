package frostdev.frostdev.PlayerWallet;

import frostdev.frostdev.HMDB;
import frostdev.frostdev.Util.TableSetup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerWalletCreate {
    private Connection connection;
    private TableSetup tableSetup;
    private ResultSet result;
    private HMDB main;
    public PlayerWalletCreate(HMDB as, Connection connection) {
        this.main = as;
        this.tableSetup = as.tableSetup();
        this.connection = connection;
    }

    public boolean CheckBookCreate(String table) {
        if (!this.main.tableExists(table)) {
            try {
                tableSetup.OnTableSetup(table, "transaction int NOT NULL PRIMARY KEY auto_increment,\n" +
                        "amount        double null,\n" +
                        "price         double null, \n" +
                        "reason        varchar(255)  null,\n" +
                        "from          varchar(255)  null,\n" +
                        "to            varchar(255)  null,\n" +
                        "item          varchar(255) null,\n" +
                        "balance       varchar(255) null,\n" +
                        "date_time   varchar(50) null", connection);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return true;
        }
    }
}
