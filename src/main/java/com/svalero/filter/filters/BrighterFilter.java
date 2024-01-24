package com.svalero.filter.filters;

import java.awt.*;

public class BrighterFilter {

    public static Color apply(Color color) {
        int brightnessFactor = 25;
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        red = Math.min(255, red + brightnessFactor);
        green = Math.min(255, green + brightnessFactor);
        blue = Math.min(255, blue + brightnessFactor);

        return new Color(red, green, blue);
    }

}
