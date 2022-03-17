package com.invisiblecat.reload.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.client.Minecraft;

public class UUIDUtils {

    private UUIDUtils() {}

    public static String getUsernameFormUUID(String uuid) {
        return Minecraft.getMinecraft().getSessionService().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(uuid), null), false).getName();
    }

    public static String getUUID() {
        return Minecraft.getMinecraft().thePlayer.getGameProfile().getId().toString();
    }
}