package com.svalero.filter.filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class SepiaFilter {

    public static Image applySepiaFilter(Image originalImage) {
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();

        WritableImage sepiaImage = new WritableImage(width, height);
        PixelReader pixelReader = originalImage.getPixelReader();
        PixelWriter pixelWriter = sepiaImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pixelReader.getColor(x, y);
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();

                double sepiaRed = (red * 0.393) + (green * 0.769) + (blue * 0.189);
                double sepiaGreen = (red * 0.349) + (green * 0.686) + (blue * 0.168);
                double sepiaBlue = (red * 0.272) + (green * 0.534) + (blue * 0.131);

                pixelWriter.setColor(x, y, Color.color(clamp(sepiaRed), clamp(sepiaGreen), clamp(sepiaBlue)));
            }
        }

        return sepiaImage;
    }

    private static double clamp(double value) {
        return Math.min(1.0, Math.max(0.0, value));
    }
}
