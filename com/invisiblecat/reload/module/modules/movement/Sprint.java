package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventJoinWorld;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    private BooleanSetting omni = new BooleanSetting("Omni directional", false);


    public Sprint() {
        super("Sprint", Keyboard.KEY_N, Category.MOVEMENT, AutoDisable.NONE);
        this.addSettings(omni);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if ((!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0) || omni.isEnabled())
            mc.thePlayer.setSprinting(true);
    }

    @EventTarget
    public void onJoinWorld(EventJoinWorld event) {
        mc.thePlayer.setSprinting(false);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.setSprinting(false);
    }
    @Override
    public void onEnable() {
        super.onEnable();
    }
}
