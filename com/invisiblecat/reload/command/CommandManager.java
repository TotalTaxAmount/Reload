package com.invisiblecat.reload.command;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.command.commands.*;
import com.invisiblecat.reload.event.events.EventChat;
import com.invisiblecat.reload.utils.ChatUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CommandManager {
    private ArrayList<Command> commands = new ArrayList<>();
    public String prefix = ".";

    public CommandManager() {
        commands.add(new Toggle());
        commands.add(new Bind());
        commands.add(new Help());
        commands.add(new Dev());

    }

    public void HandleChat(EventChat event) {
        String message = event.getMessage();

        if(!message.startsWith(prefix))
            return;

        event.setCancelled(true);
        message = message.substring(prefix.length());

        if(message.split(" ").length > 0) {
            String commandName = message.split(" ")[0];


            for (Command c : commands) {
                if(c.getAliases().contains(commandName) || c.getName().contains(commandName.toLowerCase())) {
                    c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                    return;
                }
            }
            ChatUtils.sendChatMessageClient("Unknown command: " + commandName);
        }
    }
    public ArrayList<Command> getCommands() {
        return commands;
    }

}
