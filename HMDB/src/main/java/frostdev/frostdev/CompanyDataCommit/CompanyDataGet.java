package frostdev.frostdev.CompanyDataCommit;

import frostdev.frostdev.HMDB;

import java.sql.*;
import java.util.ArrayList;

public class CompanyDataGet {
    private HMDB main;
    private Connection connection;
    private String name;
    private String des;
    private String[] members;
    private ArrayList<String> regions;
    private boolean pub;
    private double econ;
    private double stock;
    private String UUID;
    private PreparedStatement statement;
    private ResultSet results;

    public void GetData(String input){
        this.main = new HMDB();
        this.connection = main.GetConnection();
        try {
            this.statement = this.connection.prepareStatement("SELECT * FROM companies WHERE compname = "+ input);
            this.results = this.statement.executeQuery();
        }catch (SQLException e){
            try {
                this.statement = this.connection.prepareStatement("SELECT * FROM companies WHERE UUID = "+ input);
                this.results = this.statement.executeQuery();
            }catch (SQLException e1){
                try {
                    this.statement = this.connection.prepareStatement("SELECT * FROM companies WHERE founder = "+ input);
                    this.results = this.statement.executeQuery();
                }catch (SQLException e2){
                    try {
                        this.statement = this.connection.prepareStatement("SELECT * FROM companies WHERE stock = "+ input);
                        this.results = this.statement.executeQuery();
                    }catch (SQLException e3){
                        try {
                            this.statement = this.connection.prepareStatement("SELECT * FROM companies WHERE public = "+ input);
                            this.results = this.statement.executeQuery();
                        }catch (SQLException e4){
                            this.results = null;
                        }
                    }
                }
            }
        }
    }

    public String GetUUID(){
        try {
            return this.UUID = this.results.getString("UUID");
        }catch (SQLException e){
            return "No company found by the name of '" + this.name + "', please check for typos.";
        }
    }
    public String CompanyName(){
        try {
            return this.UUID = this.results.getString("compname");
        }catch (SQLException e){
            return "No company found by the name of '" + this.name + "', please check for typos.";
        }
    }

    public String CompanyStockPrice(){
        try {
            return this.UUID = this.results.getString("stock");
        }catch (SQLException e){
            return "No company found by the name of '" + this.name + "', please check for typos.";
        }
    }

    public String IsCompanyPublic(){
        try {
            return this.UUID = this.results.getString("UUID");
        }catch (SQLException e){
            return "No company found by the name of '" + this.name + "', please check for typos.";
        }
    }

    public String CompanyEcon(){
        try {
            return this.UUID = this.results.getString("UUID");
        }catch (SQLException e){
            return "No company found by the name of '" + this.name + "', please check for typos.";
        }
    }

    public String CompanyDescription(){
        try {
            return this.des = this.results.getBlob("textcompdata").toString();
        }catch (SQLException e){
            return "No company found by the name of '" + this.name + "', please check for typos.";
        }
    }


}
