package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.PacketUtils;
import com.invisiblecat.reload.utils.TimerUtils;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import com.invisiblecat.reload.utils.player.PlayerUtils;


public class Speed extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla",
            "Verus", "Test", "Vulcan");
    private final NumberSetting speed = new NumberSetting("Speed", 2, 1, 10, 0.1);

    private int wallTicks = 0, verusTicks = 0;

    private TimerUtils timer = new TimerUtils();

    private boolean direction = false;
    private boolean isTimer = false;

    public Speed() {
        super("Speed", 0, Category.MOVEMENT, AutoDisable.FLAG);
        this.addSettings(mode, speed);
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        this.setDisplayName(mode.getSelected());
        if (isTimer) {
            Reload.instance.moduleManager.getModuleByName("Timer").toggle(false);
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
                    break;
                case "ncp":
                    mc.gameSettings.keyBindJump.setState(false);
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        mc.thePlayer.motionY = 0.0;
                        PlayerUtils.strafe(speed.getValue());
                        mc.thePlayer.motionY = 0.21999998688698;
                    } else {
                        PlayerUtils.strafe();
                    }
                case "verus":
                    PlayerUtils.strafe();
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = 0.2632563;
                        PlayerUtils.strafe(.501);
                    } else if (timer.hasTimePassed(500, true)) {
                        mc.thePlayer.motionY = -2;
                        PlayerUtils.strafe(0.464);
                    }
                    if (mc.thePlayer.isCollidedHorizontally) {
                        mc.thePlayer.stepHeight = 1;
                    } else
                        mc.thePlayer.stepHeight = 0.5F;
                    break;
                case "test": {
                    PlayerUtils.strafe();
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                    }
                    break;
                } case "vulcan": {
                    mc.timer.timerSpeed = 1.4F;
                    PlayerUtils.strafe(0.25);
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        mc.thePlayer.motionY = 0.384123;
                    } else {
                        if (timer.hasTimePassed(150, false)) {
                            mc.thePlayer.motionY = -0.167435523;
                            timer.reset();
                        }
                    }
                    break;
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
        mc.timer.timerSpeed = 1f;
        if(isTimer)
            Reload.instance.moduleManager.getModuleByName("Timer").toggle(false);
        mc.thePlayer.stepHeight =.5f;
        mc.thePlayer.motionY = 0.0f;
        super.onDisable();
    }


}
