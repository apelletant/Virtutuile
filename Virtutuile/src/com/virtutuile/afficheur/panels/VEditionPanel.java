package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.panels.subpanel.*;
import com.virtutuile.afficheur.swing.panels.VPanelEvents;
import com.virtutuile.domaine.VEditorEngine;
import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.singletons.VApplicationStatus;
import com.virtutuile.systeme.tools.UnorderedMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Date;
import java.util.Vector;

public class VEditionPanel extends VPanelEvents {

    private VEditorEngine _editorEngine;
    private SettingsPanel _settings;
    private DrawShapePanel _shape;
    private PatternPanel _pattern;
    private Vector<VApplicationStatus.VPanelType> _panelsActive = new Vector<>();
    private UnorderedMap<VApplicationStatus.VPanelType, SubPanel> _panels = new UnorderedMap<>();

    public VEditionPanel(VEditorEngine editorEngine) {
        super();
        this._editorEngine = editorEngine;
        this._settings = new SettingsPanel("Settings", editorEngine);
        this._shape = new DrawShapePanel("Shape", editorEngine);
        this._pattern = new PatternPanel("Pattern", editorEngine);
        this.setOpaque(true);
        this.setName("VEditionPanel");
        this.setBackground(UIConstants.EDITIONPANEL_BACKGROUND);
        this.setForeground(UIConstants.EDITIONPANEL_FONT_COLOR);
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        _panels.put(VApplicationStatus.VPanelType.Settings, _settings);
        _panels.put(VApplicationStatus.VPanelType.DrawShape, _shape);
        _panels.put(VApplicationStatus.VPanelType.PatternManagement, _pattern);
    }

    public void persistPanels() {
        Vector<VApplicationStatus.VPanelType> activePanels =  VApplicationStatus.getInstance().getActivePanels();
        _panels.forEach((key, value) -> {
            if (activePanels.contains(key)) {
                if (!this._panelsActive.contains(key)) {
                    this.add(value);
                }
            } else {
                this.remove(value);
            }
        });
        this._panelsActive = new Vector<>(activePanels);
    }
}
