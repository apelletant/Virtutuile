package com.virtutuile.graphics.components.panels.editionpanel.subpanel;

import com.virtutuile.constants.UIConstants;
import com.virtutuile.graphics.wrap.Panel;

public class SubPanel extends Panel {
    public SubPanel(String name) {
        this.setName(name);
        this.fixSize(UIConstants.SUBPANEL_SIZE);
        this.setBackground(UIConstants.SUBPANEL_BACKGROUND);
    }
}
