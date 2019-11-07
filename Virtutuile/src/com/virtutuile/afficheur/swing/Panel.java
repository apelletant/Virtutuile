package com.virtutuile.afficheur.swing;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    public Panel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        setOpaque(false);
    }

    public Panel(LayoutManager layout) {
        super(layout);
        setOpaque(false);
    }

    public Panel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        setOpaque(false);
    }

    public Panel() {
        super();
        setOpaque(false);
    }
}
