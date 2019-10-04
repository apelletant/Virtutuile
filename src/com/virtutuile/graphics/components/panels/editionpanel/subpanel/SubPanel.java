package com.virtutuile.graphics.components.panels.editionpanel.subpanel;

import com.virtutuile.constants.UIConstants;
import com.virtutuile.graphics.components.panels.VPanel;

import javax.swing.*;

public class SubPanel extends VPanel {
    public SubPanel(String name) {
        this.setName(name);
        this.fixSize(UIConstants.SUBPANEL_SIZE);
        this.setBackground(UIConstants.SUBPANEL_BACKGROUND);
    }
}
