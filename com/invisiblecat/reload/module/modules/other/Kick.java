package com.invisiblecat.reload.module.modules.other;

import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Kick extends Module {
    public Kick() {
        super("kick", 0, Category.OTHER);
    }

    @Override
    public void onEnable() {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, !mc.thePlayer.onGround));
    }
}
