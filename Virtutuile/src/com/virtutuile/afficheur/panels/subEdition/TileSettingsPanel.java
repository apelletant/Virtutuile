package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.domaine.Controller;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class TileSettingsPanel extends SubPanel {

    private UnitInput width;
    private UnitInput height;
    private JColorChooser colorPicker;

    public TileSettingsPanel(String name, Controller controller) {
        super(name, controller);
        setButtonsOnPanel();
        setEvents();
    }

    @Override
    protected void setEvents() {

    }

    @Override
    protected void setButtonsOnPanel() {
        width = new UnitInput("Width");
        height = new UnitInput("Height");
        colorPicker = new JColorChooser();
        colorPicker.setMaximumSize(new Dimension(500,200));
        colorPicker.setMinimumSize(new Dimension(500,200));
        colorPicker.setPreferredSize(new Dimension(500,200));
        AbstractColorChooserPanel[] panels = colorPicker.getChooserPanels();
        colorPicker.setChooserPanels(new AbstractColorChooserPanel[] { panels[0] });
    }

}
