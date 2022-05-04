package com.invisiblecat.reload.config;

import com.google.gson.*;
import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.notification.Notification;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationManager;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationType;
import com.invisiblecat.reload.file.FileManager;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.setting.settings.StringSetting;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.io.*;
import java.nio.file.Paths;

public class Config {
    private String name;
    private FileManager manager = new FileManager();

    public Config(String name) {
        this.name = name;
    }

    // From ModuleFile.java

    public void load() {
        Reader reader;
      try {
          reader = new FileReader(String.valueOf(Paths.get(manager.getConfigs() + "/" + name + ".reload")));
      } catch (FileNotFoundException e) {
          NotificationManager.show(new Notification(NotificationType.ERROR, "Config", "Config file not found", 1));
          return;
      }
        JsonParser parser = new JsonParser();
        JsonObject jsonData;

        try {
            jsonData = parser.parse(reader).getAsJsonObject();
            if (jsonData.get("version").getAsDouble() < Reload.instance.version) {
                NotificationManager.show(new Notification(NotificationType.WARNING, "Reload", "Config is outdated. Please update it.", 2));
            }
        } catch (Exception e) {
            Reload.instance.reloadLogger.error("Failed to load config: \n" + ChatFormatting.RED + e);
            return;
        }
        JsonObject modules = jsonData.get("modules").getAsJsonObject();
        Reload.instance.moduleManager.getModules().forEach(m -> {
            JsonObject mod = modules.get(m.getName()) != null ? modules.get(m.getName()).getAsJsonObject() : null;
            if (mod != null) {
                m.setEnabled(mod.get("toggled").getAsBoolean());
                JsonObject settings;
                try {
                    settings = mod.get("settings").getAsJsonObject();
                } catch (Exception e) {
                    Reload.instance.reloadLogger.warn("Failed to load settings for module " + m.getName());
                    return;
                }
                m.getSettings().forEach(s -> {
                    if (s instanceof BooleanSetting) {
                        try {
                            ((BooleanSetting) s).setEnabled(settings.get(s.getName()).getAsBoolean());
                        } catch (NullPointerException e) {
                            Reload.instance.reloadLogger.warn("Could not load boolean setting: " + s.getName() + " for module " + m.getName());
                        }

                    } else if (s instanceof ModeSetting) {
                        //((ModeSetting) s).setSelected(settings.get(s.getName()).getAsString());
                        try {
                            ((ModeSetting) s).setSelected(settings.get(s.getName()).getAsString());
                        } catch (NullPointerException e) {
                            Reload.instance.reloadLogger.warn("Could not load mode setting: " + s.getName() + " for module " + m.getName());
                        }
                    } else if (s instanceof NumberSetting) {
                        // ((NumberSetting) s).setValue(settings.get(s.getName()).getAsInt());
                        try {
                            ((NumberSetting) s).setValue(settings.get(s.getName()).getAsInt());
                        } catch (NullPointerException e) {
                            Reload.instance.reloadLogger.warn("Could not load number setting: " + s.getName() + " for module " + m.getName());
                        }
                    } else if (s instanceof StringSetting) {
                        // ((StringSetting) s).setValue(settings.get(s.getName()).getAsString());
                        try {
                            ((StringSetting) s).setValue(settings.get(s.getName()).getAsString());
                        } catch (NullPointerException e) {
                            Reload.instance.reloadLogger.warn("Could not load string setting: " + s.getName() + " for module " + m.getName());
                        }
                    }
                });
            }
        });
        NotificationManager.show(new Notification(NotificationType.SUCCESS, "Config", "Loaded config " + name, 1));

    }

    // From ModuleFile.java

    public void save() throws IOException {
        JsonObject finalJson = new JsonObject();
        JsonObject allModsJson = new JsonObject();
        FileManager manager = new FileManager();



        finalJson.addProperty("version", Reload.instance.version);
        // add a property for date
        finalJson.addProperty("date", manager.getDate());
        Reload.instance.moduleManager.getModules().forEach(m -> {
            JsonObject moduleJson = new JsonObject();
            moduleJson.addProperty("toggled", m.isEnabled());
            // Create a new JsonObject for the module settings
            JsonObject settingsJson = new JsonObject();
            // Add all the boolean settings to the settingsJson
            m.getSettings().forEach(s -> {
                if (s instanceof BooleanSetting) {
                    settingsJson.addProperty(s.getName(), ((BooleanSetting) s).isEnabled());
                } else if (s instanceof ModeSetting) {
                    settingsJson.addProperty(s.getName(), ((ModeSetting) s).getSelected());
                } else if (s instanceof NumberSetting) {
                    settingsJson.addProperty(s.getName(), ((NumberSetting) s).getValue());
                } else if (s instanceof StringSetting) {
                    settingsJson.addProperty(s.getName(), ((StringSetting) s).getValue());
                }
            });
            moduleJson.add("settings", settingsJson);
            allModsJson.add(m.getName(), moduleJson);
        });
        finalJson.add("modules", allModsJson);
        Gson idk = new Gson();
        JsonElement element = idk.fromJson(finalJson.toString(), JsonElement.class);
        try (Writer writer = new FileWriter(manager.getConfigs() + "/" + name + ".reload")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(element, writer);
            NotificationManager.show(new Notification(NotificationType.SUCCESS, "Config", "Saved config " + name, 1));
        }
    }
}
