package com.invisiblecat.reload.utils;

import com.invisiblecat.reload.Reload;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {
    public static void sendChatMessageClient(String message) {
        message = "[" + ChatFormatting.GOLD + Reload.instance.clientName + ChatFormatting.RESET + "] " + message;

        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    public static void sendChatMessageServer(String message) {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
    }
}
