package com.invisiblecat.reload.module.modules.combat;

import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import org.lwjgl.input.Keyboard;

public class AntiBot extends Module {
    public AntiBot() {
        super("AntiBot", 0, Category.COMBAT, AutoDisable.NONE);
    }
}
