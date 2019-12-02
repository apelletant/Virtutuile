package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.swing.PanelEvents;
import com.virtutuile.domaine.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Vector;

public abstract class SubPanel extends PanelEvents {

    protected MainWindow mainWindow;
    protected Vector<JPanel> rows = new Vector<>();

    SubPanel(String name, MainWindow mainWindow) {
        setName(name);
        this.mainWindow = mainWindow;
        TitledBorder border = new TitledBorder(name);
        border.setTitleColor(Constants.EDITIONPANEL_FONT_COLOR);
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        setBorder(border);

        Dimension dimension = getPreferredSize();
        dimension.width = Constants.SUBPANEL_SIZE.width;
        setPreferredSize(dimension);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    }

    protected void persistLayout() {
        for (JPanel layout : rows) {
            if (!isAncestorOf(layout)) {
                add(layout);
            }
        }
    }
    protected void removeLayout() {
        for (JPanel layout : rows) {
            if (!isAncestorOf(layout)) {
                remove(layout);
            }
        }
    }


    protected abstract void setEvents();

    protected abstract void setButtonsOnPanel();
}
