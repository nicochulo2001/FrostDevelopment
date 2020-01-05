package frostdev.companies.DBHandler;

import java.util.UUID;
import frostdev.companies.Companies;
import frostdev.frostdev.CompanyDataCommit.CompanyDataCommit;
import frostdev.frostdev.HMDB;
import frostdev.frostdev.CompanyDataCommit.CompanyCreate;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.Random;
import java.util.UUID;

public class CompaniesCreateCompany {
    private Companies main;
    private HMDB databse;

    public String Create(String user, String econ, String stock, String compname, String textcompdata, Companies as){
        this.main = as;
        this.databse = as.getDatabase();
        double ec;
        ec = Double.valueOf(econ);
        if(ec>=50000) {
            String ID;
            UUID uuid = UUID.randomUUID();
            ID = uuid.toString().replaceAll("-", "");
            databse.CreateCompany(user, ID, econ, stock, compname, textcompdata);
            return "Congratulations! Your Company " + compname + " has successfully been created!";
        }else {
            return "You must have a starting balance of at least F50,000 to make a company!";
        }
    }
}
