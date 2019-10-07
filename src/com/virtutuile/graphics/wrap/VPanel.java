package com.virtutuile.graphics.wrap;

import javax.swing.*;
import java.awt.*;

public class VPanel extends JPanel {
    public VPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        setOpaque(false);
    }

    public VPanel(LayoutManager layout) {
        super(layout);
        setOpaque(false);
    }

    public VPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        setOpaque(false);
    }

    public VPanel() {
        super();
        setOpaque(false);
    }
}
