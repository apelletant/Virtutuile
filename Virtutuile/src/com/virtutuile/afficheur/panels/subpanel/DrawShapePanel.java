package com.virtutuile.afficheur.panels.subpanel;

import com.virtutuile.afficheur.inputs.VButton;
import com.virtutuile.afficheur.swing.panels.VPanel;
import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.tools.UnorderedMap;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.io.File;
import java.util.Vector;

public class DrawShapePanel extends SubPanel {
    private UnorderedMap<DrawShapeButtonType, VButton> _addShape = new UnorderedMap<>();
    private UnorderedMap<DrawShapeButtonType, VButton> _removeShape = new UnorderedMap<>();

    public DrawShapePanel(String name) {
        super(name);
        /*TitledBorder border = new TitledBorder(name);
        border
        border.setTitleColor(UIConstants.EDITIONPANEL_FONT_COLOR);
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);*/
        this.setButtonsOnDrawShape();
        /*this.setBorder(border);*/
        this.persistLayout();
    }

    private void setButtonsOnDrawShape() {
        JPanel line = new VPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        this.setDrawShapesButtons(line);

        line = new VPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        this.setRemoveShapesButtons(line);
    }

    private void setDrawShapesButtons(JPanel line) {
        this._addShape.put(DrawShapeButtonType.AddRectangularShape, new VButton("Rectangle",new File("./Virtutuile/assets/icons/shape-edit-add-square.png")));
        this._addShape.put(DrawShapeButtonType.AddFreeShape, new VButton("Free", new File("./Virtutuile/assets/icons/shape-edit-add-free.png")));
        this._addShape.put(DrawShapeButtonType.AddTriangularShape, new VButton("Triangle", new File("./Virtutuile/assets/icons/shape-edit-add-triangle.png")));

        this._addShape.forEach((key, value) -> {
            line.add(value);
        });
        this._lines.add(line);
    }

    private void setRemoveShapesButtons(JPanel line) {
        this._removeShape.put(DrawShapeButtonType.RemoveRectangularShape, new VButton("Rectangle",new File("./Virtutuile/assets/icons/shape-edit-remove-square.png")));
        this._removeShape.put(DrawShapeButtonType.RemoveFreeShape, new VButton("Free", new File("./Virtutuile/assets/icons/shape-edit-remove-free.png")));
        this._removeShape.put(DrawShapeButtonType.RemoveTriangularShape, new VButton("Triangle", new File("./Virtutuile/assets/icons/shape-edit-remove-triangle.png")));

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
}
