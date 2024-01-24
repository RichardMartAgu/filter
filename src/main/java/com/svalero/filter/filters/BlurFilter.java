package com.svalero.filter.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class BlurFilter {

    public static Color apply(BufferedImage image, int x, int y) {

        float[] blurMatrix = {
                1.0f / 9, 1.0f / 9, 1.0f / 9,
                1.0f / 9, 1.0f / 9, 1.0f / 9,
                1.0f / 9, 1.0f / 9, 1.0f / 9
        };

        float[] sum = {0, 0, 0};

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int pixelX = x + i;
                int pixelY = y + j;

                if (pixelX >= 0 && pixelX < image.getWidth() && pixelY >= 0 && pixelY < image.getHeight()) {
                    Color pixelColor = new Color(image.getRGB(pixelX, pixelY));
                    float matrixValue = blurMatrix[(i + 1) * 3 + (j + 1)];

                    sum[0] += pixelColor.getRed() * matrixValue;
                    sum[1] += pixelColor.getGreen() * matrixValue;
                    sum[2] += pixelColor.getBlue() * matrixValue;
                }
            }
        }

        int red = Math.min(255, Math.max(0, (int) sum[0]));
        int green = Math.min(255, Math.max(0, (int) sum[1]));
        int blue = Math.min(255, Math.max(0, (int) sum[2]));


        return new Color(red, green, blue);
    }
}
