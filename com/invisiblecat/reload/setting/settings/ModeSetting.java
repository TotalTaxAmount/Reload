package com.invisiblecat.reload.setting.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisiblecat.reload.setting.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {

    @Expose
    @SerializedName("value")
    private String selected;
    private int index;
    private List<String> modes;

    public ModeSetting(String name, String defaultSelected, String... options) {
        this.name = name;
        this.modes = Arrays.asList(options);
        this.index = modes.indexOf(defaultSelected);
        this.selected = modes.get(index);
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
        index = modes.indexOf(selected);
    }

    public boolean is(String mode) {
        return mode.equals(selected);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.selected = modes.get(index);
    }

    public List<String> getModes() {
        return modes;
    }

    public void setModes(List<String> modes) {
        this.modes = modes;
    }

    public void cycleMode() {
        if (index < modes.size() - 1) {
            index++;
            selected = modes.get(index);
        } else if (index >= modes.size() - 1) {
            index = 0;
            selected = modes.get(0);
        }
    }

    public void setMode(String displayString) {
        index = modes.indexOf(displayString);
        selected = modes.get(index);
    }
}