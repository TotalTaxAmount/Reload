package com.invisiblecat.reload.file.files;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.file.FileManager;
import com.invisiblecat.reload.module.Module;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public class ModuleFile {

    private final FileManager fileManager = new FileManager();


    public void save() {

        File file = new File(fileManager.getMainDir() + "/current.json");
        ObjectWriter mapper = new ObjectMapper().writerWithDefaultPrettyPrinter();
        try {
            JsonGenerator g = mapper.getFactory().createGenerator(new FileOutputStream(file));
            for (Module m : Reload.instance.moduleManager.getModules()) {
                mapper.writeValue(g, m);
            }
            g.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        try {
            //List<Module> mods = mapper.readValue(Paths.get(fileManager.getMainDir() + "/current.json").toFile(), new TypeReference<List<Module>>(){});
            List<Module> mods = Arrays.asList(mapper.readValue(Paths.get(fileManager.getMainDir() + "/current.json").toFile(), Module[].class));

            System.out.println(mods);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
