package com.svalero.filter.filters;


import java.awt.*;

public class InvertColorFilter {

    public static Color apply(Color color) {
        int red = 255 - color.getRed();
        int green = 255 - color.getGreen();
        int blue = 255 - color.getBlue();

        return new java.awt.Color(red, green, blue);
    }
}

