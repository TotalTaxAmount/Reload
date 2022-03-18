package com.invisiblecat.reload.command;

import com.invisiblecat.reload.command.commands.*;
import com.invisiblecat.reload.event.events.EventChat;
import com.invisiblecat.reload.utils.ChatUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager {
    private final ArrayList<Command> commands = new ArrayList<>();
    public String prefix = ".";

    public CommandManager() {
        commands.add(new Toggle());
        commands.add(new Bind());
        commands.add(new Help());
        commands.add(new Dev());
        commands.add(new Hide());

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
                if((c.getAliases() != null ? c.getAliases().contains(commandName.toLowerCase()): false)|| c.getName().contains(commandName.toLowerCase())) {
                    c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                    return;
                }
            }
            ChatUtils.sendChatMessageClient("Unknown command: " + commandName, ChatUtils.Type.ERROR);
        }
    }
    public ArrayList<Command> getCommands() {
        return commands;
    }

}
