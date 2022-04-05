package com.invisiblecat.reload.client.ui.hud.elements;

import com.invisiblecat.reload.client.ui.hud.Element;
import com.invisiblecat.reload.client.ui.hud.HUD;
import com.invisiblecat.reload.utils.NetworkUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;


public class Info extends Element {
    public Info() {
        super("Info", 0, 0);
    }

    @Override
    public void render() {
        NetworkUtils util = new NetworkUtils();

        mc.fontRendererObj.drawString(ChatFormatting.WHITE + "FPS" + ChatFormatting.GRAY + "> " + ChatFormatting.RESET + Minecraft.getDebugFPS(), 4, sr.getScaledHeight() - 4 - mc.fontRendererObj.FONT_HEIGHT, HUD.getClientColor().hashCode());
       // mc.fontRendererObj.drawString(ChatFormatting.WHITE + "PING" + ChatFormatting.GRAY + "> " + ChatFormatting.RESET + util.getPing(mc.thePlayer), 4, sr.getScaledHeight() - 8 - mc.fontRendererObj.FONT_HEIGHT *2, HUD.getClientColor().hashCode());

    }
}