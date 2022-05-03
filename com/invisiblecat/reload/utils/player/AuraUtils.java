package com.invisiblecat.reload.utils.player;

import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.utils.PacketUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class AuraUtils extends Module {

    public static void attack(EntityLivingBase target, boolean legit) {
        if (target.isEntityAlive()) {
            if (legit) {
                Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, target);
            } else {
                PacketUtils.sendPacket(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            }
        }
    }

    public static boolean isLookingAtEntity(EntityLivingBase entity, float yaw, float pitch) {
        Vec3 look = new Vec3(Math.sin(Math.toRadians(yaw)), Math.sin(Math.toRadians(pitch)), Math.cos(Math.toRadians(yaw)));

        Vec3 entityPos = new Vec3(entity.posX, entity.posY, entity.posZ);
        Vec3 playerPos = new Vec3(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);

        Vec3 diff = playerPos.subtract(entityPos);

        float dot = (float) diff.dotProduct(look);

        // get the length of the difference
        float length = (float) diff.lengthVector();
        float lookLength = (float) look.lengthVector();

        float angle = (float) Math.acos(dot / (length * lookLength));
        angle = (float) Math.toDegrees(angle);
        boolean looking = angle < 41.4566774F;

        System.out.println("[Smart Angle] Angle: " + angle + " | Yaw: " + yaw + " | Pitch: " + pitch + " | Looking: " + looking);
        return looking;

    }
}
