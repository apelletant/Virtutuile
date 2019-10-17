package com.virtutuile.moteur.managers;

import com.virtutuile.moteur.interfaces.IVEditorManager;
import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.components.VShape;
import com.virtutuile.systeme.constants.VPhysicsConstants;
import com.virtutuile.systeme.singletons.VActionStatus;
import com.virtutuile.systeme.tools.UnorderedMap;
import com.virtutuile.systeme.units.VCoordinates;
import com.virtutuile.systeme.units.VProperties;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class VShapeEditorManager implements IVEditorManager {
    private HashMap<VActionStatus.VActionState, Consumer<VProperties>> _actions = new HashMap<>() {{
        put(VActionStatus.VActionState.Idle, VShapeEditorManager.this::selectShape);
        put(VActionStatus.VActionState.CreatingRectShape, VShapeEditorManager.this::createRectShape);
        put(VActionStatus.VActionState.CreatingFreeShape, VShapeEditorManager.this::createFreeShape);
    }};

    private UnorderedMap<Integer, VShape> _shapes;
    private VShape _currentShape;

    private void selectShape(VProperties properties) {
        Integer shapeId = this.getShapeIdAt(properties.coordinates.firstElement());
        if (this._currentShape != null) {
            this._currentShape.selected(false);
        }
        if (shapeId != null) {
            this._currentShape = this._shapes.get(shapeId);
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

    public Integer getShapeIdAt(VCoordinates coordinates) {
        AtomicReference<Integer> shapeId = new AtomicReference<>();
        this._shapes.forEach((key, value) -> {
            if (value.polygon().contains(VPhysicsConstants.coordinatesToPoint(coordinates))) {
                shapeId.set(key);
            }
        });
        return shapeId.get();
    }

    public VDrawableShape getDrawableShape() {
        return null;
    }

    @Override
    public void mouseHover(VCoordinates coordinates) {

    }

    @Override
    public void mouseLClick(VProperties properties) {
        VActionStatus.VActionState actionState = VActionStatus.VActionStatus().doing;
        this._actions.get(actionState).accept(properties);
    }

    @Override
    public void mouseRClick(VCoordinates coordinates) {

    }

    @Override
    public List<VDrawableShape> getDrawableShapes() {
        return null;
    }
}
