package com.virtutuile.graphics.components.panels.editionpanel;

import com.virtutuile.constants.UIConstants;
import com.virtutuile.graphics.components.panels.VPanel;
import com.virtutuile.graphics.components.panels.editionpanel.subpanel.SubPanel;
import com.virtutuile.graphics.components.panels.editionpanel.subpanel.subpanels.SettingsPanel;
import com.virtutuile.graphics.components.panels.editionpanel.subpanel.subpanels.ShapeManagementPanel;
import com.virtutuile.graphics.wrap.MapList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VEditionPanel extends VPanel {

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
    private ShapeManagementPanel _shape = new ShapeManagementPanel("Shape");
    private int _panelsActive = NONE;
    private MapList<Integer, SubPanel> _panels = new MapList<>();

    public VEditionPanel() {
        super();
        this.setName("VEditionPanel");
        this.setBackground(UIConstants.EDITIONPANEL_BACKGROUND);
        this.setForeground(UIConstants.EDITIONPANEL_FONT_COLOR);
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        _panels.put(SETTINGS, _settings);
        _panels.put(SHAPE_MANAG, _shape);
    }

    public void setPanelsActive(int activatedPanels) {
        persistPanels(activatedPanels);
    }

    public void addPanelsActive(int activePanel) {
        persistPanels(this._panelsActive | activePanel);
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

    private void persistPanels(int newPannelList) {
        _panels.forEach((key, value) -> {
            if ((key & newPannelList) != 0) {
                if ((key & this._panelsActive) == 0) {
                    System.out.print("Add ");
                    System.out.println(key);
                    this.add(value);
                }
            } else if ((key & this._panelsActive) != 0) {
                System.out.print("Remove ");
                System.out.println(key);
                this.remove(value);
            }
        });
        this._panelsActive = newPannelList;
    };
}
