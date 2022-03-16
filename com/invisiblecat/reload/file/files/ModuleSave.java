package com.invisiblecat.reload.file.files;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.module.Module;

public class ModuleSave {
    public void save() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        for(Module m : Reload.instance.moduleManager.getModules()) {
           // System.out.println(gson);
        }
    }
}
