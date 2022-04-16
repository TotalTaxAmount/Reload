package com.invisiblecat.reload.client.ui.hud;

import com.invisiblecat.reload.utils.font.render.TTFFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class Element {
    private int width, height;
    private boolean toggled;
    private String name;
    protected Minecraft mc = Minecraft.getMinecraft();
    protected ScaledResolution sr = new ScaledResolution(mc);
    protected static TTFFontRenderer fontRenderer;
    public Draggable draggable;


    public Element(String name, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.name = name;

        draggable = new Draggable(x, y, x + width, y + height, new Color(0, 0, 0, 0).getRGB());


        toggled = true;
    }

    public void render() {
    }

    public void render(int mouseX, int mouseY) {
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
}
