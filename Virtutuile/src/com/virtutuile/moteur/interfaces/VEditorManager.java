package com.virtutuile.moteur.interfaces;

import com.virtutuile.systeme.components.VDrawableShape;
import com.virtutuile.systeme.units.VCoordinates;

import java.util.List;

public interface VEditorManager {

    public void mouseHover(VCoordinates coordinates);

    public void mouseLClick(VCoordinates coordinates);

    public void mouseRClick(VCoordinates coordinates);

    public List<VDrawableShape> getPrimitiveShaoes();
}
