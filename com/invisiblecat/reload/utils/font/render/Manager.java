package com.invisiblecat.reload.utils.font.render;

import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.lwjgl.opengl.GL11;

public class Manager {
    private final HashMap<String, TTFFontRenderer> fonts = new HashMap<>();
    private final TTFFontRenderer defaultFont;

    public TTFFontRenderer getFont(final String key) {
        return this.fonts.getOrDefault(key, this.defaultFont);
    }

    public Manager() {

        final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        final ConcurrentLinkedQueue<TextureData> textureQueue = new ConcurrentLinkedQueue<>();

        this.defaultFont = new TTFFontRenderer(executorService, textureQueue, new Font( "Light", Font.PLAIN, 18));

        try {

            for (final int i : new int[]{12, 18, 24, 36, 48, 60}) {
                final InputStream istream = this.getClass().getResourceAsStream("/assets/minecraft/Reload/fonts/Light.ttf");

                Font myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(Font.PLAIN, (float) i);
                this.fonts.put("Light " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (final int i : new int[]{12, 18, 24, 36, 48, 60, 72}) {
                final InputStream istream = this.getClass().getResourceAsStream("/assets/minecraft/reload/fonts/KungFu.ttf");

                Font myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(Font.PLAIN, (float) i);
                this.fonts.put("KungFu " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }
            for (final int i : new int[]{12, 18, 24, 36, 48, 60, 72}) {
                final InputStream istream = this.getClass().getResourceAsStream("/assets/minecraft/reload/fonts/Poggers.ttf");

                Font myFont = Font.createFont(0, istream);
                myFont = myFont.deriveFont(Font.PLAIN, (float) i);
                this.fonts.put("idk " + i, new TTFFontRenderer(executorService, textureQueue, myFont));
            }


        } catch (final Exception ignored) {
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10L);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            while (!textureQueue.isEmpty()) {
                final TextureData textureData = textureQueue.poll();
                GlStateManager.bindTexture(textureData.getTextureId());
                GL11.glTexParameteri(3553, 10241, 9728);
                GL11.glTexParameteri(3553, 10240, 9728);
                GL11.glTexImage2D(3553, 0, 6408, textureData.getWidth(), textureData.getHeight(), 0, 6408, 5121, textureData.getBuffer());
            }
        }
    }
}
