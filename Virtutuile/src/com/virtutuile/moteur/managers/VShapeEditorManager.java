package com.virtutuile.moteur.managers;

import com.virtutuile.moteur.interfaces.VEditorManager;
import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.components.VShape;
import com.virtutuile.systeme.tools.UnorderedMap;
import com.virtutuile.systeme.units.VCoordinates;
import com.virtutuile.systeme.units.VDimensions;

import java.util.List;
import java.util.Vector;

/**
 * Singleton
 */
class VShapeEditorManager implements VEditorManager {
    private static VShapeEditorManager _shapeEditorManager = null;

    private UnorderedMap<Integer, VShape> _shapes;
    private VShape _currentShape;

    public static VShapeEditorManager VShapeEditorManager() {
        if (_shapeEditorManager == null) {
            _shapeEditorManager = new VShapeEditorManager();
        }
        return _shapeEditorManager;
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

    public VShape getShapeAt(VCoordinates coordinates) {
        return null;
    }

    public int createRectShape(VCoordinates origin, VDimensions dimensions) {
        return 0;
    }

    public int createFreeShape(Vector<VCoordinates> coordinates) {
        return 0;
    }

    public VDrawableShape getDrawableShape() {
        return null;
    }

    @Override
    public void mouseHover(VCoordinates coordinates) {

    }

    @Override
    public void mouseLClick(VCoordinates coordinates) {

    }

    @Override
    public void mouseRClick(VCoordinates coordinates) {

    }

    @Override
    public List<VDrawableShape> getPrimitiveShaoes() {
        return null;
    }
}
