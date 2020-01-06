package frostdev.frostdev.Util;


import frostdev.frostdev.HMDB;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class GetItems{
    Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
    private ArrayList<String> found = new ArrayList<String>();

    public void Populate(HMDB main) {
        while(recipeIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();
            ItemStack result = recipe.getResult();

            found.add(result.getType().toString().toLowerCase());
        }
        int count = 0;
        String price = "10";
        String average = "10";
        Connection connection = main.GetConnection();
        try {
            TableSetup table = new TableSetup();
            table.OnTableSetup("items", "ID int, item varchar(50), " + "price varchar(255), " + "average varchar(255)", connection);
        }catch (SQLException e){
            e.printStackTrace();
        }

        for(String found : found) {
            try {
                String sql1 = "INSERT INTO items(ID, item, price, average) VALUES ("+ count +", '" + found + "', '" + price + "', '" + average + "' );";
                PreparedStatement stmt1 = connection.prepareStatement(sql1);
                stmt1.executeUpdate();
                count++;
            } catch (SQLException e){
                e.printStackTrace();
            }
            main.getLogger().info("ID: "+ count + "| Name: " + found + "| Price: "+ price);
        }
        main.getLogger().info("Successfully loaded " + count + " Items from database.");
    }
}
