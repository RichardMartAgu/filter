package com.svalero.filter.task;

import com.svalero.filter.filters.ColorTintFilter;
import com.svalero.filter.filters.GrayscaleFilter;
import com.svalero.filter.filters.InvertColorFilter;
import com.svalero.filter.filters.SepiaFilter;
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
        float totalProcessed = 0f;
        for (int y = 0; y < image.getHeight(); y++) {
            Thread.sleep(20);
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                for (String selectedFilter : this.selectedFilters) {
                    if (selectedFilter.equals("GrayscaleFilter"))
                        color = GrayscaleFilter.apply(color);
                    if (selectedFilter.equals("GrayscaleFilter"))
                        color = SepiaFilter.apply(color);
                    if (selectedFilter.equals("GrayscaleFilter"))
                        color = InvertColorFilter.apply(color);
                    if (selectedFilter.equals("GrayscaleFilter"))
                        color = ColorTintFilter.apply(color);

                }
                if (color != null)
                    image.setRGB(x, y, color.getRGB());
            }
            totalProcessedPixel++;

            updateProgress(totalProcessedPixel, imageSize);
            totalProcessed = totalProcessedPixel / (float) imageSize;
            String totalProcessedFormatted = String.format("%1f", 100 * totalProcessed);
            updateMessage(totalProcessedFormatted + "%");
        }

        String outputName = this.sourceImage.getName().substring(0, this.sourceImage.getName().length());
        File output = new File(outputName);
        ImageIO.write(image, "png", output);
        updateProgress(1, 1);
        updateMessage("100%");
        return image;
    }
}