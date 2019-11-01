package com.virtutuile.domaine;

import com.virtutuile.domaine.interfaces.IVEditorManager;
import com.virtutuile.domaine.managers.VPainterManager;
import com.virtutuile.domaine.managers.VPatternEditorManager;
import com.virtutuile.domaine.managers.VShapeEditorManager;
import com.virtutuile.systeme.components.VDrawableShape;
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

    public VEditorEngine() {super();}

    private UnorderedMap<VApplicationStatus.VActionManager, IVEditorManager> _managers = new UnorderedMap<>() {{
        put(VApplicationStatus.VActionManager.Pattern, new VPatternEditorManager());
        put(VApplicationStatus.VActionManager.Shape, new VShapeEditorManager());
    }};

    public void paint(Graphics graphics) {
        VPainter painter = VPainterManager.getInstance().getPainter(graphics);
        _managers.forEach((action, manager) -> {
            List<VDrawableShape> shapes = manager.getDrawableShapes();
            shapes.forEach(painter::paint);
        });
    }

    public void mouseHover(int x, int y){
        _hover = new VCoordinate(x, y);
        _managers.forEach((action, manager) -> {
            manager.mouseHover(_hover);
        });

    }

    public void mouseRelease(int x, int y) {
        _hover = new VCoordinate(x, y);
        _managers.forEach((action, manager) -> {
            manager.mouseRelease(_hover);
        });
    }

    public void mouseLClick(int x, int y) {
        VApplicationStatus.VActionManager manager = VApplicationStatus.getInstance().manager;
        VProperties properties = new VProperties();
        properties.coordinates.add(new VCoordinate(x,y));
        this._managers.get(manager).mouseLClick(properties);
        this._clicked = properties.coordinates.get(0);
    }

    public void mouseRClick(int x, int y) {

    }

    public void mouseDrag(int x, int y) {
        VShapeEditorManager manager =  (VShapeEditorManager)_managers.get(VApplicationStatus.VActionManager.Shape);
        VCoordinate coordinates = new VCoordinate(x,y);
        manager.mouseDrag(_hover, coordinates);
        _hover = coordinates;
    }

    public void keyEvent(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_DELETE) {

            VShapeEditorManager manager =  (VShapeEditorManager)_managers.get(VApplicationStatus.VActionManager.Shape);
            manager.deleteSelectedShape();

            //TODO VPatternEditorManager::resync -> Revérifie les liens entre les shapes et les patterns.
            ((VPatternEditorManager)_managers.get(VApplicationStatus.VActionManager.Pattern)).resync();
        }
    }
}
