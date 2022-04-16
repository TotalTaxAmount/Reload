package com.invisiblecat.reload.client.ui.hud.element;

import com.invisiblecat.reload.client.Reload;
import net.minecraft.client.gui.GuiScreen;

public class GuiHUDConfig extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        for (Element e : Reload.instance.hud.getElements()) {
            e.render(mouseX, mouseY);
            if (e.draggable.isHoverd(mouseX, mouseY)) {
                e.draggable.drawOutline();
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
