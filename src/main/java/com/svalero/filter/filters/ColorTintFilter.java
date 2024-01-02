package com.svalero.filter.filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ColorTintFilter {

    public static Image applyColorTintFilter(Image originalImage, Color tint) {
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();

        WritableImage tintedImage = new WritableImage(width, height);
        PixelReader pixelReader = originalImage.getPixelReader();
        PixelWriter pixelWriter = tintedImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pixelReader.getColor(x, y);
                double tintedRed = color.getRed() * tint.getRed();
                double tintedGreen = color.getGreen() * tint.getGreen();
                double tintedBlue = color.getBlue() * tint.getBlue();

                pixelWriter.setColor(x, y, Color.color(tintedRed, tintedGreen, tintedBlue));
            }
        }

        return tintedImage;
    }
}