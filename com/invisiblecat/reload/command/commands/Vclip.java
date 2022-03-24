package com.invisiblecat.reload.command.commands;

import com.invisiblecat.reload.command.Command;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import net.minecraft.client.Minecraft;


public class Vclip extends Command {
    public Vclip() {
        super("vclip", "clips you up/down", ".vclip [distance]");
    }

    @Override
    public void onCommand(String[] args, String command) {
        System.out.println(args[0]);
        try {
            Minecraft.getMinecraft().thePlayer.setPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + Double.parseDouble(args[0]), Minecraft.getMinecraft().thePlayer.posZ);
            ChatUtils.sendChatMessageClient("Successfully clipped you " + args[0] + " blocks.", ChatUtils.Type.INFO);
        } catch (Exception e) {
            ChatUtils.sendChatMessageClient("Error clipping player", ChatUtils.Type.ERROR);
            e.printStackTrace();
        }
    }
}

