package com.invisiblecat.reload.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class BlockUtils {
    public static float[] getRotations(final double posX, final double posY, final double posZ) {
        // get rotation of player to look at the given coordinates
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        final double diffX = posX - player.posX;
        final double diffY = posY - player.posY;
        final double diffZ = posZ - player.posZ;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));
        return new float[]{yaw, pitch};
    }

    public static Block getBlock(BlockPos block) {
        return Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock();
    }
}
