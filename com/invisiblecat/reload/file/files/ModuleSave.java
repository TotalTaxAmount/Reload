package com.invisiblecat.reload.file.files;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.file.FileManager;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.module.modules.movement.Sprint;

import java.io.DataOutput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ModuleSave {
    public void save() {
        FileManager fileManager = new FileManager();

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
}
