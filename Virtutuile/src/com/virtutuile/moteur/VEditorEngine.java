package com.virtutuile.moteur;

import com.virtutuile.moteur.interfaces.IVEditorManager;
import com.virtutuile.moteur.managers.VPainterManager;
import com.virtutuile.moteur.managers.VPatternEditorManager;
import com.virtutuile.moteur.managers.VShapeEditorManager;
import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.interfaces.IVGraphics;
import com.virtutuile.systeme.singletons.VActionStatus;
import com.virtutuile.systeme.tools.UnorderedMap;
import com.virtutuile.systeme.units.VCoordinate;
import com.virtutuile.systeme.units.VProperties;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class VEditorEngine {

    public VEditorEngine() {super();}

    private UnorderedMap<VActionStatus.VActionManager, IVEditorManager> _managers = new UnorderedMap<>() {{
        put(VActionStatus.VActionManager.Pattern, new VPatternEditorManager());
        put(VActionStatus.VActionManager.Shape, new VShapeEditorManager());
    }};

    public void paint(VPainter graphics) {
        _managers.forEach((action, manager) -> {
            List<VDrawableShape> shapes = manager.getDrawableShapes();
            shapes.forEach(graphics::paint);
        });
    }

    public void mouseHover(VCoordinate coordinates){
        _managers.forEach((action, manager) -> {
            manager.mouseHover(coordinates);
        });

    }

    public void mouseLClick(VProperties properties) {
        VActionStatus.VActionManager manager = VActionStatus.VActionStatus().manager;
        this._managers.get(manager).mouseLClick(properties);
    }

    public void mouseRClick(VCoordinate coordinates) {

    }
}
