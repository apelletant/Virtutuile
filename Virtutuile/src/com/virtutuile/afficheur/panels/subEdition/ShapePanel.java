package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.domaine.Controller;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;

public class ShapePanel extends SubPanel {

    private UnorderedMap<DrawShapeButtonType, Button> addSurface = new UnorderedMap<>();
    private UnorderedMap<DrawShapeButtonType, Button> removeSurface = new UnorderedMap<>();

    public ShapePanel(String name, Controller controller) {
        super(name, controller);
        setButtonsOnPanel();
        persistLayout();
        setEvents();
    }

    @Override
    protected void setEvents() {
        Button addRectangularSurface = addSurface.get(DrawShapeButtonType.AddRectangularSurface);

        addRectangularSurface.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            if (addRectangularSurface.isActive()) {
                controller.setDrawRectangularSurface(false);
                addRectangularSurface.setActive(false);
            } else {
                controller.setDrawRectangularSurface(true);
                addRectangularSurface.setActive(true);
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
        removeSurface.put(DrawShapeButtonType.RemoveFreeSurface, new Button("Free",  AssetLoader.loadImage("/icons/shape-edit-remove-free.png")));

        removeSurface.forEach((key, value) -> {
            line.add(value);
        });
        rows.add(line);
    }

    public enum DrawShapeButtonType {
        AddRectangularSurface,
        AddFreeSurface,
        RemoveRectangularSurface,
        RemoveFreeSurface,
    }
}
