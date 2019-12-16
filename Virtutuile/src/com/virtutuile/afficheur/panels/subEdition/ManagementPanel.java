package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.afficheur.swing.events.InputEventKind;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import java.awt.*;

public class ManagementPanel extends SubPanel {

    private UnorderedMap<String, Button> align = new UnorderedMap<>();
    private UnorderedMap<String, Button> preStick = new UnorderedMap<>();
    private UnitInput alignInput = null;
    private Button stick;
    private Button unStick;

    public ManagementPanel(String name, MainWindow mainWindow) {
        super(name, mainWindow);
        setButtonsOnPanel();
        setEvents();
        persistLayout();
    }

    // TODO IMPLEMENTS FUNCTIONE
    @Override
    public void refreshGUI() {

    }

    @Override
    protected void setButtonsOnPanel() {
        setAlignButtons();
        setAlignInput();
        setStickButtons();
    }

    @Override
    public void switchUnitsLabel() {
        alignInput.switchUnit(mainWindow.getController().getUnitSetted());
    }

    private void setStickButtons() {
        JPanel line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setOpaque(false);
        preStick.put("Horizontal Stick", new Button("Horizontal Pre-Stick", AssetLoader.loadImage("/icons/stick-h.png")));
        preStick.get("Horizontal Stick").fixSize(new Dimension(200, 80));
        preStick.put("Vertical Stick", new Button("Vertical Pre-Stick", AssetLoader.loadImage("/icons/stick-v.png")));
        preStick.get("Vertical Stick").fixSize(new Dimension(200, 80));

        JPanel finalLine = line;
        preStick.forEach((name, value) -> {
            finalLine.add(value);
        });
        rows.add(line);

        line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setOpaque(false);
        stick = new Button("Stick", AssetLoader.loadImage("/icons/stick.png"));
        unStick = new Button("Unstick", AssetLoader.loadImage("/icons/unstick.png"));

        line.add(stick);
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
        align.put("Centered Horizontal", new Button("Centered Horizontal", AssetLoader.loadImage("/icons/centered-h.png")));
        align.put("Align Bottom", new Button("Align Bottom", AssetLoader.loadImage("/icons/align-bottom.png")));
        line.add(align.get("Align Top"));
        line.add(align.get("Centered Horizontal"));
        line.add(align.get("Align Bottom"));
        rows.add(line);

        line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setOpaque(false);
        align.put("Align Left", new Button("Align Left", AssetLoader.loadImage("/icons/align-left.png")));
        align.put("Centered Vertical", new Button("Centered Vertical", AssetLoader.loadImage("/icons/centered-v.png")));
        align.put("Align Right", new Button("Align Right", AssetLoader.loadImage("/icons/align-right.png")));
        line.add(align.get("Align Left"));
        line.add(align.get("Centered Vertical"));
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

        preStick.forEach((name, button) -> {
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

        stick.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            mainWindow.getController().stickSurfaces();
        });

        unStick.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            mainWindow.getController().unstickSurface();
        });
    }

    private void setAllButtonsStickInactive() {
        preStick.forEach((name, button) -> {
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
