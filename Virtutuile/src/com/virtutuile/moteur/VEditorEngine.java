package com.virtutuile.moteur;

import com.virtutuile.moteur.interfaces.IVEditorManager;
import com.virtutuile.moteur.managers.VPatternEditorManager;
import com.virtutuile.moteur.managers.VShapeEditorManager;
import com.virtutuile.systeme.interfaces.IVGraphics;
import com.virtutuile.systeme.singletons.VActionStatus;
import com.virtutuile.systeme.units.VCoordinates;
import com.virtutuile.systeme.units.VProperties;

import java.util.HashMap;

public class VEditorEngine {

    private HashMap<VActionStatus.VActionManager, IVEditorManager> _managers = new HashMap<>() {{
        put(VActionStatus.VActionManager.Pattern, new VPatternEditorManager());
        put(VActionStatus.VActionManager.Shape, new VShapeEditorManager());
    }};

    public void paint(IVGraphics graphics) {

    }

    public void mouseHover(VCoordinates coordinates){

    }

    public void mouseLClick(VProperties properties) {
        VActionStatus.VActionManager manager = VActionStatus.VActionStatus().manager;
        this._managers.get(manager).mouseLClick(properties);
    }

    public void mouseRClick(VCoordinates coordinates) {

    }
}
