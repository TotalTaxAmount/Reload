package com.invisiblecat.reload;

import com.invisiblecat.reload.event.EventManager;
import org.lwjgl.opengl.Display;

public class Reload {
    public String clientName = "Reload", version = "0.1", creates = "InvisibleCat and Cosmics";

    public static Reload instance = new Reload();
    public EventManager eventManager;

    public void Start() {
        eventManager = new EventManager();

        Display.setTitle(clientName + " b" + version);

        eventManager.register(this);
    }
    public void Stop() {
        EventManager.unregister(this);
    }
}
