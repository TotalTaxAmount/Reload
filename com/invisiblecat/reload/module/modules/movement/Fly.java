package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.player.PlayerUtils;
import com.invisiblecat.reload.utils.TimerUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Fly extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Damage", "Velocity", "Vanilla", "Verus", "Damage");
    private final NumberSetting speed = new NumberSetting("Speed", 1, 0, 10, 0.1);
    boolean hasBeenDamaged = false;
    int wallTicks = 0;
    boolean direction = false;
    private static final TimerUtils timer = new TimerUtils();


    public Fly() {
        super("Fly", 0, Category.MOVEMENT, AutoDisable.FLAG);
        this.addSettings(mode, speed);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionY = 0;
        mc.thePlayer.motionZ = 0;


        mc.timer.timerSpeed = 1f;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        hasBeenDamaged = false;
        switch (mode.getSelected().toLowerCase().replaceAll("\\s", "")) {
            case "damage":
                if (!hasBeenDamaged) {
                    mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.1, mc.thePlayer.posZ, true));
                    mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                    mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                    hasBeenDamaged = true;
                }
        }
        super.onEnable();
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        this.setDisplayName("Fly " + ChatFormatting.GRAY + mode.getSelected());
        if (mc.thePlayer == null)
            return;

        switch (mode.getSelected().toLowerCase().replaceAll("\\s", "")) {
            case "verus":
                mc.thePlayer.setVelocity(mc.thePlayer.getVelocityPlayer().x, 0, mc.thePlayer.getVelocityPlayer().z);
                mc.thePlayer.onGround = true;
                mc.thePlayer.capabilities.isFlying = false;
                event.setGround(true);
                PlayerUtils.strafe(speed.getValueInt());
            case "damage":
                mc.timer.timerSpeed = 0.8f;
                mc.thePlayer.setSneaking(false);
                mc.thePlayer.setJumping(false);
                PlayerUtils.strafe(speed.getValueInt() * 2);
                if(mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.thePlayer.motionY += speed.getValueInt()/1.5;
                } else if(mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.thePlayer.motionY -= speed.getValueInt()/1.5;
                } else
                    mc.thePlayer.motionY = 0;
        }
    }
}

