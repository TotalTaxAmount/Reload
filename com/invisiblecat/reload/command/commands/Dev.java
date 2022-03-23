package com.invisiblecat.reload.command.commands;

import com.invisiblecat.reload.command.Command;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

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
       //ChatUtils.sendChatMessageClient("Info", ChatUtils.Type.INFO);

    }
}
