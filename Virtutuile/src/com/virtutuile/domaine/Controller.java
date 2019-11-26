package com.virtutuile.domaine;

import com.virtutuile.domaine.entities.Meta;
import com.virtutuile.domaine.entities.surfaces.Surface;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Vector;

public class Controller {

    private Meta meta;
    private Painter painter;
    private SurfaceEditor surfaceEditor;

    public Controller() {
        meta = new Meta();
        painter = new Painter(meta);
        surfaceEditor = new SurfaceEditor(meta);
    }

    public void paint(Graphics graphics) {
        Vector vector = new Vector();
        Surface currentCreatingSurface = surfaceEditor.getCurrentCreatingSurface();
        if (currentCreatingSurface != null) {
            vector.add(currentCreatingSurface);
        }
        meta.getSurfaces().toVector().forEach((surface) -> {
            vector.add(surface);
        });
        painter.paintAll(vector, graphics);
    }

    public void mouseHover(Point point) {
        Point2D.Double canvasCursor = (Point2D.Double) meta.pointToPoints2D(point);
        meta.setHover(canvasCursor);
        this.surfaceEditor.mouseHover(canvasCursor);

        if (meta.isGridActivated()) {
          canvasCursor = (Point2D.Double) meta.updatePosToMagnetic(point);
        }
    }

    public void mouseRelease(Point point) {
        Point2D.Double canvasCursor = (Point2D.Double) meta.pointToPoints2D(point);
        if (meta.isGridActivated()) {
            canvasCursor = (Point2D.Double) meta.updatePosToMagnetic(point);
        }

        meta.setHover(canvasCursor);
        this.surfaceEditor.mouseRelease(canvasCursor);
    }

    public void mouseLClick(Point point) {
        Point2D.Double canvasCursor = (Point2D.Double) meta.pointToPoints2D(point);
        if (meta.isGridActivated()) {
            canvasCursor = (Point2D.Double) meta.updatePosToMagnetic(point);
            if (meta.getSelectedSurface() != null) {
                meta.setSelectedSurface(updateSurfacePosToMagneticPos());
                System.out.println(meta.getSelectedSurface().getBounds().x);
                System.out.println(meta.getSelectedSurface().getBounds().y);
            }
        }
        this.surfaceEditor.mouseLClick(canvasCursor);
        meta.setClicked(canvasCursor);
    }

    private Surface updateSurfacePosToMagneticPos() {
        return meta.updateSurfacePosToMagneticPos();
    }

    public void mouseRClick(Point point) {
        this.surfaceEditor.mouseRClick(meta.pointToPoints2D(point));
    }

    public void mouseDrag(Point point) {
        Point2D.Double canvasCursor = (Point2D.Double) meta.pointToPoints2D(point);
        if (meta.isGridActivated())
            canvasCursor = (Point2D.Double) meta.updatePosToMagnetic(point);

        this.surfaceEditor.mouseDrag(canvasCursor);
        meta.setHover(canvasCursor);
    }

    public void setDrawRectangularSurface(boolean doing) {
        meta.setDoing(Meta.EditionAction.CreatingRectangularSurface, doing);
    }

    public void keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE || keyEvent.getKeyCode() == KeyEvent.VK_DELETE) {
            surfaceEditor.deleteSelectedShape();
        }
    }


    public void drawGrid() {
        meta.changeGridStatus();
    }

    public void applyPattern(String patternName) {
        surfaceEditor.applyPattern(patternName, meta.getDefaultTile());
    }

    public void setCanvasSize(Dimension size) {
        meta.setCanvasSize(size);
    }

    public Dimension getCanvasSize() {
        return meta.getCanvasSize();
    };

    public void updateZoom(double preciseWheelRotation, Point point) {
        meta.updateZoom(preciseWheelRotation, point);
    }

    public void setCanvasSize(int width, int height) {
        meta.setCanvasSize(width, height);
    }

    public Double[] getSelectedSurfaceDimensions() {
        return meta.getSelectedSurfaceDimensions();
    }

    public Double getSurfaceThickness() {
        return meta.getSelectedSurfaceGroutThickness();
    }

    public Double getGridSize() {
        return meta.getGridSize();
    }

    public void setGridSize(Double gridSize) {
        meta.setGridSize(gridSize);
    }

    public Double[] getHoveredSurfaceDimension() {return meta.getHoveredSurfaceDimension(); }

    public void setSurfaceWidth(double value) {
        meta.setSelectedSurfaceWidth(value);
    }

    public void setSurfaceHeight(double value) {
        meta.setSelectedSurfaceHeight(value);
    }

    public void recalcPattern() {
        meta.recalcPattern();
    }

    public void setGroutColor(Color color) {
        meta.setGroutColor(color);
    }

    public void setGroutThickness(String value) {
        meta.setGroutThickness(value);
    }

    public Double[] getTileDimension(String type) {
        return meta.getTileDimension(type);
    }

    public void setWidthForTile(String value, String name) {
        meta.setWidthForTile(value, name);
    }

    public void setHeightForTile(String value, String name) {
        meta.setHeightForTile(value, name);
    }

    public void renameTile(String newName, String oldName) {
        meta.renameTile(newName, oldName);
    }

    public String[] getTypeOfTiles() {
        return meta.getTypeOfTiles();
    }

    public boolean isSurfaceSelected() {
        return meta.isSurfaceSelected();
    }

    public void setTypeOfTileOnSurface(String typeOfTileName) {
        meta.setTypeOfTileOnSurface(typeOfTileName);
    }

    public void setTypeOfTileColor(String selectedTile, Color color) {
        meta.setTypeOfTileColor(selectedTile, color);
    }

    public Double getZoomFactor() {
        return meta.getZoomFactor();
    }

    public void mergeSurfaces() {
        meta.mergeSurfaces();
    }

    public void setPatternStartPosition(String name) {
        meta.setPatternStartPosition(name);
    }
}
