package com.invisiblecat.reload.client.ui.hud.elements;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.Element;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.utils.font.CustomFontUtil;
import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;
import com.invisiblecat.reload.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.Comparator;

public class ArrayListModules extends Element {
    private static final TTFFontRenderer font = CustomFontUtil.FONT_MANAGER.getFont("idk 18");

    public ArrayListModules() {
        super("ArrayList", 0, 0, 10, 10);
    }



    @Override
    public void render() {

        int moduleCount = 0;
        boolean showModStats = ((BooleanSetting) Reload.instance.moduleManager.getModuleByName("HUD").getSetting("Mod Stats")).isEnabled();

       // Reload.instance.moduleManager.getModules().sort(Comparator.comparingInt((Module m) -> mc.fontRendererObj.getStringWidth(showModStats ? m.getName() + " " + m.getDisplayName() : m.getName())).reversed());
        // Sort the modules by length of name, if showModStats is enabled, add the display name to the name.
        Reload.instance.moduleManager.getModules().sort(Comparator.comparingInt((Module m) ->
                mc.fontRendererObj.getStringWidth(showModStats ? m.getName() + " " + m.getDisplayName() : m.getName())).reversed());
        Reload.instance.moduleManager.getModules().forEach(m -> {
                  System.out.println(m.getName() + " " + mc.fontRendererObj.getStringWidth(showModStats ? m.getName() + " " + m.getDisplayName() : m.getName()));
                });

        // Render each module
        // if showModStats is true, render the module display name after the module name on the same line in a different color
        for (Module m : Reload.instance.moduleManager.getModules()) {
            if (m.isEnabled()) {
                // check if the element is on the left or right side of the screen
                if (this.getX() < sr.getScaledWidth() / 2) {
                    if (showModStats) {
                        font.drawString(m.getName(), this.getX(), this.getY() + (moduleCount * font.getHeight()), Color.WHITE.getRGB());
                        // then render the display name in a different color after the module name
                        font.drawString(m.getDisplayName(), this.getX() + font.getWidth(m.getName()), this.getY() + (moduleCount * font.getHeight()), Color.DARK_GRAY.getRGB());
                    } else {
                        font.drawString(m.getName(), this.getX(), this.getY() + (moduleCount * font.getHeight()), Color.WHITE.getRGB());
                    }
                } else {
                    // if the element is on the right side of the screen, render the modules so that they are aligned to the right side of the screen
                    if (showModStats) {
                        font.drawString(m.getDisplayName(), this.getX() - font.getWidth(m.getDisplayName()), this.getY() + (moduleCount * font.getHeight()), Color.DARK_GRAY.getRGB());
                        // then render the display name in a different color after the module name
                        font.drawString(m.getName(), this.getX() - font.getWidth(m.getDisplayName()) - font.getWidth(m.getName()), this.getY() + (moduleCount * font.getHeight()), Color.WHITE.getRGB());
                    } else {
                        font.drawString(m.getName(), this.getX() - font.getWidth(m.getName()), this.getY() + (moduleCount * font.getHeight()), Color.WHITE.getRGB());
                    }

                }
                moduleCount++;

            }
        }
        // set the width to the width of this biggest module name
        this.draggable.setWidth((int) font.getWidth(showModStats ? Reload.instance.moduleManager.getModules().get(0).getName() + " " + Reload.instance.moduleManager.getModules().get(0).getDisplayName() : Reload.instance.moduleManager.getModules().get(0).getName()));
        // set the height to the height of the font times the number of modules
        this.draggable.setHeight((int) (font.getHeight() * moduleCount));



    }
}
