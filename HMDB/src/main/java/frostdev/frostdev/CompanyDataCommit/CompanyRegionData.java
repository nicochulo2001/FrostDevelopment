package frostdev.frostdev.CompanyDataCommit;

import frostdev.frostdev.HMDB;
import frostdev.frostdev.Util.TableSetup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRegionData {

    private HMDB main;
    private Connection connection;

    public CompanyRegionData(HMDB as, Connection cc){
        this.main = as;
        this.connection = cc;
    }

    public void CreateCompanyRegionTable(){
        try {
            TableSetup tableSetup = this.main.tableSetup();
            tableSetup.OnTableSetup("company_regions", " id         int auto_increment,\n" +
                    "    compname   varchar(255) null,\n" +
                    "    region varchar(255) null,\n" +
                    "    constraint id_UNIQUE\n" +
                    "        unique (id)", this.connection );


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void SubmitCompanyRegion(String compname, String region){
        String sql;
        try {
            sql = "INSERT INTO company_regions(compname, region) VALUES ('"+ compname + "', '" + region + "');";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void UpdateCompanyRegion(String newcompname, String oldcompname, String region){
        String sql;
        try {
            sql = "UPDATE company_regions SET compname ='"+ newcompname + "', region ='"+ region + "' WHERE compname ='"+ oldcompname + "' and region = '" + region + "');";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean IsComnpanyRegion(String region){
        String sql;
        try {
            sql = "SELECT * FROM company_regions WHERE region='" + region + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet results = stm.executeQuery();
            if (results.next()){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
