package com.invisiblecat.reload.module.setting;

import com.invisiblecat.reload.module.Module;
import com.invisiblecat.reload.module.modules.movement.Sprint;

import java.util.ArrayList;

public class Setting {
    private String name;
    private Module parent;
    private Setting ReqSetting = null;
    private String mode;

    private String stringValue;
    private ArrayList<String> options;

    private boolean booleanValue;

    private double doubleValue;
    private double min;
    private double max;
    private boolean onlyint = false;

    public Setting(String name, Module parent, Setting ReqSetting,String stringValue, ArrayList<String> options) {
        this.name = name;
        this.parent = parent;
        this.ReqSetting = ReqSetting;
        this.stringValue = stringValue;
        this.options = options;
        this.mode = "Dropdown";
    }

    public Setting(String name, Module parent,Setting ReqSetting, boolean booleanValue) {
        this.name = name;
        this.parent = parent;
        this.ReqSetting = ReqSetting;
        this.booleanValue = booleanValue;
        this.mode = "Toggle";
    }

    public Setting(String name, Module parent,Setting ReqSetting, double doubleValue, double min, double max, boolean onlyint) {
        this.name = name;
        this.parent = parent;
        this.ReqSetting = ReqSetting;
        this.doubleValue = doubleValue;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        this.mode = "Slider";

    }
    public String getName() {
        return name;
    }
    public Module getParent() {
        return parent;
    }
    public String getStringVal() {
        return this.stringValue;
    }
    public void setStringVal(String value) {
        this.stringValue = stringValue;
    }
    public ArrayList<String> getOptions() {
        return this.options;
    }
    public boolean getBooleanVal() {
        return this.booleanValue;
    }
    public void setBooleanVal(boolean value) {
        this.booleanValue = booleanValue;
    }
    public double getDoubleVal() {
        if(this.onlyint)
            this.doubleValue = (int)doubleValue;
        return this.doubleValue;
    }
    public void setDoubleVal(double value) {
        this.doubleValue = value;
    }
    public double getMin() {
        return this.min;
    }
    public double getMax() {
        return this.max;
    }
    public boolean isDropdown() {
        return this.mode.equalsIgnoreCase("Dropdown");
    }
    public boolean isToggle() {
        return this.mode.equalsIgnoreCase("Toggle");
    }
    public boolean isSlider() {
        return this.mode.equalsIgnoreCase("Slider");
    }
    public boolean onlyInt(){
        return this.onlyint;
    }
    public boolean hasReqSetting() {
        return !(this.ReqSetting == null);
    }
    public Setting getReqSetting() {
        return this.ReqSetting;
    }


}
