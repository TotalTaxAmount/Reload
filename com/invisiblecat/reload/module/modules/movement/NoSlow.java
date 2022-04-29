package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;

public class NoSlow extends Module {
    private ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla");

    public NoSlow() {
        super("NoSlow", 0, Category.MOVEMENT, AutoDisable.FLAG);
        this.addSettings(mode);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setDisplayName(mode.getSelected());
    }

}
