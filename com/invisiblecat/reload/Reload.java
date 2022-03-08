package com.invisiblecat.reload;

import org.lwjgl.opengl.Display;

public class Reload {
    public String clientName = "Reload", version = "0.1", creates = "InvisibleCat and Cosmics";

    public static Reload instance = new Reload();

    public void Start() {
        Display.setTitle(clientName + " b" + version);
    }
}
