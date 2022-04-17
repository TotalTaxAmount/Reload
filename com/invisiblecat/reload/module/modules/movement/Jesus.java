package com.invisiblecat.reload.module.modules.movement;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventBB;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.ModeSetting;

public class Jesus extends Module {
    private ModeSetting mode = new ModeSetting("Mode", "Fly", "Bounce", "Fly");

    public Jesus() {
        super("Jesus", 0, Category.MOVEMENT, AutoDisable.FLAG);
        this.addSettings(mode);
    }

    @EventTarget
    public void onPreMotion(EventPreMotionUpdate event) {
        switch (mode.getSelected().toLowerCase().replaceAll(" ", "")) {
            case "bounce":
                if (mc.thePlayer.isInWater() && !mc.thePlayer.isInvisible()) {
                    mc.thePlayer.motionY = 0.1;
                    if(mc.thePlayer.isCollidedHorizontally) {
                        mc.thePlayer.motionY = 0.3;
                    }
                }
                break;
            case "fly":
                if (mc.thePlayer.isInWater() && !mc.thePlayer.isInvisible()) {
                    mc.thePlayer.motionY = 0;
                    event.setY(event.getY() + 0.5);

                }
        }

    }

}
