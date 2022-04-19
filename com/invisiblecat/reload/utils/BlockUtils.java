package com.invisiblecat.reload.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class BlockUtils {
    public static float[] getDirectionToBlock(final double x, final double y, final double z, final EnumFacing enumfacing) {
        final EntitySnowball var4 = new EntitySnowball(Minecraft.getMinecraft().theWorld);
        var4.posX = x + 0.5D;
        var4.posY = y + 0.5D;
        var4.posZ = z + 0.5D;
        var4.posX += (double) enumfacing.getDirectionVec().getX() * 0.5D;
        var4.posY += (double) enumfacing.getDirectionVec().getY() * 0.5D;
        var4.posZ += (double) enumfacing.getDirectionVec().getZ() * 0.5D;
        return getRotations(var4.posX, var4.posY, var4.posZ);
    }

    public static float[] getRotations(final double posX, final double posY, final double posZ) {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        final double x = posX - player.posX;
        final double y = posY - (player.posY + (double) player.getEyeHeight());
        final double z = posZ - player.posZ;
        final double dist = MathHelper.sqrt_double(x * x + z * z);
        final float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI);
        final float pitch = (float) (-(Math.atan2(y, dist) * 180.0D / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static Block getBlock(BlockPos block) {
        return Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock();
    }
}
