package com.invisiblecat.reload.utils;

import com.invisiblecat.reload.utils.player.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.*;

public class BlockUtils {
    public static float[] getDirectionToBlock(final double x, final double y, final double z) {
        // get rotation to look at the block
        // use a temp EntitySnowball
        // use enumfacing to determine the direction
        // return the direction


        EntitySnowball entitySnowball = new EntitySnowball(Minecraft.getMinecraft().theWorld);
        entitySnowball.setPosition(x, y, z);
        entitySnowball.rotationPitch = RotationUtils.getRotations(x, y, z)[1];
        entitySnowball.rotationYaw = RotationUtils.getRotations(x, y, z)[0];

        entitySnowball.setLocationAndAngles(x, y, z, RotationUtils.getRotations(x, y, z)[0], RotationUtils.getRotations(x, y, z)[1]);
        entitySnowball.setPositionAndRotation(x, y, z, RotationUtils.getRotations(x, y, z)[0], RotationUtils.getRotations(x, y, z)[1]);
        EnumFacing enumFacing = EnumFacing.getFacingFromVector((float) (entitySnowball.posX - x), (float) (entitySnowball.posY - y), (float) (entitySnowball.posZ - z));
        return new float[]{entitySnowball.rotationYaw, entitySnowball.rotationPitch, enumFacing.getHorizontalIndex()};
    }


    public static Block getBlock(BlockPos block) {
        return Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock();
    }

    public static void placeBlock(BlockPos block, float yaw, float pitch, EnumFacing enumFacing) {
        // Find a block in the player invetory
        // if it is a block, place it
        // else break

        for (int i = 0; i < Minecraft.getMinecraft().thePlayer.inventory.getSizeInventory(); i++) {
            ItemStack itemstack = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i);
            if (itemstack != null && itemstack.getItem() instanceof ItemBlock) {
                Minecraft.getMinecraft().thePlayer.inventory.currentItem = i;
            }
        }
        // place the block using mc.playerController.onPlayerRightClick
        Vec3 work = Minecraft.getMinecraft().thePlayer.rayTraceCustom(3.0D, Minecraft.getMinecraft().timer.renderPartialTicks, yaw, pitch).hitVec;
        Minecraft.getMinecraft().playerController.onPlayerRightClick(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer.getHeldItem(), block, enumFacing, work);

    }
}
