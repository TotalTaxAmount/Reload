package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;

public class Fly extends Module {
    public Fly() {
        super("Fly", 0, Category.MOVEMENT, AutoDisable.FLAG);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.thePlayer.capabilities.allowFlying = true;
        mc.thePlayer.capabilities.isFlying = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.capabilities.allowFlying = false;
        mc.thePlayer.capabilities.isFlying = false;
    }

}
