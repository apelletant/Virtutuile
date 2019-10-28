package com.virtutuile.moteur.managers;

import com.virtutuile.moteur.interfaces.IVEditorManager;
import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.components.VRectShape;
import com.virtutuile.systeme.components.VShape;
import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.constants.VPhysicsConstants;
import com.virtutuile.systeme.singletons.VActionStatus;
import com.virtutuile.systeme.tools.UnorderedMap;
import com.virtutuile.systeme.units.VCoordinate;
import com.virtutuile.systeme.units.VProperties;
import com.virtutuile.systeme.units.Vector2D;
import javafx.scene.shape.Circle;

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

    private CursorEventType _cursor = CursorEventType.Move;
    private UnorderedMap<UUID, VShape> _shapes = new UnorderedMap<>();
    private VShape _currentShape = null;
    private VShape _hoveredShape = null;

    public VShapeEditorManager() {
        VShape shape = new VRectShape(new Rectangle2D.Double(30, 30, 70, 70), false);
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
        AtomicReference<VShape> shape = new AtomicReference<>();
        this._shapes.forEach((key, value) -> {
            if (value != null
                    && value.polygon() != null
                    && value.polygon().contains(VPhysicsConstants.coordinateToPoint(coordinates))) {
                shape.set(value);
            }
        });
        return shape.get();
    }

    public VShape getShapeNear(VCoordinate coordinate, double limitDistance) {
        AtomicReference<VShape> shape = new AtomicReference<>();
        final double[] nearest = {Double.NaN};

        this._shapes.forEach((key, value) -> {
            double dist = value.circleIntersect(new Circle(coordinate.longitude, coordinate.latitude, limitDistance));

            if (!Double.isNaN(dist) && dist < limitDistance) {
                if (Double.isNaN(nearest[0])) {
                    nearest[0] = dist;
                    shape.set(value);
                } else if (nearest[0] > dist) {
                    shape.set(value);
                }
            }
        });
        return shape.get();
    }

    public VShape getShapeNear(VCoordinate coordinate) {
        return getShapeNear(coordinate, VPhysicsConstants.pixelsToCentimeters(VPhysicsConstants.Mouse.DEFAULT_PRECISION));
    }

    @Override
    public void mouseHover(VCoordinate coordinates) {
        VShape shape = getShapeAt(coordinates);
        boolean outofShape = false;
        VActionStatus actionStatus = VActionStatus.getInstance();

        actionStatus.cursorShape = UIConstants.Mouse.VCursor.Pointer;
        if (shape == null) {
            outofShape = true;
            shape = getShapeNear(coordinates);
        }
        if (_hoveredShape != null) {
            _hoveredShape.setMouseHover(false);
        }

        if (shape != null) {
            actionStatus.cursorShape = UIConstants.Mouse.VCursor.Move;
            shape.setMouseHover(true);
            _hoveredShape = shape;
            _cursor = CursorEventType.Move;
            if (outofShape) {
                VCoordinate vertice = shape.getVerticeNear(coordinates, VPhysicsConstants.pixelsToCentimeters(VPhysicsConstants.Mouse.DEFAULT_PRECISION));

                if (vertice != null) {
                    actionStatus.cursorShape = UIConstants.Mouse.VCursor.Resize;
                    _cursor = CursorEventType.Resize;
                } else {
                    actionStatus.cursorShape = UIConstants.Mouse.VCursor.Rotate;
                    _cursor = CursorEventType.Rotate;
                }
            }
        } else {
            _hoveredShape = null;
        }
    }

    @Override
    public void mouseLClick(VProperties properties) {
        VActionStatus.VActionState actionState = VActionStatus.getInstance().doing;
        this._actions.get(actionState).accept(properties);
    }

    @Override
    public void mouseRClick(VCoordinate coordinates) {

    }

    @Override
    public void mouseDrag(VCoordinate from, VCoordinate to) {
            VActionStatus actionStatus = VActionStatus.getInstance();
        if (_cursor == CursorEventType.Move && _currentShape != null) {
            actionStatus.cursorShape = UIConstants.Mouse.VCursor.Move;
            _currentShape.move(from, to);
        } else if (_cursor == CursorEventType.Rotate && _hoveredShape != null) {
            Vector2D root = Vector2D.from(_hoveredShape.getCenter());
            Vector2D origin = Vector2D.from(from);
            Vector2D target = Vector2D.from(to);

            actionStatus.cursorShape = UIConstants.Mouse.VCursor.Rotate;
            _hoveredShape.rotateRad(target.angleBetweenRad(root) - origin.angleBetweenRad(root));
        } else if (_cursor == CursorEventType.Resize && _hoveredShape != null) {
            actionStatus.cursorShape = UIConstants.Mouse.VCursor.Resize;
        }
    }

    @Override
    public void mouseRelease(VCoordinate coordinate) {
        mouseHover(coordinate);
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

    public void deleteSelectedShape() {
        if (_currentShape != null) {
            _shapes.remove(_currentShape.id());
            _currentShape = null;
        }
    }

    enum CursorEventType {
        Move,
        Resize,
        Rotate,
    }
}
