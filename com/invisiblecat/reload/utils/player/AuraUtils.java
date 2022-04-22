package com.invisiblecat.reload.utils.player;

import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.utils.PacketUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class AuraUtils extends Module {

    public static EntityLivingBase getTarget(double range) {
        EntityLivingBase entityLivingBase = null;
        for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) <= range && shouldAttack((EntityLivingBase) entity)) {
                range = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity);
                entityLivingBase = (EntityLivingBase) entity;
            }
        }
        return entityLivingBase;
    }

    public static boolean shouldAttack(EntityLivingBase target) {
        return target != Minecraft.getMinecraft().thePlayer && target.isEntityAlive() && !target.isInvisible();
    }

    public static void attack(EntityLivingBase target, boolean legit) {
        if (legit) {
            Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, target);
        } else {
            PacketUtils.sendPacket(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
        }
    }

}
