package com.virtutuile.afficheur.swing.inputs;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class CornerBorder extends AbstractBorder {
    private int _top;
    private int _left;
    private int _bottom;
    private int _right;
    private int _gap;
    private Color _color = null;

    public CornerBorder() {
        this._top = 1;
        this._left = 1;
        this._bottom = 1;
        this._right = 1;
        this._color = Color.RED;
        this._gap = 10;
    }

    public void paintBorder(Component c,
                            Graphics g,
                            int x, int y,
                            int width, int height) {


        Insets insets = getBorderInsets(c);
        if (_color != null)
            g.setColor(_color);


        // Top Left
        g.fill3DRect(0,
                0,
                _gap,
                insets.top,
                true);
        g.fill3DRect(0,
                0,
                insets.left,
                _gap,
                true);

        // Top Right
        g.fill3DRect(width - _gap,
                0,
                _gap,
                insets.top,
                true);
        g.fill3DRect(width - _gap,
                0,
                width + insets.right,
                _gap,
                true);

        // Bottom Left
        g.fill3DRect(0,
                height,
                _gap,
                insets.bottom,
                true);
        g.fill3DRect(0,
                height - _gap,
                insets.left,
                _gap,
                true);

        // Bottom Right
        g.fill3DRect(width - _gap,
                height,
                width + insets.right,
                _gap,
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
        return new Insets(_top, _left, _bottom, _right);
    }

    public boolean isBorderOpaque() {
        return true;
    }
}

