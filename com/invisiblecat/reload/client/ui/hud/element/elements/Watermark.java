package com.invisiblecat.reload.client.ui.hud.element.elements;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.HUD;
import com.invisiblecat.reload.client.ui.hud.element.Element;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.utils.font.CustomFontUtil;
import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;
import com.invisiblecat.reload.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class Watermark extends Element {
    private static final TTFFontRenderer font = CustomFontUtil.FONT_MANAGER.getFont("Light 22");
    private static final TTFFontRenderer reloadFont = CustomFontUtil.FONT_MANAGER.getFont("idk 40");
    private static final TTFFontRenderer reloadFontSmall = CustomFontUtil.FONT_MANAGER.getFont("idk 12");

    private ModeSetting theme = new ModeSetting("Theme", "Reload", "Reload", "Chan Chan");

    public Watermark() {
        super("watermark", 4, 4, 100, 100);
        this.addSettings(theme);
    }

    @Override
    public void render() {
        switch (theme.getSelected()) {
            case "Chan Chan": {
                font.drawString(String.valueOf(Reload.instance.clientName.charAt(0)), this.getX(), this.getY(), ColorUtils.Rainbow());
                font.drawString(Reload.instance.clientName.substring(1) + " b" + Reload.instance.version, this.getX() + mc.fontRendererObj.getCharWidth(Reload.instance.clientName.charAt(0)), this.getY(), -1);
            } case "Reload": {
                reloadFont.drawString(Reload.instance.clientName, this.draggable.getxPosition(), this.draggable.getyPosition(), HUD.getClientColor().getRGB());
                reloadFontSmall.drawString(String.valueOf(Reload.instance.version), this.draggable.getxPosition() + reloadFont.getWidth(Reload.instance.clientName), this.draggable.getyPosition(), Color.WHITE.getRGB());
            }
        }
    }

}
