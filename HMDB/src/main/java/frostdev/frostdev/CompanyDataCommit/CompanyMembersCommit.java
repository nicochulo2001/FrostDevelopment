package frostdev.frostdev.CompanyDataCommit;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.PlayerDataCommit.PlayerDataGet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompanyMembersCommit {
    private HMDB main;
    private Connection connection;
    public CompanyMembersCommit(HMDB as, Connection connection){
        this.main = as;
        this.connection = connection;
    }

    public void MemberCommit(String player, String CUUID){
        String sql;
                try {
                    sql = "INSERT INTO "+ CUUID + "(members) VALUES ('"+ player + "' );";
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.executeUpdate();
                } catch (SQLException e){
                    e.printStackTrace();
                }

    }

    public void TitleCommit(String PUUID, String CUUID, String title) {
        String sql;
        try {
            sql = "UPDATE " + CUUID + " SET title ='" + title + "'" + "WHERE UUID = '"+ PUUID + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void UUIDCommit(String PlayerName, String CUUID){
        PlayerDataGet pd = this.main.getPlayerData();
        String sql;
        String PUUID = pd.ReturnPlayerUUID(PlayerName);
        try {
            sql = "UPDATE " + CUUID + " SET UUID ='" + PUUID + "'" + "WHERE members = '"+ PlayerName + "';";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
