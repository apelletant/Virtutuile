package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.inputs.ColorPicker;
import com.virtutuile.afficheur.inputs.TextInput;
import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.afficheur.swing.Panel;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import java.awt.*;
import java.util.Vector;

public class TileCreation extends SubPanel {

    private Vector<JPanel> creationFrameRows = new Vector<>();
    private TextInput tileName = null;
    private TextInput tileNumberPerPack = null;
    private ColorPicker tileColorPicker = null;
    private UnitInput tileWidth = null;
    private UnitInput tileHeight = null;
    private Button validateButton = null;

    public TileCreation(String name, MainWindow mainWindow) {
        super(name, mainWindow);
        setButtonsOnPanel();
        setOpaque(true);
        setBackground(Constants.EDITIONPANEL_BACKGROUND);
        setEvents();
        persistLayout();
    }

    @Override
    protected void setEvents() {

    }

    @Override
    protected void setButtonsOnPanel() {
        JPanel line = new Panel();
        line.setBackground(Constants.EDITIONPANEL_BACKGROUND);
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        tileName = new TextInput("Name");
        tileNumberPerPack = new TextInput("Package Size", true);
        line.add(tileName);
        line.add(tileNumberPerPack);
        rows.add(line);

        line = new Panel();
        line.setBackground(Constants.EDITIONPANEL_BACKGROUND);
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        tileColorPicker = new ColorPicker();
        tileColorPicker.setMaximumSize(new Dimension(500,200));
        tileColorPicker.setMinimumSize(new Dimension(500,200));
        tileColorPicker.setPreferredSize(new Dimension(500,200));
        AbstractColorChooserPanel[] panels = tileColorPicker.getChooserPanels();
        tileColorPicker.setChooserPanels(new AbstractColorChooserPanel[] { panels[0] });
        line.add(tileColorPicker);
        rows.add(line);

        line = new Panel();
        line.setBackground(Constants.EDITIONPANEL_BACKGROUND);
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        tileWidth = new UnitInput("Width");
        tileHeight = new UnitInput("Height");
        line.add(tileWidth);
        line.add(tileHeight);
        rows.add(line);

        line = new Panel();
        line.setBackground(Constants.EDITIONPANEL_BACKGROUND);
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        validateButton = new Button("Create");
        line.add(validateButton);
        rows.add(line);
    }
}
