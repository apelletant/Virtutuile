package com.virtutuile.domaine.moteur.managers;

import com.virtutuile.domaine.moteur.interfaces.IVEditorManager;
import com.virtutuile.domaine.systeme.components.VDrawableShape;
import com.virtutuile.domaine.systeme.components.VPattern;
import com.virtutuile.domaine.systeme.components.VPatternSpec;
import com.virtutuile.domaine.systeme.tools.UnorderedMap;
import com.virtutuile.domaine.systeme.units.VCoordinate;
import com.virtutuile.domaine.systeme.units.VProperties;

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
    public void mouseDrag(VCoordinate from, VCoordinate to) {

    }

    @Override
    public void mouseRelease(VCoordinate coordinate) {

    }

    @Override
    public List<VDrawableShape> getDrawableShapes() {
        return new ArrayList<>(0);
    }

    public void resync() {
    }
}
