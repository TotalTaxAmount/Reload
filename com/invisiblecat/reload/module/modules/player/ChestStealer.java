//package com.invisiblecat.reload.module.modules.player;
//
//
//import com.invisiblecat.reload.client.ui.hud.notification.Notification;
//import com.invisiblecat.reload.client.ui.hud.notification.NotificationManager;
//import com.invisiblecat.reload.client.ui.hud.notification.NotificationType;
//import com.invisiblecat.reload.event.EventTarget;
//import com.invisiblecat.reload.event.events.Event2D;
//import com.invisiblecat.reload.event.events.EventPreMotionUpdate;
//import com.invisiblecat.reload.event.events.EventRecivePacket;
//import com.invisiblecat.reload.module.Category;
//import com.invisiblecat.reload.module.Module;
//import com.invisiblecat.reload.setting.settings.BooleanSetting;
//import com.invisiblecat.reload.setting.settings.NumberSetting;
//import com.invisiblecat.reload.utils.TimerUtils;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.ScaledResolution;
//import net.minecraft.client.gui.inventory.GuiChest;
//import net.minecraft.inventory.ContainerChest;
//import net.minecraft.item.ItemStack;
//import net.minecraft.network.play.server.S30PacketWindowItems;
//import org.apache.commons.lang3.RandomUtils;
//import org.lwjgl.input.Keyboard;
//import org.lwjgl.opengl.Display;
//
//public class ChestStealer extends Module {
//
//    private final TimerUtils timer = new TimerUtils();
//    private final TimerUtils timer2 = new TimerUtils();
//
//    private final NumberSetting minDelay = new NumberSetting("Min Delay", 100, 0, 1000, 1);
//    private final NumberSetting maxDelay = new NumberSetting("Max Delay", 200, 0, 1000, 1);
//    private final BooleanSetting stealTrashItems = new BooleanSetting("Steal trash items", true);
//    private final BooleanSetting autoClose = new BooleanSetting("Auto Close", true);
//    private final BooleanSetting hideGui = new BooleanSetting("Hide Gui", true);
//    private final BooleanSetting chestName = new BooleanSetting("Check chest name", true);
//
//
//    int timerDecide = 0;
//    private boolean done;
//    private int ticksInChest;
//    public static boolean hideChestGui, closeAfterContainer, lastInChest;
//
//
//    public ChestStealer() {
//        super("ChestStealer", 0, Category.PLAYER, AutoDisable.NONE);
//        this.addSettings(minDelay, maxDelay, stealTrashItems, autoClose, hideGui, chestName);
//    }
//
//    @Override
//    public void onDisable() {
//        super.onDisable();
//        timer.reset();
//        timer2.reset();
//        hideChestGui = false;
//        closeAfterContainer = false;
//        done = false;
//    }
//    @EventTarget
//    public void onRecivePacket(EventRecivePacket event) {
//        if (mc.thePlayer.ticksExisted <= 60) return;
//
//        if (event.getPacket() instanceof S30PacketWindowItems) {
//            done = true;
//        }
//    }
//
//    @EventTarget
//    public void onPreMotionUpdate(EventPreMotionUpdate event) {
//        if (mc.thePlayer.ticksExisted <= 60) return;
//        if (mc.currentScreen instanceof GuiChest) {
//            ticksInChest++;
//
//            if (ticksInChest * 50 > 255) {
//                ticksInChest = 10;
//            }
//        } else {
//            ticksInChest--;
//            done = false;
//            if (ticksInChest < 0) {
//                ticksInChest = 0;
//            }
//        }
//    }
//
//    @EventTarget
//    public void onRender2D(Event2D event) {
//        if (mc.thePlayer.ticksExisted <= 60) return;
//        if (!lastInChest) timer2.reset();
//        lastInChest = mc.currentScreen instanceof GuiChest;
//        if (mc.currentScreen instanceof GuiChest) {
//            if (hideGui.isEnabled()) {
//                hideChestGui = true;
//            }
//            if (chestName.isEnabled()) {
//                final String name = ((GuiChest) mc.currentScreen).lowerChestInventory.getDisplayName().getUnformattedText();
//
//                if (!name.toLowerCase().contains("chest")) return;
//            }
//
//            if (hideGui.isEnabled()) {
//                final ScaledResolution SR = new ScaledResolution(mc);
//                final String t = "Stealing chest... Press " + Keyboard.getKeyName(mc.gameSettings.keyBindInventory.getKeyCode()) + " to close the chest";
//                int o;
//
//                o = ticksInChest * 50;
//
//                if (o > 255)
//                    o = 255;
//                NotificationManager.show(new Notification(NotificationType.INFO, "Chest Stealer", t, 1));
//            }
//            if (timerDecide == 0) {
//                final int delayFirst = (int) Math.floor(Math.min(minDelay.getValue(), maxDelay.getValue()));
//                final int delaySecond = (int) Math.ceil(Math.max(minDelay.getValue(), maxDelay.getValue()));
//                timerDecide = RandomUtils.nextInt(delayFirst, delaySecond);
//            }
//            if (timer.hasTimePassed(timerDecide, false)) {
//                final ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
//
//                for (int i = 0; i < chest.inventorySlots.size(); i++) {
//                    final ItemStack stack = chest.getLowerChestInventory().getStackInSlot(i);
//                    if (stack != null && (itemWhitelisted(stack) && !stealTrashItems.isEnabled())) {
//                        mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
//                        timer.reset();
//
//                        final int delayFirst = (int) Math.floor(Math.min(minDelay.getValue(), maxDelay.getValue()));
//                        final int delaySecond = (int) Math.ceil(Math.max(minDelay.getValue(), maxDelay.getValue()));
//
//                        timerDecide = RandomUtils.nextInt(delayFirst, delaySecond);
//
//                        donw = true;
//                        return;
//                    }
//                }
//
//                if (autoClose.isEnabled() && (done || ticksInChest > 10)) {
//                    mc.thePlayer.closeScreen();
//                }
//            }
//        } else {
//            hideChestGui = false;
//        }
//    }
//}
