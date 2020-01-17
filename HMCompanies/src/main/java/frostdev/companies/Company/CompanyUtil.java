package frostdev.companies.Company;

import com.sk89q.worldedit.util.formatting.text.format.TextDecoration;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import frostdev.companies.Companies;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class CompanyUtil {
    private Companies main;
    private Economy economy;
    public StateFlag Company_Property;
    public CompanyUtil(Companies as){
        this.main = as;
        this.economy = as.getDatabase().getEconomy();
    }


    public void Create(OfflinePlayer founder, String compname, double funds){
        if(!this.Istaken(compname) && this.main.getDatabase().get) {
            if(this.economy.withdrawPlayer(founder, funds).transactionSuccess()) {
                this.economy.createBank(compname, founder);
                this.economy.bankDeposit(compname, funds);
            }else{
                Player player = this.main.getServer().getPlayer(founder.getUniqueId());
                player.sendMessage("Failed to withdraw funds from your account, company creation failed.");
            }
        }
    }

    public boolean Istaken(String compname){
        List<String> banks = this.economy.getBanks();
        for (int x = 0; x < banks.size(); x++){
            if(banks.get(x).equals(compname)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public void CompanyFlag(){
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {

            StateFlag flag = new StateFlag("Company-Property", false);
            registry.register(flag);
            this.Company_Property = flag;
        }catch (FlagConflictException e){
            Flag<?> exisiting = registry.get("Company-Property");
            if (exisiting instanceof StateFlag){
                this.Company_Property = (StateFlag) exisiting;
            }else {
                e.addSuppressed(e);
            }
        }
    }

    public void IsCompProperty(ProtectedRegion region){
        if(region.getFlag(this.Company_Property).equals(StateFlag.State.ALLOW)){

        }
    }





}
