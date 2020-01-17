package frostdev.frostdev.CompanyDataCommit;

import frostdev.frostdev.HMDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyPermissionsGet {
    private HMDB main;
    private Connection connection;
    public CompanyPermissionsGet(HMDB as, Connection cc){
        this.main = as;
        this.connection = cc;
    }

    public String[] getGeneralPerms(String player, String compname){
        String sql = "SELECT * FROM company_permissions WHERE member='" + player + "';";
        String[] result;

        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            result = new String[results.getFetchSize()];
            int x = 0;
            if(results.next()) {
                while (results.next()) {
                    result[x] = results.getString("permission");
                    x++;
                }
                return result;
            }else{
                result = new String[1];
                result[0] = "No permissions found";
                return result;
            }
        }catch (SQLException e){
            result = new String[1];
            result[0] = "No permissions found";
            return result;
        }

    }

    public boolean HasCompanyPerm(String player, String compname, String perm){
        String sql = "SELECT * FROM company_permissions WHERE member='" + player + "' and compname = '" + compname + "' and permission='" + perm + "';";
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            return false;

        }
    }
}
