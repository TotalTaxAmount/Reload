package com.invisiblecat.reload.utils;


import org.lwjgl.input.Keyboard;

public class KeyboardUtils {
    public static boolean isKeyPressed(int keyCode) { // Any key code from the KeyEvent class
        return Keyboard.isKeyDown(keyCode);
    }
}

