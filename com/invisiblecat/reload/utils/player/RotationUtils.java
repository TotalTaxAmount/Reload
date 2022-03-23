package com.invisiblecat.reload.utils.player;


/*
 * Copyright (C) 2022 Hypnotic Development
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */



import com.invisiblecat.reload.utils.PoolUtil;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RotationUtils {
    private static final PoolUtil<Rotation> rotationPool = new PoolUtil<>(Rotation::new);
    private static final List<Rotation> rotations = new ArrayList<>();
    public static float serverPitch;
    public static boolean isCustomPitch = false;
    public static boolean isCustomYaw = false;
    public static float serverYaw;
    private Minecraft mc = Minecraft.getMinecraft();

    public static void setSilentPitch(float pitch) {
        RotationUtils.serverPitch = pitch;
        isCustomPitch = true;
    }

    public static void setSilentYaw(float yaw) {
        RotationUtils.serverYaw = yaw;
        isCustomYaw = true;
    }

    public static void setSilentRotations(EntityLiving target, float yaw, float pitch) {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook((float) RotationUtils.getRotations(target)[0], (float) RotationUtils.getRotations(target)[1], Minecraft.getMinecraft().thePlayer.onGround));
        RotationUtils.serverPitch = pitch;
        isCustomPitch = true;
        RotationUtils.serverYaw = yaw;
        isCustomYaw = true;
    }

    public static void setSilentRotations(float yaw, float pitch) {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, Minecraft.getMinecraft().thePlayer.onGround));
        RotationUtils.serverPitch = pitch;
        isCustomPitch = true;
        RotationUtils.serverYaw = yaw;
        isCustomYaw = true;
    }

    public static void resetPitch() {
        isCustomPitch = false;
    }

    public static void resetYaw() {
        isCustomYaw = false;
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;

        double dist = MathHelper.sqrt_float((float) (xDiff * xDiff + zDiff * zDiff));
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
        return new float[]{yaw, pitch};
    }

    public static float[] getRotations(EntityLiving ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + ent.getEyeHeight() / 2.0F;
        return getRotationFromPosition(x, z, y);
    }

    public static float[] getRotations(Entity ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + ent.getEyeHeight() / 2.0F;
        return getRotationFromPosition(x, z, y);
    }

    public static double getYaw(Vec3d pos) {
        return Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(pos.z - Minecraft.getMinecraft().thePlayer.posZ, pos.x - Minecraft.getMinecraft().thePlayer.posX)) - 90f - Minecraft.getMinecraft().thePlayer.rotationYaw);
    }

    public static double getPitch(Vec3d pos) {
        double diffX = pos.x - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = pos.y - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double diffZ = pos.z - Minecraft.getMinecraft().thePlayer.posZ;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        return Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float) -Math.toDegrees(Math.atan2(diffY, diffXZ)) - Minecraft.getMinecraft().thePlayer.rotationPitch);
    }

    public static double getYaw(BlockPos pos) {
        return Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(pos.getX() + 0.5 - Minecraft.getMinecraft().thePlayer.posZ, pos.getX() + 0.5 - Minecraft.getMinecraft().thePlayer.posX)) - 90f - Minecraft.getMinecraft().thePlayer.rotationYaw);
    }

    public static double getPitch(BlockPos pos) {
        double diffX = pos.getX() + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = pos.getY() + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double diffZ = pos.getZ() + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        return Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float) -Math.toDegrees(Math.atan2(diffY, diffXZ)) - Minecraft.getMinecraft().thePlayer.rotationPitch);
    }

    public static void rotate(double yaw, double pitch, int priority, boolean clientSide, Runnable callback) {
        Rotation rotation = rotationPool.get();
        rotation.set(yaw, pitch, priority, clientSide, callback);

        int i = 0;
        for (; i < rotations.size(); i++) {
            if (priority > rotations.get(i).priority) break;
        }

        rotations.add(i, rotation);
    }

    public static void rotate(double yaw, double pitch, int priority, Runnable callback) {
        rotate(yaw, pitch, priority, false, callback);
    }

    public static void rotate(double yaw, double pitch, Runnable callback) {
        rotate(yaw, pitch, 0, callback);
    }

    public static void rotate(double yaw, double pitch) {
        rotate(yaw, pitch, 0, null);
    }

    private static class Rotation {
        public double yaw, pitch;
        public int priority;
        @SuppressWarnings("unused")
        public boolean clientSide;
        public Runnable callback;

        public void set(double yaw, double pitch, int priority, boolean clientSide, Runnable callback) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.priority = priority;
            this.clientSide = clientSide;
            this.callback = callback;
        }

        @SuppressWarnings("unused")
        public void sendPacket() {
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook((float) yaw, (float) pitch, Minecraft.getMinecraft().thePlayer.onGround));
            runCallback();
        }

        public void runCallback() {
            if (callback != null) callback.run();
        }
    }

    public static double getYaw(Entity entity) {
        return Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(entity.posX - Minecraft.getMinecraft().thePlayer.posZ, entity.posX - Minecraft.getMinecraft().thePlayer.posX)) - 90f - Minecraft.getMinecraft().thePlayer.rotationYaw);
    }

    public static double getPitch(Entity entity, Target target) {
        double y;
        if (target == Target.Head) y = entity.getEyeHeight();
        else if (target == Target.Body) y = entity.posY + entity.height / 2;
        else y = entity.posY;

        double diffX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = y - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double diffZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        return Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float) -Math.toDegrees(Math.atan2(diffY, diffXZ)) - Minecraft.getMinecraft().thePlayer.rotationPitch);
    }

    public static Vec3d getEyesPos()
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        return new Vec3d(player.posX,
                player.posY + player.getEyeHeight(),
                player.posZ);
    }


    public static double getPitch(Entity entity) {
        return getPitch(entity, Target.Body);
    }

}

