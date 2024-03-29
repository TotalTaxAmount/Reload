package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.notification.Notification;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationManager;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationType;
import com.invisiblecat.reload.event.EventManager;
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
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.io.Reader;
import java.util.UUID;

public class Fly extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Verus2", "Vanilla", "Verus", "Verus2","Damage", "Collide", "Vulcan", "Matrix");
    private final NumberSetting speed = new NumberSetting("Speed", 2, 0, 10, 0.1);
    boolean hasBeenDamaged = false;
    private static final TimerUtils timer = new TimerUtils();
    private int count, offGroundTicks, onGroundTicks, ticks = 0;
    private boolean idk = true, matrixRod = false, disable = false;



    public Fly() {
        super("Fly", 0, Category.MOVEMENT, AutoDisable.FLAG);
        this.addSettings(mode, speed);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        disable = false;
        count = 0;
        ticks = 0;
        offGroundTicks = 0;
        onGroundTicks = 0;
        hasBeenDamaged = false;
        matrixRod = false;
        idk = true;
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
            case "matrix":
                // Look for a fishing rod in the players hot bar
                for (int i = 0; i < 9; i++) {
                    if (mc.thePlayer.inventory.getStackInSlot(i) != null) {
                        if (mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemFishingRod || mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBow) {
                            mc.thePlayer.inventory.currentItem = i;
                            break;
                        }
                    }
                }

                // if the player is not holding a fishing rod, then disable the module
                if (!(mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemFishingRod || mc.thePlayer.getHeldItem().getItem() instanceof ItemBow))) {
                    NotificationManager.show(new Notification(NotificationType.ERROR, "Fly", "You must have a rod in your hotbar", 1));
                    mc.timer.timerSpeed = 1.0F;
                    disable = true;

                }

                if (mc.thePlayer.hurtResistantTime > 0) {
                    NotificationManager.show(new Notification(NotificationType.ERROR, "Fly", "Wait a bit bozo", 1));
                    disable = true;
                    mc.timer.timerSpeed = 1.0F;
                }
                break;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1;

        PlayerUtils.strafe(0);
        mc.thePlayer.motionY = 0;
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
            case "minebox": {
                if (packet instanceof C03PacketPlayer) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        if (disable) {
            this.toggle(false);
        }
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
               if (count > 20) {
                   count = 0;
                   mc.timer.timerSpeed = 0.4F;
               } else {
                   mc.timer.timerSpeed = 1F;
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
            case "collide": {
                if (!mc.gameSettings.keyBindSneak.isKeyDown()) {
                    if (PlayerUtils.isMoving()) {
                        PlayerUtils.strafe(speed.getValue());
                    } else {
                        mc.thePlayer.motionY = 0;
                        mc.thePlayer.motionX = 0;
                        mc.thePlayer.motionZ = 0;
                    }

                    if (mc.gameSettings.keyBindJump.isKeyDown()) {
                        mc.thePlayer.motionY = -(mc.thePlayer.posY - roundToOnGround(mc.thePlayer.posY + 0.5));
                    } else {
                        mc.thePlayer.motionY = -(mc.thePlayer.posY - roundToOnGround(mc.thePlayer.posY));
                    }

                    if (mc.thePlayer.posY % (1.0F / 64.0F) < 0.005) {
                        event.setGround(true);
                    }
                }

                break;
            }
            case "vulcan": {
                mc.timer.timerSpeed = 1.2F;
                mc.thePlayer.jump();
                mc.thePlayer.motionY = 0;
                PlayerUtils.strafe(0.5);
                if (idk) {
                    idk = false;
                    mc.thePlayer.motionY = 0.4;
                } else {
                    idk = true;
                    mc.thePlayer.motionY = -0.4;
                }
                break;
            }

            case "matrix": {
                count++;
                if (!matrixRod) {
                    if (count > 2) {
                        PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));

                        matrixRod = true;
                    }
                }

                if (count < 3) {
                    event.setPitch(-90);
                    mc.thePlayer.rotationPitchHead = -90;
                }

                if (matrixRod && (mc.thePlayer.hurtResistantTime > 0 || hasBeenDamaged)) {
                    mc.timer.timerSpeed = 0.2F;
                    if (idk && count > 5) {
                        idk = false;
                        PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                        NotificationManager.show(new Notification(NotificationType.SUCCESS, "Matrix Fly", "You can now fly", 1));
                    }

                    mc.thePlayer.motionY = 0;
                    PlayerUtils.strafe(speed.getValue() * 4.5);
                    if (mc.gameSettings.keyBindJump.isKeyDown()) {
                        mc.thePlayer.motionY = 4;
                    } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                        mc.thePlayer.motionY = -4;
                    }
                    // this is not for damage
                    hasBeenDamaged = true;
                } else {
                    mc.thePlayer.motionX = 0;
                    mc.thePlayer.motionZ = 0;
                }
                break;
            }

        }
    }

    private double roundToOnGround(double posY) {
        return posY - (posY % 0.015625);
    }


//    @EventTarget
//    public void onUpdate(EventUpdate event) {
//        if(bypassVanillaKick.isEnabled())
//            PlayerUtils.bypassVanillaKick();
//    }
}

