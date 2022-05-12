package com.invisiblecat.reload.module.modules.render;

import com.invisiblecat.reload.client.ui.clickgui.reload.ClickGUI;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.client.ui.clickgui.csgo.CSGOGui;
import org.lwjgl.input.Keyboard;

public class ClickGUIModule extends Module {
    private ModeSetting mode = new ModeSetting("Mode", "CSGO", "CSGO", "Normal");

    public ClickGUIModule() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER, AutoDisable.NONE);
        this.getBooleanSetting("Hide").setEnabled(true);
        this.addSettings(mode);
    }

    public void onEnable() {
        if (mc.currentScreen == null) {
            switch (mode.getSelected()) {
                case "CSGO":
                    mc.displayGuiScreen(new CSGOGui());
                    break;
                case "Normal":
                    mc.displayGuiScreen(new ClickGUI());
                    break;
            }
        }
        System.out.println("ClickGUI enabled");
        this.toggle(false);
    }


}
