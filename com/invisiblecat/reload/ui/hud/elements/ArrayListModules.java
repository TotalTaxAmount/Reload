package com.invisiblecat.reload.ui.hud.elements;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.ui.hud.Element;

import java.util.Comparator;

public class ArrayListModules extends Element {
    public ArrayListModules() {
        super("ArrayList", 0, 0);
    }


    @Override
    public void render() {

        int moduleCount = 0;

        Reload.instance.moduleManager.getModules().sort(Comparator.comparingInt(m -> mc.fontRendererObj.getStringWidth(((BooleanSetting)Reload.instance.moduleManager.getModuleByName("HUD").getSetting("Mod Stats")).isEnabled() ? ((Module)m).getDisplayName() : ((Module)m).getName())).reversed());

        for (Module m : Reload.instance.moduleManager.getModules()) {
            if(m.isEnabled() && !((BooleanSetting) m.getSetting("Hide")).isEnabled()) {
                String modString = ((BooleanSetting)Reload.instance.moduleManager.getModuleByName("HUD").getSetting("Mod Stats")).isEnabled() ? m.getDisplayName() : m.getName();
                double offset = moduleCount*(mc.fontRendererObj.FONT_HEIGHT+4);
                mc.fontRendererObj.drawString(modString, this.getX() + sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(modString) - 4, this.getY() + 4 + offset, -1);
                moduleCount++;
            }
        }
    }
}
