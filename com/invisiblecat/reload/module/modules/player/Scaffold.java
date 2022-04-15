package com.invisiblecat.reload.module.modules.player;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPostMotionUpdate;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class Scaffold extends Module {
    private ModeSetting mode = new ModeSetting("Mode", "Normal", "Normal", "Hypixel");
    private BooleanSetting jump = new BooleanSetting("Jump", false);
    private BooleanSetting keepY = new BooleanSetting("Keep Y", false);
    private BooleanSetting sprint = new BooleanSetting("Sprint", false);

    public Scaffold() {
        super("Scaffold", 0, Category.PLAYER, AutoDisable.WORLD);
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        if (keepY.isEnabled() && mc.thePlayer.isCollidedVertically) {
            mc.thePlayer.motionY = 0.0D;
        }

    }




}
