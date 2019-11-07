package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.Button;
import com.virtutuile.afficheur.swing.BorderedPanel;
import com.virtutuile.domaine.Controller;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import java.awt.*;

public class BottomToolbar extends BorderedPanel {

    private Controller controller;
    private UnorderedMap<TargetButton, Button> buttons = new UnorderedMap<>();

    public BottomToolbar(Controller controller) {
        super();
        this.controller = controller;

        setPadding(5);
        setMargin(5);
        sizingPolicy(SizingPolicy.ContentBox);

        Button button = new Button("Show Bounds");
        buttons.put(TargetButton.ShowBounds, button);

        add(buttons.get(TargetButton.ShowBounds));
        add(Box.createHorizontalGlue());
        add(Box.createVerticalGlue());
    }

    public Button getButton(TargetButton name) {
        return buttons.get(name);
    }

    public enum TargetButton {
        ShowBounds
    }
}
