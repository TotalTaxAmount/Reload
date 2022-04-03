package com.invisiblecat.reload.file.files;


import com.google.gson.*;
import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.file.FileManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


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
        if(!Files.exists(Paths.get(fileManager.getMainDir() + "/current.json"))) {System.out.println("idkk"); return;}
        Reader reader = Files.newBufferedReader(Paths.get(fileManager.getMainDir() + "/current.json"));
        JsonParser parser = new JsonParser();
        JsonObject jsonData = parser.parse(reader).getAsJsonObject();

        Reload.instance.moduleManager.getModules().forEach(m -> {
            JsonObject mod = jsonData.get(m.getName()) != null ? jsonData.get(m.getName()).getAsJsonObject() : null;
            if (mod != null) {
                if (mod.get("toggled").getAsBoolean() && !(jsonData.get("HUD").getAsJsonObject() == mod) && mod.get("autodisable").getAsString().equalsIgnoreCase("NONE")) {
                    m.toggle(false);
                }
                m.setKey(mod.get("key").getAsInt());
            }
        });

    }

}
