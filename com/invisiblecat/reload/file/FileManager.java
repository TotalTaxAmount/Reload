package com.invisiblecat.reload.file;

import com.invisiblecat.reload.Reload;
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
}
