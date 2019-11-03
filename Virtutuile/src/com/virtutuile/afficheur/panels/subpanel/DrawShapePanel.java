package com.virtutuile.afficheur.panels.subpanel;

import com.virtutuile.afficheur.inputs.VButton;
import com.virtutuile.afficheur.inputs.VMetricInputText;
import com.virtutuile.afficheur.swing.panels.MouseEventKind;
import com.virtutuile.afficheur.swing.panels.VPanel;
import com.virtutuile.domaine.VEditorEngine;
import com.virtutuile.systeme.singletons.VApplicationStatus;
import com.virtutuile.systeme.tools.AssetLoader;
import com.virtutuile.systeme.tools.UnorderedMap;

import javax.swing.*;

public class DrawShapePanel extends SubPanel {
    private UnorderedMap<DrawShapeButtonType, VButton> _addShape = new UnorderedMap<>();
    private UnorderedMap<DrawShapeButtonType, VButton> _removeShape = new UnorderedMap<>();
    private UnorderedMap<DrawShapeInputType, VMetricInputText> _rectSize = new UnorderedMap<>();

    public DrawShapePanel(String name, VEditorEngine engine) {
        super(name, engine);
        /*TitledBorder border = new TitledBorder(name);
        border
        border.setTitleColor(UIConstants.EDITIONPANEL_FONT_COLOR);
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);*/
        this.setButtonsOnDrawShape();
        /*this.setBorder(border);*/
        this.persistLayout();
        this.setEvents();
    }

    private void setEvents() {
        VButton addRect = _addShape.get(DrawShapeButtonType.AddRectangularShape);
        VApplicationStatus manager = VApplicationStatus.getInstance();

        addRect.addMouseEventListener(MouseEventKind.MouseLClick, (evt) -> {
            if (addRect.isActive()) {
                manager.doing = VApplicationStatus.VActionState.Idle;
                addRect.setActive(false);
            } else {
                manager.doing = VApplicationStatus.VActionState.CreatingRectShape;
                addRect.setActive(true);
            }
        });
    }

    private void setButtonsOnDrawShape() {
        JPanel line = new VPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        this.setDrawShapesButtons(line);

        line = new VPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        this.setRemoveShapesButtons(line);

        /*line = new VPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        this.setRectSettings(line);*/
    }

    private void setDrawShapesButtons(JPanel line) {
        this._addShape.put(DrawShapeButtonType.AddRectangularShape, new VButton("Rectangle", AssetLoader.loadImage("/icons/shape-edit-add-square.png")));
        this._addShape.put(DrawShapeButtonType.AddFreeShape, new VButton("Free", AssetLoader.loadImage("/icons/shape-edit-add-free.png")));
        this._addShape.put(DrawShapeButtonType.AddTriangularShape, new VButton("Triangle",  AssetLoader.loadImage("/icons/shape-edit-add-triangle.png")));

        this._addShape.forEach((key, value) -> {
            line.add(value);
        });
        this._lines.add(line);
    }

    private void setRemoveShapesButtons(JPanel line) {
        this._removeShape.put(DrawShapeButtonType.RemoveRectangularShape, new VButton("Rectangle", AssetLoader.loadImage("/icons/shape-edit-remove-square.png")));
        this._removeShape.put(DrawShapeButtonType.RemoveFreeShape, new VButton("Free",  AssetLoader.loadImage("/icons/shape-edit-remove-free.png")));
        this._removeShape.put(DrawShapeButtonType.RemoveTriangularShape, new VButton("Triangle",  AssetLoader.loadImage("/icons/shape-edit-remove-triangle.png")));

        this._removeShape.forEach((key, value) -> {
            line.add(value);
        });
        this._lines.add(line);
    }

    public enum DrawShapeButtonType {
        AddRectangularShape,
        AddFreeShape,
        AddTriangularShape,
        RemoveRectangularShape,
        RemoveFreeShape,
        RemoveTriangularShape
    }

    public enum DrawShapeInputType {
        Width,
        Height
    }
}
