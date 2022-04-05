package com.invisiblecat.reload.client.ui.hud;

import com.invisiblecat.reload.client.Reload;
import net.minecraft.client.gui.GuiScreen;

public class GuiHUDConfig extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        for (Element e : Reload.instance.hud.getElements()) {
            // TODO: draw draggable
            e.draggable.draw(mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
