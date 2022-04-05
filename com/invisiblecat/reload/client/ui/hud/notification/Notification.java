package com.invisiblecat.reload.client.ui.hud.notification;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Notification {
    private NotificationType type;
    private String title;
    private String message;

    private long start, fadeIn, fadeOut, end;

    public Notification(NotificationType type, String title, String message, int duration) {
        this.type = type;
        this.title = title;
        this.message = message;

        fadeIn = 200L * duration;
        fadeOut =  fadeIn + 500L * duration;
        end =fadeIn + fadeOut;
    }

    public void show() {
        start = System.currentTimeMillis();

    }

    public boolean isShow () {
        return getTime() < end;
    }

    private long getTime() {
        return  (System.currentTimeMillis() - start);
    }
    public void render() {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

        double offset = 0;
        double width = fr.getStringWidth(message.length() > title.length() ? message : title) * 1.2;
        int height = 30;
        long time = getTime();

        if (time < fadeIn) {
            offset = Math.tanh(time / (double) (fadeIn) * 2.5) * width ;
        } else if (time > fadeOut) {
            offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 2.5) * width);
        } else {
            offset = width;
        }

        Color color = new Color(0, 0, 0, 220);


        drawRect((GuiScreen.width - 6) - offset, GuiScreen.height - 5 - height, GuiScreen.width, GuiScreen.height - 5, color.getRGB());
        GL11.glLineWidth(4.0F);
        drawRect(GL11.GL_LINE_LOOP,(GuiScreen.width - 6) - offset , GuiScreen.height - 5 - height, GuiScreen.width, GuiScreen.height - 5, type.color.getRGB());


        fr.drawString(title, (int) (GuiScreen.width - offset - 2), GuiScreen.height - 2 - height, -1);
        fr.drawString(message, (int) (GuiScreen.width - offset - 2), GuiScreen.height - 15, -1);

    }
    public static void drawRect(int mode, double left, double top, double right, double bottom, int color) {
        if (left < right)
        {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(mode, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (left < right)
        {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
