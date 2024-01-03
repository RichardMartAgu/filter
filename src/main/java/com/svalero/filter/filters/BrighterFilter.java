package com.svalero.filter.filters;

import java.awt.*;

public class BrighterFilter {

    public static Color apply(Color color) {
        int brightnessFactor = 50;
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        red= Math.min(255, red + brightnessFactor);
        green= Math.min(255, red + brightnessFactor);
        blue= Math.min(255, red + brightnessFactor);

        int gray = (red + green + blue) / 3;

        return new Color(gray, gray, gray);
    }

}
