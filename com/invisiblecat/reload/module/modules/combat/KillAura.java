package com.invisiblecat.reload.module.modules.combat;


import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.PacketUtils;
import com.invisiblecat.reload.utils.TimerUtils;
import com.invisiblecat.reload.utils.player.AuraUtils;
import com.invisiblecat.reload.utils.player.RotationUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class KillAura extends Module {

    private ModeSetting sort = new ModeSetting("Sort", "Sort by health", "Sort by distance", "Sort by health", "Sort by Hurt time");

    private NumberSetting range = new NumberSetting("Range", 3, 1, 6, 1);
    private NumberSetting aps = new NumberSetting("APS", 20, 1, 20, 1);

    private BooleanSetting autoBlock = new BooleanSetting("Auto Block", false);
    private BooleanSetting legitAttack = new BooleanSetting("Legit Attack", false);
    private BooleanSetting swing = new BooleanSetting("Swing", false);


    private static float yaw, pitch;
    public EntityLivingBase target;
    private boolean isBlock;
    private final TimerUtils timer = new TimerUtils();


    public KillAura() {
        super("KillAura", 0, Category.COMBAT, AutoDisable.WORLD);
        this.addSettings(range, autoBlock, legitAttack, swing, aps, sort);
    }


    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        target = AuraUtils.getTarget(range.getValueInt(), sort);
        if (target == null) {
            return;
        }
        float[] rotations = RotationUtils.getRotations(target);
        yaw = rotations[0];
        pitch = rotations[1];

        event.setYaw(yaw);
        event.setPitch(pitch);
        mc.thePlayer.rotationPitchHead = pitch;
        mc.thePlayer.rotationYawHead = yaw;
        mc.thePlayer.renderYawOffset = yaw;

        if (timer.hasTimePassed(1000 / aps.getValueInt(), true)) {
            AuraUtils.attack(target, legitAttack.isEnabled());
            if (swing.isEnabled()) {
                mc.thePlayer.swingItem();
            }
            if (autoBlock.isEnabled()) {
                block();
            }

        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        target = AuraUtils.getTarget(range.getValue(), sort);

        if (target == null) {
            return;
        }
        float[] rotations = RotationUtils.getRotations(target);
        yaw = rotations[0];
        pitch = rotations[1];

        if (timer.hasTimePassed(1000 / aps.getValueInt(), true)) {
            AuraUtils.attack(target, legitAttack.isEnabled());
            if (swing.isEnabled()) {
                mc.thePlayer.swingItem();
            }
            if (autoBlock.isEnabled()) {
                block();
            }
        }
    }

    @Override
    public void onDisable() {
        unblock();
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
    }

    private void block() {
        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
        mc.gameSettings.keyBindUseItem.setState(true);
        mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
        isBlock = true;
    }

    private void unblock() {
        if (isBlock) {
            mc.gameSettings.keyBindUseItem.setState(false);
            PacketUtils.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            isBlock = false;
        }
    }
}
