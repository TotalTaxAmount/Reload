package com.invisiblecat.reload.module.modules.render;

import com.invisiblecat.reload.client.ui.clickgui.ClickGUI;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUIModule extends Module {
    public ClickGUIModule() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER, AutoDisable.NONE);
        this.getBooleanSetting("Hide").setEnabled(true);
    }

    public void onEnable() {
        if (mc.currentScreen == null) {
            mc.displayGuiScreen(new ClickGUI());
        }
        this.toggle(false);
    }


}
