package com.invisiblecat.reload.module.modules.combat;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.HUD;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPostMotionUpdate;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class KillAura extends Module {
    private final TimerUtils timer = new TimerUtils();
    private final Random random = new Random();

    private NumberSetting range = new NumberSetting("Range", 3, 1, 8, 0.1);
    private NumberSetting rotRange = new NumberSetting("Rotation Range", 3, 1, 12, 0.1);
    private NumberSetting minCps = new NumberSetting("Minimum CPS", 10, 1, 20, 1);
    private NumberSetting maxCps = new NumberSetting("Maximum CPS", 15, 1, 20, 1);

    private ModeSetting timing = new ModeSetting("Timing", "Post", "Pre", "Post", "Both");

    private ModeSetting rotMode = new ModeSetting("Rotation Mode", "Normal", "Normal", "Down", "Snap", "Legit");
    private NumberSetting snapDegree = new NumberSetting("Snap Degree", 10, 1, 45, 1);

    private ModeSetting sort = new ModeSetting("Sort", "Distance", "Health", "Distance", "Hurt Time");
    private ModeSetting blockMode = new ModeSetting("Block Mode", "Normal", "Normal", "Fake");

    private BooleanSetting players = new BooleanSetting("Players", true);
    private BooleanSetting others = new BooleanSetting("Outers", true);
    private BooleanSetting invsibles = new BooleanSetting("Invisible", false);

    private BooleanSetting legit = new BooleanSetting("Legit", false);
    private BooleanSetting swing = new BooleanSetting("Swing", true);
    private BooleanSetting block = new BooleanSetting("Block", true);
    private BooleanSetting targetESP = new BooleanSetting("Target ESP", true);

    private List<EntityLivingBase> entities;
    private EntityLivingBase target;
    private float yaw, pitch,
            lastYaw, lastPitch;

    private AntiBot antiBot;


    private boolean blocking;

    public KillAura() {
        super("KillAura", 0, Category.PLAYER, AutoDisable.WORLD);
        this.addSettings(range, rotRange,sort, rotMode, timing,
                players, others, legit, minCps, maxCps, swing, block, blockMode, invsibles, targetESP, snapDegree);
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        entities = getTargets();
        lastYaw = yaw;
        lastPitch = pitch;
        if (entities.size() > 0) {
            target = entities.get(0);
            yaw = getRotations(target)[0];
            pitch = getRotations(target)[1];



            if (mc.thePlayer.getDistanceToEntity(target) <= rotRange.getValue()) {
                event.setYaw(yaw);
                event.setPitch(pitch);

                mc.thePlayer.rotationYawHead = yaw;
                mc.thePlayer.renderYawOffset = yaw;
                mc.thePlayer.rotationPitchHead = pitch;
            }

            if (timing.is("Pre") || timing.is("Both")) {
                attack();
            }
        }
    }

    @EventTarget
    public void onPostMotionUpdate(EventPostMotionUpdate event) {
        if (blockMode.is("Normal")) {
            if (block.isEnabled() && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && target.isEntityAlive() && mc.thePlayer.getDistanceToEntity(target) <= range.getValue()) {
                mc.gameSettings.keyBindUseItem.setState(true);
            } else {
                if (mc.thePlayer.isBlocking()) {
                    mc.gameSettings.keyBindUseItem.setState(false);
                }
            }
        }

        if (timing.is("Post") || timing.is("Both")) {
            if (entities.size() > 0) {
                target = entities.get(0);
                yaw = getRotations(target)[0];
                pitch = getRotations(target)[1];
                attack();
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        antiBot = (AntiBot) Reload.instance.moduleManager.getModuleByClass(AntiBot.class);
        this.setDisplayName(rotMode.getSelected() + ", " + timing.getSelected());
        if (maxCps.getValue() < minCps.getValue()) maxCps.setValue(minCps.getValueInt());
        if (rotRange.getValueInt() < range.getValueInt()) rotRange.setValue(range.getValueInt());

        entities = getTargets();

        if (entities == null) {
            target = null;
            return;
        }
        target = entities.get(0);


        //attack();
    }

    private List<EntityLivingBase> getTargets() {
        List<EntityLivingBase> var2 = mc.theWorld.loadedEntityList
                .stream().filter(entity -> entity instanceof EntityLivingBase)
                .map(entity -> ((EntityLivingBase) entity))
                .filter(entity -> {
                    if (entity instanceof EntityPlayer && !players.isEnabled()) return false;

                    if (!(entity instanceof EntityPlayer) && !others.isEnabled()) return false;

                    if (!entity.isEntityAlive()) return false;

                    if (antiBot.bots.contains(entity)) return false;



                    if (entity.ticksExisted < 2) return false;

                    if (entity.isInvisible() && !invsibles.isEnabled()) return false;

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
        if (var2.isEmpty() && timer.hasTimePassed(1000, true)) return null;
        return var2;
    }


    private void unblock() {
        if (blocking) {
            mc.gameSettings.keyBindUseItem.setState(false);
            PacketUtils.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            blocking = false;
        }
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

        switch (rotMode.getSelected()) {
            case "Snap":
                yaw = (float) Math.round(yaw / snapDegree.getValueInt()) * snapDegree.getValueInt();
                pitch = (float) Math.round(pitch / snapDegree.getValueInt()) * snapDegree.getValueInt();
                break;
            case "Normal":
                break;
            case "Down":
                pitch = RandomUtils.nextFloat(89, 90);
                break;
            case "Smooth":
                float fps = Minecraft.getDebugFPS();
                float yawDelta = (float) (((((yaw - lastYaw) + 540) % 360) - 180) / (fps / 200 * (1 + Math.random())));
                float pitchDelta = (float) (((((pitch - lastPitch) + 540) % 360) - 180) / (fps / 200 * (1 + Math.random())));

                yaw = lastYaw + yawDelta;
                pitch = lastPitch + pitchDelta;
            case "Legit":
                yaw += Math.random() * 10 - 5;
                pitch += Math.random() * 10 - 5;
                break;

            case "Smart":
                if (AuraUtils.isLookingAtEntity(entity, yaw, lastPitch, 47.212F))
                    pitch = lastPitch ;

                if (AuraUtils.isLookingAtEntity(entity, lastYaw, pitch, 19.123F))
                    yaw = lastYaw;

                break;

            case "None":
                yaw = mc.thePlayer.rotationYaw;
                pitch = mc.thePlayer.rotationPitch;
                break;
        }

        return new float[]{yaw, pitch};
    }

    private void attack() {
        long cps = random.nextInt(maxCps.getValueInt() - minCps.getValueInt() + 1) + minCps.getValueInt();

        if (timer.hasTimePassed(1000 / cps, true)) {
            if (!target.isEntityAlive()) return;
            AuraUtils.attack(target, legit.isEnabled());
            if (swing.isEnabled()) mc.thePlayer.swingItem();
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        target = null;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        target = null;

    }

    public EntityLivingBase getTarget() {
        return target;
    }
}
