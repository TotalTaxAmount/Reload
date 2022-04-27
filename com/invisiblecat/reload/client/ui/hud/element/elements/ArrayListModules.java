package com.invisiblecat.reload.client.ui.hud.element.elements;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.element.Element;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.utils.font.CustomFontUtil;
import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;
import org.lwjgl.opengl.GL11;

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
        Reload.instance.moduleManager.getModules().sort(
                Comparator.comparingInt(
                        (Module m) -> (int)font.getWidth(showModStats ? m.getName() + " " + m.getDisplayName() : m.getName())).reversed());

        for (Module m : Reload.instance.moduleManager.getModules()) {
            if (m.isEnabled()) {
                if (this.getX() + this.draggable.getWidth()/2 < sr.getScaledWidth() / 2) {
                    // left side
                    if (showModStats) {
                        font.drawString(m.getName(), this.getX(), this.getY() + (moduleCount * font.getHeight()), Color.WHITE.getRGB());
                        font.drawString(m.getDisplayName(), this.getX() + font.getWidth(m.getName()), this.getY() + (moduleCount * font.getHeight()), Color.DARK_GRAY.getRGB());

                    } else {
                        font.drawString(m.getName(), this.getX(), this.getY() + (moduleCount * font.getHeight()), Color.WHITE.getRGB());
                    }
                } else {
                    // right side
                    if (showModStats) {
                        font.drawString(m.getDisplayName(), this.getX() - font.getWidth(m.getDisplayName()) + this.draggable.getWidth(), this.getY() + (moduleCount * font.getHeight()), Color.DARK_GRAY.getRGB());
                        font.drawString(m.getName(), this.getX() - font.getWidth(m.getName() + " " + m.getDisplayName()) + this.draggable.getWidth(), this.getY() + (moduleCount * font.getHeight()), Color.WHITE.getRGB());
                    } else {
                        font.drawString(m.getName(), this.getX() - font.getWidth(m.getName()), this.getY() + (moduleCount * font.getHeight()), Color.WHITE.getRGB());
                    }
                    // blur the background with GSGL

                }


                moduleCount++;

            }
        }
        this.draggable.setWidth((int) font.getWidth(showModStats ? Reload.instance.moduleManager.getModules().get(0).getName() + " " + Reload.instance.moduleManager.getModules().get(0).getDisplayName() : Reload.instance.moduleManager.getModules().get(0).getName()));
        this.draggable.setHeight((int) (font.getHeight() * moduleCount));
    }

    private void drawBlur(String text, int padding, int x, int y, int color) {
       g

    }

}
