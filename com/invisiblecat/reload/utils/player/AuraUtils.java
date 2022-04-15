package com.invisiblecat.reload.utils.player;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class AuraUtils {
    // create a staic function to get a target
    // prams: EntityPlayer player, int range
    // returns: EntityLivingBase
    public static EntityLivingBase getTarget(EntityPlayer player, int range) {
        EntityLivingBase target = null;
        for (Entity e : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (e.getDistanceToEntity(player) <= range && e.isEntityAlive()) {
                if (!(e == player)) {
                    if (e instanceof EntityLivingBase) {
                        target = (EntityLivingBase) e;
                    }
                }

            }
        }
        return target;
    }

}
