package com.invisiblecat.reload.utils.chat;

import com.invisiblecat.reload.Reload;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.sun.istack.internal.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatUtils {
    public static void sendChatMessageClient(String message, Type type) {
        message = "[" + ChatFormatting.GOLD + Reload.instance.clientName + ChatFormatting.RESET + "]" + type.getText() + message;

        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public static void sendChatMessageServer(String message) {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
    }
    public static void sendChatMessageServerWithDelay(String message, int delay) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> ChatUtils.sendChatMessageServer(message), delay, TimeUnit.MILLISECONDS);
    }
    public static void sendChatMessageClientWithDelay(String message, Type type, int delay) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> ChatUtils.sendChatMessageClient(message, type), delay, TimeUnit.MILLISECONDS);
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

