package com.invisiblecat.reload.module.modules.combat;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventRecivePacket;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {

    public NumberSetting horizontal = new NumberSetting("Horizontal", 0, -100, 200, 1);
    public NumberSetting vertical = new NumberSetting("Vertical", 0, 0, 200, 1);

    public Velocity() {
        super("Velocity", 0, Category.COMBAT, AutoDisable.NONE);
        addSettings(horizontal, vertical);
    }

    @EventTarget
    public void onReceivePacket(EventRecivePacket event) {
        if (mc.theWorld == null || mc.thePlayer == null) return;
        if(event.getPacket() instanceof S12PacketEntityVelocity) {
            event.setCancelled(true);
        }
    }
}
