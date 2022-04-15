package com.invisiblecat.reload.utils.render;

import java.awt.*;

public class ColorUtils {
    public static int Rainbow() {
        float hue = (System.currentTimeMillis() % 3000) / 3000f;
        return Color.HSBtoRGB(hue, 1, 1);
    }

    // Create a function that creates a moving gradient between two color objects.
    // The gradient should move from the first color to the second color.
    // The gradient should move in a linear fashion.
    // It should only use the 2 colors provided.
    // It should not change the alpha value.
    public static int LinearGradient(Color color1, Color color2) {
        int red = color1.getRed() + (color2.getRed() - color1.getRed()) * (int) ((System.currentTimeMillis() % 3000) / 3000f);
        int green = color1.getGreen() + (color2.getGreen() - color1.getGreen()) * (int) ((System.currentTimeMillis() % 3000) / 3000f);
        int blue = color1.getBlue() + (color2.getBlue() - color1.getBlue()) * (int) ((System.currentTimeMillis() % 3000) / 3000f);
        return new Color(red, green, blue).getRGB();
    }

}
