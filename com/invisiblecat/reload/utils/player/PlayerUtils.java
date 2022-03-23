package com.invisiblecat.reload.utils.player;

import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import net.minecraft.client.Minecraft;

public class PlayerUtils {
    private final Minecraft mc = Minecraft.getMinecraft();

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
        Minecraft.getMinecraft().thePlayer.setVelocity(x, yVelocity, z);
    }

    public static void setMotion(double speed) {
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
            double x = forward * speed * cos + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            double z = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * cos;
            Minecraft.getMinecraft().thePlayer.setVelocity(x, Minecraft.getMinecraft().thePlayer.getVelocityPlayer().y, z);
        }

    }
    public static void airStrafe() {
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
}
