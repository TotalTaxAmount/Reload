package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.event.events.EventSendPacket;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.PacketUtils;
import com.invisiblecat.reload.utils.TimerUtils;
import com.invisiblecat.reload.utils.player.PlayerUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.util.EnumFacing;

import java.util.UUID;

public class Fly extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Verus2", "Velocity", "Vanilla", "Verus", "Verus2", "VerusSilent","Damage");
    private final NumberSetting speed = new NumberSetting("Speed", 2, 0, 10, 0.1);
    private final BooleanSetting bypassVanillaKick = new BooleanSetting("BypassVanillaKick", true);
    boolean hasBeenDamaged = false;
    private static final TimerUtils timer = new TimerUtils();
    private int count, offGroundTicks, onGroundTicks, ticks = 0;


    public Fly() {
        super("Fly", 0, Category.MOVEMENT, AutoDisable.FLAG);
        this.addSettings(mode, speed);
    }

    @Override
    public void onEnable() {
        count = 0;
        ticks = 0;
        offGroundTicks = 0;
        onGroundTicks = 0;
        hasBeenDamaged = false;
        switch (mode.getSelected().toLowerCase().replaceAll("\\s", "")) {
            case "damage":
                if (!hasBeenDamaged) {
                    PlayerUtils.selfHurt();
                    hasBeenDamaged = true;
                }
                break;
            case "verus2":
            case "verus":
                PacketUtils.sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                mc.timer.timerSpeed = 0.15F;
                PlayerUtils.selfHurt();
                mc.thePlayer.jump();
                break;

        }
        super.onEnable();
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        Packet<?> packet = event.getPacket();
        switch (mode.getSelected().toLowerCase().replaceAll("\\s", "")) {
            case "verus2":
            case "verus":
                if (ticks < 20) {
                    if (packet instanceof C0BPacketEntityAction) {
                        C0BPacketEntityAction action = (C0BPacketEntityAction) packet;
                        if (action.getAction().equals(C0BPacketEntityAction.Action.START_SPRINTING)) {
                            if(EntityPlayerSP.serverSprintState) {
                                PacketUtils.sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                                EntityPlayerSP.serverSprintState = false;
                            }
                            event.setCancelled(true);
                        }
                        if (action.getAction().equals(C0BPacketEntityAction.Action.STOP_SPRINTING)) {
                            event.setCancelled(true);
                        }
                    }
                }
                break;
        }
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        this.setDisplayName(mode.getSelected());
        ticks++;
        if (mc.thePlayer == null)
            return;

        if (mc.thePlayer.onGround) {
            offGroundTicks = 0;
            ++onGroundTicks;
        } else {
            onGroundTicks = 0;
            ++offGroundTicks;
        }

        switch (mode.getSelected().toLowerCase().replaceAll("\\s", "")) {
            case "verus":
                if (offGroundTicks > 2 && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                    if (mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (mc.thePlayer.ticksExisted % 2 == 0)
                            mc.thePlayer.motionY = 0.42F;
                    } else {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                        }

                        if (mc.thePlayer.fallDistance > 1) {
                            mc.thePlayer.motionY = -((mc.thePlayer.posY) - Math.floor(mc.thePlayer.posY));
                        }

                        if (mc.thePlayer.motionY == 0) {
                            mc.thePlayer.jump();

                            mc.thePlayer.onGround = true;
                            mc.thePlayer.fallDistance = 0;
                            event.setGround(true);
                        }
                    }
                    PlayerUtils.strafe(speed.getValue());
                    mc.timer.timerSpeed = 1;
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
                   mc.thePlayer.setSneaking(false);
                   mc.thePlayer.setJumping(false);
                   PlayerUtils.setSpeed(speed.getValueInt(), 0, 90, speed.getValueInt(), speed.getValueInt());
                   PlayerUtils.strafe();
                   if(count > 10) {
                       event.setGround(true);
                       count = 0;
                   } else {
                       count++;
                   }
                   if (mc.gameSettings.keyBindJump.isKeyDown()) {
                       mc.thePlayer.motionY += (float) speed.getValueInt() / 2;
                   } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                       mc.thePlayer.motionY -= (float) speed.getValueInt() / 2;
                   }
                   break;
           case "verus2":
               mc.thePlayer.motionY = 0;
               mc.thePlayer.fallDistance = 0;
               if (offGroundTicks > 125) {
                   mc.thePlayer.setSprinting(false);
                   mc.timer.timerSpeed = 1f;
                   PlayerUtils.strafe(speed.getValueInt()/2.5F);
               } else {
                   mc.timer.timerSpeed = 1.5F;
                   mc.thePlayer.setSprinting(false);
                   PacketUtils.sendPacketNoEvent(new C18PacketSpectate(UUID.randomUUID()));
                   PlayerUtils.strafe(speed.getValueInt()/1.9F);
               }
               if (count > 10) {
                   count = 0;
                   PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.2, mc.thePlayer.posZ, false));
               } else {
                   count++;
               }



               if (mc.gameSettings.keyBindJump.isKeyDown()) {
                   mc.thePlayer.motionY = 0.7F;
                   if (mc.thePlayer.ticksExisted % 2 == 0) {
                       event.setGround(true);
                   }
               } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                   mc.thePlayer.motionY = -0.7F;
                   if (mc.thePlayer.ticksExisted % 2 == 0) {
                       event.setGround(true);
                   }
               }
               break;

        }
    }
    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1;
        PlayerUtils.strafe(0);
    }
//    @EventTarget
//    public void onUpdate(EventUpdate event) {
//        if(bypassVanillaKick.isEnabled())
//            PlayerUtils.bypassVanillaKick();
//    }
}

