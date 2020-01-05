package frostdev.frostdev.Util;

import frostdev.frostdev.HMDB;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class SetItemValue {
    HMDB main;
    Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
    private ArrayList<String> found = new ArrayList<String>();

    public SetItemValue(HMDB as){
        this.main = as;
        while(recipeIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();
            ItemStack result = recipe.getResult();
            found.add(result.getType().toString());

        }
    }


}
