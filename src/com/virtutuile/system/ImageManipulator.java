package com.virtutuile.system;

import javax.swing.*;
import java.awt.*;

public class ImageManipulator {
    /**
     * Image resizer for icon fit purpose
     *
     * @param img  The icon to resize
     * @param size The desired size
     * @return The resized image
     */
    public static ImageIcon Resize(ImageIcon img, Dimension size) {
        return ImageManipulator.Resize(img, size, true);
    }

    public static ImageIcon Resize(ImageIcon img, Dimension size, boolean preserveRatio) {
        Image ref = img.getImage();
        int oWidth = ref.getWidth(null);
        int oHeight = ref.getHeight(null);

        float ratioX = size.width / (float) oWidth;
        float ratioY = size.height / (float) oHeight;

        if (preserveRatio) {
            if (ratioX > ratioY) {
                ratioX = ratioY;
            } else {
                ratioY = ratioX;
            }
        }
        oWidth *= ratioX;
        oHeight *= ratioY;

        Image outRef = ref.getScaledInstance(oWidth, oHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(outRef);
    }

    public static ImageIcon Resize(ImageIcon img, int width, int height) {
        return ImageManipulator.Resize(img, new Dimension(width, height), true);
    }

}
