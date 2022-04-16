package com.invisiblecat.reload.client.ui.hud.element.elements;

import com.invisiblecat.reload.client.ui.hud.element.Element;
import com.invisiblecat.reload.client.ui.hud.HUD;
import com.invisiblecat.reload.utils.NetworkUtils;
import com.invisiblecat.reload.utils.font.CustomFontUtil;
import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;


public class Info extends Element {
    private static final TTFFontRenderer font = CustomFontUtil.FONT_MANAGER.getFont("Light 22");

    public Info() {
        super("Info", 4, 0, 10, 10);
        this.setY(sr.getScaledHeight() - 4 - mc.fontRendererObj.FONT_HEIGHT);
    }

    @Override
    public void render() {
        NetworkUtils util = new NetworkUtils();
        draggable.setWidth(mc.fontRendererObj.getStringWidth("FPS> " + Minecraft.getDebugFPS()));

        mc.fontRendererObj.drawString(ChatFormatting.WHITE + "FPS" + ChatFormatting.GRAY + "> " + ChatFormatting.RESET + Minecraft.getDebugFPS(), this.getX(), this.getY(), HUD.getClientColor().hashCode());
       // mc.fontRendererObj.drawString(ChatFormatting.WHITE + "PING" + ChatFormatting.GRAY + "> " + ChatFormatting.RESET + util.getPing(mc.thePlayer), 4, sr.getScaledHeight() - 8 - mc.fontRendererObj.FONT_HEIGHT *2, HUD.getClientColor().hashCode());

    }
}
