package com.invisiblecat.reload.client.ui.hud.element;

import com.invisiblecat.reload.client.Reload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiHUDConfig extends GuiScreen {

    private final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        for (Element e : Reload.instance.hud.getElements()) {
            e.render(mouseX, mouseY);
            if (e.draggable.getxPosition() < 0) {
                e.draggable.setxPosition(0);
            } else if (e.draggable.getxPosition() > sr.getScaledWidth() - e.draggable.getWidth()) {
                e.draggable.setxPosition(sr.getScaledWidth() - e.draggable.getWidth());
            }
            if (e.draggable.getyPosition() < 0) {
                e.draggable.setyPosition(0);
            } else if (e.draggable.getyPosition() > sr.getScaledHeight() - e.draggable.getHeight()) {
                e.draggable.setyPosition(sr.getScaledHeight() - e.draggable.getHeight());

            }

            if (e.draggable.isHoverd(mouseX, mouseY)) {
                e.draggable.drawOutline();
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);

        // if an element is being dragged, stop all other elements from being dragged
    }
}
