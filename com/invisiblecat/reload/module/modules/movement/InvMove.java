package com.invisiblecat.reload.module.modules.movement;


import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.utils.KeyboardUtils;

import net.minecraft.client.gui.GuiChat;

public class InvMove extends Module {

    private final BooleanSetting jump = new BooleanSetting("Jump", true);
    private final BooleanSetting sneak = new BooleanSetting("Sneak", true);


    public InvMove() {
        super("InvMove", 0, Category.MOVEMENT, AutoDisable.NONE);
        this.addSettings(jump, sneak);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            mc.gameSettings.keyBindForward.setState(KeyboardUtils.isKeyPressed(mc.gameSettings.keyBindForward.getKeyCode()));
            mc.gameSettings.keyBindBack.setState(KeyboardUtils.isKeyPressed(mc.gameSettings.keyBindBack.getKeyCode()));
            mc.gameSettings.keyBindLeft.setState(KeyboardUtils.isKeyPressed(mc.gameSettings.keyBindLeft.getKeyCode()));
            mc.gameSettings.keyBindRight.setState(KeyboardUtils.isKeyPressed(mc.gameSettings.keyBindRight.getKeyCode()));
            if(jump.isEnabled()) mc.gameSettings.keyBindJump.setState(KeyboardUtils.isKeyPressed(mc.gameSettings.keyBindJump.getKeyCode()));
            if(sneak.isEnabled()) mc.gameSettings.keyBindSneak.setState(KeyboardUtils.isKeyPressed(mc.gameSettings.keyBindSneak.getKeyCode()));


        }
    }

}
