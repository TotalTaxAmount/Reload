package com.invisiblecat.reload.file;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.command.commands.Hide;
import com.invisiblecat.reload.file.files.HUDFile;
import com.invisiblecat.reload.file.files.ModuleFile;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private Minecraft mc = Minecraft.getMinecraft();

    private final ModuleFile moduleFile = new ModuleFile();
    private final HUDFile hudFile = new HUDFile();


    private final File mainDir = new File(mc.mcDataDir, Reload.instance.clientName);
    private final File fontDir = new File(mainDir, "fonts");
    private final File configs = new File(mainDir, "configs");

    public FileManager() {
        if(!mainDir.exists()) {
            mainDir.mkdir();
            Reload.instance.reloadLogger.info("[Reload] Created " + mainDir.getPath());
        }
        if(!fontDir.exists()) {
            fontDir.mkdir();
            Reload.instance.reloadLogger.info("[Reload] Created " + fontDir.getPath());

        }
        if(!configs.exists()) {
            configs.mkdir();
            Reload.instance.reloadLogger.info("[Reload] Created " + configs.getPath());

        }
    }

    public File getMainDir() {
        return mainDir;
    }

    public File getFontDir() {
        return fontDir;
    }

    public File getConfigs() {
        return configs;
    }

    public void save() {
        try {
            moduleFile.save();
            hudFile.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadOld() {
        try {
            moduleFile.load();
            hudFile.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
