package com.invisiblecat.reload.module.setting;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.module.Module;

import java.util.ArrayList;


public class SettingManager {

    private ArrayList<Setting> settings;

    public SettingManager(){
        this.settings = new ArrayList<>();
    }

    public void addSetting(Setting in){
        this.settings.add(in);
    }

    public ArrayList<Setting> getSettings(){
        return this.settings;
    }

    public ArrayList<Setting> getSettingsByModule(Module mod){
        ArrayList<Setting> out = new ArrayList<>();
        for(Setting s : getSettings()){
            if(s.getParent().equals(mod)){
                out.add(s);
            }
        }
        if(out.isEmpty()){
            return null;
        }
        return out;
    }

    public Setting getSetting(Module module,String name){
        for(Setting setting : getSettingsByModule(module)){
            if(setting.getName().equalsIgnoreCase(name)){
                return setting;
            }
        }
        System.err.println("["+ Reload.instance.clientName + "] Error Setting NOT found: '" + name +"'!");
        return null;
    }

}