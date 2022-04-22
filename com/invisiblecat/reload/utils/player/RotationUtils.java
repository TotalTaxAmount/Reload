package com.invisiblecat.reload.utils.player;


import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtils {
    public static float[] getRotations(EntityLivingBase target) {
        // get the roatations so that the playewr is alsways looking at the target
        // use updateRotation to make it smooth
        final float[] var4 = new float[2];
        double d0 = target.posX - Minecraft.getMinecraft().thePlayer.posX;
        double d1 = (target.posY + target.getEyeHeight()) - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double d2 = target.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float f = (float)(MathHelper.func_181159_b(d2, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float)(-(MathHelper.func_181159_b(d1, d3) * 180.0D / Math.PI));
//        var4[1] = updateRotation(Minecraft.getMinecraft().thePlayer.rotationPitch, f1, 20);
//        var4[0] = updateRotation(Minecraft.getMinecraft().thePlayer.rotationYawHead, f, 25);
        return new float[]{f, f1};
    }
    public static float[] getRotations(final double posX, final double posY, final double posZ) {
        // get the yaw and pitch to look at thoes coords
        final float[] var4 = new float[2];
        final EntityPlayerSP var5 = Minecraft.getMinecraft().thePlayer;
        final double var6 = posX - var5.posX;
        final double var8 = posY - var5.posY;
        final double var10 = posZ - var5.posZ;
        final double var12 = Math.sqrt(var6 * var6 + var10 * var10);
        var4[0] = (float) (Math.atan2(var6, var10) * 180.0D / Math.PI) - 90.0F;
        var4[1] = (float) -(Math.atan2(var8, var12) * 180.0D / Math.PI);
        return var4;
    }

    private static float updateRotation(float p_75652_1_, float p_75652_2_, float p_75652_3_)
    {
        float f = MathHelper.wrapAngleTo180_float(p_75652_2_ - p_75652_1_);

        if (f > p_75652_3_)
        {
            f = p_75652_3_;
        }

        if (f < -p_75652_3_)
        {
            f = -p_75652_3_;
        }

        return p_75652_1_ + f;
    }
}

