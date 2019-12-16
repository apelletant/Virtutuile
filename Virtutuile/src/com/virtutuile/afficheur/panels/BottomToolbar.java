package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.inputs.TextInput;
import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.afficheur.swing.BorderedPanel;
import com.virtutuile.afficheur.swing.Label;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.InputEventKind;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.domaine.entities.Meta;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import java.awt.*;

public class BottomToolbar extends BorderedPanel {

    private MainWindow mainWindow;
    private UnorderedMap<TargetButton, Button> buttons = new UnorderedMap<>();
    private UnorderedMap<String, UnitInput> hoveredSurfaceDim = new UnorderedMap<>();
    private UnitInput gridSizeInput;
    private UnitInput zoom;

    public BottomToolbar(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;

        sizingPolicy(SizingPolicy.ContentBox);

        Button button = new Button("Metric");
        buttons.put(TargetButton.Unit, button);
        buttons.put(TargetButton.MagneticGrid, new Button("Magnetic grid", AssetLoader.loadImage("/icons/magnetic-grid.png")));

        buttons.forEach((key, value) -> {
            add(value);
        });

        setUpGrid();
        setZoomInput();
        add(gridSizeInput);
        hoveredSurfaceDim.put("width", setUpUnitInput("Width:"));
        hoveredSurfaceDim.put("height", setUpUnitInput("Height:"));
        add(zoom);

        Panel hoveredSurfacePanel = setUpSurfaceDataPanel();

        add(Box.createHorizontalGlue());
        add(Box.createVerticalGlue());
        add(hoveredSurfacePanel);

        setEvent();
        setUpInputEvent();
    }

    private void setUpGrid() {
        gridSizeInput = new UnitInput("Grid size", false, "double");

        Dimension dim = new Dimension(Constants.BUTTON_SIZE.width * 2, Constants.BUTTON_SIZE.height);
        gridSizeInput.setUnitLabel("cm/pix");

        gridSizeInput.setPreferredSize(dim);
        gridSizeInput.setMinimumSize(dim);
        gridSizeInput.setMaximumSize(dim);
    }


    private void setZoomInput() {
        zoom = new UnitInput("Zoom Ratio", false, "double");

        Dimension dim = new Dimension(Constants.BUTTON_SIZE.width * 2, Constants.BUTTON_SIZE.height);
        zoom.setUnitLabel("cm/pix");
        zoom.setEditableFalse();

        zoom.setPreferredSize(dim);
        zoom.setMinimumSize(dim);
        zoom.setMaximumSize(dim);
    }

    private Panel setZoomLvlPanel() {
        Panel pan = new Panel();

        pan.setPreferredSize(new Dimension(Constants.BUTTON_SIZE.width  * 4, Constants.BUTTON_SIZE.height));
        pan.setMinimumSize(new Dimension(Constants.BUTTON_SIZE.width  * 4, Constants.BUTTON_SIZE.height));
        pan.setMaximumSize(new Dimension(Constants.BUTTON_SIZE.width  * 4, Constants.BUTTON_SIZE.height));

        zoom.setText(mainWindow.getController().getZoomFactor().toString());
        zoom.setEditableFalse();

        Panel subPan = new Panel();

        Label lab = new Label("cm/pixel");
        subPan.add(zoom);
        subPan.setOpaque(true);
        subPan.setBackground(Constants.SUBPANEL_BACKGROUND);
        subPan.add(lab);

        pan.add(subPan);
        return pan;
    }

    private Panel setUpSurfaceDataPanel() {
        Panel pan = new Panel();

        pan.setPreferredSize(new Dimension(Constants.BUTTON_SIZE.width  * 5, Constants.BUTTON_SIZE.height));
        pan.setMinimumSize(new Dimension(Constants.BUTTON_SIZE.width  * 5, Constants.BUTTON_SIZE.height));
        pan.setMaximumSize(new Dimension(Constants.BUTTON_SIZE.width  * 5, Constants.BUTTON_SIZE.height));

        hoveredSurfaceDim.forEach((key, value) -> {
            pan.add(value);
        });
        return pan;
    }

