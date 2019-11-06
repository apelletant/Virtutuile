package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.inputs.VButton;
import com.virtutuile.afficheur.swing.panels.VBorderedPanel;
import com.virtutuile.systeme.tools.AssetLoader;
import com.virtutuile.systeme.tools.UnorderedMap;

import javax.swing.*;
import java.awt.*;

public class VBottomToolbar extends VBorderedPanel {
    private UnorderedMap<TargetButton, VButton> _buttons = new UnorderedMap<>();

    public VBottomToolbar() {
        super();
        this.setName("BottomToolbar");
        this.setBackground(new Color(66, 66, 66));

        sizingPolicy(SizingPolicy.ContentBox);

        this._buttons.put(TargetButton.ShowBounds, new VButton("Show Bounds"));

        this._buttons.put(TargetButton.DisplayGrid, new VButton("Magnetic Grid", AssetLoader.loadImage("/icons/magnetic-grid.png")));

        this._buttons.forEach((key, value) -> {
            this.add(value);
        });

        this.add(Box.createHorizontalGlue());
        this.add(Box.createVerticalGlue());
    }

    public VButton getButton(TargetButton name) {
        return _buttons.get(name);
    }

    public enum TargetButton {
        ShowBounds,
        DisplayGrid
    }
}
