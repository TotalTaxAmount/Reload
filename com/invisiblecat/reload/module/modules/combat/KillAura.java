package com.invisiblecat.reload.module.modules.combat;

import com.invisiblecat.reload.client.Reload;
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

    private ModeSetting rotMode = new ModeSetting("Rotation Mode", "Normal", "Normal");

    private ModeSetting sort = new ModeSetting("Sort", "Distance", "Health", "Distance", "Hurt Time");

    private BooleanSetting players = new BooleanSetting("Players", true);
    private BooleanSetting others = new BooleanSetting("Outers", true);

    private BooleanSetting legit = new BooleanSetting("Legit", false);
    private BooleanSetting swing = new BooleanSetting("Swing", true);
    private BooleanSetting block = new BooleanSetting("Block", true);

    private List<EntityLivingBase> entities;
    private EntityLivingBase target;


    public KillAura() {
        super("KillAura", 0, Category.PLAYER, AutoDisable.WORLD);
        this.addSettings(range, sort, rotMode, players, others, legit, minCps, maxCps, swing);
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        entities = getTargets();
        if (entities.size() > 0) {
            target = entities.get(0);

            event.setYaw(getRotations(target)[0]);
            event.setPitch(getRotations(target)[1]);

            mc.thePlayer.rotationYawHead = getRotations(target)[0];
            mc.thePlayer.renderYawOffset = getRotations(target)[0];
            mc.thePlayer.rotationPitchHead = getRotations(target)[1];

            attack();
        }
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (maxCps.getValue() < minCps.getValue()) maxCps.setValue(minCps.getValueInt());
        entities = getTargets();
        target = entities.get(0);

        attack();
    }

    private List<EntityLivingBase> getTargets() {
        AntiBot antiBot = (AntiBot) Reload.instance.moduleManager.getModuleByClass(AntiBot.class);
        return mc.theWorld.loadedEntityList
                .stream().filter(entity -> entity instanceof EntityLivingBase)
                .map(entity -> ((EntityLivingBase) entity))
                .filter(entity -> {
                    if (entity instanceof EntityPlayer && !players.isEnabled()) return false;

                    if (!(entity instanceof EntityPlayer) && !others.isEnabled()) return false;

                    if (entity.isDead) return false;

                    if (antiBot.bots.contains(entity)) return false;

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
    }

    private float[] getRotations(EntityLivingBase entity) {
        double deltaX = entity.posX + (entity.posX - entity.lastTickPosX) - mc.thePlayer.posX;
        double deltaZ = entity.posZ + (entity.posZ - entity.lastTickPosZ) - mc.thePlayer.posZ;
        double deltaY = entity.posY - 3.5 + entity.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
        double dist = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ));
        float pitch = (float) Math.toDegrees(-Math.atan(deltaY / dist));

        double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + v);
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + v);
        }

        return new float[]{yaw, pitch};

    }

    private void attack() {
        long cps = random.nextInt(maxCps.getValueInt() - minCps.getValueInt() + 1) + minCps.getValueInt();

        if (timer.hasTimePassed(1000 / cps, true)) {
            if (target.isDead) return;
            AuraUtils.attack(target, legit.isEnabled());
            if (swing.isEnabled()) mc.thePlayer.swingItem();
        }
    }


    public EntityLivingBase getTarget() {
        return target;
    }
}
