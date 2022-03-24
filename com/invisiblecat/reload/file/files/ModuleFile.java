package com.invisiblecat.reload.file.files;


import com.google.gson.*;
import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.file.FileManager;
import com.invisiblecat.reload.module.Module;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ModuleFile {

    private final FileManager fileManager = new FileManager();


    public void save() throws IOException {
        JsonObject finalJson = new JsonObject();

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
        Reader reader = Files.newBufferedReader(Paths.get(fileManager.getMainDir() + "/current.json"));
        JsonParser parser = new JsonParser();
        JsonObject jsonData = parser.parse(reader).getAsJsonObject();
        for(Module m : Reload.instance.moduleManager.getModules()) {
            JsonObject module = jsonData.get("AutoDisable").getAsJsonObject();
            if(module.get("toggled").getAsBoolean())
                m.toggle(false);
        }
        String sprint = jsonData.get("Sprint").getAsJsonObject().get("toggled").toString();
        System.out.println(sprint);

    }
}
