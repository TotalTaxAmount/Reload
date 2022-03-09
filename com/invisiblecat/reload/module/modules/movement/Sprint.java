package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.module.setting.Setting;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Keyboard.KEY_N, Category.MOVEMENT);
    }
    @Override
    public void setup() {
        Reload.instance.settingManager.addSetting(new Setting("Multi-directional", this, null, false));
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(Reload.instance.settingManager.getSetting(this, "Multi-directional").getBooleanVal()) {
            mc.thePlayer.setSprinting(true);
        } else {
            if (!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0)
                mc.thePlayer.setSprinting(true);
        }
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
