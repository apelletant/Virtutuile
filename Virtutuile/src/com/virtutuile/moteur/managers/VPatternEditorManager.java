package com.virtutuile.moteur.managers;

import com.virtutuile.moteur.interfaces.IVEditorManager;
import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.components.VPattern;
import com.virtutuile.systeme.components.VPatternSpec;
import com.virtutuile.systeme.tools.UnorderedMap;
import com.virtutuile.systeme.units.VCoordinates;
import com.virtutuile.systeme.units.VProperties;

import java.util.List;

public class VPatternEditorManager implements IVEditorManager {
    private UnorderedMap<Integer, VPatternSpec> _patternList;

    public void addPatternToShape(int shapeId, VPattern pattern) {

    }

    public void removePatternFromShape(int shapeId) {

    }

    public VPatternSpec getPatternFromShape(int shapeId) {
        return null;
    }

    @Override
    public void mouseHover(VCoordinates coordinates) {

    }

    @Override
    public void mouseLClick(VProperties properties) {

    }

    @Override
    public void mouseRClick(VCoordinates coordinates) {

    }

    @Override
    public List<VDrawableShape> getDrawableShapes() {
        return null;
    }
}
