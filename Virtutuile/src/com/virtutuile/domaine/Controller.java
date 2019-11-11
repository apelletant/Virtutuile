package com.virtutuile.domaine;

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

    public void mouseHover(Point point){
       this.surfaceEditor.mouseHover(point);
    }

    public void mouseRelease(Point point) {
        if (meta.isGridActivated()) {
            point = coordToMagneticCoord(point);
        }
        this.surfaceEditor.mouseRelease(point);
    }

    public void mouseLClick(Point point) {
        this.surfaceEditor.mouseLClick(point);

        if (meta.isGridActivated()) {
            Surface currentshape = meta.getSelectedSurface();
            if (currentshape == null) {
                System.out.println("null");
                return;
            }
            Rectangle2D bounds = currentshape.getPolygon().getBounds2D();
            Point oldShapePos = Constants.point2DToPoint(new Point2D.Double(bounds.getX(), bounds.getY()));
            point = coordToMagneticCoord(oldShapePos);
            currentshape.getPolygon().moveTo(point.x, point.y);
            }
    }

    public void mouseRClick(Point point) {
        this.surfaceEditor.mouseRClick(point);
    }

    public void mouseDrag(Point point) {
        if (meta.isGridActivated()) {
            point = coordToMagneticCoord(point);
            //System.out.println(point);
        }
        this.surfaceEditor.mouseDrag(point);
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

//        int zoom = manager.getZoom();
        //TODO replace with real zoom value
        double zoom = 100;

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

        System.out.println(newCoord);
        return newCoord;
    }

//    public Rectangle2D getHoveredSurfaceBounds() {
//        return meta.getHoveredSurface().getPolygon().getBounds2D();
//    }
}
