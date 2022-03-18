package com.invisiblecat.reload.setting.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisiblecat.reload.setting.Setting;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

public class EntitySetting extends Setting {

    @Expose
    @SerializedName("value")
    private boolean selected;
    private ArrayList<Setting> children = new ArrayList<>();

    public EntitySetting(String name, boolean selected, Entity entity) {
        this.name = name;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void toggle() {
        setSelected(!isSelected());
    }

    public ArrayList<Setting> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Setting> children) {
        this.children = children;
    }

    public void addChild(Setting child) {
        children.add(child);
    }

    public void addChildren(Setting... children) {
        for (Setting child : children)
            addChild(child);
    }
}