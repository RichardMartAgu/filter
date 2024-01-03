package com.svalero.filter.filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ColorTintFilter {

    public static java.awt.Color apply(java.awt.Color color) {

        int tintRed = 255;
        int tintGreen = 100;
        int tintBlue = 100;

        int red = Math.min(255, color.getRed() + tintRed);
        int green = Math.min(255, color.getGreen() + tintGreen);
        int blue = Math.min(255, color.getBlue() + tintBlue);

        return new java.awt.Color(red, green, blue);
    }
}