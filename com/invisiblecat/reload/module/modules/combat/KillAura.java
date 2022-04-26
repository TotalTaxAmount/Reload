package com.invisiblecat.reload.module.modules.combat;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.TimerUtils;
import com.invisiblecat.reload.utils.player.AuraUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class KillAura extends Module {
    private final TimerUtils timer = new TimerUtils();
    private final Random random = new Random();

    private NumberSetting range = new NumberSetting("Range", 3, 1, 8, 0.1);
    private NumberSetting minCps = new NumberSetting("Minimum CPS", 10, 1, 20, 1);
    private NumberSetting maxCps = new NumberSetting("Maximum CPS", 15, 1, 20, 1);

    private ModeSetting sort = new ModeSetting("Sort", "Distance", "Health", "Distance", "Hurt Time");

    private BooleanSetting players = new BooleanSetting("Players", true);
    private BooleanSetting others = new BooleanSetting("Outers", true);

    private BooleanSetting legit = new BooleanSetting("Legit", false);
    private BooleanSetting swing = new BooleanSetting("Swing", true);

    public KillAura() {
        super("KillAura", 0, Category.PLAYER, AutoDisable.NONE);
        this.addSettings(range, sort, players, others, legit, minCps, maxCps, swing);
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        List<EntityLivingBase> entities = mc.theWorld.loadedEntityList
                .stream().filter(entity -> entity instanceof EntityLivingBase)
                .map(entity -> ((EntityLivingBase) entity))
                .filter(entity -> {
                    if (entity instanceof EntityPlayer && !players.isEnabled()) return false;

                    if (!(entity instanceof EntityPlayer) && !others.isEnabled()) return false;

                    if (entity.isDead) return false;

                    if (entity.ticksExisted < 2) return false;

                    if (mc.thePlayer.getDistanceToEntity(entity) > range.getValue()) return false;

                    return mc.thePlayer != entity;
                })
                .sorted(Comparator.comparingDouble(entity -> {
                    switch (sort.getSelected()) {
                        case "Distance":
                            return mc.thePlayer.getDistanceSqToEntity(entity);
                        case "Health":
                            return entity.getHealth();
                        case "Hurt Time":
                            return entity.hurtTime;

                        default:
                            return -1;
                    }
                })).collect(Collectors.toList());

        if (entities.size() > 0) {
            EntityLivingBase target = entities.get(0);

            if (timer.hasTimePassed(1000 / random.nextInt(maxCps.getValueInt() - minCps.getValueInt() + 1) + minCps.getValueInt(), false)) {
                AuraUtils.attack(target, legit.isEnabled());
                if (swing.isEnabled()) mc.thePlayer.swingItem();
                timer.reset();
            }
        }
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {

    }

}
