package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.Button;
import com.virtutuile.afficheur.swing.BorderedPanel;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.domaine.Controller;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;

public class BottomToolbar extends BorderedPanel {

    private Controller controller;
    private UnorderedMap<TargetButton, Button> buttons = new UnorderedMap<>();
    private Canvas canvas;

    public BottomToolbar(Controller controller, Canvas canvas) {
        super();
        this.controller = controller;
        this.canvas = canvas;
        
        sizingPolicy(SizingPolicy.ContentBox);

        Button button = new Button("Show Bounds");
        buttons.put(TargetButton.ShowBounds, button);
        buttons.put(TargetButton.MagneticGrid, new Button("Magnetic grid", AssetLoader.loadImage("/icons/magnetic-grid.png")));

        buttons.forEach((key, value) -> {
            add(value);
        });
        add(Box.createHorizontalGlue());
        add(Box.createVerticalGlue());
    }

    public Button getButton(TargetButton name) {
        return buttons.get(name);
    }

    public enum TargetButton {
        ShowBounds,
        MagneticGrid
    }
}
