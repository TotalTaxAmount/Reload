package com.invisiblecat.reload.module.modules.combat;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.event.events.EventSendPacket;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class AntiBot extends Module {
    public List<EntityLivingBase> bots = new ArrayList<>();

    private BooleanSetting idCheck = new BooleanSetting("ID Check", true);
    private BooleanSetting pingCheck = new BooleanSetting("Ping check", true);
    private BooleanSetting nameCheck = new BooleanSetting("Name check", true);
    private BooleanSetting sameName = new BooleanSetting("Same name", true);
    private BooleanSetting ticksExist = new BooleanSetting("Ticks existed", true);
    private BooleanSetting noDamage = new BooleanSetting("No Damage", true);
    private BooleanSetting tabListCheck = new BooleanSetting("Tablist check", true);

    private int ticks = 0;
    private float lastDamage = 0;

    public AntiBot() {
        super("AntiBot", 0, Category.COMBAT, AutoDisable.NONE);
        this.addSettings(idCheck, pingCheck, nameCheck, sameName, ticksExist, noDamage, tabListCheck);
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {

        List<String> names = new ArrayList<>();

        bots.clear();
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player == mc.thePlayer) return;

            if (idCheck.isEnabled() && player.getEntityId() <= 0) {
                bots.add(player);
            }

            if (ticksExist.isEnabled() && player.ticksExisted <= 200) {
                bots.add(player);
            }

            if (pingCheck.isEnabled()) {
                NetworkPlayerInfo idk = mc.getNetHandler().getPlayerInfo(player.getUniqueID());

                if (idk != null && idk.getResponseTime() < 0)
                    bots.add(player);
            }
            if (nameCheck.isEnabled()) {
                final String valid = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_";
                final String name = player.getName();

                for (int i = 0; i < name.length(); i++) {
                    final String c = String.valueOf(name.charAt(i));
                    if (!valid.contains(c)) {
                        bots.add(player);
                        break;
                    }
                }
            }

            if (sameName.isEnabled()) {
                final String name = player.getName();

                if (names.contains(name))
                    bots.add(player);
                else
                    names.add(name);
            }

//            if (noDamage.isEnabled()) {
//                lastDamage = player.getHealth();
//            }

            if (tabListCheck.isEnabled()) {
                 // get all the players in the tablist
                if (!mc.getNetHandler().getPlayerInfoMap().contains(player.getName())) {
                    bots.add(player);
                    System.out.println("Found player in tablist: " + player.getName());
                }
            }
        }
    }

    @EventTarget
    public void onUpdate (EventUpdate event) {
        ticks++;
        for (EntityLivingBase bot : bots) {
            mc.theWorld.removeEntity(bot);
        }
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        Packet<?> packet = event.getPacket();

//        if (noDamage.isEnabled()) {
//            if (packet instanceof C02PacketUseEntity) {
//                C02PacketUseEntity useEntity = (C02PacketUseEntity) packet;
//                EntityPlayer player = (EntityPlayer) useEntity.getEntityFromWorld(mc.theWorld);
//                if (player.getHealth() == lastDamage) {
//                    bots.add(player);
//                    System.out.println("Removed " + player.getName());
//                }
//
//            }
//        }
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
