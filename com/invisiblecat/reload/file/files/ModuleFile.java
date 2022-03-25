package com.invisiblecat.reload.file.files;


import com.google.gson.*;
import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.file.FileManager;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import optifine.Json;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ModuleFile {




    public void save() throws IOException {
        JsonObject finalJson = new JsonObject();
        FileManager fileManager = new FileManager();


        Reload.instance.moduleManager.getModules().forEach(m -> {
            JsonObject moduleJson = new JsonObject();
            moduleJson.addProperty("toggled", m.isEnabled());
            moduleJson.addProperty("key", m.getKey());
            moduleJson.addProperty("autodisable", m.getAutoDisable().toString());

            finalJson.add(m.getName(), moduleJson);

        });
        Gson idk = new Gson();
        JsonElement element = idk.fromJson(finalJson.toString(), JsonElement.class);
        try (Writer writer = new FileWriter(fileManager.getMainDir() + "/current.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(element, writer);
        }
    }

    public void load() throws IOException {
        FileManager fileManager = new FileManager();
        if(!Files.exists(Paths.get(fileManager.getMainDir() + "/current.json"))) return;
        Reader reader = Files.newBufferedReader(Paths.get(fileManager.getMainDir() + "/current.json"));
        JsonParser parser = new JsonParser();
        JsonObject jsonData = parser.parse(reader).getAsJsonObject();
//        for(Module m : Reload.instance.moduleManager.getModules()) {
//            JsonObject module = jsonData.get("AutoDisable").getAsJsonObject();
//            if(module.get("toggled").getAsBoolean())
//                m.toggle(false);
//        }

        Reload.instance.moduleManager.getModules().forEach(m -> {
            JsonObject mod = jsonData.get(m.getName()).getAsJsonObject();
            if(mod.get("toggled").getAsBoolean() && !(jsonData.get("HUD").getAsJsonObject() == mod)) {
                m.toggle(false);
            }
            m.setKey(mod.get("key").getAsInt());
        });

    }

}
