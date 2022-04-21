package com.invisiblecat.reload.module.modules.combat;


import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.TimerUtils;
import com.invisiblecat.reload.utils.player.AuraUtils;
import com.invisiblecat.reload.utils.player.RotationUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.S42PacketCombatEvent;

public class KillAura extends Module {

    private NumberSetting range = new NumberSetting("Range", 3, 1, 6, 1);
    private NumberSetting aps = new NumberSetting("APS", 20, 1, 20, 1);


    private BooleanSetting autoBlock = new BooleanSetting("Auto Block", false);
    private BooleanSetting legitAttack = new BooleanSetting("Legit Attack", false);
    private BooleanSetting swing = new BooleanSetting("Swing", false);


    private static float yaw, pitch;
    public EntityLivingBase target;
    private TimerUtils timer = new TimerUtils();


    public KillAura() {
        super("KillAura", 0, Category.COMBAT, AutoDisable.WORLD);
        this.addSettings(range, autoBlock, legitAttack, swing, aps);
    }


    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
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

        if (timer.hasTimePassed(1000 / aps.getValueInt(), false)) {
            AuraUtils.attack(target, legitAttack.isEnabled());
            mc.thePlayer.swingItem();
            timer.reset();
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        target = AuraUtils.getTarget(range.getValueInt());

        if (target == null) {
            return;
        }
        float[] rotations = RotationUtils.getRotations(target);
        yaw = rotations[0];
        pitch = rotations[1];

        if (timer.hasTimePassed(1000 / aps.getValueInt(), false)) {
            AuraUtils.attack(target, legitAttack.isEnabled());
            mc.thePlayer.swingItem();
            timer.reset();
        }
    }


    @Override
    public void onEnable() {
        super.onEnable();
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
    }
}
