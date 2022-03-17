package com.invisiblecat.reload.module;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.event.EventManager;
import com.invisiblecat.reload.ui.sound.PlaySounds;
import com.invisiblecat.reload.utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Module {
    protected Minecraft mc = Minecraft.getMinecraft();

    private String name;
    private String displayName;
    private int key;
    private Category category;
    private AutoDisable autoDisable;
    private boolean toggled;

    public Module() {
        super();
    }

    public Module(String name, int key, Category category, AutoDisable autoDisable) {
        this.name = name;
        this.key = key;
        this.category = category;
        this.displayName = this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
        this.autoDisable = autoDisable;
        toggled = false;
        setup();
    }
    public void onEnable() {
        Reload.instance.eventManager.register(this);
        if(Files.exists(Paths.get("../src/minecraft/assets/minecraft/reload/sound/enable.wav"))) {
            PlaySounds sound = new PlaySounds(new File("../src/minecraft/assets/minecraft/reload/sound/enable.wav"));
            sound.playSound();
        }
    }
    public void onDisable() {
        EventManager.unregister(this);
        if(Files.exists(Paths.get("../src/minecraft/assets/minecraft/reload/sound/disable.wav"))) {
            PlaySounds sound = new PlaySounds(new File("../src/minecraft/assets/minecraft/reload/sound/disable.wav"));
            sound.playSound();
        }
    }
    public void onToggle() {
        //ChatUtils.sendChatMessageClient("Toggled: " + this.getDisplayName() + " [" +  (this.isToggled() ? ChatFormatting.GREEN + "On" : ChatFormatting.RED + "Off") + ChatFormatting.RESET + "]");
        ChatUtils.sendChatMessageClient("[" + (this.isToggled() ? ChatFormatting.GREEN + "Enabled" : ChatFormatting.RED + "Disabled") + ChatFormatting.RESET + "]: " + this.getDisplayName());
    }
    public AutoDisable getAutoDisable() {
        return autoDisable;
    }
    public void setToggled(boolean t) {
        this.toggled = t;
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
    public boolean isToggled() {
        return toggled;
    }
    public String getDisplayName() {
        return displayName == null ? name : displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void setup() {}

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