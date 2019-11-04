package com.virtutuile.afficheur.panels.subpanel;

import com.virtutuile.afficheur.swing.panels.VPanelEvents;
import com.virtutuile.domaine.VEditorEngine;
import com.virtutuile.systeme.constants.UIConstants;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Vector;

public abstract class SubPanel extends VPanelEvents {

    protected VEditorEngine _editorEngine;
    protected Vector<JPanel> _lines = new Vector<>();

    public SubPanel(String name, VEditorEngine editorEngine) {
        this.setName(name);
        this._editorEngine = editorEngine;

        TitledBorder border = new TitledBorder(name);
        border.setTitleColor(UIConstants.EDITIONPANEL_FONT_COLOR);
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        this.setBorder(border);

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

    protected abstract void setEvents();
}
