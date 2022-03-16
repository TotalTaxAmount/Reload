package com.invisiblecat.reload.utils.render;

import java.awt.*;

public class ColorUtils {
    public static int Rainbow() {
        float hue = (System.currentTimeMillis() % 3000) / 3000f;
        int color = Color.HSBtoRGB(hue, 1, 1);
        return color;
    }
}
