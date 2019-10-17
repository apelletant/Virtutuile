package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.swing.panels.VBorderedPanel;

public class VBottomToolbar extends VBorderedPanel {
    public VBottomToolbar() {
        super();

        setPadding(5);
        setMargin(5);
        sizingPolicy(SizingPolicy.ContentBox);
    }
}
