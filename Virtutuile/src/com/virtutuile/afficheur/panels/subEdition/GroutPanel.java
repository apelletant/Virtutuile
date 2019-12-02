package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.ColorPicker;
import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.InputEventKind;
import com.virtutuile.domaine.Controller;
import com.virtutuile.domaine.entities.Grout;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class GroutPanel extends SubPanel{

    private ColorPicker colorPicker = null;
    private UnitInput thickness = null;

    public GroutPanel(String name, MainWindow mainWindow) {
        super(name, mainWindow);
        setButtonsOnPanel();
        setEvents();
        persistLayout();
    }

    @Override
    protected void setEvents() {
        colorPicker.addInputListener(InputEventKind.OnChange, (color, self) -> {
            mainWindow.getController().setGroutColor(color);
            mainWindow.repaint();
        });

        thickness.addInputListener(InputEventKind.OnChange, (value, self) -> {
            mainWindow.getController().setGroutThickness(value);
            mainWindow.repaint();
        });
    }

    @Override
    protected void setButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setSize(100, 100);

        colorPicker = new ColorPicker();
        colorPicker.setMaximumSize(new Dimension(500,200));
        colorPicker.setMinimumSize(new Dimension(500,200));
        colorPicker.setPreferredSize(new Dimension(500,200));
        AbstractColorChooserPanel[] panels = colorPicker.getChooserPanels();
        colorPicker.setChooserPanels(new AbstractColorChooserPanel[] { panels[0] });
        line.add(colorPicker);
        rows.add(line);

        thickness = new UnitInput("Thickness", true, "double");
        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(thickness);
        rows.add(line);

    }

    public void retrieveGroutThickness() {
        Double thickness =  mainWindow.getController().getSurfaceThickness();

        if (thickness != null) {
            this.thickness.setText(thickness.toString());
        } else {
            this.thickness.setText("0");
        }
    }

    enum GroutButtonType {
        ColorPicker,
        Thickness
    }
}

