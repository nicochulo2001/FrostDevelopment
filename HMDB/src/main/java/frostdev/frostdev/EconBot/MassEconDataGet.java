package frostdev.frostdev.EconBot;

import frostdev.frostdev.HMDB;
import frostdev.frostdev.Util.TableSetup;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.ArrayList;

public class MassEconDataGet {
    private Connection connection;
    private HMDB main;
    private ItemAverageCommit itemAverageCommit;
    public MassEconDataGet(HMDB as, Connection connection){
        this.connection = connection;
        this.main = as;
        this.itemAverageCommit = this.main.itemAverageCommit();
    }

    public String ReturnAvgValue(String name)throws SQLException{

        name = name.replaceAll("_", " ");
        ArrayList<Double> list = new ArrayList<>();
        double avg = 0;
        double num = 0;
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM chest_shop_sales WHERE item = '"+ name + "';");
        ResultSet  results = statement.executeQuery();
        double check = 0;
        while (results.next()) {
            double price = Double.valueOf(results.getString("price"));
            double amount = Double.valueOf(results.getString("amount"));
            double result = price/amount;
            if(result > check){
                if(check == 0) {
                    check = result;
                }else if(result < check*3 && result > check/3){
                    list.add(result);
                    check = result;
                }else{
                    this.main.getLogger().info("HMDB DEBUGGER: Value outside of configured average range has been redacted : " + result);
                }
            }
        }
        double temp = 0;
        for (int x = 0; x<list.size(); x++){
            temp = Double.valueOf(list.get(x));

            num = num + temp;
        }
        avg = num / list.size();
        this.itemAverageCommit.CommitAvg(name, String.valueOf(avg));
        return  String.valueOf(avg);

    }

    public String ReturnNumItemsLogged(String name)throws SQLException{
        name = name.replaceAll("_", " ");
        ArrayList<Double> list = new ArrayList<>();
        int ret = 0;
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM chest_shop_sales WHERE item = '"+ name + "';");
        ResultSet  results = statement.executeQuery();
        while (results.next()) {
            int amount = Integer.valueOf(results.getString("amount"));
            ret = ret + amount;
        }
        return String.valueOf(ret);
    }



}
