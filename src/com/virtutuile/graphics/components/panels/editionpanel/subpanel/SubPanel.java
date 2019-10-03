package com.virtutuile.graphics.components.panels.editionpanel.subpanel;

import com.virtutuile.constants.UIConstants;

import javax.swing.*;

public class SubPanel extends JPanel {
    public SubPanel(String name) {
        this.setName(name);
        this.fixSize(UIConstants.SUBPANEL_SIZE);
        this.setBackground(UIConstants.SUBPANEL_BACKGROUND);
    }
}
