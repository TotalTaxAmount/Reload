package com.invisiblecat.reload.file.files;

import com.google.gson.*;
import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.file.FileManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HUDFile {
    private final FileManager manager = Reload.instance.fileManager;


    public void save() {
        JsonObject json = new JsonObject();

        Reload.instance.hud.getElements().forEach(element -> {
            JsonObject elementJson = new JsonObject();
            elementJson.addProperty("name", element.getName());
            elementJson.addProperty("x", element.getX());
            elementJson.addProperty("y", element.getY());
            elementJson.addProperty("width", element.getWidth());
            elementJson.addProperty("height", element.getHeight());
            elementJson.addProperty("enabled", element.isToggled());
            json.add(element.getName(), elementJson);
        });

        Gson idk = new Gson();
        JsonElement element = idk.fromJson(json, JsonElement.class);
        try (Writer writer = new FileWriter(manager.getMainDir() + "/HUD.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(element, writer);

        } catch (IOException ignored) {}
    }

    public void load() throws IOException {
        if(!Files.exists(Paths.get(manager.getMainDir() + "/HUD.json"))) {
            System.out.println("no");
            return;
        }
        Reader reader = Files.newBufferedReader(Paths.get(manager.getMainDir() + "/HUD.json"));
        JsonParser parser = new JsonParser();
        JsonObject jsonData = parser.parse(reader).getAsJsonObject();

        Reload.instance.hud.getElements().forEach(element -> {
            JsonObject elementJson = jsonData.get(element.getName()).getAsJsonObject();
            if (elementJson != null) {
                if (elementJson.has("x")) element.setX(elementJson.get("x").getAsInt());
                if (elementJson.has("y")) element.setY(elementJson.get("y").getAsInt());
                if (elementJson.has("width")) element.setWidth(elementJson.get("width").getAsInt());
                if (elementJson.has("height")) element.setHeight(elementJson.get("height").getAsInt());
                if (elementJson.has("enabled")) element.setToggled(elementJson.get("enabled").getAsBoolean());
            }
        });

    }

}
