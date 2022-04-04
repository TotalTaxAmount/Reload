package com.invisiblecat.reload.module.modules.player;


import com.invisiblecat.reload.event.EventTarget;
import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
import com.invisiblecat.reload.module.Category;
import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.utils.PacketUtils;
import com.invisiblecat.reload.utils.TimerUtils;
import com.invisiblecat.reload.utils.player.PlayerUtils;
import net.minecraft.client.gui.inventory.GuiInventory;

import java.util.ArrayList;
import java.util.List;

public class InvManager extends Module {
    private int bestSwordSlot = -1, bestPickaxeSlot, bestBowSlot, bestBlockSlot;
    private int[] bestArmorDamage, bestArmorSlot;

    private List<Integer> allSwords = new ArrayList<>();
    private List<Integer> allBows = new ArrayList<>();
    private List<Integer> allPicks = new ArrayList<>();
    private List[] allArmors = new List[4];
    private List<Integer> trash = new ArrayList<>();

    private TimerUtils timer = new TimerUtils();
    private TimerUtils startDelay = new TimerUtils();

    private NumberSetting minDelay = new NumberSetting("Min Delay", 100, 0,1000, 1);
    private NumberSetting maxDelay = new NumberSetting("Max Delay", 200, 0,1000, 1);
    private NumberSetting startDelaySetting = new NumberSetting("Start Delay", 500, 0,1000, 1);
    private BooleanSetting sort = new BooleanSetting("Sort", true);
    private BooleanSetting autoArmor = new BooleanSetting("Auto Armor", true);
    private BooleanSetting noMove = new BooleanSetting("No Move", false);
    private BooleanSetting invOpen = new BooleanSetting("Open Inventory", false);
    private BooleanSetting random = new BooleanSetting("Random", true);

    public InvManager() {
        super("InvManager", 0, Category.PLAYER, AutoDisable.NONE);
    }

    @EventTarget
    public void onPreMotion(EventPreMotionUpdate event) {
        if (noMove.isEnabled()) {
            if (PlayerUtils.isMoving()) {
                startDelay.reset();
            }
            if (invOpen.isEnabled()) {
                if(!(mc.currentScreen instanceof GuiInventory)) {
                    return;
                }
                searchForItems();
                seachForArmor();
                seachForTrash();
            }
        }
    }

    private void searchForItems() {}

}