package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.domaine.Controller;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;

public class PatternPanel extends SubPanel {

    private UnorderedMap<String, Button> patterns = new UnorderedMap<>();
    private UnorderedMap<String, Button> options = new UnorderedMap<>();

    public PatternPanel(String name, Controller controller) {
        super(name, controller);
        this.setButtonsOnPanel();
        this.setEvents();
        this.persistLayout();
    }

    public void setButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setButtonsPatternOnPanel(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setPatternOptionsButtonOnPanel(line);
    }

    private void setButtonsPatternOnPanel(JPanel line) {
        patterns.put("Classic", new Button("Classic", AssetLoader.loadImage("/icons/classic-pattern.png")));

        patterns.forEach((key, value) -> {
            line.add(value);
        });

        rows.add(line);
    }

    private void setPatternOptionsButtonOnPanel(JPanel line) {
        options.put("Center", new Button("Center", AssetLoader.loadImage("/icons/center-pattern.png")));
        options.put("Top Right", new Button("Top Right", AssetLoader.loadImage("/icons/top-right-pattern.png")));

        options.forEach((key, value) -> {
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
