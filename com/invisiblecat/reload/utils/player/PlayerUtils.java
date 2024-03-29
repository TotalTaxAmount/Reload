package com.invisiblecat.reload.utils.player;

import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import javafx.geometry.BoundingBox;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class PlayerUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();



    public static void strafe(double speed) {
        double forward = Minecraft.getMinecraft().thePlayer.moveForward;
        double strafe = Minecraft.getMinecraft().thePlayer.moveStrafing;
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            Minecraft.getMinecraft().thePlayer.motionX = 0.0;
            Minecraft.getMinecraft().thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            double sin = Math.sin(Math.toRadians(yaw + 90.0F));
            double cos = Math.cos(Math.toRadians(yaw + 90.0F));
            double x = forward * speed * cos + strafe * speed * sin;
            double z = forward * speed * sin - strafe * speed * cos;
            Minecraft.getMinecraft().thePlayer.motionX = (x);
            Minecraft.getMinecraft().thePlayer.motionZ = (z);
        }

    }
    public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }
    public static void strafe() {
        double forward = Minecraft.getMinecraft().thePlayer.moveForward;
        double strafe = Minecraft.getMinecraft().thePlayer.moveStrafing;
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            Minecraft.getMinecraft().thePlayer.setVelocity(0, Minecraft.getMinecraft().thePlayer.getVelocityPlayer().y, 0);
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            final double cos = Math.cos(Math.toRadians(yaw + 90.0F));
            double x = forward  * cos + strafe  * Math.sin(Math.toRadians(yaw + 90.0F));
            double z = forward  * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe  * cos;
            Minecraft.getMinecraft().thePlayer.setVelocity(x, Minecraft.getMinecraft().thePlayer.getVelocityPlayer().y, z);
        }

    }

    public static double getGroundInfo() {
        Minecraft mc = Minecraft.getMinecraft();
        AxisAlignedBB BoundingBox = mc.thePlayer.getEntityBoundingBox();
        double blockHeight = 0;
        double y = mc.thePlayer.posY;
        while (y > 0.0) {
            AxisAlignedBB customBoundingBox = new AxisAlignedBB(BoundingBox.maxX, y + blockHeight, BoundingBox.maxZ, BoundingBox.minX, y, BoundingBox.minZ);
            if(mc.theWorld.checkBlockCollision(customBoundingBox)) {
                if (blockHeight <= 0.05) return y + blockHeight;
                y += blockHeight;
                blockHeight = 0.05;
            }
            y -= blockHeight;
        }
        return 0.0;
    }

    public static void bypassVanillaKick() {
        Minecraft mc = Minecraft.getMinecraft();
        double ground = getGroundInfo();
        new Thread("Vanilla kick") {
            @Override
            public void run() {
                double posY = mc.thePlayer.posY;
                while (posY > ground) {
                    mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, posY, mc.thePlayer.posZ, true));
                    if (posY - 8.0 < ground) break; // Prevent next step
                    posY -= 8.0;
                }
            }
        }.start();
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, ground, mc.thePlayer.posZ, true));
        double posY = ground;
        while (posY < mc.thePlayer.posY) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, posY, mc.thePlayer.posZ, true));
            if (posY + 8.0 > mc.thePlayer.posY) break; // Prevent next step
            posY += 8.0;
        }
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));

    }

    public static void selfHurt() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.3, mc.thePlayer.posZ, true));
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
    }
    public static void setSpeed(final double moveSpeed, double yVelocity, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0D) {
            if (pseudoStrafe > 0.0D) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? -45 : 45);
            } else if (pseudoStrafe < 0.0D) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? 45 : -45);
            }

            strafe = 0.0D;
            if (pseudoForward > 0.0D) {
                forward = 1.0D;
            } else if (pseudoForward < 0.0D) {
                forward = -1.0D;
            }
        }

        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }

        double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
        double x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        double z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
        mc.thePlayer.setVelocity(x, yVelocity, z);
    }

    public static Block getBlock(double xCoord, double yCoord, double zCoord) {
        return mc.theWorld.getBlockState(new BlockPos(xCoord, yCoord, zCoord)).getBlock();
    }

}
