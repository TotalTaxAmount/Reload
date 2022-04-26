package com.invisiblecat.reload.module.modules.other;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventSendPacket;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class ClientSpoofer extends Module {
    private ModeSetting client = new ModeSetting("Client", "Lunar", "Lunar", "Forge");

    public ClientSpoofer() {
        super("ClientSpoofer", 0, Category.OTHER, AutoDisable.NONE);
    }

    @EventTarget
    public void onPacketSend(EventSendPacket event) {
        if (event.getPacket() instanceof C17PacketCustomPayload) {
            C17PacketCustomPayload packet = (C17PacketCustomPayload) event.getPacket();
            switch (client.getSelected()) {
                case "Lunar": {
                    packet.setChannel("REGISTER");
                    packet.setData(createBuffer("Lunar-Client", false));
                } case "Forge": {
                    packet.setData(createBuffer("FML", true));
                }
            }

        }
    }

    private PacketBuffer createBuffer(String data, boolean b) {
        if (b)
            return new PacketBuffer(Unpooled.buffer()).writeString(data);
        else
            return new PacketBuffer(Unpooled.wrappedBuffer(data.getBytes()));
    }
}
