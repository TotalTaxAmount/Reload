package com.invisiblecat.reload.module.modules.player;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPostMotionUpdate;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class Scaffold extends Module {
    private ModeSetting mode = new ModeSetting("Mode", "Normal", "Normal", "Hypixel");
    private BooleanSetting jump = new BooleanSetting("Jump", false);
    private BooleanSetting keepY = new BooleanSetting("Keep Y", false);
    private BooleanSetting sprint = new BooleanSetting("Sprint", false);

    public Scaffold() {
        super("Scaffold", 0, Category.PLAYER, AutoDisable.WORLD);
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        if (keepY.isEnabled() && mc.thePlayer.isCollidedVertically) {
            mc.thePlayer.motionY = 0.0D;
        }

        if (mode.getSelected().equals("Normal")) {
            // get the block below the player
            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);

            double blockX = pos.getX();
            double blockY = pos.getY();
            double blockZ = pos.getZ();

            // calculate the player's needed rotation to face the block
            double neededPitch = Math.toDegrees(Math.atan2(blockY - mc.thePlayer.posY, blockZ - mc.thePlayer.posZ)) - 180.0D;
            double neededYaw = Math.toDegrees(Math.atan2(blockX - mc.thePlayer.posX, blockZ - mc.thePlayer.posZ)) + Math.toDegrees(mc.thePlayer.rotationYaw)/2;

            mc.thePlayer.rotationYawHead = (float) neededYaw;
            mc.thePlayer.renderYawOffset = (float) neededYaw;
            mc.thePlayer.rotationPitchHead = (float) neededPitch;

            event.setPitch((float) neededPitch);
            event.setYaw((float) neededYaw);

            // if the block is air, get the rotations and place the block
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
                // get the rotations so that the player is facing the block


                // set the player's rotatio

                // set the client side rotation


                // place the block
                mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(pos, 1, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));



            }
        }

    }
}
