package com.invisiblecat.reload.client.ui.hud.element.elements;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.element.Element;
import com.invisiblecat.reload.utils.font.CustomFontUtil;
import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;
import com.invisiblecat.reload.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;

public class Watermark extends Element {
    private static final TTFFontRenderer font = CustomFontUtil.FONT_MANAGER.getFont("Light 22");

    public Watermark() {
        super("watermark", 4, 4, Minecraft.getMinecraft().fontRendererObj.getStringWidth("watermark") - 5, (int) font.getHeight() - 3);
    }

    @Override
    public void render() {
        font.drawString(String.valueOf(Reload.instance.clientName.charAt(0)), this.getX(), this.getY(), ColorUtils.Rainbow());
        font.drawString(Reload.instance.clientName.substring(1) + " b" + Reload.instance.version, this.getX() + mc.fontRendererObj.getCharWidth(Reload.instance.clientName.charAt(0)), this.getY(), -1);
    }

}
