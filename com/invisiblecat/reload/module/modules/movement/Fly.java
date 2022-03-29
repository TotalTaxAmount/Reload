package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.PacketUtils;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import com.invisiblecat.reload.utils.player.PlayerUtils;
import com.invisiblecat.reload.utils.TimerUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C18PacketSpectate;

public class Fly extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Velocity", "Velocity", "Vanilla", "Verus", "VerusSilent","Damage");
    private final NumberSetting speed = new NumberSetting("Speed", 2, 0, 10, 0.1);
    private final BooleanSetting bypassVanillaKick = new BooleanSetting("BypassVanillaKick", true);
    boolean hasBeenDamaged = false;
    private static final TimerUtils timer = new TimerUtils();
    private int count = 0;


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
                    PlayerUtils.selfHurt();
                    hasBeenDamaged = true;
                }
            case "verus":
                if(!mc.thePlayer.onGround) ChatUtils.sendChatMessageClient("Cannot enable fly in air", ChatUtils.Type.ERROR);
                if (!hasBeenDamaged) {
                    PlayerUtils.selfHurt();
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
                mc.timer.timerSpeed = 0.6f;
                PlayerUtils.strafe(speed.getValueInt());
                if (count == 2) {
                    mc.thePlayer.motionY = -0.36969420;
                    PlayerUtils.strafe(speed.getValueInt());
                    count++;
                } else if (count == 4) {
                    mc.thePlayer.motionY = 0.36969420;
                    PlayerUtils.strafe(speed.getValueInt());
                    count++;
                } else if(count == 6) {
                    event.setGround(false);
                    count = 0;
                }
                else {
                    count++;
                    mc.thePlayer.motionY = 0;
                    PlayerUtils.strafe(speed.getValueInt());
                }
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.thePlayer.motionY = 0.9;
                } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.thePlayer.motionY = -0.9;
                }
                break;
            case "damage":
                mc.timer.timerSpeed = 0.8f;
                mc.thePlayer.setSneaking(false);
                mc.thePlayer.setJumping(false);
                PlayerUtils.strafe(speed.getValueInt() * 3);
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.thePlayer.motionY += (float) speed.getValueInt() / 2;
                } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.thePlayer.motionY -= (float) speed.getValueInt() / 2;
                } else
                    mc.thePlayer.motionY = 0;
                   break;
               case "velocity":
                   PlayerUtils.setSpeed(speed.getValueInt(), 0, 90, speed.getValueInt(), speed.getValueInt());
                   PlayerUtils.strafe();
                   if(count > 10) {
                       event.setGround(true);
                       count = 0;
                   } else {
                       count++;
                   }

        }
    }
//    @EventTarget
//    public void onUpdate(EventUpdate event) {
//        if(bypassVanillaKick.isEnabled())
//            PlayerUtils.bypassVanillaKick();
//    }
}

