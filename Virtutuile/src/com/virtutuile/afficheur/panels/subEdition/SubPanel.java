package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.swing.PanelEvents;

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

    protected void removeLayout() {
        removeAll();
    }

    protected void removeLayout(JPanel line) {
        remove(line);
        rows.remove(line);
    }

    public abstract void refreshGUI();

    protected abstract void setEvents();

    protected abstract void setButtonsOnPanel();

    protected abstract void switchUnitsLabel();
}
