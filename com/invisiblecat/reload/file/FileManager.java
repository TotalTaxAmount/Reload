package com.invisiblecat.reload.file;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.file.files.ModuleFile;
import net.minecraft.client.Minecraft;

import java.io.File;

public class FileManager {
    private Minecraft mc = Minecraft.getMinecraft();

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
        ModuleFile moduleSaver = new ModuleFile();
        moduleSaver.save();
    }
    public void loadOld() {
        ModuleFile moduleSaver = new ModuleFile();
        moduleSaver.load();
    }
}
