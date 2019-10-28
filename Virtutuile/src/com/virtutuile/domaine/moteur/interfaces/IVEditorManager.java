package com.virtutuile.domaine.moteur.interfaces;

import com.virtutuile.domaine.systeme.components.VDrawableShape;
import com.virtutuile.domaine.systeme.units.VCoordinate;
import com.virtutuile.domaine.systeme.units.VProperties;

import java.util.List;

public interface IVEditorManager {

    public void mouseHover(VCoordinate coordinates);

    public void mouseLClick(VProperties properties);

    public void mouseRClick(VCoordinate coordinates);

    public void mouseDrag(VCoordinate from, VCoordinate to);

    public List<VDrawableShape> getDrawableShapes();

    public  void mouseRelease(VCoordinate coordinate);
}
