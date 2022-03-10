
package com.invisiblecat.reload.command.commands;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.command.Command;
import com.invisiblecat.reload.utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;

public class Help extends Command {
    private final ArrayList<String> aliases = new ArrayList<>(Arrays.asList("h"));

    public Help() {
        super("help", "displays this page", ".help");
        this.setAliases(aliases);
    }

    @Override
    public void onCommand(String[] args, String command) {
        StringBuilder text = new StringBuilder("Help Menu\n");
        for(Command c : Reload.instance.commandManager.getCommands()) {
            text.append(c.getName().substring(0, 1).toUpperCase() + c.getName().substring(1) + ": " + c.getDescription() + ", " + ChatFormatting.GRAY + "(Usage: " + c.getSyntax() + ")\n");
        }
        ChatUtils.sendChatMessageClient(text.toString());
    }
}
