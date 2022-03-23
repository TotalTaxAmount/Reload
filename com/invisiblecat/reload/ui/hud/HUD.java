package com.invisiblecat.reload.ui.hud;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.ui.hud.elements.*;
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
        if(!Minecraft.getMinecraft().gameSettings.showDebugInfo && Reload.instance.moduleManager.getModuleByName("HUD").isEnabled()) {
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
}
