package com.virtutuile.domaine.entities.tools;

import java.awt.*;

public class ColorTransformer {
    public static Color Invert(Color c) {
        return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
    }
}
