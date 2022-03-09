package com.invisiblecat.reload.command.commands;

import com.invisiblecat.reload.command.Command;
import com.invisiblecat.reload.utils.ChatUtils;

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
            ChatUtils.sendChatMessageClient("test idk");
            System.out.println("daf");
        }
    }

}
