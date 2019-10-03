package com.virtutuile.graphics.components.panels.editionpanel;

import com.virtutuile.constants.UIConstants;
import com.virtutuile.graphics.components.panels.editionpanel.subpanel.subpanels.SettingsPanel;

import javax.swing.*;
import java.awt.*;

public class VEditionPanel extends JScrollPane {
    private boolean _isActive = false;
    private ScrollPaneLayout _scrollPaneLayout = new ScrollPaneLayout();

    private SettingsPanel _settingsPanel;
    private SettingsPanel _settingsPanel1;
    private SettingsPanel _settingsPanel2;
    private SettingsPanel _settingsPanel3;
    private SettingsPanel _settingsPanel4;
    private SettingsPanel _settingsPanel5;


    public VEditionPanel() {
        super();
        this.setName("VEditionPanel");
        this.setBackground(UIConstants.EDITIONPANEL_BACKGROUND);
        this.setForeground(UIConstants.EDITIONPANEL_FONT_COLOR);
        /*this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));*/
        this.setLayout(this._scrollPaneLayout);
        this.setPreferredSize(new Dimension(550, 1080));


        this.setAutoscrolls(true);

        this._settingsPanel = new SettingsPanel("Settings Panel");
        this._settingsPanel1 = new SettingsPanel("Settings Panel");
        this.revalidate();
        /*this._settingsPanel2 = new SettingsPanel("Settings Panel");
        this._settingsPanel3 = new SettingsPanel("Settings Panel");
        this._settingsPanel4 = new SettingsPanel("Settings Panel");
        this._settingsPanel5 = new SettingsPanel("Settings Panel");*/

        /*this._settingsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this._settingsPanel1.setAlignmentX(Component.CENTER_ALIGNMENT);*/
        /*this._settingsPanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        this._settingsPanel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        this._settingsPanel4.setAlignmentX(Component.CENTER_ALIGNMENT);
        this._settingsPanel5.setAlignmentX(Component.CENTER_ALIGNMENT);*/

        this.getViewport().add(this._settingsPanel);
        this.getViewport().add(this._settingsPanel1);
        /*this.getViewport().add(this._settingsPanel2);
        this.getViewport().add(this._settingsPanel5);
        this.getViewport().add(this._settingsPanel3);
        this.getViewport().add(this._settingsPanel4);*/
    }
}

