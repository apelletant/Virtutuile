package com.virtutuile.afficheur.panels.subpanel;

import com.virtutuile.afficheur.swing.panels.VPanelEvents;
import com.virtutuile.systeme.constants.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public abstract class SubPanel extends VPanelEvents {

    protected Vector<JPanel> _lines = new Vector<>();

    public SubPanel(String name) {
        this.setName(name);

        Dimension dim = getPreferredSize();
        dim.width = UIConstants.SUBPANEL_SIZE.width;
        setPreferredSize(dim);
        this.setBackground(UIConstants.SUBPANEL_BACKGROUND);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    protected void persistLayout() {
        for (JPanel layout : _lines) {
            if (!isAncestorOf(layout)) {
                add(layout);
            }
        }
    }
}
