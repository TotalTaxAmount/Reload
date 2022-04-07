package com.invisiblecat.reload.module.modules.render;

import com.invisiblecat.reload.event.Event;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.Event3D;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.monster.EntityCreeper;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Chams extends Module {
    //public static final Setting<Color> chamColor = new Setting<>("Color", new Color(168, 0, 232, 150));
    public static final BooleanSetting players = new BooleanSetting("Players", true);
    public static final BooleanSetting mobs = new BooleanSetting("Mobs", true);
    public static final NumberSetting range = new NumberSetting("Range", 40, 0, 100, 1);


    public Chams() {
        super("Chams", 0, Category.RENDER, AutoDisable.NONE);
    }

    @EventTarget
    public void onEvent3D(Event3D event) {
    }


}
