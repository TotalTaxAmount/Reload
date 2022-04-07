package com.invisiblecat.reload.client.ui.hud.elements;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.Element;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.utils.font.CustomFontUtil;
import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;

import java.util.Comparator;

public class ArrayListModules extends Element {
    private static final TTFFontRenderer font = CustomFontUtil.FONT_MANAGER.getFont("idk 18");

    public ArrayListModules() {
        super("ArrayList", 0, 0, 10, 10);
        this.setY(0);
        this.setX(sr.getScaledWidth());
    }


    @Override
    public void render() {

        int moduleCount = 0;
        boolean showModStats = ((BooleanSetting) Reload.instance.moduleManager.getModuleByName("HUD").getSetting("Mod Stats")).isEnabled();

        Reload.instance.moduleManager.getModules().sort(Comparator.comparingInt((Module m) -> mc.fontRendererObj.getStringWidth(showModStats ? m.getName() + " " + m.getDisplayName() : m.getName())).reversed());

        for (Module m : Reload.instance.moduleManager.getModules()) {
            if(m.isEnabled() && !((BooleanSetting) m.getSetting("Hide")).isEnabled()) {
                String modString = ((BooleanSetting)Reload.instance.moduleManager.getModuleByName("HUD").getSetting("Mod Stats")).isEnabled() ? m.getDisplayName() : m.getName();
                double offset = moduleCount*(mc.fontRendererObj.FONT_HEIGHT+4);
                String text = showModStats ? m.getName() + " "+ m.getDisplayName() : m.getName();

                mc.fontRendererObj.drawString(text, (int) (this.getX() - font.getWidth(text) - 4), (float) (this.getY() + 4 + offset), -1);
                moduleCount++;
            }
        }
    }
}
