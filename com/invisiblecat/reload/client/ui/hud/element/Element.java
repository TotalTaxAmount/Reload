package com.invisiblecat.reload.client.ui.hud.element;

import com.invisiblecat.reload.setting.Setting;
import com.invisiblecat.reload.setting.settings.BooleanSetting;
import com.invisiblecat.reload.setting.settings.ModeSetting;
import com.invisiblecat.reload.setting.settings.NumberSetting;
import com.invisiblecat.reload.setting.settings.StringSetting;
import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Element {
    private int width, height;
    private boolean toggled;
    private String name;
    protected Minecraft mc = Minecraft.getMinecraft();
    protected ScaledResolution sr = new ScaledResolution(mc);
    protected static TTFFontRenderer fontRenderer;
    public Draggable draggable;
    private List<Setting> settings = new ArrayList<>();


    public Element(String name, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.name = name;

        draggable = new Draggable(x, y, x + width, y + height, new Color(0, 0, 0, 0).getRGB());

        toggled = true;
    }

    public void render() {
    }

    public void renderDrag(int mouseX, int mouseY) {
        render();
        draggable.draw(mouseX, mouseY);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void onEnable() {}
    public void onToggle() {}
    public void onDisable() {}
    public void toggle() {
        toggled = !toggled;
        onToggle();
        if(toggled)
            onEnable();
        else
            onDisable();
    }
    public boolean isToggled() {
        return toggled;
    }
    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }
    public int getX() {
        return draggable.getxPosition();
    }
    public void setX(int x) {
        draggable.setxPosition(x);
    }
    public int getY() {
        return draggable.getyPosition();
    }
    public void setY(int y) {
        draggable.setyPosition(y);
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
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
    // get setting as BooleanSetting
    public BooleanSetting getBooleanSetting(String name) {
        Setting setting = getSetting(name);
        if(setting instanceof BooleanSetting) {
            return (BooleanSetting) setting;
        }
        return null;
    }

    // get setting as NumberSetting
    public NumberSetting getNumberSetting(String name) {
        Setting setting = getSetting(name);
        if(setting instanceof NumberSetting) {
            return (NumberSetting) setting;
        }
        return null;
    }
    // get setting as StringSetting
    public StringSetting getStringSetting(String name) {
        Setting setting = getSetting(name);
        if(setting instanceof StringSetting) {
            return (StringSetting) setting;
        }
        return null;
    }

    // get setting as ModeSetting
    public ModeSetting getModeSetting(String name) {
        Setting setting = getSetting(name);
        if(setting instanceof ModeSetting) {
            return (ModeSetting) setting;
        }
        return null;
    }

}
