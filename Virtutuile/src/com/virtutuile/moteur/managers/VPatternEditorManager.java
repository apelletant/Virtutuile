package com.virtutuile.moteur.managers;

import com.virtutuile.moteur.interfaces.VEditorManager;
import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.components.VPattern;
import com.virtutuile.systeme.components.VPatternSpec;
import com.virtutuile.systeme.tools.UnorderedMap;
import com.virtutuile.systeme.units.VCoordinates;

import java.util.List;

/**
 * Singleton
 */
public class VPatternEditorManager implements VEditorManager {
    private static VPatternEditorManager _patternEditorManager = null;

    private UnorderedMap<Integer, VPatternSpec> _patternList;

    public static VPatternEditorManager VPatternEditorManager() {
        if (_patternEditorManager == null) {
            _patternEditorManager = new VPatternEditorManager();
        }
        return _patternEditorManager;
    }

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
