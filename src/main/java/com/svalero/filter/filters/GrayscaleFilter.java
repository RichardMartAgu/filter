package com.svalero.filter.filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GrayscaleFilter {

    public static Image applyGrayscaleFilter(Image originalImage) {
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();

        WritableImage grayImage = new WritableImage(width, height);
        PixelReader pixelReader = originalImage.getPixelReader();
        PixelWriter pixelWriter = grayImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pixelReader.getColor(x, y);
                double luminosity = 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue();
                pixelWriter.setColor(x, y, Color.color(luminosity, luminosity, luminosity));
            }
        }

        return grayImage;
    }
}
