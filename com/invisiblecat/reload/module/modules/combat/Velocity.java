package com.invisiblecat.reload.module.modules.combat;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventRecivePacket;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {

    public NumberSetting horizontal = new NumberSetting("Horizontal", 0, 0, 100, 1);
    public NumberSetting vertical = new NumberSetting("Vertical", 0, 0, 100, 1);
    public ModeSetting mode = new ModeSetting("Mode", "Normal", "Normal");

    public Velocity() {
        super("Velocity", 0, Category.COMBAT, AutoDisable.NONE);
        this.addSettings(horizontal, vertical, mode);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setDisplayName("H: " + horizontal.getValueInt() + "% V: " + vertical.getValueInt() + "%");
    }

    @EventTarget
    public void onReceivePacket(EventRecivePacket event) {
        switch (mode.getSelected().toLowerCase().replaceAll("\\s", "")) {
            case "normal":
                if(event.getPacket() instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity p = (S12PacketEntityVelocity) event.getPacket();
                    if (mc.thePlayer.getEntityId() != p.getEntityID()) return;
                    if(horizontal.getValueInt() == 0 && vertical.getValueInt() == 0) {
                        p.setMotionX(0);
                        p.setMotionY(0);
                        p.setMotionZ(0);
                    }

                    p.setMotionX(p.getMotionX() * horizontal.getValueInt() / 100);
                    p.setMotionY(p.getMotionY() * vertical.getValueInt() / 100);
                    p.setMotionZ(p.getMotionZ() * horizontal.getValueInt() / 100);

                }
        }
    }
}
