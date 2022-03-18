package com.invisiblecat.reload.module.modules.movement;


import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.utils.KeyboardUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;

public class InvMove extends Module {
    public InvMove() {
        super("InvMove", 0, Category.MOVEMENT, AutoDisable.NONE);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            System.out.println("dddddddddddddddddd");
            mc.gameSettings.keyBindForward.setState(true);
            mc.gameSettings.keyBindBack.setState(true);
            mc.gameSettings.keyBindLeft.setState(true);
            mc.gameSettings.keyBindRight.setState(true);
        }
    }
}
