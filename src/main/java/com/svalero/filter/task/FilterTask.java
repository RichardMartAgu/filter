package com.svalero.filter.task;

import com.svalero.filter.filters.*;
import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.MalformedParametersException;
import java.util.List;

public class FilterTask extends Task<BufferedImage> {
    private File sourceImage;
    private List<String> selectedFilters;

    public FilterTask(File sourceImage, List<String> selectedFilters) throws MalformedParametersException {
        this.sourceImage = sourceImage;
        this.selectedFilters = selectedFilters;
    }

    @Override
    protected BufferedImage call() throws Exception {

        int totalProcessedPixel = 0;
        updateMessage("Iniciando Filtro");
        BufferedImage image = ImageIO.read(this.sourceImage);
        int imageSize = image.getHeight() * image.getWidth();
        float totalProcessed;

        for (int y = 0; y < image.getHeight(); y++) {
            Thread.sleep(20);
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                for (String selectedFilter : this.selectedFilters) {
                    if (selectedFilter.equals("GrayscaleFilter"))
                        color = GrayscaleFilter.apply(color);
                    if (selectedFilter.equals("BrighterFilter"))
                        color = BrighterFilter.apply(color);
                    if (selectedFilter.equals("SepiaFilter"))
                        color = SepiaFilter.apply(color);
                    if (selectedFilter.equals("InvertColorFilter"))
                        color = InvertColorFilter.apply(color);
                    if (selectedFilter.equals("ColorTintFilter"))
                        color = ColorTintFilter.apply(color);

                }
                if (color != null)
                    image.setRGB(x, y, color.getRGB());

                totalProcessedPixel++;

                updateProgress(totalProcessedPixel, imageSize);
                totalProcessed = totalProcessedPixel / (float) imageSize;
                String totalProcessedFormatted = String.format("%.2f", 100 * totalProcessed);
                updateMessage(totalProcessedFormatted + "%");
            }
        }
        updateProgress(totalProcessedPixel, imageSize);
        updateMessage("100%");
        return image;
    }
}