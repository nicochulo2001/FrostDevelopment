package frostdev.frostdev.CompanyDataCommit;
import java.sql.*;

public class CompanyDataGet {
    private Connection connection;
    private String name;
    private String UUID;
    private PreparedStatement statement;
    private ResultSet results;

    public CompanyDataGet(Connection connection){
        this.connection = connection;

    }
    private void QueryName(String name) throws SQLException {
            this.statement = this.connection.prepareStatement("SELECT * FROM companies WHERE compname = "+ name);
            this.results = this.statement.executeQuery();
    }

    private void QueryUUID (String UUID) throws SQLException {
        this.statement = this.connection.prepareStatement("SELECT * FROM companies WHERE  UUID = "+ UUID);
        this.results = this.statement.executeQuery();

    }

    public String GetUUID(String name){
        try {
            this.QueryName(name);
            this.name = this.CompanyName(UUID);
            return this.UUID = this.results.getString("UUID");
        }catch (SQLException e){
            return "No company found by the name of '" + this.name + "', please check for typos.";
        }
    }
    public String CompanyName(String UUID){
        try {
            this.QueryUUID(UUID);
            return this.UUID = this.results.getString("compname");
        }catch (SQLException e){
            return "No company found by the UUID of '" + UUID + "', please check for typos.";
        }
    }

    public String CompanyStockPrice(String UUID){
        try {
            this.QueryUUID(UUID);
            this.name = this.CompanyName(UUID);
            return this.UUID = this.results.getString("stock");
        }catch (SQLException e){
            return "No company found by the UUID of '" + UUID + "', please check for typos.";
        }
    }

    public String IsCompanyPublic(String UUID){
        try {
            this.QueryUUID(UUID);
            this.name = this.CompanyName(UUID);
            return this.UUID = this.results.getString("UUID");
        }catch (SQLException e){
            return "No company found by the UUID of '" + UUID + "', please check for typos.";
        }
    }

    public String CompanyEcon(String UUID){
        try {
            this.QueryUUID(UUID);
            this.name = this.CompanyName(UUID);
            return this.UUID = this.results.getString("public");
        }catch (SQLException e){
            return "No company found by the UUID of '" + UUID + "', please check for typos.";
        }
    }

    public String CompanyDescription(String UUID){
        try {
            this.QueryUUID(UUID);
            this.name = this.CompanyName(UUID);
            return this.results.getBlob("textcompdata").toString();
        }catch (SQLException e){
            return "No company found by the UUID of '" + UUID + "', please check for typos.";
        }
    }


}
