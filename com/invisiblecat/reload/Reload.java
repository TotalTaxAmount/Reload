package com.invisiblecat.reload;

import com.invisiblecat.reload.event.EventManager;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.module.ModuleManager;
import com.invisiblecat.reload.event.events.EventKey;
import org.lwjgl.opengl.Display;


public class Reload {
    public String clientName = "Reload", version = "0.1", creates = "InvisibleCat and Cosmics";

    public static Reload instance = new Reload();
    public EventManager eventManager;
    public ModuleManager moduleManager;

    public void Start() {
        eventManager = new EventManager();
        moduleManager = new ModuleManager();

        Display.setTitle(clientName + " b" + version);

        eventManager.register(this);
    }
    public void Stop() {
        EventManager.unregister(this);
    }

    @EventTarget
    public void onKey(EventKey event) {
        moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
    }
}
