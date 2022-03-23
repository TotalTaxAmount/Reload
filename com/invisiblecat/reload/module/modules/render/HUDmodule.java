package com.invisiblecat.reload.module.modules.render;

import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;

public class HUDmodule extends Module {
    private final BooleanSetting extra = new BooleanSetting("Mod Stats", true);

    public HUDmodule() {
        super("HUD", 0, Category.RENDER, AutoDisable.NONE);
        this.addSettings(extra);
        this.setEnabled(true);
    }
}
