package com.virtutuile.engine;

import com.virtutuile.data.VShape;

import java.awt.*;
import java.util.Vector;

public class VEditorEngine {

    Vector<VShape> _shapes = new Vector<>();
    Point _mouse = new Point();
    VPhysicalEngine _pe = null;
    VEditorState _state = VEditorState.Idle;

    public VEditorEngine(VPhysicalEngine pe) {
        _pe = pe;
    }

    public void paint(Graphics gfx) {
        this.drawShapes(gfx);
    }

    public void setMouse(Point point) {
        _mouse = point;
    }

    public void mouseClick(Point point) {
        switch (_state) {
            case CreatingFreeShape:
                this.addFreeShapePoint(point);
        }
    }

    public void mouseDbClick(Point point) {

    }

    public void setState(VEditorState state) {
        _state = state;
    }

    public VEditorState setState() {
        return _state;
    }

    private void drawShapes(Graphics gfx) {

    }

    private void addFreeShapePoint(Point point) {

    }
}
