package com.invisiblecat.reload.module.modules.other;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.utils.KeyboardUtils;
import net.minecraft.client.gui.GuiChat;

import java.awt.event.KeyEvent;

public class AutoCommand extends Module {
    public AutoCommand() {
        super("AutoCommand", 0, Category.OTHER, AutoDisable.NONE);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(KeyboardUtils.isKeyPressed(KeyEvent.VK_PERIOD))
            mc.displayGuiScreen(new GuiChat());
    }
}
