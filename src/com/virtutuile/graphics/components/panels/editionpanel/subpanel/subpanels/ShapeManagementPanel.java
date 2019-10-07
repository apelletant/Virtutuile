package com.virtutuile.graphics.components.panels.editionpanel.subpanel.subpanels;

import com.virtutuile.constants.UIConstants;
import com.virtutuile.graphics.components.inputs.VInput;
import com.virtutuile.graphics.components.panels.editionpanel.subpanel.SubPanel;

import javax.swing.border.TitledBorder;

public class ShapeManagementPanel extends SubPanel {
    private VInput _input = new VInput("Area");

    public ShapeManagementPanel(String name) {
        super(name);
        TitledBorder border = new TitledBorder(name);
        border.setTitleColor(UIConstants.EDITIONPANEL_FONT_COLOR);
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        this.setBorder(border);

        this.add(_input);

    }
}
