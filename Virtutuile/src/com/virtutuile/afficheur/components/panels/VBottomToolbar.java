package com.virtutuile.afficheur.components.panels;

import com.virtutuile.afficheur.wrap.panels.VBorderedPanel;

public class VBottomToolbar extends VBorderedPanel {
    public VBottomToolbar() {
        super();

        setPadding(5);
        setMargin(5);
        sizingPolicy(SizingPolicy.ContentBox);
    }
}
