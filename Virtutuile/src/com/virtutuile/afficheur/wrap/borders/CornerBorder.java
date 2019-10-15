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
//public class CornerBorder extends AbstractBorder {
//    private Color borderColour;
//    private int gap;
//
//    public CornerBorder(Color colour, int g) {
//        borderColour = colour;
//        gap = g;
//    }
//
//    @Override
//    public void paintBorder(Component c, Graphics g, int x, int y
//            , int width
//            , int height) {
//        super.paintBorder(c, g, x, y, width, height);
//        Graphics2D g2d = null;
//        if (g instanceof Graphics2D) {
//            g2d = (Graphics2D) g;
//            g2d.setColor(borderColour);
//            //Left Border
//            g2d.draw(new Line2D.Double((double) x + 10, (double) y + 10
//                    , (double) x + 10, (double) y + 20));
//            g2d.draw(new Line2D.Double((double) x + 10, (double) y + 10
//                    , (double) x + 20, (double) y + 10));
//            // Right Border
//            g2d.draw(new Line2D.Double((double) width - 10, (double) y + 10
//                    , (double) width - 10, (double) y + 20));
//            g2d.draw(new Line2D.Double((double) width - 10, (double) y + 10
//                    , (double) width - 20, (double) y + 10));
//            // Lower Left Border
//            g2d.draw(new Rectangle2D.Double(20, 20, 40, 40));
//            g2d.draw(new Line2D.Double((double) x + 10, (double) height - 10
//                    , (double) x + 20, (double) height - 10));
//            g2d.draw(new Line2D.Double((double) x + 10, (double) height - 10
//                    , (double) x + 10, (double) height - 20));
//            // Lower Right Border
//            g2d.draw(new Line2D.Double((double) width - 10, (double) height - 10
//                    , (double) width - 20, (double) height - 10));
//            g2d.draw(new Line2D.Double((double) width - 10, (double) height - 10
//                    , (double) width - 10, (double) height - 20));
//        }
//    }
//
//    @Override
//    public Insets getBorderInsets(Component c) {
//        return (getBorderInsets(c, new Insets(1, 1, 1, 1)));
//    }
//
//    @Override
//    public Insets getBorderInsets(Component c, Insets insets) {
//        insets.left = insets.top = insets.right = insets.bottom = gap;
//        return insets;
//    }
//
//    @Override
//    public boolean isBorderOpaque() {
//        return true;
//    }
//
//}
