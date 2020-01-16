package frostdev.frostdev.DBConnect;

import frostdev.frostdev.HMDB;
import frostdev.frostdev.Util.GetConfigData;
import gyurix.mysql.MySQLDatabase;
import me.vagdedes.mysql.database.MySQL;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

public class MySQLConnect {
    private Connection connect;
    private HMDB main;
    private GetConfigData conf;
    private String host;
    private String port;
    private String database;
    private String user;
    private String pass;



    public MySQLConnect(HMDB as) {

        this.main = as;
        this.conf = as.getConfigData();
        this.host = conf.GetConfigString("database-host");
        this.port = conf.GetConfigString("database-port");
        this.database = conf.GetConfigString("database-DBname");
        this.user = conf.GetConfigString("database-username");
        this.pass = conf.GetConfigString("database-password");
        MySQL.setConnection(this.host,this.user,this.pass,this.database, this.port);
        MySQL.connect();
        this.connect = MySQL.getConnection();
       // try {
         //   Class.forName("com.mysql.jdbc.Driver");
      //  } catch (ClassNotFoundException e) {
       //     e.printStackTrace();
       //     System.err.println("jdbc driver unavailable!");
     //   }

    //}

    // public void openConnection() throws SQLException {
      //  if (connect != null && !connect.isClosed()) {
       //     return;
      //  }

        //synchronized (this) {
          //  if (connect != null && !connect.isClosed()) {
         //       return;
           // }
          //  connect = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, pass);
       // }
    }
    public Connection GetConnection(){
        return this.connect;
    }
}
