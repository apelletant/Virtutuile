package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.afficheur.swing.events.InputEventKind;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;

public class ManagementPanel extends SubPanel {

    private UnorderedMap<String, Button> align = new UnorderedMap<>();
    private UnorderedMap<String, Button> stick = new UnorderedMap<>();
    private UnitInput alignInput = null;
    private Button unStick;

    public ManagementPanel(String name, MainWindow mainWindow) {
        super(name, mainWindow);
        setButtonsOnPanel();
        setEvents();
        persistLayout();
    }

    @Override
    protected void setButtonsOnPanel() {
        setAlignButtons();
        setAlignInput();
        setStickButtons();
    }

    private void setStickButtons() {
        JPanel line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setOpaque(false);
        stick.put("Horizontal Stick", new Button("Horizontal Stick", AssetLoader.loadImage("/icons/stick-h.png")));
        stick.put("Vertical Stick", new Button("Vertical Stick", AssetLoader.loadImage("/icons/stick-v.png")));

        JPanel finalLine = line;
        stick.forEach((name, value) -> {
            finalLine.add(value);
        });
        rows.add(line);

        line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setOpaque(false);
        unStick = new Button("Unstick", AssetLoader.loadImage("/icons/unstick.png"));

        line.add(unStick);
        rows.add(line);
    }

    private void setAlignInput() {
        JPanel line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        alignInput = new UnitInput("Align Distance", true, "doubleZeroAllowed");
        line.add(alignInput);
        rows.add(line);
    }

    private void setAlignButtons() {
        JPanel line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setOpaque(false);
        align.put("Align Top", new Button("Align Top", AssetLoader.loadImage("/icons/align-top.png")));
        align.put("Align Bottom", new Button("Align Bottom", AssetLoader.loadImage("/icons/align-bottom.png")));
        line.add(align.get("Align Top"));
        line.add(align.get("Align Bottom"));
        rows.add(line);

        line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setOpaque(false);
        align.put("Align Left", new Button("Align Left", AssetLoader.loadImage("/icons/align-left.png")));
        align.put("Align Right", new Button("Align Right", AssetLoader.loadImage("/icons/align-right.png")));
        line.add(align.get("Align Left"));
        line.add(align.get("Align Right"));
        rows.add(line);
    }

    @Override
    protected void setEvents() {
        align.forEach((name, button) -> {
            button.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
                if (button.isActive()) {
                    button.setActive(false);
                    mainWindow.getController().setAlignAction(null);
                } else {
                    if (mainWindow.getController().setAlignAction(name)) {
                        setAllButtonsAlignInactive();
                        button.setActive(true);
                    }
                }
                repaint();
            });
        });

        alignInput.addInputListener(InputEventKind.OnChange, (value, self) -> {
            mainWindow.getController().setAlignDistance(Double.parseDouble(value));
            mainWindow.getCanvas().repaint();
        });

        stick.forEach((name, button) -> {
            button.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
                if (button.isActive()) {
                    button.setActive(false);
                    mainWindow.getController().setStickAction(null);
                } else {
                    if (mainWindow.getController().setStickAction(name)) {
                        setAllButtonsAlignInactive();
                        setAllButtonsStickInactive();
                        button.setActive(true);
                    }
                }
                repaint();
            });
        });
    }

    private void setAllButtonsStickInactive() {
        stick.forEach((name, button) -> {
            button.setActive(false);
        });
    }

    public void setAllButtonsAlignInactive() {
        align.forEach((name, button) -> {
            button.setActive(false);
        });
    }

    public void retrieveDistance() {
        Double distance = mainWindow.getController().getAlignmentDistance();
        if (distance != null) {
            alignInput.setValue(distance);
        }
    }

}
