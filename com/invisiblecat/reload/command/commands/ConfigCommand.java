package com.invisiblecat.reload.command.commands;

import com.invisiblecat.reload.command.Command;
import com.invisiblecat.reload.config.Config;
import com.invisiblecat.reload.file.FileManager;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class ConfigCommand extends Command {
    private final ArrayList<String> aliases = new ArrayList<>(Collections.singletonList("cfg"));


    public ConfigCommand() {
        super("config", "load / save configs", ".config load/save <config>");
        this.setAliases(aliases);
    }

    @Override
    public void onCommand(String[] args, String command) {

        String opperation;
        String config;

        try {
            opperation = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            ChatUtils.sendChatMessageClient("Invalid arguments. Usage: " + ChatFormatting.GOLD + this.getSyntax(), ChatUtils.Type.ERROR);
            return;
        }

        switch (opperation) {
            case "load": {
                try {
                    config = args[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    ChatUtils.sendChatMessageClient("Invalid arguments. Usage: " + ChatFormatting.GOLD + this.getSyntax(), ChatUtils.Type.ERROR);
                    return;
                }

                Config cfg = new Config(config);
                cfg.load();
                break;
            } case "save": {
                try {
                    config = args[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    ChatUtils.sendChatMessageClient("Invalid arguments. Usage: " + ChatFormatting.GOLD + this.getSyntax(), ChatUtils.Type.ERROR);
                    return;
                }
                Config cfg = new Config(config);
                try {
                    cfg.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            } case "list": {
               ChatUtils.sendChatMessageClient("Configs: ", ChatUtils.Type.INFO);
               if (getConfigs().isEmpty()) ChatUtils.sendChatMessageClient("No configs found.", ChatUtils.Type.INFO);
               for (String s : getConfigs()) {
                   ChatUtils.sendChatMessageClient(ChatFormatting.GRAY + " > " + ChatFormatting.RESET + s, ChatUtils.Type.INFO);
               }
            }
            break;

            default: {
                ChatUtils.sendChatMessageClient("Invalid opperation", ChatUtils.Type.WARN);
            }
        }
    }

    public ArrayList<String> getConfigs() {
        ArrayList<String> configs = new ArrayList<>();
        FileManager f1 = new FileManager();

        if (f1.getConfigs().listFiles() == null) return new ArrayList<>();

        for (File f : Objects.requireNonNull(f1.getConfigs().listFiles())) {
            if (f.isDirectory() || !f.getName().endsWith(".reload")) return new ArrayList<>();
            configs.add(f.getName().replace(".reload", ""));
        }
        return configs;
    }
}
