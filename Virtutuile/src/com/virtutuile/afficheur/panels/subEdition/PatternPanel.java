package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.InputEventKind;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.shared.UnorderedMap;
import com.virtutuile.shared.Vector2D;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.Vector;

public class PatternPanel extends SubPanel {

    private UnorderedMap<String, Button> patterns = new UnorderedMap<>();
    private UnorderedMap<String, Button> options = new UnorderedMap<>();
    private UnitInput patternPositionX = new UnitInput("Position X", true, "doubleInf");
    private UnitInput patternPositionY = new UnitInput("Position Y", true, "doubleInf");
    private UnitInput patternShiftConfig = new UnitInput("Shift value", true, "doubleInf");
    private Button patternShiftDirection = new Button("Shift on X");
    private UnitInput rotation = new UnitInput("Rotation", true, "doubleInf");

    public PatternPanel(String name, MainWindow mainWindow) {
        super(name, mainWindow);
        setButtonsOnPanel();
        setEvents();
        persistLayout();
    }

    // TODO IMPLEMENTS FUNCTIONE
    @Override
    public void refreshGUI() {
        retrieveInfoSelected();
    }

    public void setButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setButtonsPatternOnPanel(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setPatternOptionsButtonOnPanel(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setPatternPositionInput(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setPatternConfigurationInput(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setRotationInput(line);
    }

    @Override
    public void switchUnitsLabel() {
        patternPositionX.switchUnit(mainWindow.getController().getUnitSetted());
        patternPositionY.switchUnit(mainWindow.getController().getUnitSetted());
        patternShiftConfig.switchUnit(mainWindow.getController().getUnitSetted());
    }

    private void setRotationInput(JPanel line) {
        rotation.setUnitLabel("deg");
        rotation.addInputListener(InputEventKind.OnChange, (value, input) -> {
            mainWindow.getController().rotatePattern(Double.parseDouble(value));
            mainWindow.repaint();
        });
        line.add(rotation);
        rows.add(line);
    }

    private void setButtonsPatternOnPanel(JPanel line) {
        patterns.put("Classic", new Button("Classic", AssetLoader.loadImage("/icons/classic-pattern.png")));
        patterns.put("Offset", new Button("Offset", AssetLoader.loadImage("/icons/offset-pattern.png")));

        patterns.forEach((key, value) -> {
            line.add(value);
        });

        rows.add(line);
    }

    private void setPatternOptionsButtonOnPanel(JPanel line) {
        options.put("Center", new Button("Center", AssetLoader.loadImage("/icons/center-pattern.png")));
        options.put("Top Left", new Button("Top Left", AssetLoader.loadImage("/icons/top-left-pattern.png")));

        options.forEach((key, value) -> {
            line.add(value);
        });

        rows.add(line);
    }

    private void setPatternPositionInput(JPanel line) {
        patternPositionX.addInputListener(InputEventKind.OnChange, (val, input) -> {
            mainWindow.getController().moveSelectedPattern(Double.parseDouble(val), Double.NaN);
            mainWindow.repaint();
        });

        patternPositionY.addInputListener(InputEventKind.OnChange, (val, input) -> {
            mainWindow.getController().moveSelectedPattern(Double.NaN, Double.parseDouble(val));
            mainWindow.repaint();
        });
        line.add(patternPositionX);
        line.add(patternPositionY);
        rows.add(line);
    }

    private void setPatternConfigurationInput(JPanel line) {
        patternShiftConfig.addInputListener(InputEventKind.OnChange, (val, input) -> {
            mainWindow.getController().changeSelectedShiftValue(Double.parseDouble(val));
            mainWindow.repaint();
        });

        patternShiftDirection.addMouseEventListener(MouseEventKind.MouseLClick, (mouseEvent) -> {
            boolean active = !patternShiftDirection.isActive();
            mainWindow.getController().changeSelectedShiftDirection(active);
            patternShiftDirection.setActive(active);
            if (active)
                patternShiftDirection.setText("Shift on Y");
            else
                patternShiftDirection.setText("Shift on X");
            mainWindow.repaint();
        });
        line.add(patternShiftConfig);
        line.add(patternShiftDirection);
        rows.add(line);
    }

    @Override
    protected void setEvents() {

        patterns.forEach((name, button) -> {
            button.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
                mainWindow.getController().applyPattern(name);
                mainWindow.getEditionPanel().getInfoPanel().retrieveInfoSelected();
                mainWindow.getEditionPanel().getInfoPanel().retrieveGeneralTileInfo();
                mainWindow.repaint();
            });
        });

        options.forEach((name, button) -> {
            button.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
                mainWindow.getController().setPatternStartPosition(name);
                mainWindow.getEditionPanel().getInfoPanel().retrieveInfoSelected();
                mainWindow.getEditionPanel().getInfoPanel().retrieveGeneralTileInfo();
                mainWindow.repaint();
            });
        });
    }

    public void retrieveInfoSelected() {
        Vector2D pt = mainWindow.getController().getSelectedSurfacePatternOrigin();
        double ps = mainWindow.getController().getSelectedSurfacePatternShift();
        boolean ds = mainWindow.getController().getSelectedSurfacePatternDirectionShift();
        double rotationPattern = mainWindow.getController().getSelectedSurfacePatternRotation();

        rotation.setText(Double.toString(rotationPattern));
        if (pt != null) {
            patternPositionX.setValue(pt.x);
            patternPositionY.setValue(pt.y);
        }
        patternShiftConfig.setValue(ps);
        patternShiftDirection.setActive(ds);
        if (ds)
            patternShiftDirection.setName("Shift on Y");
        else
            patternShiftDirection.setName("Shift on X");
    }
}
