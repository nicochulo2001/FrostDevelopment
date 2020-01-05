package frostdev.frostdev.Util;

import frostdev.frostdev.HMDB;
import org.bukkit.inventory.ItemStack;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetItemData {
    private String result;
    private ResultSet results;
    private String name;
    private HMDB main;
    private boolean isValid = true;
    public GetItemData(HMDB as, String e) {
        Connection connection;
        this.name = e;
        this.main = as;
        connection = as.GetConnection();
        String sql = "SELECT ID, item, price, average FROM items WHERE item='" + e.toLowerCase() + "';";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
             this.results = stmt.executeQuery(sql);
        } catch (SQLException x) {
            try{
                sql = "SELECT ID, item, price, average FROM items WHERE ID='" + e.toLowerCase() + "';";
                PreparedStatement stmt = connection.prepareStatement(sql);
                this.results = stmt.executeQuery(sql);
            }catch (SQLException e1){
                e1.printStackTrace();
            }
            x.printStackTrace();
        }
    }

    public String ReturnItemName(){
        try {
            while (results.next()) {
                this.result = this.results.getString("item");
                main.getLogger().info("Item: " + this.name + " Value: " + this.result);
            }
        }catch (SQLException e){
            this.isValid = false;
            e.printStackTrace();
            return this.result = "Error";

        }
        return this.result;
    }

    public String ReturnItemID(){
        try {
            while (results.next()) {
                this.result = this.results.getString("ID");
                main.getLogger().info("Item: " + this.name + " Value: " + this.result);
            }
        }catch (SQLException e){
            this.isValid = false;
            e.printStackTrace();
            return this.result = "Error";

        }
        return this.result;
    }

    public String ReturnItemValue(){
        try {
            while (results.next()) {
                this.result = this.results.getString("price");
                main.getLogger().info("Item: " + this.name + " Value: " + this.result);
            }
        }catch (SQLException e){
            this.isValid = false;
            e.printStackTrace();
            return this.result = "Error";
        }
        return this.result;
    }

    public Boolean IsValidItem(){
        return this.isValid;
    }
}
