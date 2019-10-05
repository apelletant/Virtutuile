package com.virtutuile.graphics.components.panels.editionpanel;

import com.virtutuile.constants.UIConstants;
import com.virtutuile.graphics.components.panels.VPanel;

import javax.swing.*;

public class VEditionPanel extends VPanel {

    private boolean _isActive = false;

    public VEditionPanel() {
        super();
        this.setName("VEditionPanel");
        this.setBackground(UIConstants.EDITIONPANEL_BACKGROUND);
        this.setForeground(UIConstants.EDITIONPANEL_FONT_COLOR);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}

