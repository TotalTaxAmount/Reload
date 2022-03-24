package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import com.invisiblecat.reload.utils.player.PlayerUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Speed extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Verus", "Vanilla",
            "NCP", "Verus", "NCP");
    private final NumberSetting speed = new NumberSetting("Speed", 1, 1, 10, 0.1);

    private int wallTicks = 0, verusTicks = 0;

    private boolean direction = false;
    private boolean isTimer = false;

    public Speed() {
        super("Speed", 0, Category.MOVEMENT, AutoDisable.FLAG);
        this.addSettings(mode, speed);
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        this.setDisplayName("Speed " + ChatFormatting.GRAY + mode.getSelected());
        if(isTimer) {
            Reload.instance.moduleManager.getModuleByName("Timer").setEnabled(false);
        }

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
                        PlayerUtils.strafe(speed.getValue() / 2);
                    PlayerUtils.strafe();
                    if (mc.thePlayer.onGround)
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
                    mc.timer.timerSpeed = 1f;
                    mc.thePlayer.setSprinting(false);
                    mc.gameSettings.keyBindJump.setState(false);
                    mc.gameSettings.keyBindSneak.setState(false);
                    if(verusTicks >= 2 && mc.thePlayer.onGround) {
                        PlayerUtils.strafe(speed.getValueFloat() / 4);
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(event.getX(), event.getY(), event.getZ(), true));
                        mc.thePlayer.motionY = 0.43474172222F;
                        verusTicks++;
                    } else {
                        PlayerUtils.strafe();
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

    @Override
    public void onEnable() {
        isTimer = Reload.instance.moduleManager.getModuleByName("Timer").isEnabled();
        if(isTimer) {
            Reload.instance.moduleManager.getModuleByName("Timer").setEnabled(false);
            ChatUtils.sendChatMessageClient("Timer is incompatible with speed, it will be enabled after speed is disabled", ChatUtils.Type.WARN);
        }
        mc.timer.timerSpeed = 1f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if(isTimer)
            Reload.instance.moduleManager.getModuleByName("Timer").setEnabled(true);
        mc.thePlayer.stepHeight =.5f;
        mc.thePlayer.motionY = 0.0f;
        super.onDisable();
    }


}
