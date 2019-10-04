package com.virtutuile.engine;

import com.virtutuile.data.VRect;
import com.virtutuile.data.VShape;

import java.awt.*;
import java.util.Vector;

public class VEditorEngine {

    private Vector<VShape> _shapes = new Vector<>();
    private VPhysicalEngine _pe = null;
    private VEditorState _state = VEditorState.Idle;
    private VShape _currentEditingShape = null;

    public VEditorEngine(VPhysicalEngine pe) {
        _pe = pe;
    }

    public void paint(Graphics gfx) {
        this.drawShapes(gfx);
        switch (_state) {
            case CreatingRectShape:
            case CreatingFreeShape:
                _currentEditingShape.draw(gfx);
                break;
        }
    }

    public void setMouse(Point point) {
        switch (_state) {
            case CreatingRectShape:
                showShapePoint(point);
        }
//        _mouse = point;
    }

    public void mouseLClick(Point point) {
        System.out.println("Click fire");
        switch (_state) {
            case CreatingRectShape:
                this.placeShapePoint(point);
        }
    }

    public void mouseRClick(Point point) {

    }

    public void setState(VEditorState state) {
        switch (state) {
            case CreatingRectShape:
                _currentEditingShape = new VRect();
                break;
            case Idle:
                persistChanges(state);
                if (_currentEditingShape != null)
                    _currentEditingShape = null;
        }
        _state = state;
    }

    public VEditorState getState() {
        return _state;
    }

    private void drawShapes(Graphics gfx) {
        for (VShape shape : _shapes) {
            shape.draw(gfx);
        }
    }

    private void placeShapePoint(Point point) {
        _currentEditingShape.setPoint(point);
        switch (_state) {
            case CreatingRectShape:
                if (_currentEditingShape.isDone()) {
                    _shapes.add(_currentEditingShape);
                    _currentEditingShape = new VRect();
                }
                break;
            case CreatingFreeShape:
                break;
        }
    }

    private void showShapePoint(Point point) {
        _currentEditingShape.viewPoint(point);
    }

    private void persistChanges(VEditorState newState) {
        switch (_state) {
            case CreatingRectShape:
                if (newState == VEditorState.Idle) {
                    _shapes.add(_currentEditingShape);
                    _currentEditingShape = null;
                }
                break;
            case Idle:
                break;
        }
    }
}
