package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.inputs.TextInput;
import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.InputEventKind;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.domaine.Constants;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;

public class SurfacePanel extends SubPanel {

    private UnorderedMap<DrawShapeButtonType, Button> addSurface = new UnorderedMap<>();
    private UnorderedMap<DrawShapeButtonType, Button> removeSurface = new UnorderedMap<>();
    private UnorderedMap<DrawShapeButtonType, Button> surfaceManagement = new UnorderedMap<>();
    private UnorderedMap<InputContextType, UnitInput> unitInputs = new UnorderedMap<>();
    private UnorderedMap<InputContextType, UnitInput> positionInputs = new UnorderedMap<>();

    public SurfacePanel(String name, MainWindow mainWindow) {
        super(name, mainWindow);
        setButtonsOnPanel();
        setEvents();
        persistLayout();
    }

    @Override
    protected void setEvents() {
        Button addRectangularSurface = addSurface.get(DrawShapeButtonType.AddRectangularSurface);
        Button addFreeSurface = addSurface.get(DrawShapeButtonType.AddFreeSurface);
        Button merge = surfaceManagement.get(DrawShapeButtonType.MergeSurfaces);

        addRectangularSurface.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            if (addRectangularSurface.isActive()) {
                mainWindow.getController().setDrawRectangularSurface(false);
                addRectangularSurface.setActive(false);
            } else {
                mainWindow.getController().setDrawRectangularSurface(true);
                addRectangularSurface.setActive(true);
                addFreeSurface.setActive(false);
            }
        });

        addFreeSurface.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            if (addFreeSurface.isActive()) {
                mainWindow.getController().setDrawFreeSurface(false);
                addFreeSurface.setActive(false);
            } else {
                mainWindow.getController().setDrawFreeSurface(true);
                addRectangularSurface.setActive(false);
                addFreeSurface.setActive(true);
            }
        });

        merge.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            mainWindow.getController().mergeSurfaces();
            mainWindow.getEditionPanel().getInfoPanel().retrieveInfoSelected();
            mainWindow.getEditionPanel().getInfoPanel().retrieveGeneralTileInfo();
            mainWindow.repaint();
        });

        unitInputs.get(InputContextType.Width).addInputListener(InputEventKind.OnChange, (value, self) -> {
            mainWindow.getController().setSurfaceWidth(Double.parseDouble(value));
            mainWindow.getController().recalcPattern();
            mainWindow.repaint();
        });

        unitInputs.get(InputContextType.Height).addInputListener(InputEventKind.OnChange, (value, self) -> {
            mainWindow.getController().setSurfaceHeight(Double.parseDouble(value));
            mainWindow.getController().recalcPattern();
            mainWindow.repaint();
        });

        positionInputs.get(InputContextType.Longitude).addInputListener(InputEventKind.OnChange, (value, self) -> {
            if (!value.isEmpty()) {
                mainWindow.getController().setSurfaceLongitude(Double.parseDouble(value));
                mainWindow.getController().recalcPattern();
                mainWindow.repaint();
            }
        });

        positionInputs.get(InputContextType.Latitude).addInputListener(InputEventKind.OnChange, (value, self) -> {
            if (!value.isEmpty()) {
                mainWindow.getController().setSurfaceLatitude(Double.parseDouble(value));
                mainWindow.getController().recalcPattern();
                mainWindow.repaint();
            }
        });

    }

    protected void setButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setDrawShapesButtons(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setRemoveShapesButtons(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setUnitInputsOnPanel(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setPositionInputsOnPanel(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setManagementButtonOnPanel(line);
    }

    private void setManagementButtonOnPanel(JPanel line) {
        surfaceManagement.put(DrawShapeButtonType.MergeSurfaces, new Button("Merge", AssetLoader.loadImage("/icons/position-merge-shapes.png")));

        surfaceManagement.forEach((key, value) -> {
            line.add(value);
        });

        rows.add(line);
    }

    private void setDrawShapesButtons(JPanel line) {
        addSurface.put(DrawShapeButtonType.AddRectangularSurface, new Button("Rectangle", AssetLoader.loadImage("/icons/shape-edit-add-square.png")));
        addSurface.put(DrawShapeButtonType.AddFreeSurface, new Button("Free", AssetLoader.loadImage("/icons/shape-edit-add-free.png")));

        addSurface.forEach((key, value) -> {
            line.add(value);
        });

        rows.add(line);
    }

    private void setRemoveShapesButtons(JPanel line) {
        removeSurface.put(DrawShapeButtonType.RemoveRectangularSurface, new Button("Rectangle", AssetLoader.loadImage("/icons/shape-edit-remove-square.png")));
        removeSurface.put(DrawShapeButtonType.RemoveFreeSurface, new Button("Free", AssetLoader.loadImage("/icons/shape-edit-remove-free.png")));

        removeSurface.forEach((key, value) -> {
            line.add(value);
        });
        rows.add(line);
    }

    private void setUnitInputsOnPanel(JPanel line) {
        unitInputs.put(InputContextType.Width, new UnitInput("Width", true, "double"));
        unitInputs.put(InputContextType.Height, new UnitInput("Height", true, "double"));

        unitInputs.forEach((key, value) -> {
            line.add(value);
        });

        rows.add(line);
    }

    private void setPositionInputsOnPanel(JPanel line) {
        positionInputs.put(InputContextType.Longitude, new UnitInput("Longitude", true, "doubleInf"));
        positionInputs.put(InputContextType.Latitude, new UnitInput("Latitude", true, "doubleInf"));

        positionInputs.get(InputContextType.Longitude).setUnitLabel("cm to 0");
        positionInputs.get(InputContextType.Latitude).setUnitLabel("cm to 0");

        positionInputs.forEach((key, value) -> {
            line.add(value);
        });

        rows.add(line);
    }

    public void retrieveSelectedSurfaceProperties() {
        Double[] selectedSurfaceDimensions = mainWindow.getController().getSelectedSurfaceProperties();
        if (selectedSurfaceDimensions != null) {
            unitInputs.get(InputContextType.Width).setValue(Math.round(selectedSurfaceDimensions[0] * 10000) / 10000D);
            unitInputs.get(InputContextType.Height).setValue(Math.round(selectedSurfaceDimensions[1] * 10000) / 10000D);
            positionInputs.get(InputContextType.Longitude).setValue(selectedSurfaceDimensions[2]);
            positionInputs.get(InputContextType.Latitude).setValue(selectedSurfaceDimensions[3]);
        } else {
            setSurfaceDimensions(0.0,0.0);
            setSurfacePosition(0.0,0.0);
        }
    }

    private void setSurfaceDimensions(Double width, Double height) {
        unitInputs.get(InputContextType.Width).setValue(Math.round(width * 10000) / 10000D);
        unitInputs.get(InputContextType.Height).setValue(Math.round(height * 10000) / 10000D);
    }

    private void setSurfacePosition(Double width, Double height) {
        positionInputs.get(InputContextType.Longitude).setValue(Math.round(width * 10000) / 10000D);
        positionInputs.get(InputContextType.Latitude).setValue(Math.round(height * 10000) / 10000D);
    }

    public enum DrawShapeButtonType {
        AddRectangularSurface,
        AddFreeSurface,
        RemoveRectangularSurface,
        RemoveFreeSurface,
        MergeSurfaces,
    }

    public enum InputContextType {
        Width,
        Height,
        Longitude,
        Latitude
    }
}
