package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.inputs.ColorPicker;
import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.InputEventKind;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class SurfacePanel extends SubPanel {

    private UnorderedMap<DrawShapeButtonType, Button> addSurface = new UnorderedMap<>();
    private UnorderedMap<DrawShapeButtonType, Button> surfaceManagement = new UnorderedMap<>();
    private UnorderedMap<InputContextType, UnitInput> unitInputs = new UnorderedMap<>();
    private UnorderedMap<InputContextType, UnitInput> positionInputs = new UnorderedMap<>();
    private ColorPicker colorPicker = null;

    public SurfacePanel(String name, MainWindow mainWindow) {
        super(name, mainWindow);
        setButtonsOnPanel();
        setEvents();
        persistLayout();
    }

    // TODO IMPLEMENTS FUNCTIONE
    @Override
    public void refreshGUI() {
        retrieveSelectedSurfaceProperties();
    }

    @Override
    protected void setEvents() {
        Button addRectangularSurface = addSurface.get(DrawShapeButtonType.AddRectangularSurface);
        Button addFreeSurface = addSurface.get(DrawShapeButtonType.AddFreeSurface);
        Button merge = surfaceManagement.get(DrawShapeButtonType.MergeSurfaces);
        Button makeHole = surfaceManagement.get(DrawShapeButtonType.MakeHole);

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
            mainWindow.getEditionPanel().getManagementPanel().setAllButtonsAlignInactive();
            mainWindow.repaint();
        });

        unitInputs.get(InputContextType.Width).addInputListener(InputEventKind.OnChange, (value, self) -> {
            mainWindow.getController().setSurfaceWidth(Double.parseDouble(value));
            mainWindow.getController().recalcPattern(); /*TODO*/
            mainWindow.repaint();
        });

        unitInputs.get(InputContextType.Height).addInputListener(InputEventKind.OnChange, (value, self) -> {
            mainWindow.getController().setSurfaceHeight(Double.parseDouble(value));
            mainWindow.getController().recalcPattern(); /*TODO*/
            mainWindow.repaint();
        });

        positionInputs.get(InputContextType.Longitude).addInputListener(InputEventKind.OnChange, (value, self) -> {
            if (!value.isEmpty()) {
                mainWindow.getController().setSurfaceLongitude(Double.parseDouble(value));
                mainWindow.getController().recalcPattern(); /*TODO*/
                mainWindow.repaint();
            }
        });

        positionInputs.get(InputContextType.Latitude).addInputListener(InputEventKind.OnChange, (value, self) -> {
            if (!value.isEmpty()) {
                mainWindow.getController().setSurfaceLatitude(Double.parseDouble(value));
                mainWindow.getController().recalcPattern(); /*TODO*/
                mainWindow.repaint();
            }
        });

        positionInputs.get(InputContextType.Rotation).addInputListener(InputEventKind.OnChange, (value, self) -> {
            if (!value.isEmpty()) {
                mainWindow.getController().rotateSurface(Double.parseDouble(value));
                mainWindow.getController().recalcPattern(); /*TODO*/
                mainWindow.repaint();
            }
        });

        makeHole.addMouseEventListener(MouseEventKind.MouseLClick, (mouseEvent) -> {
            mainWindow.getController().makeSurfaceHole();
            mainWindow.getEditionPanel().getInfoPanel().retrieveInfoSelected();
            mainWindow.getEditionPanel().getInfoPanel().retrieveGeneralTileInfo();
            mainWindow.getEditionPanel().getManagementPanel().setAllButtonsAlignInactive();
            mainWindow.repaint();
        });

        colorPicker.addInputListener(InputEventKind.OnChange, (color, self) -> {
            mainWindow.getController().setSurfaceColor(color);
            mainWindow.repaint();
        });

    }

    protected void setButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setDrawShapesButtons(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setUnitInputsOnPanel(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setPositionInputsOnPanel(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setColorPickerOnPanel(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        setManagementButtonOnPanel(line);
    }

    @Override
    public void switchUnitsLabel() {
        //.switchUnit(mainWindow.getController().getUnitSetted());
        unitInputs.forEach((name, self) -> {
            self.switchUnit(mainWindow.getController().getUnitSetted());
        });

        positionInputs.forEach((name, self) -> {
            if (name != InputContextType.Rotation) {
                self.switchUnit(mainWindow.getController().getUnitSetted());
            }
        });
    }

    private void setColorPickerOnPanel(JPanel line) {
        colorPicker = new ColorPicker();

        colorPicker.setMaximumSize(new Dimension(500,200));
        colorPicker.setMinimumSize(new Dimension(500,200));
        colorPicker.setPreferredSize(new Dimension(500,200));
        AbstractColorChooserPanel[] panels = colorPicker.getChooserPanels();
        colorPicker.setChooserPanels(new AbstractColorChooserPanel[] { panels[0] });
        line.add(colorPicker);
        rows.add(line);
    }

    private void setManagementButtonOnPanel(JPanel line) {
        surfaceManagement.put(DrawShapeButtonType.MakeHole, new Button("Hole", AssetLoader.loadImage("/icons/hole.png")));
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

        JPanel finalLine = new JPanel();
        finalLine.setLayout(new BoxLayout(finalLine, BoxLayout.X_AXIS));
        positionInputs.put(InputContextType.Rotation, new UnitInput("Rotation", true, "doubleInf"));
        positionInputs.get(InputContextType.Rotation).setUnitLabel("deg");
        finalLine.add(positionInputs.get(InputContextType.Rotation));
        rows.add(finalLine);
    }

    public void retrieveSelectedSurfaceProperties() {
        Double[] selectedSurfaceDimensions = mainWindow.getController().getSelectedSurfaceProperties();
        if (selectedSurfaceDimensions != null) {
            unitInputs.get(InputContextType.Width).setValue(selectedSurfaceDimensions[0]);
            unitInputs.get(InputContextType.Height).setValue(selectedSurfaceDimensions[1]);
            positionInputs.get(InputContextType.Longitude).setValue(selectedSurfaceDimensions[2]);
            positionInputs.get(InputContextType.Latitude).setValue(selectedSurfaceDimensions[3]);
        } else {
            setSurfaceDimensions(0.0,0.0);
            setSurfacePosition(0.0,0.0);
        }
    }

    private void setSurfaceDimensions(Double width, Double height) {
        unitInputs.get(InputContextType.Width).setValue(width);
        unitInputs.get(InputContextType.Height).setValue(height);
    }

    private void setSurfacePosition(Double width, Double height) {
        positionInputs.get(InputContextType.Longitude).setValue(width);
        positionInputs.get(InputContextType.Latitude).setValue(height);
    }

    public enum DrawShapeButtonType {
        AddRectangularSurface,
        AddFreeSurface,
        MergeSurfaces,
        MakeHole
    }

    public enum InputContextType {
        Width,
        Height,
        Longitude,
        Latitude,
        Rotation
    }
}
