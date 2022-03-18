package com.invisiblecat.reload.utils;

import net.minecraft.client.settings.KeyBinding;

public class Keys {
    public static int getKey(KeyBinding bind) {
        return bind.getKeyCode();
    }
}
