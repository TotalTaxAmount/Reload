package com.invisiblecat.reload.module.modules.other;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventKey;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import net.minecraft.client.gui.GuiChat;


public class AutoCommand extends Module {
    public AutoCommand() {
        super("AutoCommand", 0, Category.OTHER, AutoDisable.NONE);
    }

    @EventTarget
    public void onKey(EventKey event) {
        if(event.getKey() == 52 && mc.currentScreen == null) {
            mc.displayGuiScreen(new GuiChat("."));
        }
    }
}
