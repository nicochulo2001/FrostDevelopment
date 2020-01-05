package frostdev.frostdev.CompanyDataCommit;

import frostdev.frostdev.HMDB;
import frostdev.frostdev.Util.GetPlayerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyMembersCommit {
    private String UUID;
    private String econ;
    private Connection connection;
    private ResultSet result;
    private String datatype;
    private String datacontent;
    public CompanyMembersCommit CommitInstance(String UUID, String datacontent, String datatype, Connection connection){
        this.UUID = UUID;
        this.datacontent = datacontent;
        this.datatype = datatype;
        this.connection = connection;
        this.Commit();
        return this;
    }

    private void Commit(){
        String sql;
                try {
                    sql = "INSERT INTO "+ this.UUID + "("+ this.datatype + ") VALUES ('"+ this.datacontent + "' );";
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.executeUpdate();
                } catch (SQLException e){
                    e.printStackTrace();
                }

    }

    public void TitleCommit(String title) {
        String sql;
        try {
            sql = "UPDATE " + this.UUID + " SET title ='" + title + "'" + "WHERE members = '"+ this.datacontent + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void UUIDCommit(String PlayerName, HMDB as){
        GetPlayerData pd = as.getPlayerData(PlayerName);
        String sql;
        String PUUID = pd.ReturnPlayerUUID();
        try {
            sql = "UPDATE " + this.UUID + " SET UUID ='" + PUUID + "'" + "WHERE members = '"+ this.datacontent + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
