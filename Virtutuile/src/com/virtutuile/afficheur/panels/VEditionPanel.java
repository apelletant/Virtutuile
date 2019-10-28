package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.panels.subpanel.DrawShapePanel;
import com.virtutuile.afficheur.panels.subpanel.SettingsPanel;
import com.virtutuile.afficheur.panels.subpanel.ShapeManagementPanel;
import com.virtutuile.afficheur.panels.subpanel.SubPanel;
import com.virtutuile.afficheur.swing.panels.VPanelEvents;
import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.tools.UnorderedMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VEditionPanel extends VPanelEvents {

    public static final int NONE = 0;
    public static final int SETTINGS = 0x0001;
    public static final int DRAW_SHAPE = 0x0002;
    public static final int PATTERN = 0x0004;
    public static final int SHAPE_MANAG = 0x0008;
    public static final int ALERTS = 0x0010;
    public static final int OTHER1 = 0x0020;
    public static final int OTHER2 = 0x0040;
    public static final int OTHER3 = 0x0080;


    private SettingsPanel _settings = new SettingsPanel("Settings");
    private DrawShapePanel _shape = new DrawShapePanel("Shape");
    private int _panelsActive = NONE;
    private UnorderedMap<Integer, SubPanel> _panels = new UnorderedMap<>();

    public VEditionPanel() {
        super();
        this.setName("VEditionPanel");
        this.setBackground(UIConstants.EDITIONPANEL_BACKGROUND);
        this.setForeground(UIConstants.EDITIONPANEL_FONT_COLOR);
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        _panels.put(SETTINGS, _settings);
        _panels.put(DRAW_SHAPE, _shape);
    }

    public void addPanelsActive(int activePanel) {
        persistPanels(this._panelsActive | activePanel);
    }

    private void persistPanels(int newPanelList) {
        _panels.forEach((key, value) -> {
            if ((key & newPanelList) != 0) {
                if ((key & this._panelsActive) == 0) {
                    this.add(value);
                }
            } else if ((key & this._panelsActive) != 0) {
                this.remove(value);
            }
        });
        this._panelsActive = newPanelList;
    }

    public void removePanelsActive(int deactivatePanels) {
        persistPanels(this._panelsActive & (this._panelsActive ^ deactivatePanels));
    }

    public boolean isPanelActive(int panel) {
        return (this._panelsActive & panel) != 0;
    }

    public int getPanelsActive() {
        return this._panelsActive;
    }

    public void setPanelsActive(int activatedPanels) {
        persistPanels(activatedPanels);
    }
}
