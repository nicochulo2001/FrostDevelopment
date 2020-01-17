package frostdev.companies.MemberManager;

import frostdev.companies.Companies;
import frostdev.frostdev.HMDB;


public class MemberApply {
    private Companies main;
    private HMDB database;
    public MemberApply(Companies as){
        this.main = as;
        this.database = as.getDatabase();

    }

    public void apply(String player, String CUUID){

    }
}
