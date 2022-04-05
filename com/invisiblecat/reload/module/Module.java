package com.invisiblecat.reload.module;

import com.invisiblecat.reload.client.Reload;
import com.invisiblecat.reload.client.ui.hud.notification.Notification;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationManager;
import com.invisiblecat.reload.client.ui.hud.notification.NotificationType;
import com.invisiblecat.reload.event.EventManager;
import com.invisiblecat.reload.setting.Setting;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.client.ui.sound.PlaySounds;
import com.invisiblecat.reload.utils.chat.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Module {
    protected Minecraft mc = Minecraft.getMinecraft();

    private String name;
    private String displayName;
    private int key;
    private Category category;
    private AutoDisable autoDisable;
    private boolean toggled;

    private final List<Setting> settings = new ArrayList<>();
    private final BooleanSetting hide = new BooleanSetting("Hide", false);

    public Module() {
        super();
    }

    public Module(String name, int key, Category category, AutoDisable autoDisable) {
        this.name = name;
        this.key = key;
        this.category = category;
        this.autoDisable = autoDisable;

        this.addSettings(hide);
        toggled = false;


    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }
    public List<Setting> getSettings() {
        return settings;
    }
    public Setting getSetting(String name) {
        for (Setting setting : this.settings) {
            if(setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }
    public void onEnable() {
        Reload.instance.eventManager.register(this);

    }
    public void onDisable() {
        EventManager.unregister(this);

    }
    public void onToggle() {
        if (this.isEnabled()) {
            NotificationManager.show(new Notification( NotificationType.INFO, "Info", this.getName() + " has been enabled", 1));
            if (Files.exists(Paths.get("../src/minecraft/assets/minecraft/reload/sound/enable.wav"))) {
                PlaySounds sound = new PlaySounds(new File("../src/minecraft/assets/minecraft/reload/sound/enable.wav"));
                sound.playSound();
            }
        }
        if (!this.isEnabled()) {
            NotificationManager.show(new Notification( NotificationType.INFO, "Info", this.getName() + " has been disabled", 1));
            if (Files.exists(Paths.get("../src/minecraft/assets/minecraft/reload/sound/disable.wav"))) {
                PlaySounds sound = new PlaySounds(new File("../src/minecraft/assets/minecraft/reload/sound/disable.wav"));
                sound.playSound();
            }
        }
        //ChatUtils.sendChatMessageClient("Toggled: " + this.getDisplayName() + " [" +  (this.isToggled() ? ChatFormatting.GREEN + "On" : ChatFormatting.RED + "Off") + ChatFormatting.RESET + "]");
        ChatUtils.sendChatMessageClient("[" + (this.isEnabled() ? ChatFormatting.GREEN + "Enabled" : ChatFormatting.RED + "Disabled") + ChatFormatting.RESET + "]: " + this.getName(), ChatUtils.Type.INFO);
    }
    public AutoDisable getAutoDisable() {
        return autoDisable;
    }
    public void setEnabled(boolean t) {
        toggled = t;
    }
    public void toggle(boolean onToggle) {
        toggled = !toggled;
        if(onToggle)
            onToggle();
        if(toggled)
            onEnable();
        else
            onDisable();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public boolean isEnabled() {
        return toggled;
    }
    public String getDisplayName() {
        return displayName == null ? "" : displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return name+"{" +
                "displayName='" + displayName + '\'' +
                ", key=" + key +
                ", category=" + category +
                ", toggled=" + toggled +
                '}';
    }
    public enum AutoDisable {
        RESPAWN, FLAG, WORLD, NONE;
    }
}