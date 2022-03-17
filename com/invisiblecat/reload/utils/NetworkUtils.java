package com.invisiblecat.reload.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class NetworkUtils {
    public long getPing(EntityPlayer player) {
        if (player == null) {
            return Minecraft.getMinecraft().getCurrentServerData().pingToServer;
        }
        if(Minecraft.getMinecraft().getNetHandler().getPlayerInfo(UUID.fromString(UUIDUtils.getUUID())) != null) {
            return Minecraft.getMinecraft().getNetHandler().getPlayerInfo(UUID.fromString(UUIDUtils.getUUID())).getResponseTime();
        }
        return Minecraft.getMinecraft().getNetHandler().getPlayerInfo(UUID.fromString(UUIDUtils.getUUID())).getResponseTime();
    }
}
