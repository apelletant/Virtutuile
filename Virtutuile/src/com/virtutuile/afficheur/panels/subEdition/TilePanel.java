package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.domaine.Controller;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class TilePanel extends SubPanel {

    private JColorChooser colorPicker = null;
    private UnitInput width = null;
    private UnitInput height = null;

    public TilePanel(String name, Controller controller) {
        super(name, controller);
        this.setButtonsOnPanel();
        this.setEvents();
        this.persistLayout();
    }

    @Override
    protected void setEvents() {

    }

    @Override
    protected void setButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setSize(100, 100);

        colorPicker = new JColorChooser();
        colorPicker.setMaximumSize(new Dimension(500,200));
        colorPicker.setMinimumSize(new Dimension(500,200));
        colorPicker.setPreferredSize(new Dimension(500,200));
        AbstractColorChooserPanel[] panels = colorPicker.getChooserPanels();
        colorPicker.setChooserPanels(new AbstractColorChooserPanel[] { panels[0] });
        line.add(colorPicker);
        rows.add(line);

        width = new UnitInput("Width");
        height = new UnitInput("Height");

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(width);
        line.add(height);
        rows.add(line);
    }

    private void setColorPickerOnPanel(JPanel line) {

    }

    private void setThicknessInputOnPanel(JPanel line) {

    }

    enum GroutButtonType {
        ColorPicker,
        Thickness
    }
}
