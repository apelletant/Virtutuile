package com.virtutuile.moteur.interfaces;

import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.units.VCoordinates;
import com.virtutuile.systeme.units.VProperties;

import java.util.List;

public interface IVEditorManager {

    public void mouseHover(VCoordinates coordinates);

    public void mouseLClick(VProperties properties);

    public void mouseRClick(VCoordinates coordinates);

    public List<VDrawableShape> getDrawableShapes();
}
