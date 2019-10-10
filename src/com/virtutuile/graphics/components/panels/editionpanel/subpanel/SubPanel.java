package com.virtutuile.graphics.components.panels.editionpanel.subpanel;

import com.virtutuile.constants.UIConstants;
import com.virtutuile.graphics.wrap.panels.VPanelEvents;

import javax.swing.*;
import java.awt.*;

public class SubPanel extends VPanelEvents {

    public SubPanel(String name) {
        this.setName(name);

        Dimension dim = getPreferredSize();
        dim.width = UIConstants.SUBPANEL_SIZE.width;
        setPreferredSize(dim);
        this.setBackground(UIConstants.SUBPANEL_BACKGROUND);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
