package com.invisiblecat.reload.client.ui.hud;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.clickgui.ClickGUI;
import com.invisiblecat.reload.client.ui.hud.element.Element;
import com.invisiblecat.reload.client.ui.hud.element.elements.ArrayListModules;
import com.invisiblecat.reload.client.ui.hud.element.elements.Info;
import com.invisiblecat.reload.client.ui.hud.element.elements.TargetHUD;
import com.invisiblecat.reload.client.ui.hud.element.elements.Watermark;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationManager;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;

public class HUD {
    private final ArrayList<Element> elements = new ArrayList<Element>();

    private static Color clientColor = new Color(30, 212, 218);

    public HUD() {
        elements.add(new Watermark());
        elements.add(new ArrayListModules());
        elements.add(new Info());
        elements.add(new TargetHUD());
    }

    public void render() {
        NotificationManager.render();
        if(!Minecraft.getMinecraft().gameSettings.showDebugInfo && Reload.instance.moduleManager.getModuleByName("HUD").isEnabled() && !(Minecraft.getMinecraft().currentScreen instanceof ClickGUI)) {
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
