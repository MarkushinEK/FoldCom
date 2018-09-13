package com.service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileHandlerServiceImpl implements FileHandlerService {

    private volatile static FileHandlerService fileHandlerService;
    private static final String IMAGE_ROOT = "src/main/webapp/WEB-INF/image/";

    private FileHandlerServiceImpl() {}

    public static FileHandlerServiceImpl instance() {
        if (fileHandlerService == null)
            synchronized (FileHandlerServiceImpl.class) {
                if (fileHandlerService == null)
                    fileHandlerService = new FileHandlerServiceImpl();
            }
        return (FileHandlerServiceImpl) fileHandlerService;
    }

    @Override
    public BufferedImage getBufferedImage(String imageName, String extension) {
        BufferedImage bufImg = null;
        try {
            StringBuilder path = new StringBuilder(IMAGE_ROOT)
                    .append(imageName)
                    .append('.')
                    .append(extension);

            Image image = ImageIO.read(new File(path.toString()));
            bufImg = ImageIO.read(new File(path.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufImg;
    }

}
