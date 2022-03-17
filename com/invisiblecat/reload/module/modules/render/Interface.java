package com.invisiblecat.reload.module.modules.render;

import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.utils.ChatUtils;
import org.lwjgl.input.Keyboard;

public class Interface extends Module {
    public Interface() {
        super("Interface", 0, Category.OTHER, AutoDisable.NONE);
        this.setToggled(true);
    }

    @Override
    public void onToggle() {
    }

    @Override
    public void setToggled(boolean t) {

    }

    @Override
    public void toggle(boolean onToggle) {
    }

    @Override
    public void setKey(int key) {
        ChatUtils.sendChatMessageClient("[Bind] Error cannot set key " + Keyboard.getKeyName(key).toUpperCase() + " to this module");
    }
}
