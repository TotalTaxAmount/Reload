package com.invisiblecat.reload.command.commands;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.command.Command;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Toggle extends Command {
    private final ArrayList<String> aliases = new ArrayList<>(Arrays.asList("t", "tog"));

    public Toggle() {
        super("toggle", "Toggles a module", ".toggle [name]");
        this.setAliases(aliases);
    }

    @Override
    public void onCommand(String[] args, String command) {
        if(args.length > 0) {
            String moduleName = args[0];

            for(Module module : Reload.instance.moduleManager.getModules()) {
                if(module.getName().equalsIgnoreCase(moduleName)) {
                    module.toggle(true);
                    return;
                }
            }
            ChatUtils.sendChatMessageClient("["+ ChatFormatting.RED + "Error" + ChatFormatting.RESET + "] could not find module: " + moduleName.substring(0, 1).toUpperCase() + moduleName.substring(1));
        }

    }

}
