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

public class AuraUtils extends Module {

    public static void attack(EntityLivingBase target, boolean legit) {
        if (legit) {
            Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, target);
        } else {
            PacketUtils.sendPacket(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
        }
    }

}
