package com.invisiblecat.reload.module.modules.other;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventJoinWorld;
import com.invisiblecat.reload.event.events.EventRespawn;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;

public class AutoDisable extends Module {
    public AutoDisable() {
        super("AutoDisable", 0, Category.OTHER, AutoDisable.NONE);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventTarget
    public void onJoinWorld(EventJoinWorld event) {
        Reload.instance.moduleManager.getModules().forEach(m -> {
            if(m.getAutoDisable() == AutoDisable.WORLD && m.isToggled()) {
                m.setToggled(false);
            }
        });
    }
    @EventTarget
    public void onRespawn(EventRespawn event) {
        Reload.instance.moduleManager.getModules().forEach(m -> {
            if(m.getAutoDisable() == AutoDisable.RESPAWN && m.isToggled()) {
                m.setToggled(false);
            }
        });
    }
}
