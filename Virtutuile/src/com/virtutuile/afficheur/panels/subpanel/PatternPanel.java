package com.virtutuile.afficheur.panels.subpanel;

import com.virtutuile.afficheur.inputs.VButton;
import com.virtutuile.afficheur.swing.panels.MouseEventKind;
import com.virtutuile.afficheur.swing.panels.VPanel;
import com.virtutuile.domaine.VEditorEngine;
import com.virtutuile.systeme.shared.PatternType;
import com.virtutuile.systeme.tools.AssetLoader;
import com.virtutuile.systeme.tools.UnorderedMap;

import javax.swing.*;

public class PatternPanel extends SubPanel {

    private UnorderedMap<PatternType, VButton> _patterns = new UnorderedMap<>();

    public PatternPanel(String name, VEditorEngine engine) {
        super(name, engine);
        this.setButtonsOnPanel();
        this.setEvents();
        this.persistLayout();
    }

    protected void setEvents() {
        VButton classic = _patterns.get(PatternType.Classic);

        classic.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            this._editorEngine.applyPattern(PatternType.Classic);
        });
    }

    public void setButtonsOnPanel() {
        JPanel line = new VPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));

        this._patterns.put(PatternType.Classic, new VButton("Classic", AssetLoader.loadImage("/icons/classic-pattern.png")));

        this._patterns.forEach((key, value) -> {
            line.add(value);
        });

        this._lines.add(line);
    }

}
