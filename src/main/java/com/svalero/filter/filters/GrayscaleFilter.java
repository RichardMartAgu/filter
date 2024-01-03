package com.svalero.filter.filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;

public class GrayscaleFilter {

    public static java.awt.Color apply(java.awt.Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        int gray = (red + green + blue) / 3;

        return new Color(gray, gray, gray);
    }

}

