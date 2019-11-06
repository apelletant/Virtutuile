package com.virtutuile.domaine;

import com.virtutuile.domaine.interfaces.IVEditorManager;
import com.virtutuile.domaine.managers.VPainterManager;
import com.virtutuile.domaine.managers.VPatternEditorManager;
import com.virtutuile.domaine.managers.VShapeEditorManager;
import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.shared.PatternType;
import com.virtutuile.systeme.singletons.VApplicationStatus;
import com.virtutuile.systeme.tools.UnorderedMap;
import com.virtutuile.systeme.units.VCoordinate;
import com.virtutuile.systeme.units.VProperties;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class VEditorEngine {

    private VCoordinate _clicked = null;
    private VCoordinate _hover = null;
    private boolean _mousePressed = false;
    private UnorderedMap<VApplicationStatus.VActionManager, IVEditorManager> _managers = new UnorderedMap<>();

    public VEditorEngine() {
        this._managers.put(VApplicationStatus.VActionManager.Shape, new VShapeEditorManager());
        this._managers.put(VApplicationStatus.VActionManager.Pattern, new VPatternEditorManager((VShapeEditorManager) this._managers.get(VApplicationStatus.VActionManager.Shape)));
    }

    public void paint(Graphics graphics) {
        VPainter painter = VPainterManager.getInstance().getPainter(graphics);
        _managers.forEach((action, manager) -> {
            List<VDrawableShape> shapes = manager.getDrawableShapes();
            painter.paintAll(shapes);
        });
    }

    public void applyPattern(PatternType pattern) {
        VPatternEditorManager patternEditorManager = (VPatternEditorManager) this._managers.get(VApplicationStatus.VActionManager.Pattern);
        patternEditorManager.addPatternToShape(pattern);
    }

    public void mouseHover(int x, int y){
        _hover = new VCoordinate(x, y);
        VApplicationStatus.VEditor editorManager = VApplicationStatus.VEditor.getInstance();

        if (editorManager.getGridStatus()) {
            _hover = coordToMagneticCoord(_hover);
        }

        _managers.forEach((action, manager) -> {
            manager.mouseHover(_hover);
        });
    }

    public void mouseRelease(int x, int y) {
        _hover = new VCoordinate(x, y);
        VApplicationStatus.VEditor editorManager = VApplicationStatus.VEditor.getInstance();

        if (editorManager.getGridStatus()) {
            _hover = coordToMagneticCoord(_hover);
        }
        _mousePressed = false;
        _managers.forEach((action, manager) -> {
            manager.mouseRelease(_hover);
        });
    }

    public void mouseLClick(int x, int y) {
        VApplicationStatus.VActionManager manager = VApplicationStatus.getInstance().manager;
        VApplicationStatus.VEditor editorManager = VApplicationStatus.VEditor.getInstance();
        VProperties properties = new VProperties();
        VCoordinate coord = new VCoordinate(x, y);

        if (editorManager.getGridStatus()) {
            coord = coordToMagneticCoord(coord);
        }

        properties.coordinates.add(coord);
        this._managers.get(manager).mouseLClick(properties);
        this._clicked = properties.coordinates.get(0);
        _mousePressed = true;
    }

    public void mouseRClick(int x, int y) {

    }

    public void mouseDrag(int x, int y) {
        VCoordinate coordinates = new VCoordinate(x,y);
        VApplicationStatus.VEditor editorManager = VApplicationStatus.VEditor.getInstance();

        if (editorManager.getGridStatus()) {
            coordinates = coordToMagneticCoord(coordinates);
        }

        VShapeEditorManager manager =  (VShapeEditorManager)_managers.get(VApplicationStatus.VActionManager.Shape);
        manager.mouseDrag(_hover, coordinates);
        _hover = coordinates;
    }

    public void keyEvent(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_DELETE) {

            VShapeEditorManager manager = (VShapeEditorManager) _managers.get(VApplicationStatus.VActionManager.Shape);
            manager.deleteSelectedShape();
        }
    }

    private VCoordinate coordToMagneticCoord(VCoordinate oldCoord) {
        VApplicationStatus.VEditor manager = VApplicationStatus.VEditor.getInstance();
        VCoordinate newCoord = new VCoordinate();

//        int zoom = manager.getZoom();
        //TODO replace with real zoom value
        int zoom = 100;

        if (oldCoord.latitude % (zoom /4) <= 12) {
            newCoord.latitude = oldCoord.latitude - (oldCoord.latitude % (zoom /4));
        } else {
            newCoord.latitude = oldCoord.latitude + ( (zoom / 4) - (oldCoord.latitude % (zoom /4)));
        }

        if (oldCoord.longitude % (zoom /4) <= 12) {
            newCoord.longitude = oldCoord.longitude - (oldCoord.longitude % (zoom /4));
        } else {
            newCoord.longitude = oldCoord.longitude + ( (zoom / 4) - (oldCoord.longitude % (zoom /4)));
        }

        return newCoord;
    }
}
