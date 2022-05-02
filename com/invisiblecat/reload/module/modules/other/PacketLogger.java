package com.invisiblecat.reload.module.modules.other;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventSendPacket;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

public class PacketLogger extends Module {
    private long lastC00;
    private long lastTransaction;

    public PacketLogger() {
        super("PacketLogger", 0, Category.OTHER, AutoDisable.NONE);
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        Packet<?> packet = event.getPacket();

        if (packet instanceof C0FPacketConfirmTransaction) {
            long lastPacket = System.currentTimeMillis() - lastTransaction;
            ChatUtils.sendChatMessageClient("[" + ChatFormatting.DARK_AQUA + "PacketLogger" + ChatFormatting.RESET + "] Transaction: " + ((C0FPacketConfirmTransaction) packet).getWindowId() + " UID: " +  ((C0FPacketConfirmTransaction) packet).getUid() + " " + lastPacket + "ms",
                    ChatUtils.Type.INFO);
            lastTransaction = System.currentTimeMillis();
        }
        if (packet instanceof C00PacketKeepAlive) {
            long lastPacket = System.currentTimeMillis() - lastC00;
            ChatUtils.sendChatMessageClient("[" + ChatFormatting.DARK_AQUA + "PacketLogger" + ChatFormatting.RESET + "] KeepAlive: " + ((C00PacketKeepAlive) packet).getKey() + " " + lastPacket + "ms", ChatUtils.Type.INFO);
            lastC00 = System.currentTimeMillis();
        }

        if (packet instanceof S3FPacketCustomPayload) {
            ChatUtils.sendChatMessageClient("[" + ChatFormatting.DARK_AQUA + "PacketLogger" + ChatFormatting.RESET + "] CustomPayload: " + ((S3FPacketCustomPayload) packet).getChannelName() + " Buffer:" + ((S3FPacketCustomPayload) packet).getBufferData().toString(), ChatUtils.Type.INFO);
        }
    }


}
