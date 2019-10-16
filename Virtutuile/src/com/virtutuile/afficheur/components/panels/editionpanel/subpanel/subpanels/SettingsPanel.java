package com.virtutuile.afficheur.components.panels.editionpanel.subpanel.subpanels;

import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.afficheur.components.inputs.VMetricInput;
import com.virtutuile.afficheur.components.panels.editionpanel.subpanel.SubPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.Vector;

public class SettingsPanel extends SubPanel {
    private VMetricInput _areaInput = new VMetricInput("Area");
    private VMetricInput _perimeterInput = new VMetricInput("Perimeter");
    private VMetricInput _width = new VMetricInput("Width");
    private VMetricInput _height = new VMetricInput("Height");
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
