package com.invisiblecat.reload.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class PacketUtils {

    public static void sendPacketNoEvent(Packet<?> packet) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueueNoEvent(packet);
    }
    public static void sendPacket(Packet<?> packet) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
    }
}
