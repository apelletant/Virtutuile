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
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import java.awt.*;

public class BottomToolbar extends BorderedPanel {

    private MainWindow mainWindow;
    private UnorderedMap<TargetButton, Button> buttons = new UnorderedMap<>();
    private UnorderedMap<String, UnitInput> hoveredSurfaceDim = new UnorderedMap<>();
    private UnitInput zoomLevel = new UnitInput("Zoom:");
    private TextInput gridSizeInput = new TextInput("Grid size: ");

    public BottomToolbar(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;

        sizingPolicy(SizingPolicy.ContentBox);

        Button button = new Button("Show Bounds");
        buttons.put(TargetButton.ShowBounds, button);
        buttons.put(TargetButton.MagneticGrid, new Button("Magnetic grid", AssetLoader.loadImage("/icons/magnetic-grid.png")));

        buttons.forEach((key, value) -> {
            add(value);
        });

        Panel gridSizePanel = setUpGridSizePanel();
        add(gridSizePanel);

        hoveredSurfaceDim.put("width", setUpUnitInput("Width:"));
        hoveredSurfaceDim.put("height", setUpUnitInput("Height:"));

        setEvent();
        Panel hoveredSurfacePanel = setUpSurfaceDataPanel();
        Panel zoomLvlPanel = setZoomLvlPanel();

        
        add(Box.createHorizontalGlue());
        add(Box.createVerticalGlue());
        add(hoveredSurfacePanel);

        setUpInputEvent();
    }

    private Panel setUpGridSizePanel() {
        Panel pan = new Panel();

        pan.setPreferredSize(new Dimension(Constants.BUTTON_SIZE.width  * 4, Constants.BUTTON_SIZE.height));
        pan.setMinimumSize(new Dimension(Constants.BUTTON_SIZE.width  * 4, Constants.BUTTON_SIZE.height));
        pan.setMaximumSize(new Dimension(Constants.BUTTON_SIZE.width  * 4, Constants.BUTTON_SIZE.height));

        gridSizeInput.setText(mainWindow.getController().getGridSize().toString());

        Panel subPan = new Panel();

        Label lab = new Label("cm/pixel");
        subPan.add(gridSizeInput);
        subPan.setOpaque(true);
        subPan.setBackground(Constants.SUBPANEL_BACKGROUND);
        subPan.add(lab);

        pan.add(subPan);
        return pan;
    }

    private Panel setZoomLvlPanel() {
        Panel pan = new Panel();

        pan.setPreferredSize(new Dimension(Constants.BUTTON_SIZE.width  * 5, Constants.BUTTON_SIZE.height));
        pan.setMinimumSize(new Dimension(Constants.BUTTON_SIZE.width  * 5, Constants.BUTTON_SIZE.height));
        pan.setMaximumSize(new Dimension(Constants.BUTTON_SIZE.width  * 5, Constants.BUTTON_SIZE.height));

        pan.add(zoomLevel);

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

        buttonMG.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            mainWindow.getController().drawGrid();
            mainWindow.repaint();
        });
    }

    public Button getButton(TargetButton name) {
        return buttons.get(name);
    }

    public void setHoveredSurfaceDimension(Double[] dim) {
        hoveredSurfaceDim.get("width").setValue(dim[0]);
        hoveredSurfaceDim.get("height").setValue(dim[1]);
    }

    public void getHoverSurfaceBound() {
        Double[] dim = mainWindow.getController().getHoveredSurfaceDimesions();

        if (dim != null) {
            hoveredSurfaceDim.get("width").setValue(dim[0]);
            hoveredSurfaceDim.get("height").setValue(dim[1]);
            repaint();
        } else {
            hoveredSurfaceDim.get("width").setValue(0);
            hoveredSurfaceDim.get("height").setValue(0);
        }
    }

    public enum TargetButton {
        ShowBounds,
        MagneticGrid
    }

    private UnitInput setUpUnitInput(String label) {
        UnitInput input = new UnitInput(label);

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
