package frostdev.frostdev.CompanyDataCommit;

import frostdev.frostdev.Util.TableSetup;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.Util.TableExists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyCreate {
    private HMDB main;
    private TableSetup tableSetup;
    private ResultSet result;
    private Connection connection;
    private String user;
    private String UUID;
    private String econ;
    private String stock;
    private String compname;
    private String textcompdata;
    private CompanyMembersCreate companyMembersCreate;
    private CompanyMembersCommit companyMembersCommit;

    public boolean CompanyCreate(String user, String UUID, String econ, String stock, String compname, String textcompdata, HMDB as) {
        this.main = as;
        this.connection = main.GetConnection();
        this.tableSetup = main.tableSetup();
        this.user = user;
        this.UUID = UUID;
        this.econ = econ;
        this.stock = stock;
        this.compname = compname;
        this.textcompdata = textcompdata;
        this.companyMembersCreate = main.companyMembersCreate();
        try {
            Commit();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public void Commit() throws SQLException {

        boolean pub = false;
        String exist = "SELECT * FROM companies WHERE uuid='" + UUID + "';";
        String sql;
        TableExists test = main.tableExists();

        boolean bool = test.TableExists("companies", main.GetConnection());
        if (!bool) {
            tableSetup.OnTableSetup("companies", "compname VARCHAR(255), UUID VARCHAR(255), econbal VARCHAR(255), founder VARCHAR(255), stock VARCHAR(255), textcompdata BLOB, public VARCHAR(10)", this.connection);
            main.getLogger().info("Companies Table Generated.");
        }

        try {
            this.main.getLogger().info("PASS 1");
            PreparedStatement update = connection.prepareStatement(exist);
            result = update.executeQuery();

        } catch (SQLException e) {
            this.main.getLogger().info("PASS 1 FAIL");
            e.printStackTrace();
        }
        if (!result.next()) {
            try {
                sql = "INSERT INTO companies(compname, UUID, econbal, founder, stock, textcompdata, public ) VALUES ('" + compname + "', '" + UUID + "', '" + econ + "', '" + user + "', '" + stock + "', '" + textcompdata + "', '" + pub + "');";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.executeUpdate();
                this.companyMembersCreate.CompanyMembersCreate(this.UUID, this.user, main);

                this.main.getLogger().info("PASS 1 SUCCESS");
            } catch (SQLException e) {
                main.getLogger().info("PASS 2 FAIL");
            }
        } else {
            sql = "UPDATE companies SET compname ='" + compname + "', econbal = '" + econ + "'" + "WHERE UUID = '" + UUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        }

    }


}
