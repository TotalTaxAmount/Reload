package com.invisiblecat.reload.ui.elements;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.ui.Element;
import net.minecraft.client.Minecraft;

import java.util.Collections;
import java.util.Comparator;

public class ArrayListModules extends Element {
    public ArrayListModules() {
        super("ArrayList", 0, 0);
    }

    public static class ModuleComparator implements Comparator<Module> {
        @Override
        public int compare(Module a, Module b) {
            if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(a.getName()) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(b.getName())) {
                return -1;
            }
            if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(a.getName()) < Minecraft.getMinecraft().fontRendererObj.getStringWidth(b.getName())) {
                return 1;
            }
            return 0;
        }
    }

    @Override
    public void render() {

        int moduleCount = 0;

        Collections.sort(Reload.instance.moduleManager.getModules(), new ModuleComparator());

        for (Module m : Reload.instance.moduleManager.getModules()) {
            if(m.isToggled()) {
                double offset = moduleCount*(mc.fontRendererObj.FONT_HEIGHT+4);
                mc.fontRendererObj.drawString(m.getDisplayName(), sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getName()) - 4, 4 + offset, -1);
                moduleCount++;
            }
        }
    }
}
