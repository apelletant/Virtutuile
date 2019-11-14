package com.virtutuile.afficheur.swing;

import com.virtutuile.afficheur.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Label extends JLabel {
    public Label(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        setup();
    }

    private void setup() {
        setOpaque(false);
        setForeground(Constants.EDITIONPANEL_FONT_COLOR);
        setBorder(new EmptyBorder(3, 3, 3, 3));
    }

    public Label(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        setup();
    }

    public Label(String text) {
        super(text);
        setup();
    }

    public Label(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        setup();
    }

    public Label(Icon image) {
        super(image);
        setup();
    }

    public Label() {
        setup();
    }

    public int getFontSize() {
        return getFont().getSize();
    }

    public void setFontSize(int fontSize) {
        Font font = getFont();
        setFont(new Font(font.getName(), Font.PLAIN, fontSize));
    }
}
