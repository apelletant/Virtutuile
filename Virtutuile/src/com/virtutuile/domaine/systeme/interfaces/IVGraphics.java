package com.virtutuile.domaine.systeme.interfaces;

import com.virtutuile.domaine.systeme.units.VLine;

import java.awt.*;
import java.util.List;

public interface IVGraphics {
    public void setDrawColo(Color color);
    public void setFillColor(Color color);
    public void drawLine(VLine line);
    public void drawShape(List<VLine> lines);
}
