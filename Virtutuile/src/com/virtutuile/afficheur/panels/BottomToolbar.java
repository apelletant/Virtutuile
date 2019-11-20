package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.afficheur.swing.BorderedPanel;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.domaine.Controller;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BottomToolbar extends BorderedPanel {

    private Controller controller;
    private UnorderedMap<TargetButton, Button> buttons = new UnorderedMap<>();
    private UnitInput[] hoveredSurfaceDim = new UnitInput[2];


    public BottomToolbar(Controller controller) {
        super();
        this.controller = controller;

        sizingPolicy(SizingPolicy.ContentBox);

        Button button = new Button("Show Bounds");
        buttons.put(TargetButton.ShowBounds, button);
        buttons.put(TargetButton.MagneticGrid, new Button("Magnetic grid", AssetLoader.loadImage("/icons/magnetic-grid.png")));

        buttons.forEach((key, value) -> {
            add(value);
        });

        setEvent();
        Panel gridSizePanel = setUpGridPanel();
        Panel hoveredSurfacePanel = setUpSurfaceDataPanel();

        add(gridSizePanel);
        add(Box.createHorizontalGlue());
        add(hoveredSurfacePanel);
//        JLabel label = setUpLabel();
//        if (label != null) {
//            add(label);
//            repaint();
//        }
        add(Box.createVerticalGlue());
    }

    public void setHoveredSurfaceDimension(Double[] dimensions) {
        if (dimensions[0] != null && dimensions[0] != null) {
            System.out.println("**********");
            System.out.println(dimensions[0]);
            System.out.println(dimensions[1]);
            System.out.println("**********");
//            this.hoveredSurfaceDim[0] = dimensions[0];
//            this.hoveredSurfaceDim[1] = dimensions[1];
        }
    }
    private void setEvent() {
        Button buttonMG = buttons.get(TargetButton.MagneticGrid);

        buttonMG.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            controller.drawGrid();
            repaint();
        });
    }

    public Button getButton(TargetButton name) {
        return buttons.get(name);
    }

    public void setSurfaceBounds() {
        Double[] dim = controller.getHoveredSurfaceDimesions();
//        this.hoveredSurfaceDim[0] = dim[0];
//        this.hoveredSurfaceDim[1] = dim[1];
    }

//    public void setSurfaceBounds() {
//        controller.getHoveredSurfaceBounds();
//    }

    public enum TargetButton {
        ShowBounds,
        MagneticGrid
    }

//    private JLabel setUpLabel() {
//        Surface hoveredSurface = controller.getHoveredSurface();
//        if (hoveredSurface != null) {
//            JLabel label = new JLabel("width", SwingConstants.CENTER);
//            return label;
//        }
//        return null;
//    }

    private Panel setUpGridPanel() {
        UnitInput gridSizeValue = new UnitInput("Grid size");
        gridSizeValue.setValue(controller.getGridSize());

        Panel gridSizePanel = new Panel();

        Border textMargin = new EmptyBorder(1, 3, 3, 3);
        gridSizePanel.setBorder(new CompoundBorder(gridSizePanel.getBorder(), textMargin));

        Dimension dim = new Dimension(Constants.BUTTON_SIZE.width * 2, Constants.BUTTON_SIZE.height);

        gridSizeValue.setPreferredSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
        gridSizeValue.setMinimumSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
        gridSizeValue.setMaximumSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
        gridSizePanel.add(gridSizeValue);

        gridSizePanel.setPreferredSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
        gridSizePanel.setMinimumSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
        gridSizePanel.setMaximumSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
        return gridSizePanel;
    }

    private Panel setUpSurfaceDataPanel() {
        Panel surfaceDataPanel = new Panel();

        UnitInput surfaceWidth = new UnitInput("Width:");
        surfaceWidth.setPreferredSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
        surfaceWidth.setMinimumSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
        surfaceWidth.setMaximumSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));

        UnitInput surfaceHeight = new UnitInput("Height:");
        surfaceHeight.setPreferredSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
        surfaceHeight.setMinimumSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
        surfaceHeight.setMaximumSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));

        if (hoveredSurfaceDim[0] != null && hoveredSurfaceDim[1] != null) {
            System.out.println(hoveredSurfaceDim[0]);
            System.out.println(hoveredSurfaceDim[1]);

//            surfaceWidth.setValue(hoveredSurfaceDim[0]);
//            surfaceHeight.setValue(hoveredSurfaceDim[1]);
        }

        surfaceDataPanel.add(surfaceWidth);
        surfaceDataPanel.add(surfaceHeight);

        surfaceDataPanel.setPreferredSize(new Dimension(Constants.BUTTON_SIZE.width  * 5, Constants.BUTTON_SIZE.height));
        surfaceDataPanel.setMinimumSize(new Dimension(Constants.BUTTON_SIZE.width  * 5, Constants.BUTTON_SIZE.height));
        surfaceDataPanel.setMaximumSize(new Dimension(Constants.BUTTON_SIZE.width  * 5, Constants.BUTTON_SIZE.height));
        repaint();

        return surfaceDataPanel;
    }
}


//surfaceDataPanel.setPreferredSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
//surfaceDataPanel.setMinimumSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
//surfaceDataPanel.setMaximumSize(new Dimension(Constants.BUTTON_SIZE.width  * 2, Constants.BUTTON_SIZE.height));
