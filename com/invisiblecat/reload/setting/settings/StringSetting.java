package com.invisiblecat.reload.setting.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invisiblecat.reload.setting.Setting;

public class StringSetting extends Setting {
    @Expose
    @SerializedName("value")
    private String value;

    public StringSetting(String name, String value) {
        this.name = name;
        this.value = value;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
