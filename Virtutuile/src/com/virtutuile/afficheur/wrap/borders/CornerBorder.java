package com.virtutuile.afficheur.wrap.borders;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class CornerBorder extends AbstractBorder {
    int top;
    int left;
    int bottom;
    int right;
    int gap;
    Color color = null;

    public CornerBorder() {
        this.top = 1;
        this.left = 1;
        this.bottom = 1;
        this.right = 1;
        this.color = Color.RED;
        this.gap = 10;
    }

    public void paintBorder(Component c,
                            Graphics g,
                            int x, int y,
                            int width, int height) {


        Insets insets = getBorderInsets(c);
        if (color != null)
            g.setColor(color);


        // Top Left
        g.fill3DRect(0,
                0,
                gap,
                insets.top,
                true);
        g.fill3DRect(0,
                0,
                insets.left,
                gap,
                true);

        // Top Right
        g.fill3DRect(width - gap,
                0,
                gap,
                insets.top,
                true);
        g.fill3DRect(width - gap,
                0,
                width + insets.right,
                gap,
                true);

        // Bottom Left
        g.fill3DRect(0,
                height,
                gap,
                insets.bottom,
                true);
        g.fill3DRect(0,
                height - gap,
                insets.left,
                gap,
                true);

        // Bottom Right
        g.fill3DRect(width - gap,
                height,
                width + insets.right,
                gap,
                true);

        // Bottom left
//        g.fill3DRect(0, insets.top, insets.left,
//                height - insets.top, true);
//        g.fill3DRect(insets.left, height - insets.bottom,
//                width - insets.left, insets.bottom, true);
//        g.fill3DRect(width - insets.right, 0, insets.right,
//                height - insets.bottom, true);
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(top, left, bottom, right);
    }

    public boolean isBorderOpaque() {
        return true;
    }
}

