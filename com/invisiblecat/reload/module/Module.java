package com.invisiblecat.reload.module;

import com.invisiblecat.reload.Reload;
import com.invisiblecat.reload.utils.ChatUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;

public class Module {
    protected Minecraft mc = Minecraft.getMinecraft();

    private String name, displayName;
    private int key;
    private Category category;
    private boolean toggled;

    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
        this.displayName = this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
        toggled = false;

        setup();
    }

    public void onEnable() {
        Reload.instance.eventManager.register(this);
    }
    public void onDisable() {
        Reload.instance.eventManager.unregister(this);
    }
    public void onToggle() {
        //ChatUtils.sendChatMessageClient("Toggled: " + this.getDisplayName() + " [" +  (this.isToggled() ? ChatFormatting.GREEN + "On" : ChatFormatting.RED + "Off") + ChatFormatting.RESET + "]");
        ChatUtils.sendChatMessageClient("[" + (this.isToggled() ? ChatFormatting.GREEN + "Enabled" : ChatFormatting.RED + "Disabled") + ChatFormatting.RESET + "]: " + this.getDisplayName());

    }
    public void toggle() {
        toggled = !toggled;
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
}