package com.virtutuile.graphics.components.panels.editionpanel.subpanel.subpanels;

import com.virtutuile.constants.UIConstants;
import com.virtutuile.graphics.components.inputs.VDistanceInput;
import com.virtutuile.graphics.components.panels.editionpanel.subpanel.SubPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.Vector;

public class SettingsPanel extends SubPanel {
    private VDistanceInput _areaInput = new VDistanceInput("Area");
    private VDistanceInput _perimeterInput = new VDistanceInput("Perimeter");
    private VDistanceInput _width = new VDistanceInput("Width");
    private VDistanceInput _height = new VDistanceInput("Height");
    private Vector<JPanel> _lines = new Vector<>();

    public SettingsPanel(String name) {
        super(name);
        TitledBorder border = new TitledBorder(name);
        border.setTitleColor(UIConstants.EDITIONPANEL_FONT_COLOR);
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        this.setBorder(border);

        JPanel line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(_areaInput);
        line.add(_perimeterInput);
        _lines.add(line);

        line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(_width);
        line.add(_height);
        _lines.add(line);

        persistLayout();
    }

    private void persistLayout() {
        for (JPanel layout : _lines) {
            if (!isAncestorOf(layout)) {
                add(layout);
            }
        }
    }
}
