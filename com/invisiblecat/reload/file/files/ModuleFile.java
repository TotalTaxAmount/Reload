package com.invisiblecat.reload.file.files;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.file.FileManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ModuleFile {

    private final FileManager fileManager = new FileManager();


    public void save() throws IOException {
        JsonObject finalJson = new JsonObject();

        Reload.instance.moduleManager.getModules().forEach(m -> {
            JsonObject moduleJson = new JsonObject();
            moduleJson.addProperty("toggled", m.isToggled());
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
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(fileManager.getMainDir() + "/current.json"));

    }
}
