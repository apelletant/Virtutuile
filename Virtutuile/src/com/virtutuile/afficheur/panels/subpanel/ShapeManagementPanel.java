package com.virtutuile.afficheur.panels.subpanel;

import com.virtutuile.afficheur.inputs.VInputText;
import com.virtutuile.systeme.constants.UIConstants;

import javax.swing.border.TitledBorder;

public class ShapeManagementPanel extends SubPanel {
    private VInputText _input = new VInputText("Area");

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
