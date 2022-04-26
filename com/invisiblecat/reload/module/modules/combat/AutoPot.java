package com.invisiblecat.reload.module.modules.combat;

import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.PacketUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public class AutoPot extends Module {
    private NumberSetting health = new NumberSetting("Health", 13, 1, 19, 1);
    private BooleanSetting back = new BooleanSetting("Back", true);

    private final ArrayList<Integer> goodPots = new ArrayList();

    private int oldSlot;

    public AutoPot() {
        super("AutoPot", 0, Category.COMBAT, AutoDisable.NONE);
        this.addSettings(health);

        goodPots.add(6);
        goodPots.add(1);
        goodPots.add(5);
        goodPots.add(8);
        goodPots.add(14);
        goodPots.add(12);
        goodPots.add(10);
        goodPots.add(16);
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotionUpdate event) {
        oldSlot = mc.thePlayer.inventory.currentItem;
        if (mc.thePlayer.getHealth() < health.getValue()) {
            if (mc.thePlayer.inventory.getCurrentItem() == null || !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemPotion)) {
                for (int i = 0; i < 9; i++) {
                    if (mc.thePlayer.inventory.getStackInSlot(i) != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemPotion) {
                        mc.thePlayer.inventory.currentItem = i;
                        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
                    }
                }
            }
        }
    }
}