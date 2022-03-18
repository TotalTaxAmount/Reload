package com.invisiblecat.reload.utils;

import com.google.common.collect.Lists;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class KeyboardUtils {

    public static KeyboardUtils INSTANCE = new KeyboardUtils();

    private static final boolean[] keys = new boolean[512];
    private static final boolean[] buttons = new boolean[16];

    public List<String> keyList = Lists.newArrayList();
    public List<Integer> keyList2 = Lists.newArrayList();

    public KeyboardUtils() {
        addKeys();
    }

    public static void setKeyState(int key, boolean pressed) {
        if (key >= 0 && key < keys.length) keys[key] = pressed;
    }

    public static void setButtonState(int button, boolean pressed) {
        if (button >= 0 && button < buttons.length) buttons[button] = pressed;
    }

    public static void setKeyState(KeyBinding bind, boolean pressed) {
        setKeyState(Keys.getKey(bind), pressed);
    }

    public static boolean isPressed(KeyBinding bind) {
        return isKeyPressed(Keys.getKey(bind));
    }

    public static boolean isKeyPressed(int key) {
        if (key == Keyboard.KEY_UNLABELED) return false;
        return key < keys.length && keys[key];
    }

    public static boolean isButtonPressed(int button) {
        if (button == -1) return false;
        return button < buttons.length && buttons[button];
    }

    public static int getKey(String key) {
        switch (key.toLowerCase()) {
            case ".":
                return Keyboard.KEY_PERIOD;
            case "1":
                return Keyboard.KEY_1;
            case "2":
                return Keyboard.KEY_2;
            case "3":
                return Keyboard.KEY_3;
            case "4":
                return Keyboard.KEY_4;
            case "5":
                return Keyboard.KEY_5;
            case "6":
                return Keyboard.KEY_6;
            case "7":
                return Keyboard.KEY_7;
            case "8":
                return Keyboard.KEY_8;
            case "9":
                return Keyboard.KEY_9;
            case "0":
                return Keyboard.KEY_0;
            case ",":
                return Keyboard.KEY_COMMA;
            case "a":
                return Keyboard.KEY_A;
            case "b":
                return Keyboard.KEY_B;
            case "c":
                return Keyboard.KEY_C;
            case "d":
                return Keyboard.KEY_D;
            case "e":
                return Keyboard.KEY_E;
            case "f":
                return Keyboard.KEY_F;
            case "g":
                return Keyboard.KEY_G;
            case "h":
                return Keyboard.KEY_H;
            case "i":
                return Keyboard.KEY_I;
            case "j":
                return Keyboard.KEY_J;
            case "k":
                return Keyboard.KEY_K;
            case "l":
                return Keyboard.KEY_L;
            case "m":
                return Keyboard.KEY_M;
            case "n":
                return Keyboard.KEY_N;
            case "o":
                return Keyboard.KEY_O;
            case "p":
                return Keyboard.KEY_P;
            case "q":
                return Keyboard.KEY_Q;
            case "r":
                return Keyboard.KEY_R;
            case "s":
                return Keyboard.KEY_S;
            case "t":
                return Keyboard.KEY_T;
            case "u":
                return Keyboard.KEY_U;
            case "v":
                return Keyboard.KEY_V;
            case "w":
                return Keyboard.KEY_W;
            case "x":
                return Keyboard.KEY_X;
            case "y":
                return Keyboard.KEY_Y;
            case "z":
                return Keyboard.KEY_Z;
            case "`":
                return Keyboard.KEY_GRAVE;
            case "rshift":
                return Keyboard.KEY_RSHIFT;
            case "lshift":
                return Keyboard.KEY_LSHIFT;
            case "rctrl":
                return Keyboard.KEY_RCONTROL;
            case "lctrl":
                return Keyboard.KEY_LCONTROL;
            case "\\":
                return Keyboard.KEY_BACKSLASH;
            case "space":
                return Keyboard.KEY_SPACE;
            case ";":
                return Keyboard.KEY_SEMICOLON;
            case "-":
                return Keyboard.KEY_MINUS;
            case "=":
                return Keyboard.KEY_EQUALS;
            case "[":
                return Keyboard.KEY_LBRACKET;
            case "]":
                return Keyboard.KEY_RBRACKET;
            case "/":
                return Keyboard.KEY_SLASH;
            case "'":
                return Keyboard.KEY_APOSTROPHE;
        }
        return Keyboard.KEY_UNLABELED;
    }

    public static int getKey(char key) {
        String stringKey = String.valueOf(key);
        return getKey(stringKey);
    }


    public void addKeys() {
        keyList.add(".");
        keyList.add("1");
        keyList.add("2");
        keyList.add("3");
        keyList.add("4");
        keyList.add("5");
        keyList.add("6");
        keyList.add("7");
        keyList.add("8");
        keyList.add("9");
        keyList.add("0");
        keyList.add(",");
        keyList.add("a");
        keyList.add("b");
        keyList.add("c");
        keyList.add("d");
        keyList.add("e");
        keyList.add("f");
        keyList.add("g");
        keyList.add("h");
        keyList.add("i");
        keyList.add("j");
        keyList.add("k");
        keyList.add("l");
        keyList.add("m");
        keyList.add("n");
        keyList.add("o");
        keyList.add("p");
        keyList.add("q");
        keyList.add("r");
        keyList.add("s");
        keyList.add("t");
        keyList.add("u");
        keyList.add("v");
        keyList.add("w");
        keyList.add("x");
        keyList.add("y");
        keyList.add("z");
        keyList.add("`");
        keyList.add("rshift");
        keyList.add("lshift");
        keyList.add("rctrl");
        keyList.add("lctrl");
        keyList.add("space");
        keyList.add(";");
        keyList.add("-");
        keyList.add("=");
        keyList.add("[");
        keyList.add("]");
        keyList.add("/");

        keyList2.add(getKey("."));
        keyList2.add(getKey("1"));
        keyList2.add(getKey("2"));
        keyList2.add(getKey("3"));
        keyList2.add(getKey("4"));
        keyList2.add(getKey("5"));
        keyList2.add(getKey("6"));
        keyList2.add(getKey("7"));
        keyList2.add(getKey("8"));
        keyList2.add(getKey("9"));
        keyList2.add(getKey("0"));
        keyList2.add(getKey(","));
        keyList2.add(getKey("a"));
        keyList2.add(getKey("b"));
        keyList2.add(getKey("c"));
        keyList2.add(getKey("d"));
        keyList2.add(getKey("e"));
        keyList2.add(getKey("f"));
        keyList2.add(getKey("g"));
        keyList2.add(getKey("h"));
        keyList2.add(getKey("i"));
        keyList2.add(getKey("j"));
        keyList2.add(getKey("k"));
        keyList2.add(getKey("l"));
        keyList2.add(getKey("m"));
        keyList2.add(getKey("n"));
        keyList2.add(getKey("o"));
        keyList2.add(getKey("p"));
        keyList2.add(getKey("q"));
        keyList2.add(getKey("r"));
        keyList2.add(getKey("s"));
        keyList2.add(getKey("t"));
        keyList2.add(getKey("u"));
        keyList2.add(getKey("v"));
        keyList2.add(getKey("w"));
        keyList2.add(getKey("x"));
        keyList2.add(getKey("y"));
        keyList2.add(getKey("z"));
        keyList2.add(getKey("`"));
        keyList2.add(getKey("rshift"));
        keyList2.add(getKey("lshift"));
        keyList2.add(getKey("rctrl"));
        keyList2.add(getKey("lctrl"));
        keyList2.add(getKey("space"));
        keyList2.add(getKey(";"));
        keyList2.add(getKey("-"));
        keyList2.add(getKey("="));
        keyList2.add(getKey("["));
        keyList2.add(getKey("]"));
        keyList2.add(getKey("/"));
    }
}

