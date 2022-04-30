package com.invisiblecat.reload.module.modules.combat;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.event.events.EventSendPacket;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.utils.PacketUtils;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module {
    private ModeSetting mode = new ModeSetting("Mode", "Packet", "Position", "Packet");

    private int count;

    public Criticals() {
        super("Criticals", 0, Category.COMBAT, AutoDisable.NONE);
        this.addSettings(mode);
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        switch (mode.getSelected()) {
            case "Position":
                if (mc.thePlayer.onGround) {
                    count++;
                    switch (count) {
                        case 1:
                            event.setY(event.getY() + 0.0625 + Math.random() / 100);
                            event.setGround(false);
                            break;

                        case 2:
                            event.setY(event.getY() + 0.03125 + Math.random() / 100);
                            event.setGround(false);
                            break;

                        case 3:
                            count = 0;
                            break;

                    }
                }
                break;

        }
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        if (mode.is("Packet")) {
            if (event.getPacket() instanceof C02PacketUseEntity) {
                C02PacketUseEntity packet = (C02PacketUseEntity)event.getPacket();
                if(packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0625, mc.thePlayer.posZ, false));
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - mc.thePlayer.fallDistance, mc.thePlayer.posZ, true));
                }
            }
        }
    }
}
