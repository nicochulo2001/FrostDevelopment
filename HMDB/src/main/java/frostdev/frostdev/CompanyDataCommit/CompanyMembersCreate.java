package frostdev.frostdev.CompanyDataCommit;

import frostdev.frostdev.Util.TableSetup;
import frostdev.frostdev.HMDB;

import java.sql.Connection;
import java.sql.SQLException;

public class CompanyMembersCreate {
    private HMDB main;
    private TableSetup tableSetup;
    private String table;
    private String founder;
    private Connection connection;
    public void CompanyMembersCreate(String companyUUID, String playerfounder, HMDB as) throws SQLException {
        this.main = as;
        this.connection = main.GetConnection();
        this.tableSetup = main.tableSetup();
        this.table = companyUUID;
        this.founder = playerfounder;
        this.Create();

    }

    private void Create(){
        this.table = "members_" + this.table;
        try {
            this.tableSetup.OnTableSetup(this.table, "members VARCHAR(255), " +  "title VARCHAR(255), " + "UUID VARCHAR(255)", this.connection);
            main.companyMembersCommit(this.table, this.founder, "members");
            main.MemberTitleCommit("Founder");
            main.MemberUUIDCommit(this.founder);
        } catch (SQLException e){
            e.printStackTrace();
        }
        }
}
