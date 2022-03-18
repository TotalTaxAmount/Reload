package com.invisiblecat.reload.command.commands;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.command.Command;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.ChatUtils;

import java.util.ArrayList;
import java.util.Collections;

public class Dev extends Command {
    private final ArrayList<String> aliases = new ArrayList<>(Collections.singletonList("d"));

    public Dev() {
        super("dev", "Developer cmd does random test things idk", ".dev");
        this.setAliases(aliases);
    }

    @Override
    public void onCommand(String[] args, String command) {
       // Reload.instance.fileManager.loadOld();
       ChatUtils.sendChatMessageClient("Info", ChatUtils.Type.INFO);
    }
}
