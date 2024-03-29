package com.invisiblecat.reload.command.commands;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.notification.Notification;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationManager;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationType;
import com.invisiblecat.reload.command.Command;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

public class Bind extends Command {
    private final ArrayList<String> aliases = new ArrayList<>(Arrays.asList("b"));
    public Bind() {
        super("bind", "Binds a module to a key", ".bind [module] [key]");
        this.setAliases(aliases);
    }

    @Override
    public void onCommand(String[] args, String command) {
        if(args.length == 2) {
            String module = args[0];
            String key = args[1];

            if(!Reload.instance.moduleManager.getModules().contains(Reload.instance.moduleManager.getModuleByName(module))) {
                ChatUtils.sendChatMessageClient("Module " + module + " does not exist!", ChatUtils.Type.ERROR);
            }

            for (Module m : Reload.instance.moduleManager.getModules()) {
                if(key.length() > 1) {
                    ChatUtils.sendChatMessageClient("Cleared bind for " + m.getName().substring(0, 1).toUpperCase() + m.getName().substring(1), ChatUtils.Type.INFO);
                    m.setKey(Keyboard.KEY_NONE);
                    break;
                }
                if(m.getName().equalsIgnoreCase(module)) {
                    m.setKey(Keyboard.getKeyIndex(key.toUpperCase()));

                    ChatUtils.sendChatMessageClient("Bound " + m.getName().substring(0, 1).toUpperCase() + m.getName().substring(1) + " to " + key.toUpperCase() + ".", ChatUtils.Type.INFO);
                    NotificationManager.show(new Notification(NotificationType.SUCCESS, "Bindings", "Bound " + m.getName().substring(0, 1).toUpperCase() + m.getName().substring(1) + " to " + key.toUpperCase() + ".", 3));
                    break;
                }
            }
        }
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("remove")) {
                for(Module m : Reload.instance.moduleManager.getModules()) {
                    m.setKey(Keyboard.KEY_NONE);
                }
                ChatUtils.sendChatMessageClient("Cleared all binds.", ChatUtils.Type.INFO);
            } else {
                ChatUtils.sendChatMessageClient("Invalid arguments.", ChatUtils.Type.ERROR);

            }
        }
    }
}
