package com.svalero.filter.filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class InvertColorFilter {

    public static Image applyInvertColorFilter(Image originalImage) {
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();

        WritableImage invertedImage = new WritableImage(width, height);
        PixelReader pixelReader = originalImage.getPixelReader();
        PixelWriter pixelWriter = invertedImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pixelReader.getColor(x, y);
                double invertedRed = 1.0 - color.getRed();
                double invertedGreen = 1.0 - color.getGreen();
                double invertedBlue = 1.0 - color.getBlue();

                pixelWriter.setColor(x, y, Color.color(invertedRed, invertedGreen, invertedBlue));
            }
        }

        return invertedImage;
    }
}


