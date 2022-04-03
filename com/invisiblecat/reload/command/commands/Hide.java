package com.invisiblecat.reload.command.commands;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.command.Command;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.utils.chat.ChatUtils;

public class Hide extends Command {
    public Hide() {
        super("hide", "Toggles whether a module is show on the array list", ".hide [name]");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if(args.length > 0) {
            String moduleName = args[0];

            for(Module module : Reload.instance.moduleManager.getModules()) {
                if(module.getName().equalsIgnoreCase(moduleName)) {
                    ((BooleanSetting)module.getSetting("hide")).toggle();
                    ChatUtils.sendChatMessageClient("Now " + (((BooleanSetting)module.getSetting("hide")).isEnabled() ? "hiding " : "showing ") + module.getName(), ChatUtils.Type.INFO);
                    return;
                }
            }
            ChatUtils.sendChatMessageClient("Could not find module: " + moduleName.substring(0, 1).toUpperCase() + moduleName.substring(1), ChatUtils.Type.ERROR);
            return;
        }
        ChatUtils.sendChatMessageClient("You need at least one argument", ChatUtils.Type.WARN );

    }
}
