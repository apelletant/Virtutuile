package com.virtutuile.domaine;

import com.virtutuile.afficheur.panels.BottomToolbar;
import com.virtutuile.domaine.entities.Meta;
import com.virtutuile.domaine.entities.surfaces.Surface;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class Controller {

    private Meta meta;
    private Painter painter;
    private SurfaceEditor surfaceEditor;
    private BottomToolbar bottomToolbar;

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

    public void createProject() {

    }

    public void loadProject() {

    }

    public void saveProject() {

    }

    public void mouseHover(Point point) {
        meta.setHover(meta.pointToPoints2D(point));
        this.surfaceEditor.mouseHover(meta.pointToPoints2D(point));

        if (meta.getHoveredSurface() != null && meta.getHoveredSurface().getBounds() != null) {
            Double[] dim = new Double[2];

            dim[0] = meta.getHoveredSurface().getBounds().width;
            dim[1] = meta.getHoveredSurface().getBounds().height;

            this.bottomToolbar.setHoveredSurfaceDimension(dim);
        }
    }

    public void mouseRelease(Point point) {
        if (meta.isGridActivated()) {
            point = coordToMagneticCoord(point);
        }
        meta.setHover(meta.pointToPoints2D(point));
        this.surfaceEditor.mouseRelease(meta.pointToPoints2D(point));
    }

    public void mouseLClick(Point point) {
        this.surfaceEditor.mouseLClick(meta.pointToPoints2D(point));

        meta.setClicked(meta.pointToPoints2D(point));
        if (meta.isGridActivated()) {
            Surface currentshape = meta.getSelectedSurface();
            if (currentshape == null) {
                /*System.out.println("null");*/
                return;
            }
            Rectangle2D bounds = currentshape.getPolygon().getBounds2D();
            Point oldShapePos = meta.point2DToPoint(new Point2D.Double(bounds.getX(), bounds.getY()));
            point = coordToMagneticCoord(oldShapePos);
            currentshape.getPolygon().moveTo(point.x, point.y);
        }
    }

    public void mouseRClick(Point point) {
        this.surfaceEditor.mouseRClick(meta.pointToPoints2D(point));
    }

    public void mouseDrag(Point point) {
        if (meta.isGridActivated()) {
            point = coordToMagneticCoord(point);
            //System.out.println(point);
        }
        this.surfaceEditor.mouseDrag(meta.pointToPoints2D(point));
        meta.setHover(meta.pointToPoints2D(point));
    }

    public void setDrawRectangularSurface(boolean doing) {
        meta.setDoing(Meta.EditionAction.CreatingRectangularSurface, doing);
    }

    public void keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE || keyEvent.getKeyCode() == KeyEvent.VK_DELETE) {
            surfaceEditor.deleteSelectedShape();
        }

    }

    public void setCurrentShape() {

    }

    public void drawSurface() {

    }

    public void drawGrid() {
        meta.changeGridStatus();
    }

    public void applyPattern(String patternName) {
        surfaceEditor.applyPattern(patternName);
    }

    public void setCanvasSize(Dimension size) {
        meta.setCanvasSize(size);
    }

    public Dimension getCanvasSize() {
        return meta.getCanvasSize();
    };

    private Point coordToMagneticCoord(Point oldCoord) {
        Point newCoord = new Point();

        double zoom = meta.getZoomFactor();

        double y = oldCoord.getY();
        double x = oldCoord.getX();

        if (oldCoord.getY() % (zoom /4) <= 12) {
            newCoord.y = (int)(y - (y % (zoom /4)));
        } else {
            newCoord.y = (int)(y + ( (zoom / 4) - (y % (zoom /4))));
        }

        if (x % (zoom /4) <= 12) {
            newCoord.x = (int)(x - (x % (zoom /4)));
        } else {
            newCoord.x = (int)(x + ( (zoom / 4) - (x % (zoom /4))));
        }

        return newCoord;
    }

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

//    public Rectangle2D getHoveredSurfaceBounds() {
//        return meta.getHoveredSurface().getPolygon().getBounds2D();
//    }

    public int getGridSize() {
        return meta.getGridSize();
    }

    public void setGridSize(int gridSize) {
        meta.setGridSize(gridSize);
    }

    public Double[] getHoveredSurfaceDimesions() {return meta.getHoveredSurfaceDimesions(); }

    public void setBottomToolbar(BottomToolbar bottomToolbar) {
        this.bottomToolbar = bottomToolbar;
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
}
