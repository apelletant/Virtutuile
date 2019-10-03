package com.virtutuile.graphics.components.panels.editionpanel.subpanel.subpanels;

import com.virtutuile.graphics.components.panels.editionpanel.subpanel.SubPanel;

import javax.swing.border.TitledBorder;

public class SettingsPanel extends SubPanel {
    public SettingsPanel(String name) {
        super(name);
        TitledBorder border = new TitledBorder("This is my title");
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        this.setBorder(border);
    }
}
