package com.invisiblecat.reload.client.ui.hud;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.elements.*;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationManager;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventUpdate;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;

public class HUD {
    private final ArrayList<Element> elements = new ArrayList<Element>();

    private static Color clientColor = new Color(119, 175, 36);

    public HUD() {
        elements.add(new Watermark());
        elements.add(new ArrayListModules());
        elements.add(new Info());
    }

    public void render() {
        NotificationManager.render();
        if(!Minecraft.getMinecraft().gameSettings.showDebugInfo && Reload.instance.moduleManager.getModuleByName("HUD").isEnabled() && !(Minecraft.getMinecraft().currentScreen instanceof GuiHUDConfig)) {
            for (Element e : elements) {
                if(e.isToggled()) {
                    e.render();
                }
            }
        }
    }

    public static Color getClientColor() {
        return clientColor;
    }


    public void setClientColor(Color clientColor) {
        HUD.clientColor = clientColor;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

}
