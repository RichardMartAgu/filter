package com.svalero.filter.filters;

public class SepiaFilter {

        public static java.awt.Color apply(java.awt.Color color) {
            int red = (int) (0.393 * color.getRed() + 0.769 * color.getGreen() + 0.189 * color.getBlue());
            int green = (int) (0.349 * color.getRed() + 0.686 * color.getGreen() + 0.168 * color.getBlue());
            int blue = (int) (0.272 * color.getRed() + 0.534 * color.getGreen() + 0.131 * color.getBlue());

            red = Math.min(255, red);
            green = Math.min(255, green);
            blue = Math.min(255, blue);

            return new java.awt.Color(red, green, blue);
        }
    }