package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.client.ui.hud.notification.Notification;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationManager;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationType;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.utils.PacketUtils;
import com.invisiblecat.reload.utils.TimerUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class ClickTP extends Module {
    private ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "Matrix");

    private TimerUtils timer = new TimerUtils();

    private boolean disable = false, matrix = false, notify = true;
    private int matrixTick = 0;

    public ClickTP() {
        super("ClickTP", 0, Category.MOVEMENT, AutoDisable.NONE);
        this.addSettings(mode);
    }

    @EventTarget
    public void onPreMotion(EventPreMotionUpdate event) {
        if (disable) this.toggle(false);
        if (mc.gameSettings.keyBindUseItem.isKeyDown() && timer.hasTimePassed(500, true)) {
            // funni code
            BlockPos pos = mc.thePlayer.rayTrace(999, 1).getBlockPos();
            BlockPos tpPos = pos.up();

            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir || !(mc.theWorld.getBlockState(tpPos).getBlock() instanceof BlockAir))
                return;


            switch (mode.getSelected()) {
                case "Vanilla": {
                    mc.thePlayer.posX = tpPos.getX();
                    mc.thePlayer.posY = tpPos.getY();
                    mc.thePlayer.posZ = tpPos.getZ();
                    break;
                } case "Matrix": {
                    matrixTick++;
                    if (!matrix) {
                        if (matrixTick > 2) {
                            PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));

                            matrix = true;
                        }
                    }

                    if (matrixTick < 3) {
                        event.setPitch(-90);
                        mc.thePlayer.rotationPitchHead = -90;
                    }

                    if (notify && matrixTick > 5) {
                        notify = false;
                        PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                        NotificationManager.show(new Notification(NotificationType.SUCCESS, "Click TP", "Teleported", 1));
                    }

                    mc.timer.timerSpeed = 0.8f;
                    mc.thePlayer.posX = tpPos.getX();
                    mc.thePlayer.posY = tpPos.getY();
                    mc.thePlayer.posZ = tpPos.getZ();
                }
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        matrix = false;
        disable = false;
        notify = true;
        matrixTick = 0;

        if (mode.is("Matrix")) {
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
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1.0F;
    }
}
