package com.invisiblecat.reload.module.modules.render;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;

public class Animations extends Module {
    private ModeSetting mode = new ModeSetting("Mode", "1.7", "1.7", "Cool", "Skid");
    private BooleanSetting always = new BooleanSetting("Always", true);

    public Animations() {
        super("Animations", 0, Category.RENDER, AutoDisable.NONE);
        this.addSettings(mode, always);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setDisplayName(mode.getSelected());
    }
}
