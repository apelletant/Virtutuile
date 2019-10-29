package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.panels.subpanel.DrawShapePanel;
import com.virtutuile.afficheur.panels.subpanel.SettingsPanel;
import com.virtutuile.afficheur.panels.subpanel.SubPanel;
import com.virtutuile.afficheur.swing.panels.VPanelEvents;
import com.virtutuile.domaine.systeme.constants.UIConstants;
import com.virtutuile.domaine.systeme.singletons.VApplicationStatus;
import com.virtutuile.domaine.systeme.tools.UnorderedMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.Vector;

public class VEditionPanel extends VPanelEvents {

    private SettingsPanel _settings = new SettingsPanel("Settings");
    private DrawShapePanel _shape = new DrawShapePanel("Shape");
    private Vector<VApplicationStatus.VPanelType> _panelsActive = new Vector<>();
    private UnorderedMap<VApplicationStatus.VPanelType, SubPanel> _panels = new UnorderedMap<>();

    public VEditionPanel() {
        super();
        this.setName("VEditionPanel");
        this.setBackground(UIConstants.EDITIONPANEL_BACKGROUND);
        this.setForeground(UIConstants.EDITIONPANEL_FONT_COLOR);
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        _panels.put(VApplicationStatus.VPanelType.Settings, _settings);
        _panels.put(VApplicationStatus.VPanelType.DrawShape, _shape);
    }

    public void persistPanels() {
        Vector<VApplicationStatus.VPanelType> activePanels =  VApplicationStatus.getInstance().getActivePanels();
        _panels.forEach((key, value) -> {
            if (activePanels.contains(key)) {
                if (!this._panelsActive.contains(key)) {
                    System.out.println("add in persist: " + key);
                    this.add(value);
                }
            } else {
                System.out.println("remove in persist: " + key);
                this.remove(value);
            }
        });
        this._panelsActive = new Vector<>(activePanels);
    }
}
