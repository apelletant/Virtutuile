package com.virtutuile.moteur.managers;

import com.virtutuile.moteur.interfaces.IVEditorManager;
import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.components.VPattern;
import com.virtutuile.systeme.components.VPatternSpec;
import com.virtutuile.systeme.tools.UnorderedMap;
import com.virtutuile.systeme.units.VCoordinate;
import com.virtutuile.systeme.units.VProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VPatternEditorManager implements IVEditorManager {
    private UnorderedMap<UUID, VPatternSpec> _patternList;

    public void addPatternToShape(UUID shapeId, VPattern pattern) {

    }

    public void removePatternFromShape(UUID shapeId) {

    }

    public VPatternSpec getPatternFromShape(UUID shapeId) {
        return null;
    }

    @Override
    public void mouseHover(VCoordinate coordinates) {

    }

    @Override
    public void mouseLClick(VProperties properties) {

    }

    @Override
    public void mouseRClick(VCoordinate coordinates) {

    }

    @Override
    public List<VDrawableShape> getDrawableShapes() {
        return new ArrayList<>(0);
    }

    public void resync() {
    }
}
