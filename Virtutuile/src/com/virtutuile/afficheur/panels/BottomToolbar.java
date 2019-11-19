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
    //private hoveredSurface

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
        add(gridSizePanel);
        add(Box.createHorizontalGlue());

//        JLabel label = setUpLabel();
//        if (label != null) {
//            add(label);
//            repaint();
//        }
        add(Box.createVerticalGlue());
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
        System.out.println(controller.getGridSize());
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
}
