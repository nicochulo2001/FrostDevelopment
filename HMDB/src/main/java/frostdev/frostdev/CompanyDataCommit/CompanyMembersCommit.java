package frostdev.frostdev.CompanyDataCommit;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.PlayerDataCommit.PlayerDataGet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompanyMembersCommit {
    private String UUID;
    private Connection connection;
    private String player;
    private String datacontent;
    public CompanyMembersCommit CommitInstance(String UUID, String datacontent, String player, Connection connection){
        this.UUID = UUID;
        this.datacontent = datacontent;
        this.player = player;
        this.connection = connection;
        this.MemberCommit();
        return this;
    }

    private void MemberCommit(){
        String sql;
                try {
                    sql = "INSERT INTO "+ this.UUID + "("+ this.player + ") VALUES ('"+ this.datacontent + "' );";
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
        PlayerDataGet pd = as.getPlayerData(PlayerName);
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
