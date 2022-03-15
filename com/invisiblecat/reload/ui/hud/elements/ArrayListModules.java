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


    @Override
    public void render() {

        int moduleCount = 0;

        Reload.instance.moduleManager.getModules().sort(Comparator.comparingInt(m -> mc.fontRendererObj.getStringWidth(((Module)m).getName())).reversed());

        for (Module m : Reload.instance.moduleManager.getModules()) {
            if(m.isToggled()) {
                double offset = moduleCount*(mc.fontRendererObj.FONT_HEIGHT+4);
                mc.fontRendererObj.drawString(m.getDisplayName(), sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getName()) - 4, 4 + offset, -1);
                moduleCount++;
            }
        }
    }
}
