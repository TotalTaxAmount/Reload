package com.invisiblecat.reload.file.files;


import com.google.gson.*;
import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.file.FileManager;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.Setting;
import com.invisiblecat.reload.setting.settings.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ModuleFile {

    public void save() throws IOException {
        JsonObject finalJson = new JsonObject();
        FileManager manager = Reload.instance.fileManager;


        Reload.instance.moduleManager.getModules().forEach(m -> {
            JsonObject moduleJson = new JsonObject();
            moduleJson.addProperty("toggled", m.isEnabled());
            moduleJson.addProperty("key", m.getKey());
            // Create a new JsonObject for the module settings
            JsonObject settingsJson = new JsonObject();
            // Add all the boolean settings to the settingsJson
            m.getSettings().forEach(s -> {
                        if (s instanceof BooleanSetting) {
                            settingsJson.addProperty(s.getName(), ((BooleanSetting) s).isEnabled());
                        } else if (s instanceof ModeSetting) {
                            settingsJson.addProperty(s.getName(), ((ModeSetting) s).getSelected());
                            System.out.println(((ModeSetting) s).getSelected());
                        } else if (s instanceof NumberSetting) {
                            settingsJson.addProperty(s.getName(), ((NumberSetting) s).getValue());
                        } else if (s instanceof StringSetting) {
                            settingsJson.addProperty(s.getName(), ((StringSetting) s).getValue());
                            System.out.println("String setting: " + ((StringSetting) s).getValue());
                        }
                    });
            moduleJson.add("settings", settingsJson);

            finalJson.add(m.getName(), moduleJson);

        });
        Gson idk = new Gson();
        JsonElement element = idk.fromJson(finalJson.toString(), JsonElement.class);
        try (Writer writer = new FileWriter(manager.getMainDir() + "/current.json")) {
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
                if (mod.get("toggled").getAsBoolean()  && m.getAutoDisable() == Module.AutoDisable.NONE) {
                    m.toggle(false);
                }
                m.setKey(mod.get("key").getAsInt());
                JsonObject settings = mod.get("settings").getAsJsonObject();
                m.getSettings().forEach(s -> {
                    if (s instanceof BooleanSetting) {
                        ((BooleanSetting) s).setEnabled(settings.get(s.getName()).getAsBoolean());
                    } else if (s instanceof ModeSetting) {
                        ((ModeSetting) s).setSelected(settings.get(s.getName()).getAsString());
                    } else if (s instanceof NumberSetting) {
                        ((NumberSetting) s).setValue(settings.get(s.getName()).getAsInt());
                    } else if (s instanceof StringSetting) {
                        ((StringSetting) s).setValue(settings.get(s.getName()).getAsString());
                    }
                });
            }
        });

    }

}
