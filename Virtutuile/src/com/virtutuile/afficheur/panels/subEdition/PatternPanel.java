package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.Button;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.domaine.Controller;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import java.util.Vector;

public class PatternPanel extends SubPanel {

    private UnorderedMap<String, Button> patterns = new UnorderedMap<>();

    public PatternPanel(String name, Controller controller) {
        super(name, controller);
        this.setButtonsOnPanel();
        this.setEvents();
        this.persistLayout();
    }

    public void setButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));

        patterns.put("Classic", new Button("Classic", AssetLoader.loadImage("/icons/classic-pattern.png")));

        patterns.forEach((key, value) -> {
            line.add(value);
        });

        rows.add(line);
    }

    @Override
    protected void setEvents() {
        Button classic = patterns.get("Classic");

        classic.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            controller.applyPattern("Classic");
            revalidate();
            repaint();
        });
    }
}
