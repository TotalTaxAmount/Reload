package com.invisiblecat.reload.module.modules.combat;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.event.events.EventRecivePacket;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {

    public NumberSetting horizontal = new NumberSetting("Horizontal", 0, 0, 100, 1);
    public NumberSetting vertical = new NumberSetting("Vertical", 0, 0, 100, 1);
    public ModeSetting mode = new ModeSetting("Mode", "Simple", "Cancel", "ACCv4", "Simple");

    public Velocity() {
        super("Velocity", 0, Category.COMBAT, AutoDisable.NONE);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setDisplayName(this.getName() + ChatFormatting.GRAY + " H: " + horizontal.getValueInt() + "% V: " + vertical.getValueInt() + "%");
    }

    @EventTarget
    public void onReceivePacket(EventRecivePacket event) {
        switch (mode.getSelected().toLowerCase().replaceAll("\\s", "")) {
            case "cancel":
                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    final S12PacketEntityVelocity p = (S12PacketEntityVelocity) event.getPacket();
                    if (this.mc.thePlayer != null && this.mc.thePlayer.getEntityId() == p.getEntityID()) {
                        event.setCancelled(true);
                    }
                } else if (event.getPacket() instanceof S27PacketExplosion) {
                    event.setCancelled(true);
                }
                break;
            case "accv4":
                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity p = (S12PacketEntityVelocity) event.getPacket();
                    mc.thePlayer.setVelocity(0.0D, p.getMotionY() / 8000.0D, p.getMotionZ() / 8000.0D / 2.0D);
                }
                break;
            case "simple":
                if(event.getPacket() instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity p = (S12PacketEntityVelocity) event.getPacket();
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
