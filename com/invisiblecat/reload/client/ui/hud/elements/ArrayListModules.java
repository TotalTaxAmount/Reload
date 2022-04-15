package com.invisiblecat.reload.client.ui.hud.elements;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.Element;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.utils.font.CustomFontUtil;
import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;
import net.minecraft.client.gui.Gui;

import java.util.Comparator;

public class ArrayListModules extends Element {
    private static final TTFFontRenderer font = CustomFontUtil.FONT_MANAGER.getFont("idk 18");

    public ArrayListModules() {
        super("ArrayList", 10, 10, 10, 10);
    }


    @Override
    public void render() {

        int moduleCount = 0;
        boolean showModStats = ((BooleanSetting) Reload.instance.moduleManager.getModuleByName("HUD").getSetting("Mod Stats")).isEnabled();

        Reload.instance.moduleManager.getModules().sort(Comparator.comparingInt((Module m) -> mc.fontRendererObj.getStringWidth(showModStats ? m.getName() + " " + m.getDisplayName() : m.getName())).reversed());

//        for (Module m : Reload.instance.moduleManager.getModules()) {
//            if(m.isEnabled() && !((BooleanSetting) m.getSetting("Hide")).isEnabled()) {
//                double offset = moduleCount*(mc.fontRendererObj.FONT_HEIGHT+4);
//                System.out.println("Render: " + m.getName());
//                if(showModStats) {
//                    font.drawString(m.getName() + " " + m.getDisplayName(), (int) (getX() + getWidth() / 2 - font.getWidth(m.getName() + " " + m.getDisplayName()) / 2), (int) (getY() + offset), 0xFFFFFF);
//                }
//                else {
//                    font.drawString(m.getName(), (int) (getX() + getWidth() / 2 - font.getWidth(m.getName()) / 2), (int) (getY() + offset), 0xFFFFFF);
//                }
//                moduleCount++;
//
//            }
//        }
    }
}
