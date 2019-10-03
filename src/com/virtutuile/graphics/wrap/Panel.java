package com.virtutuile.graphics.wrap;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    /**
     * Force the button to be this size
     * @param size The desired size
     */
    public void fixSize(Dimension size) {
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.setMinimumSize(size);
    }
}
