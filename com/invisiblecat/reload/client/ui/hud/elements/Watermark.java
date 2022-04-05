package com.invisiblecat.reload.client.ui.hud.elements;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.Element;
import com.invisiblecat.reload.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;

public class Watermark extends Element {
    public Watermark() {
        super("watermark", 4, 4, Minecraft.getMinecraft().fontRendererObj.getStringWidth("watermark"), 9);
    }

    @Override
    public void render() {
        mc.fontRendererObj.drawString(String.valueOf(Reload.instance.clientName.charAt(0)), this.getX(), this.getY(), ColorUtils.Rainbow());
        mc.fontRendererObj.drawString(Reload.instance.clientName.substring(1) + " b" + Reload.instance.version, this.getX() + mc.fontRendererObj.getCharWidth(Reload.instance.clientName.charAt(0)), this.getY(), -1);
    }

}
