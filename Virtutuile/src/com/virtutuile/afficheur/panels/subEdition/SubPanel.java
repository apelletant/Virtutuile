package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.swing.PanelEvents;
import com.virtutuile.domaine.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Vector;

public abstract class SubPanel extends PanelEvents {

    protected Controller controller;
    protected Vector<JPanel> rows = new Vector<>();

    SubPanel(String name, Controller controller) {
        setName(name);
        this.controller = controller;

        TitledBorder border = new TitledBorder(name);
        border.setTitleColor(Constants.EDITIONPANEL_FONT_COLOR);
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        setBorder(border);

        Dimension dimension = getPreferredSize();
        dimension.width = Constants.SUBPANEL_SIZE.width;
        setPreferredSize(dimension);
        setBackground(Constants.SUBPANEL_BACKGROUND);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    protected void persistLayout() {
        for (JPanel layout : rows) {
            if (!isAncestorOf(layout)) {
                add(layout);
            }
        }
    }

    protected abstract void setEvents();

    protected abstract void setButtonsOnPanel();
}
