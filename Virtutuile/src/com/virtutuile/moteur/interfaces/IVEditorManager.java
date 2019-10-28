package com.virtutuile.moteur.interfaces;

import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.units.VCoordinate;
import com.virtutuile.systeme.units.VProperties;

import java.util.List;

public interface IVEditorManager {

    public void mouseHover(VCoordinate coordinates);

    public void mouseLClick(VProperties properties);

    public void mouseRClick(VCoordinate coordinates);

    public void mouseDrag(VCoordinate from, VCoordinate to);

    public List<VDrawableShape> getDrawableShapes();

    public  void mouseRelease(VCoordinate coordinate);
}