    private void setEvent() {
        Button buttonMG = buttons.get(TargetButton.MagneticGrid);
        Button unit = buttons.get(TargetButton.Unit);

        buttonMG.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            mainWindow.getController().drawGrid();
            mainWindow.repaint();
        });

        unit.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            mainWindow.getController().switchUnit();
            //
            boolean active = !unit.isActive();
            mainWindow.getController().changeSelectedShiftDirection(active);
            unit.setActive(active);
            if (active)
                unit.setText("Imperial");
            else
                unit.setText("Metric");
            switchUnitsPanel();
            mainWindow.repaint();
        });
    }

    private void switchUnitsPanel() {
        mainWindow.getEditionPanel().getSurfacePanel().switchUnitsLabel();
        mainWindow.getEditionPanel().getManagementPanel().switchUnitsLabel();
        mainWindow.getEditionPanel().getGroutPanel().switchUnitsLabel();
        mainWindow.getEditionPanel().getPatternPanel().switchUnitsLabel();
        mainWindow.getEditionPanel().getTileSettingsPanel().switchUnitsLabel();
        switchUnitsLabel();
    }

    private void switchUnitsLabel() {
        if (mainWindow.getController().getUnitSetted().equals("Imperial")) {
            gridSizeInput.setUnitLabel("in/pix");
            zoom.setUnitLabel("in/pix");
        } else {
            gridSizeInput.setUnitLabel("cm/pix");
            zoom.setUnitLabel("cm/pix");
        }
        hoveredSurfaceDim.forEach((key, value) -> {
            value.switchUnit(mainWindow.getController().getUnitSetted());
        });
    }

    public Button getButton(TargetButton name) {
        return buttons.get(name);
    }

    public void setHoveredSurfaceDimension(Double[] dim) {
        hoveredSurfaceDim.get("width").setText(dim[0].toString());
        hoveredSurfaceDim.get("height").setText(dim[1].toString());
    }

    public void getHoverSurfaceBound() {
        Double[] dim = mainWindow.getController().getHoveredSurfaceDimension();

        if (dim != null) {
            hoveredSurfaceDim.get("width").setText(dim[0].toString());
            hoveredSurfaceDim.get("height").setText(dim[1].toString());
            repaint();
        } else {
            hoveredSurfaceDim.get("width").setText(("0"));
            hoveredSurfaceDim.get("height").setText(("0"));
        }
    }

    public void setZoomLevel() {
        Double zoomlevel = Math.round(mainWindow.getController().getZoomFactor() * 10000) / 10000D;
        if (zoomlevel.toString().equals("0.0")) {
            zoom.setText("Tiny");
        } else {
            zoom.setText(zoomlevel.toString());
        }
    }

    public void refreshGUI() {
        getHoverSurfaceBound();
        setZoomLevel();

        gridSizeInput.setText(mainWindow.getController().getGridSize().toString());
        zoom.setText(mainWindow.getController().getZoomFactor().toString());
        repaint();
    }

    public enum TargetButton {
        Unit,
        MagneticGrid
    }



    private UnitInput setUpUnitInput(String label) {
        UnitInput input = new UnitInput(label, false, "doubleInf");

        Dimension dim = new Dimension(Constants.BUTTON_SIZE.width * 2, Constants.BUTTON_SIZE.height);

        input.setPreferredSize(dim);
        input.setMinimumSize(dim);
        input.setMaximumSize(dim);
        input.setEditableFalse();
        return input;
    }

    private void setUpInputEvent() {
        gridSizeInput.addInputListener(InputEventKind.OnChange, (value, self) -> {
            if (!value.isEmpty()) {
                mainWindow.getController().setGridSize(Double.parseDouble(value));
                mainWindow.repaint();
            }
        });
    }
}
