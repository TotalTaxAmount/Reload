package com.invisiblecat.reload.setting;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Setting {

    @Expose
    @SerializedName("name")
    public String name;
    private String displayName;
    private boolean focused;
    private boolean visible = true;
    private ArrayList<Setting> children = new ArrayList<>();

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void addChild(Setting child) {
        children.add(child);
    }

    public void addChildren(Setting... children) {
        for (Setting child : children)
            addChild(child);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public ArrayList<Setting> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Setting> children) {
        this.children = children;
    }
}