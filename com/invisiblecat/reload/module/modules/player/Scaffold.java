package com.invisiblecat.reload.module.modules.player;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPostMotionUpdate;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.module.modules.movement.Speed;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.utils.BlockUtils;
import com.invisiblecat.reload.utils.EnumFacingUtils;
import com.invisiblecat.reload.utils.PacketUtils;
import com.invisiblecat.reload.utils.player.PlayerUtils;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.Random;

public class Scaffold extends Module {
    private ModeSetting mode = new ModeSetting("Mode", "Simple", "Normal", "Simple");
    private ModeSetting timing = new ModeSetting("Timing", "Pre", "Pre", "Post");
    private BooleanSetting jump = new BooleanSetting("Jump", false);
    private BooleanSetting keepY = new BooleanSetting("Keep Y", false);
    private BooleanSetting sprint = new BooleanSetting("Sprint", false);
    private BooleanSetting drag = new BooleanSetting("Drag", true);

    private EnumFacingUtils enumFacing;
    private int offGroundTicks = 0;
    private double startY;

    public Scaffold() {
        super("Scaffold", 0, Category.PLAYER, AutoDisable.WORLD);
        this.addSettings(mode, timing, jump, keepY, sprint, drag);
    }

    @Override
    public void onEnable() {
        startY = mc.thePlayer.posY;
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.thePlayer.setSprinting(sprint.isEnabled());
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        if (mc.thePlayer.onGround) {
            offGroundTicks = 0;
        } else
            offGroundTicks++;

        if (keepY.isEnabled() && mc.thePlayer.isCollidedVertically) {
            mc.thePlayer.motionY = 0.0D;
        }

        if (mc.thePlayer.onGround || (mc.gameSettings.keyBindJump.isKeyDown() && !keepY.isEnabled()))
            startY = mc.thePlayer.posY;

        if (keepY.isEnabled() && mc.thePlayer.posY < startY) startY = mc.thePlayer.posY;

        BlockPos block = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ );

        enumFacing = getEnumFacing(new Vec3(block.getX(), block.getY(), block.getZ()));

        event.setYaw(getBlockRotations(block)[0]);
        event.setPitch(getBlockRotations(block)[1]);

        mc.thePlayer.renderYawOffset = getBlockRotations(block)[0];
        mc.thePlayer.rotationYawHead = getBlockRotations(block)[0];
        mc.thePlayer.rotationPitchHead = getBlockRotations(block)[1];


        if (timing.getSelected().equalsIgnoreCase("pre") && BlockUtils.getBlock(block) instanceof BlockAir && offGroundTicks < 5) {
            PacketUtils.sendPacketNoEvent(new C0APacketAnimation());

//            MovingObjectPosition work = mc.thePlayer.rayTraceCustom(3.0D, mc.timer.renderPartialTicks, getBlockRotations(block)[0], getBlockRotations(block)[1]);
//            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), block, enumFacing.getEnumFacing(), work.hitVec);
            mc.thePlayer.swingItem();
            BlockUtils.placeBlock(block, getBlockRotations(block)[0], getBlockRotations(block)[1], enumFacing.getEnumFacing());

            PacketUtils.sendPacketNoEvent(new C0APacketAnimation());
        }
    }

    @EventTarget
    public void onPostMotionUpdate(EventPostMotionUpdate event) {
        BlockPos block = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ );

        mc.thePlayer.renderYawOffset = getBlockRotations(block)[0];
        mc.thePlayer.rotationYawHead = getBlockRotations(block)[0];
        mc.thePlayer.rotationPitchHead = getBlockRotations(block)[1];



        if (timing.getSelected().equalsIgnoreCase("post") && BlockUtils.getBlock(block) instanceof BlockAir && offGroundTicks < 5) {
            PacketUtils.sendPacketNoEvent(new C0APacketAnimation());

//            MovingObjectPosition work = mc.thePlayer.rayTraceCustom(3.0D, mc.timer.renderPartialTicks, getBlockRotations(block)[0], getBlockRotations(block)[1]);
//            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), block, enumFacing.getEnumFacing(), work.hitVec);
            mc.thePlayer.swingItem();
            BlockUtils.placeBlock(block, getBlockRotations(block)[0], getBlockRotations(block)[1], enumFacing.getEnumFacing());

            PacketUtils.sendPacketNoEvent(new C0APacketAnimation());
        }


    }

    private float[] getBlockRotations(BlockPos pos) {
        final float[] rotations = BlockUtils.getDirectionToBlock(pos.getX(), pos.getY(), pos.getZ());
        float yaw = 0;
        float pitch = 0;

        switch (mode.getSelected().toLowerCase().replaceAll(" ", "")) {
            case "simple":
                Random random = new Random();
                switch (enumFacing.getEnumFacing()) {
                    case SOUTH: {
                        yaw = 180;
                        break;
                    }

                    case EAST: {
                        yaw = 90;
                        break;
                    }

                    case WEST: {
                        yaw = -90;
                        break;
                    }
                }
                if (mc.thePlayer.motionY > 0.0D) {
                    pitch = 90;
                } else {
                    pitch = random.nextInt(85 - 80) + 80;
                }
                break;

                case "normal":
                    yaw = rotations[0];
                    pitch = rotations[1];
                    break;


            }
        return new float[]{yaw, pitch};
    }

    private EnumFacingUtils getEnumFacing(Vec3 pos) {
        for (int x2 = -1; x2 <= 1; x2 += 2) {
            if (!(PlayerUtils.getBlock(pos.xCoord + x2, pos.yCoord, pos.zCoord) instanceof BlockAir)) {
                if (x2 > 0) {
                    return new EnumFacingUtils(EnumFacing.WEST, new Vec3(x2, 0, 0));
                } else {
                    return new EnumFacingUtils(EnumFacing.EAST, new Vec3(x2, 0, 0));
                }
            }
        }

        for (int y2 = -1; y2 <= 1; y2 += 2) {
            if (!(PlayerUtils.getBlock(pos.xCoord, pos.yCoord + y2, pos.zCoord) instanceof BlockAir)) {
                if (y2 < 0) {
                    return new EnumFacingUtils(EnumFacing.UP, new Vec3(0, y2, 0));
                }
            }
        }

        for (int z2 = -1; z2 <= 1; z2 += 2) {
            if (!(PlayerUtils.getBlock(pos.xCoord, pos.yCoord, pos.zCoord + z2) instanceof BlockAir)) {
                if (z2 < 0) {
                    return new EnumFacingUtils(EnumFacing.SOUTH, new Vec3(0, 0, z2));
                } else {
                    return new EnumFacingUtils(EnumFacing.NORTH, new Vec3(0, 0, z2));
                }
            }
        }

        return null;
    }

}
