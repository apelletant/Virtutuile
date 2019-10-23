package com.virtutuile.moteur.managers;

import com.virtutuile.moteur.interfaces.IVEditorManager;
import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.components.VRectShape;
import com.virtutuile.systeme.components.VShape;
import com.virtutuile.systeme.constants.VPhysicsConstants;
import com.virtutuile.systeme.singletons.VActionStatus;
import com.virtutuile.systeme.tools.UnorderedMap;
import com.virtutuile.systeme.units.VCoordinate;
import com.virtutuile.systeme.units.VProperties;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class VShapeEditorManager implements IVEditorManager {
    private HashMap<VActionStatus.VActionState, Consumer<VProperties>> _actions = new HashMap<>() {{
        put(VActionStatus.VActionState.Idle, VShapeEditorManager.this::selectShape);
        put(VActionStatus.VActionState.CreatingRectShape, VShapeEditorManager.this::createRectShape);
        put(VActionStatus.VActionState.CreatingFreeShape, VShapeEditorManager.this::createFreeShape);
    }};

    private UnorderedMap<UUID, VShape> _shapes = new UnorderedMap<>();
    private VShape _currentShape = null;
    private VShape _hoveredShape = null;

    public VShapeEditorManager() {
        VShape shape = new VRectShape(new Rectangle2D.Double(30, 30, 240, 120), false);
        _shapes.put(shape.id(), shape);
        shape = new VRectShape(new Rectangle2D.Double(500, 230, 240, 120), false);
        _shapes.put(shape.id(), shape);
        shape = new VRectShape(new Rectangle2D.Double(100, 190, 240, 120), false);
        shape.rotateDeg(45);
        _shapes.put(shape.id(), shape);
    }

    private void selectShape(VProperties properties) {
        VShape shape = this.getShapeAt(properties.coordinates.firstElement());
        if (this._currentShape != null) {
            this._currentShape.selected(false);
        }
        _currentShape = shape;
        if (shape != null) {
            this._currentShape = this._shapes.get(shape.id());
            this._currentShape.selected(true);
        }
    }

    public void createRectShape(VProperties properties) {

    }

    public void createFreeShape(VProperties properties) {

    }

    public VShape getShapeById(int id) {
        if (this._shapes.containsKey(id)) {
            return _shapes.get(id);
        }
        return null;
    }

    public int addShape(VShape shape) {
        return 0;
    }

    public void removeShape(int id) {
        if (this._shapes.containsKey(id)) {
            this._shapes.remove(id);
        }
    }

    public int buildShapeAt(VShape shape) {
        return 0;
    }

    public UUID getShapeIdAt(VCoordinate coordinates) {
        AtomicReference<UUID> shapeId = new AtomicReference<>();
        this._shapes.forEach((key, value) -> {
            if (value != null
                    && value.polygon() != null
                    && value.polygon().contains(VPhysicsConstants.coordinateToPoint(coordinates))) {
                shapeId.set(key);
            }
        });
        return shapeId.get();
    }

    public VShape getShapeAt(VCoordinate coordinates) {
        AtomicReference<VShape> shapeId = new AtomicReference<>();
        this._shapes.forEach((key, value) -> {
            if (value != null
                    && value.polygon() != null
                    && value.polygon().contains(VPhysicsConstants.coordinateToPoint(coordinates))) {
                shapeId.set(value);
            }
        });
        return shapeId.get();
    }

    @Override
    public void mouseHover(VCoordinate coordinates) {
        VShape shape = getShapeAt(coordinates);

        if (_hoveredShape != null) {
            _hoveredShape.setMouseHover(false);
        }
        if (shape != null) {
            shape.setMouseHover(true);
            _hoveredShape = shape;
        }
    }

    @Override
    public void mouseLClick(VProperties properties) {
        VActionStatus.VActionState actionState = VActionStatus.VActionStatus().doing;
        this._actions.get(actionState).accept(properties);
    }

    @Override
    public void mouseRClick(VCoordinate coordinates) {

    }

    @Override
    public List<VDrawableShape> getDrawableShapes() {
        List<VDrawableShape> list = new ArrayList<>();
        _shapes.forEach((id, shape) -> {
            VCoordinate[] coords = shape.getVertices();
            Point[] points = VPhysicsConstants.coordinatesToPoints(coords);
            VDrawableShape drawable = new VDrawableShape(points);
            drawable.setActive(shape.isSelected());
            drawable.setMouseHovered(shape.isMouseHover());
            drawable.fillColor(shape.fillColor());
            list.add(drawable);
        });
        return list;
    }

    public void moveShape(VCoordinate from, VCoordinate to) {
        if (_currentShape != null) {
            _currentShape.move(from, to);
        }
    }

    public void deleteSelectedShape() {
        if (_currentShape != null) {
            _shapes.remove(_currentShape.id());
            _currentShape = null;
        }
    }
}
