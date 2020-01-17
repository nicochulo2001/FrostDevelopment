package frostdev.frostdev.CompanyDataCommit;
import frostdev.frostdev.HMDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyPermissionsCommit {
    private HMDB main;
    private Connection connection;
    public CompanyPermissionsCommit(HMDB as, java.sql.Connection cc){
        this.main = as;
        this.connection = cc;
    }

    public boolean Exists(String compname) {
        String sql = "SELECT * FROM company_permissions WHERE company = '" + compname + "';";
        ResultSet resultSet;
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            resultSet  = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e){
            return false;
        }

    }
    public void SubmitNewRegionPermission(String player, String compname, String perm, String region){
        String sql;
        try {
            sql = "INSERT INTO company_permissions(compname, member, permission, region) VALUES ('"+ compname + "', '"+ player + "', '" + perm + "', '" + region + "');";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void SubmitNewGeneralPermission(String player, String compname, String perm){
        String sql;
        try {
            sql = "INSERT INTO company_permissions(compname, member, permission) VALUES ('"+ compname + "', '"+ player + "', '" + perm + "');";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void UpdateRegionPermission(String player, String compname, String perm, String region){
        String sql;
        try {
            sql = "UPDATE company_permissions SET permission ='"+ perm + "', region ='"+ region + "' WHERE compname ='"+ compname + "' and member = '" + player + "');";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void UpdateGeneralPermission(String player, String compname, String perm){
        String sql;
        try {
            sql = "UPDATE company_permissions SET permission ='"+ perm +  "' WHERE compname ='"+ compname + "' and member = '" + player + "');";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}