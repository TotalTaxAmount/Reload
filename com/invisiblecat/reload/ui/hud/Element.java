package com.invisiblecat.reload.ui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Element {
    private int x, y, width, height;
    private boolean toggled;
    private String name;
    protected Minecraft mc = Minecraft.getMinecraft();
    protected ScaledResolution sr = new ScaledResolution(mc);


    public Element(String name, int x, int y) {
        this.x = x;
        this.y = y;
        toggled = false;
    }

    public void render() {

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
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
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
}
