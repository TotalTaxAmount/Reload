package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.player.PlayerUtils;
import com.invisiblecat.reload.utils.TimerUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Fly extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Verus", "Velocity", "Vanilla", "Verus");
    private final NumberSetting speed = new NumberSetting("Speed", 1, 0, 10, 0.1);
    public BooleanSetting damage = new BooleanSetting("Damage", false);
    public BooleanSetting blink = new BooleanSetting("Blink", false);
    boolean hasBeenDamaged = false;
    int wallTicks = 0;
    boolean direction = false;
    private static final TimerUtils timer = new TimerUtils();


    public Fly() {
        super("Fly", 0, Category.MOVEMENT, AutoDisable.FLAG);
        this.addSettings(mode, speed, damage, blink);
    }

    @EventTarget
    public void onPreMotion(EventPreMotionUpdate event) {
        this.setDisplayName("Fly " + ChatFormatting.GRAY + mode.getSelected());
        if (mc.thePlayer == null)
            return;

        if (!mc.thePlayer.onGround) {
            wallTicks++;
            if (wallTicks > 7 && mc.thePlayer.isCollidedHorizontally) {
                direction = !direction;
                wallTicks = 0;
            }
        } else wallTicks = 0;
        if (damage.isEnabled() && !hasBeenDamaged) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.1, mc.thePlayer.posZ, true));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
            hasBeenDamaged = true;
        }
        if (mode.is("Verus")) {
            mc.thePlayer.setVelocity(mc.thePlayer.getVelocityPlayer().x, 0, mc.thePlayer.getVelocityPlayer().z);
            mc.thePlayer.onGround = true;
            mc.thePlayer.capabilities.isFlying = false;
            event.setGround(true);
            PlayerUtils.setMotion(speed.getValueInt());

        }
    }
}

