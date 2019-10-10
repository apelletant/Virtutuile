package com.virtutuile.graphics.components.panels;

import com.virtutuile.graphics.wrap.panels.VBorderedPanel;

public class VBottomToolbar extends VBorderedPanel {
    public VBottomToolbar() {
        super();

        setPadding(5);
        setMargin(5);
        sizingPolicy(SizingPolicy.ContentBox);
    }
}
