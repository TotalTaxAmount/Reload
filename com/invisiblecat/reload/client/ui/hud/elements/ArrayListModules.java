package com.invisiblecat.reload.client.ui.hud.elements;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.client.ui.hud.Element;
import com.invisiblecat.reload.utils.font.CustomFontUtil;
import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;

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

        Reload.instance.moduleManager.getModules().sort(Comparator.comparingInt(m -> mc.fontRendererObj.getStringWidth(((BooleanSetting)Reload.instance.moduleManager.getModuleByName("HUD").getSetting("Mod Stats")).isEnabled() ? ((Module)m).getName() + " " + ((Module)m).getDisplayName() : ((Module)m).getName())).reversed());

        for (Module m : Reload.instance.moduleManager.getModules()) {
            if(m.isEnabled() && !((BooleanSetting) m.getSetting("Hide")).isEnabled()) {
                //String modString = ((BooleanSetting)Reload.instance.moduleManager.getModuleByName("HUD").getSetting("Mod Stats")).isEnabled() ? m.getDisplayName() : m.getName();
                double offset = moduleCount*(mc.fontRendererObj.FONT_HEIGHT+4);
                boolean showModStats = ((BooleanSetting) Reload.instance.moduleManager.getModuleByName("HUD").getSetting("Mod Stats")).isEnabled();
                String text = showModStats ? m.getName() + " "+ m.getDisplayName() : m.getName();
                font.drawString(m.getName(), this.getX() + sr.getScaledWidth() - font.getWidth(text) - 4, (float) (this.getY() + 4 + offset), -1);
                if (showModStats) {
                    String idk = m.getDisplayName();
                    font.drawString(idk, this.getX() + sr.getScaledWidth() - font.getWidth(m.getDisplayName()) - 4, (float) (this.getY() + 4 + offset), new Color(0, 0, 0, 132).getRGB());
                }
                moduleCount++;
            }
        }
    }
}
