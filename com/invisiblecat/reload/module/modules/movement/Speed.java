package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.player.PlayerUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Speed extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Verus", "Vanilla",
            "NCP", "Verus", "NCP");
    private final NumberSetting speed = new NumberSetting("Speed", 4, 1, 10, 0.1);
    private final NumberSetting timerBoost = new NumberSetting("Timer Boost", 1, 1, 5, 0.01);
    private final BooleanSetting jump = new BooleanSetting("Jump", true);
    private final NumberSetting jumpHeight = new NumberSetting("Jump Height", 4, 1, 10, 0.1);
    private final BooleanSetting override = new BooleanSetting("Override Jump Height", true);

    private int wallTicks = 0, verusTicks = 0;

    private boolean direction = false;
    public Speed() {
        super("Speed", 0, Category.MOVEMENT, AutoDisable.FLAG);
        this.addSettings(mode, speed, timerBoost, jump, jumpHeight, override);
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        this.setDisplayName("Speed " + ChatFormatting.GRAY + mode.getSelected());
        if (mc.thePlayer != null && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) && !mc.thePlayer.isInWater()) {
            if (!mc.thePlayer.onGround) {
                wallTicks++;
                if (wallTicks > 7 && mc.thePlayer.isCollidedHorizontally) {
                    direction = !direction;
                    wallTicks = 0;
                }
            } else wallTicks = 0;

            switch (mode.getSelected().toLowerCase().replaceAll("\\s", "")) {
                case "vanilla":
                    if (mc.thePlayer.onGround)
                        PlayerUtils.setMotion(speed.getValue() / 2);
                    PlayerUtils.airStrafe();
                    if (jump.isEnabled() && mc.thePlayer.onGround)
                        mc.thePlayer.jump();
                case "ncp":
//                    mc.gameSettings.keyBindJump.setState(false);
//                    if (mc.thePlayer.onGround) {
//                        mc.thePlayer.jump();
//                        mc.thePlayer.motionY = 0.0;
//                        PlayerUtils.setMotion(speed.getValue());
//                        mc.thePlayer.motionY = 0.41999998688698;
//                    } else {
//                        PlayerUtils.airStrafe();
//                    }
                case "verus":
                    mc.thePlayer.setSprinting(false);
                    mc.gameSettings.keyBindJump.setState(false);
                    mc.gameSettings.keyBindSneak.setState(false);
                    if(verusTicks >= 2 && mc.thePlayer.onGround) {
                        PlayerUtils.setMotion(speed.getValue()/4);
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(event.getX(), event.getY(), event.getZ(), true));
                        mc.thePlayer.motionY = 0.43474172222F;
                        verusTicks++;
                    } else {
                        PlayerUtils.airStrafe();
                        verusTicks++;
                    }
                    if (verusTicks >= 6){
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(event.getX(), event.getY(), event.getZ(), false));
                        mc.thePlayer.motionY = -0.43474172222F;
                        verusTicks = 0;
                    }
            }
        }
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {

    }
    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight =.5f;
        mc.thePlayer.motionY = 0.0f;
        super.onDisable();
    }


}
