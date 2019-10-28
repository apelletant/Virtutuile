package com.virtutuile.afficheur.swing.tools;

import com.virtutuile.domaine.systeme.constants.UIConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VLabel extends JLabel {
    public VLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        setup();
    }

    private void setup() {
        setOpaque(false);
        setForeground(UIConstants.EDITIONPANEL_FONT_COLOR);
        setBorder(new EmptyBorder(3, 3, 3, 3));
    }

    public VLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        setup();
    }

    public VLabel(String text) {
        super(text);
        setup();
    }

    public VLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        setup();
    }

    public VLabel(Icon image) {
        super(image);
        setup();
    }

    public VLabel() {
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
