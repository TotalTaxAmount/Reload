package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPostMotionUpdate;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.module.modules.combat.KillAura;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.utils.PacketUtils;
import com.invisiblecat.reload.utils.TimerUtils;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.RandomUtils;

public class NoSlow extends Module {
    private ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "NCP", "Place", "Delay");
    private BooleanSetting onlySwords = new BooleanSetting("Only Swords", true);

    private TimerUtils timer = new TimerUtils();

    private KillAura ka;

    private boolean lolBadAc;

    public NoSlow() {
        super("NoSlow", 0, Category.MOVEMENT, AutoDisable.NONE);
        this.addSettings(mode, onlySwords);

    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setDisplayName(mode.getSelected());
        ka = (KillAura) Reload.instance.moduleManager.getModuleByClass(KillAura.class);
    }


    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        if (onlySwords.isEnabled() && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) return;
        switch (mode.getSelected()) {
            case "Delay": {
                if (!mc.thePlayer.isBlocking()) lolBadAc = false;

                if (mc.thePlayer.isBlocking() && mc.thePlayer.ticksExisted % 5 == 0 && lolBadAc) {
                    mc.playerController.syncCurrentPlayItem();
                    PacketUtils.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));

                    lolBadAc = false;
                }

                if (mc.thePlayer.isBlocking() && mc.thePlayer.ticksExisted % 5 == 1 && !lolBadAc) {
                    mc.playerController.syncCurrentPlayItem();
                    PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));

                    lolBadAc = true;
                }
                break;


            } case "MCP": {
                if (mc.thePlayer.isBlocking()) {
                    mc.playerController.syncCurrentPlayItem();
                    PacketUtils.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
                break;
            }
        }
    }

    @EventTarget
    public void onPostMotionUpdate(EventPostMotionUpdate event) {
        if (onlySwords.isEnabled() && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) return;

        switch (mode.getSelected()) {
            case "Place": {
                if (mc.thePlayer.isUsingItem() && ka.getTarget() == null)
                    PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
                break;
            } case "NCP": {
                if (mc.thePlayer.isBlocking() && timer.hasTimePassed(100, true)) {
                    mc.playerController.syncCurrentPlayItem();
                    PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
                }
            }
        }
    }
}
