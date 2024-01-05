package com.svalero.filter.utils;

import java.io.File;

public class IsImage {
    public static boolean isImage(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||fileName.endsWith(".bmp");
    }
}
