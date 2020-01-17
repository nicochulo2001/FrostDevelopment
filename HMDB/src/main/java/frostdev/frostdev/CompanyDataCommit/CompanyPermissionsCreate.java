package frostdev.frostdev.CompanyDataCommit;

import frostdev.frostdev.HMDB;
import frostdev.frostdev.Util.TableSetup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyPermissionsCreate {

    private HMDB main;
    private Connection connection;
    public CompanyPermissionsCreate(HMDB as, Connection cc){
        this.main = as;
        this.connection = cc;
    }

    public void PermTableCreate(){
        try {
            TableSetup tableSetup = this.main.tableSetup();
            tableSetup.OnTableSetup("company_permissions", " id         int auto_increment,\n" +
                    "    compname   varchar(255) null,\n" +
                    "    member     varchar(255) null,\n" +
                    "    permission varchar(255) null,\n" +
                    "    region varchar(255) null,\n" +
                    "    constraint id_UNIQUE\n" +
                    "        unique (id)", this.connection );


        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
