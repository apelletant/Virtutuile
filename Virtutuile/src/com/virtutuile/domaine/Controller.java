package com.virtutuile.domaine;

import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.domaine.entities.Meta;
import com.virtutuile.domaine.entities.PatternGroup;
import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.shared.Vector2D;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Vector;

public class Controller {

    private Meta meta;
    private Painter painter;
    private SurfaceEditor surfaceEditor;
    private SaveManager saveManager;

    public Controller() {
        meta = new Meta();
        painter = new Painter(meta);
        surfaceEditor = new SurfaceEditor(meta);
        saveManager = new SaveManager(meta);
    }

    public void paint(Graphics graphics) {
        Vector<Surface> vector = new Vector<>();
        Surface currentCreatingSurface = surfaceEditor.getCurrentCreatingSurface();
        if (currentCreatingSurface != null) {
            vector.add(currentCreatingSurface);
        }
        meta.getSurfaces().forEach((key, surface) -> {
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
        meta.setLastEvent(MouseEventKind.MouseRelease);
    }

    public void mouseLClick(Point point) {
        Point2D.Double canvasCursor = (Point2D.Double) meta.pointToPoints2D(point);
        if (meta.isGridActivated()) {
            canvasCursor = (Point2D.Double) meta.updatePosToMagnetic(point);
            if (meta.getSelectedSurface() != null) {
                meta.setSelectedSurface(updateSurfacePosToMagneticPos());
            }
        }
        this.surfaceEditor.mouseLClick(canvasCursor);
        meta.setClicked(canvasCursor);
        meta.setLastEvent(MouseEventKind.MouseLClick);
    }

    private Surface updateSurfacePosToMagneticPos() {
        return meta.updateSurfacePosToMagneticPos();
    }

    public void mouseRClick(Point point) {
        this.surfaceEditor.mouseRClick(meta.pointToPoints2D(point));
        meta.setLastEvent(MouseEventKind.MouseRClick);
    }

    public void mouseDrag(Point point, int button) {
        Point2D.Double canvasCursor = (Point2D.Double) meta.pointToPoints2D(point);
        if (button == 1) {
            if (meta.isGridActivated())
                canvasCursor = (Point2D.Double) meta.updatePosToMagnetic(point);
            this.surfaceEditor.mouseDrag(canvasCursor);
        } else {
            this.surfaceEditor.dragPattern(canvasCursor);
            meta.addToUndo();
        }
        meta.setHover(canvasCursor);
        meta.setLastEvent(MouseEventKind.MouseDrag);
    }

    public void setDrawRectangularSurface(boolean doing) {
        meta.setDoing(Meta.EditionAction.CreatingRectangularSurface, doing);
    }

    public void setDrawFreeSurface(boolean doing) {
        if (!doing)
            this.surfaceEditor.endBuildingSurface();
        meta.setDoing(Meta.EditionAction.CreatingFreeSurface, doing);
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
    }

    public void updateZoom(double preciseWheelRotation, Point point) {
        meta.updateZoom(preciseWheelRotation, point);
    }

    public void setCanvasSize(int width, int height) {
        meta.setCanvasSize(width, height);
    }

    public Double[] getSelectedSurfaceProperties() {
        return meta.getSelectedSurfaceProperties();
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

    public Double[] getHoveredSurfaceDimension() {
        return meta.getHoveredSurfaceDimension();
    }

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

    public void setMinimalCutForTile(String selectedTile, double value) {
        meta.setMinimalCutForTile(selectedTile, value);
    }

    public double getMinimalCutSizeFor(String selectedTile) {
        return meta.getMinimalCutSizeFor(selectedTile);
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
        surfaceEditor.mergeSurfaces();
    }

    public void setPatternStartPosition(String name) {
        meta.setPatternStartPosition(name);
    }

    public void setSurfaceLongitude(Double longitude) {
        meta.setSelectedSurfaceLongitude(longitude);
    }

    public void setSurfaceLatitude(Double latitude) {
        meta.setSelectedSurfaceLatitude(latitude);
    }

    public void setPackageSizeFor(String tileName, int packageSize) {
        meta.setPackageSizeFor(tileName, packageSize);
    }

    public int getPackageSizeFor(String tileName) {
        return meta.getPackageSizeFor(tileName);
    }

    public boolean createNewTile(double width, double height, Color color, String name, boolean deletable, int packageSize) {
        return meta.createNewTile(width, height, color, name, deletable, packageSize);
    }

    public void setHighlightCuttedTiles(boolean highlight) {
        meta.displayCuttedTiles(highlight);
    }

    public boolean deleteTile(String selectedTile) {
        return meta.deleteTile(selectedTile);
    }

    public Integer[] getSurfaceTileProperties() {
        return meta.getSurfaceTileProperties();
    }

    public int[] getAllSurfaceTileProperties() {
        return meta.getAllSurfaceTileProperties();
    }

    public Integer getUsedPackageOnSurface() {
        return meta.getUsedPackageOnSurface();
    }

    public Integer[] getTotalTileFor(String tileName) {
        return meta.getTotalTileFor(tileName);
    }

    public Integer getUsedPackageFor(String tileName) {
        return meta.getUsedPackageFor(tileName);
    }

    public void deleteThisTile(String tileName) {
        meta.deleteThisTile(tileName);
    }

    public void makeSurfaceHole() {
        meta.makeSurfaceHole();
    }

    public boolean setAlignAction(String name) {
        return meta.setAlignAction(name);
    }

    public void setAlignDistance(double distance) {
        surfaceEditor.setAlignDistance(distance);
    }

    public Double getAlignmentDistance() {
        return surfaceEditor.getAlignmentDistance();
    }

    public void setSurfaceColor(Color color) {
        surfaceEditor.setSurfaceColor(color);
    }

    public boolean setStickAction(String name) {
        return meta.setStickAction(name);
    }
    
    public void saveCanvas(String path) {
        saveManager.saveCanvas(path);
    }

    public void loadCanvas(String path) {
        meta.setMeta(saveManager.loadCanvas(path));
    }

    public void moveSelectedPattern(double moveX, double moveY) {

        if (meta.getUnitSetted().equals("Imperial")) {
            moveX = meta.inchToCentimeter(moveX);
            moveY = meta.inchToCentimeter(moveY);
        }

        Surface surface = meta.getSelectedSurface();
        if (surface != null && surface.getPatternGroup() != null) {
            surface.getPatternGroup().setOrigin(moveX, moveY);
            surface.getPatternGroup().recalcPattern(surface);/*TODO*/
        }
    }

    public Vector2D getSelectedSurfacePatternOrigin() {
        Vector2D ret = null;
        if (meta.getSelectedSurface() != null && meta.getSelectedSurface().getPatternGroup() != null) {
            ret = meta.getSelectedSurface().getPatternGroup().getOrigin();
            if (meta.getUnitSetted().equals("Imperial")) {
                ret.x = meta.centimeterToInch(ret.x);
                ret.y = meta.centimeterToInch(ret.y);
            }
        }
        return ret;
    }

    public void stickSurfaces() {
        surfaceEditor.stickSurfaces();
    }

    public void unstickSurface() {
        surfaceEditor.unstickSurfaces();
    }

    public void rotateSurface(double rotationDeg) {
        surfaceEditor.rotateSurface(rotationDeg);
    }

    public Double getSurfaceRotation() {
        return meta.getSurfaceRotation();
    }

    public Double getSurfaceRotationDeg() {
        return meta.getSurfaceRotationDeg();
    }

    public double getSelectedSurfacePatternShift() {
        double shift = 0;
        if (meta.getSelectedSurface() != null && meta.getSelectedSurface().getPatternGroup() != null) {
            shift = meta.getSelectedSurface().getPatternGroup().getShift();
            if (meta.getUnitSetted().equals("Imperial")) {
                shift = meta.centimeterToInch(shift);
            }
        }
        return shift;
    }

    public boolean getSelectedSurfacePatternDirectionShift() {
        if (meta.getSelectedSurface() != null && meta.getSelectedSurface().getPatternGroup() != null)
            return meta.getSelectedSurface().getPatternGroup().getShiftDirection();
        return false;
    }

    public void changeSelectedShiftValue(double shift) {
        Surface surface = meta.getSelectedSurface();

        if (meta.getUnitSetted().equals("Imperial")) {
            shift = meta.inchToCentimeter(shift);
        }

        if (surface != null && surface.getPatternGroup() != null) {
            PatternGroup pg = surface.getPatternGroup();
            pg.setShift(shift);
            pg.recalcPattern(surface);
        }
    }

    public void changeSelectedShiftDirection(boolean shiftDirection) {
        Surface surface = meta.getSelectedSurface();
        if (surface != null && surface.getPatternGroup() != null) {
            PatternGroup pg = surface.getPatternGroup();
            pg.setShiftDirection(shiftDirection);
            pg.recalcPattern(surface);
        }
    }

    public void rotatePattern(double rotation) {
        surfaceEditor.rotatePattern(rotation);
    }

    public double getSelectedSurfacePatternRotation() {
        return meta.getSelectedSurfacePatternRotation();
    }

    public void setUnit(String unit) {
        meta.setUnit(unit);
    }

    public String getUnitSetted() {
        return meta.getUnitSetted();
    }

    public void switchUnit() {
        meta.switchUnit();
    }

    public Double getZoomFactorFront() {
        return meta.getZoomFactorFront();
    }

    public Double getGridSizeFront() {
        return meta.getGridSizeFront();
    }

    public void undo() {
        meta.undo();
    }

    public void redo() {
        meta.redo();
    }

    public void newCanvas() {
        Meta met = new Meta();
        meta.setMeta(met);
    }
}
