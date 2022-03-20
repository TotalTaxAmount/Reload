package com.invisiblecat.reload.module.modules.player;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.NumberSetting;

public class FastPlace extends Module {
    private final NumberSetting delay = new NumberSetting("Delay", 1000, 0, 10000, 1);

    public FastPlace() {
        super("FastPlace", 0, Category.PLAYER, AutoDisable.NONE);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.rightClickDelayTimer = delay.getValueFloat()/1000;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.rightClickDelayTimer = 6;
    }

}
