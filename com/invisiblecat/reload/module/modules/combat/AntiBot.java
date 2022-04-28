package com.invisiblecat.reload.module.modules.combat;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class AntiBot extends Module {
    public List<EntityLivingBase> bots = new ArrayList<>();

    private BooleanSetting idCheck = new BooleanSetting("ID Check", true);
    private BooleanSetting pingCheck = new BooleanSetting("Ping check", true);

    public AntiBot() {
        super("AntiBot", 0, Category.COMBAT, AutoDisable.NONE);
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        bots.clear();
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player == mc.thePlayer) return;

            if (idCheck.isEnabled() && player.getEntityId() <= 0) {
                bots.add(player);
            }
            if (pingCheck.isEnabled()) {
                NetworkPlayerInfo idk = mc.getNetHandler().getPlayerInfo(player.getUniqueID());

                if (idk != null && idk.getResponseTime() < 0)
                    bots.add(player);
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        bots.clear();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        bots.clear();
    }
}
