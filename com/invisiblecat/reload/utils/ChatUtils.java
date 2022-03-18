package com.invisiblecat.reload.utils;

import com.invisiblecat.reload.Reload;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {
    public static void sendChatMessageClient(String message, Type type) {
        message = "[" + ChatFormatting.GOLD + Reload.instance.clientName + ChatFormatting.RESET + "]" + type.getText() + message;

        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    public static void sendChatMessageServer(String message) {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
    }
    public enum Type {

        INFO("[" + ChatFormatting.BLUE + "Info" + ChatFormatting.RESET + "] "),
        ERROR("[" + ChatFormatting.RED + "Error" + ChatFormatting.RESET + "] "),
        WARN("[" + ChatFormatting.YELLOW + "Warning" + ChatFormatting.RESET + "] ");

        private final String text;

        Type(String text) {
            this.text = text;
        }
        public String getText() {
            return text;
        }
    }
}

