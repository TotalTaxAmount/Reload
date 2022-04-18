package com.invisiblecat.reload.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class BlockUtils {
    public static float[] getDirectionToBlock(double x,  double y,  double z) {
        // calculate the direction face the side of the block (block x pos = x, block y pos = y, block z pos = z)
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        final double xDiff = x - player.posX;
        final double yDiff = y - (player.posY + (double) player.getEyeHeight());
        final double zDiff = z - player.posZ;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) (-(Math.atan2(yDiff, dist) * 180.0D / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static float[] getRotations(final double posX, final double posY, final double posZ) {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        final double x = posX - player.posX;
        final double y = posY - (player.posY + (double) player.getEyeHeight());
        final double z = posZ - player.posZ;
        final double dist = MathHelper.sqrt_double(x * x + z * z);
        final float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) (-(Math.atan2(y, dist) * 180.0D / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static Block getBlock(BlockPos block) {
        return Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock();
    }
}
