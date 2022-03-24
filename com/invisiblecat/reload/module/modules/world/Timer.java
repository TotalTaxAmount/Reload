package com.invisiblecat.reload.module.modules.world;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.mojang.realmsclient.gui.ChatFormatting;

public class Timer extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Pulse", "Vanilla", "Pulse");
    private final NumberSetting tpsMin = new NumberSetting("TPS Minimum", 10, 1, 20, 0.5);
    private final NumberSetting tpsMax = new NumberSetting("TPS Maximum", 20, 1, 20, 0.5);
    private final NumberSetting tps = new NumberSetting("TPS", 10, 1, 20, 0.5);

    private float ticks = tpsMin.getValueFloat();

    public Timer() {
        super("Timer", 0, Category.WORLD, AutoDisable.FLAG);
        this.addSettings(mode, tps, tpsMin, tpsMax);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        switch (mode.getSelected().toLowerCase().replaceAll("\\s", "")) {
            case "vanilla":
                this.setDisplayName("Timer " + ChatFormatting.GRAY + tps.getValue());
                mc.timer.timerSpeed = tps.getValueFloat() / 10;
            case "pulse":
                this.setDisplayName("Timer " + ChatFormatting.GRAY + ticks);
                if(ticks > tpsMax.getValueFloat()) {
                    ticks = tpsMin.getValueFloat();
                } else {
                    ticks += tps.getIncrement();
                }
                mc.timer.timerSpeed = ticks / 10;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1f;
    }

}
