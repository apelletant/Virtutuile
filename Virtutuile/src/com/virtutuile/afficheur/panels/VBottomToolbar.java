package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.inputs.VButton;
import com.virtutuile.afficheur.swing.panels.VBorderedPanel;
import com.virtutuile.systeme.tools.UnorderedMap;

import javax.swing.*;

public class VBottomToolbar extends VBorderedPanel {
    private UnorderedMap<TargetButton, VButton> _buttons = new UnorderedMap<>();

    public VBottomToolbar() {
        super();

        setPadding(5);
        setMargin(5);
        sizingPolicy(SizingPolicy.ContentBox);

        VButton btn = new VButton("Show Bounds");
        _buttons.put(TargetButton.ShowBounds, btn);

        this.add(_buttons.get(TargetButton.ShowBounds));
        this.add(Box.createHorizontalGlue());
        this.add(Box.createVerticalGlue());
    }

    public VButton getButton(TargetButton name) {
        return _buttons.get(name);
    }


    public enum TargetButton {
        ShowBounds
    }
}
