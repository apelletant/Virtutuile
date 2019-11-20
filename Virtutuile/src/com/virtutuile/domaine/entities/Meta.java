package com.virtutuile.domaine.entities;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.domaine.entities.surfaces.FreeSurface;
import com.virtutuile.domaine.entities.surfaces.RectangularSurface;
import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.domaine.entities.surfaces.Tile;
import com.virtutuile.shared.UnorderedMap;
import com.virtutuile.shared.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.UUID;

public class Meta {

    private UnorderedMap<UUID, Surface> surfaces;
    private UnorderedMap<String, Tile> typeOfTiles;

    private EditionAction doing;

    private Surface selectedSurface;
    private Surface hoveredSurface;
    private boolean isSelectedSurfaceCanBeResized;

    private Point2D clicked;
    private Point2D hover;
    private boolean mousePressed;
    private boolean isGridActivated;
    private Dimension canvasSize = new Dimension();

    private double zoomFactor = Constants.NORMAL_ZOOM;
    private Point2D.Double canvasPosition = new Point2D.Double();

    private int gridSize = 10;

    public Meta() {
        selectedSurface = null;
        hoveredSurface = null;
        surfaces = new UnorderedMap<>();
        doing = EditionAction.Idle;
        clicked = null;
        hover = null;
        mousePressed = false;
        isGridActivated = false;
        typeOfTiles = new UnorderedMap<>();

        createNewTile(20, 20, Constants.DEFAULT_SHAPE_FILL_COLOR, "Square", false);
        createNewTile(20, 10, Constants.DEFAULT_SHAPE_FILL_COLOR, "Small", false);
        createNewTile(40, 20, Constants.DEFAULT_SHAPE_FILL_COLOR, "Medium", false);
        createNewTile(80, 40, Constants.DEFAULT_SHAPE_FILL_COLOR, "Large", false);
        createNewTile(140, 80, Constants.DEFAULT_SHAPE_FILL_COLOR, "Extra Large", false);

        Surface surface = new RectangularSurface(new Rectangle2D.Double(30, 30, 70, 70), false);
        Path2D.Double polygon = new Path2D.Double();

        polygon.moveTo(100, 100);
        polygon.lineTo(300, 100);
        polygon.lineTo(300, 300);
        polygon.lineTo(400, 400);
        polygon.lineTo(400, 500);
        polygon.lineTo(200, 500);
        polygon.lineTo(200, 350);
        polygon.lineTo(100, 350);
        polygon.closePath();
        surface = new FreeSurface(polygon);
        surfaces.put(surface.getId(), surface);

    }

    public void createNewTile(double width, double height, Color color, String name, boolean deletable) {
        typeOfTiles.put(name, new Tile(width, height, color, name, deletable));
    }

    public Surface getSelectedSurface() {
        return selectedSurface;
    }

    public void setSelectedSurface(Surface selectedSurface) {
        this.selectedSurface = selectedSurface;
    }

    public UnorderedMap<UUID, Surface> getSurfaces() {
        return surfaces;
    }

    public void setSurfaces(UnorderedMap<UUID, Surface> surfaces) {
        this.surfaces = surfaces;
    }

    public EditionAction getDoing() {
        return doing;
    }

    public void setDoing(EditionAction doing, boolean isDoing) {
        if (isDoing) {
            this.doing = doing;
        } else {
            this.doing = EditionAction.Idle;
        }
    }

    public Point2D getClicked() {
        return clicked;
    }

    public void setClicked(Point2D clicked) {
        this.clicked = clicked;
    }

    public Point2D getHover() {
        return hover;
    }

    public void setHover(Point2D hover) {
        this.hover = hover;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Surface getHoveredSurface() {
        return hoveredSurface;
    }

    public void setHoveredSurface(Surface hoveredSurface) {
        this.hoveredSurface = hoveredSurface;
    }

    public boolean isGridActivated() {
        return isGridActivated;
    }

    public void changeGridStatus() {
        isGridActivated = !isGridActivated;
    }

    public void setCanvasSize(int width, int height) {
        canvasSize.width = width;
        canvasSize.height = height;
    }

    public void setCanvasSize(Dimension size) {
        canvasSize = size;
    }

    public Dimension getCanvasSize() {
        return canvasSize;
    }

    public boolean isSelectedSurfaceCanBeResized() {
        return isSelectedSurfaceCanBeResized;
    }

    public void setSelectedSurfaceCanBeResized(boolean selectedSurfaceCanBeResized) {
        isSelectedSurfaceCanBeResized = selectedSurfaceCanBeResized;
    }

    //TODO: Développer la méthode
    public Point2D pointToPoints2D(Point point) {
        Vector2D ret = new Vector2D(point.x, point.y);
        return ret.add(canvasPosition).multiply(zoomFactor).toPoint2D();
    }

    public Point point2DToPoint(Point2D coordinates) {
        return new Vector2D(coordinates).divide(zoomFactor).subtract(canvasPosition).toPoint();
    }

    //TODO: Développer la méthode
    public Point[] points2DToPoints(Point2D[] point2D) {
        Point[] points = new Point[point2D.length];

        for (int i = 0; i < point2D.length; ++i) {
            points[i] = point2DToPoint(point2D[i]);
        }
        return points;
    }

    public int[][] points2DToRawPoints(Point2D[] point2D) {
        int[][] ret = new int[2][point2D.length];

        for (int i = 0; i < point2D.length; ++i) {
            Point p = point2DToPoint(point2D[i]);
            ret[0][i] = p.x;
            ret[1][i] = p.y;
        }
        return ret;
    }

    public double pixelsToCentimeters(int pixels) {
        return (double) pixels * zoomFactor;
    }

    public int centimetersToPixels(double centimeters) {
        return (int) (centimeters / zoomFactor);
    }

    public Point2D.Double getCanvasPosition() {
        return canvasPosition;
    }

    public Meta setCanvasPosition(double posX, double posY) {
        return setCanvasPosition(new Point2D.Double(posX, posY));
    }

    public Meta setCanvasPosition(Point2D.Double canvasPosition) {
        this.canvasPosition = canvasPosition;
        return this;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public Meta setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
        return this;
    }

    public void updateZoom(double zoom, Point cursor) {
        double oldWidth = pixelsToCentimeters(getCanvasSize().width);
        double oldHeight = pixelsToCentimeters(getCanvasSize().height);
        double newCanvasSize = pixelsToCentimeters((int) ((double) getCanvasSize().width - (zoom * Constants.WHEEL_TICK_RATIO)));
        double zoomFactor = (newCanvasSize / oldWidth);
        Point2D.Double pos = getCanvasPosition();
        if (zoomFactor == 1) {
            return;
        }

        Path2D.Double rect = new Path2D.Double();

        rect.moveTo(pos.x, pos.y);
        rect.lineTo(pos.x + oldWidth, pos.y);
        rect.lineTo(pos.x + oldWidth, pos.y + oldHeight);
        rect.lineTo(pos.x, pos.y + oldHeight);
        rect.closePath();

        cursor.x -= getCanvasSize().width / 4;
        cursor.y -= getCanvasSize().height / 4;

        AffineTransform at = new AffineTransform();
        at.translate(cursor.x, cursor.y);
        at.scale(zoomFactor, zoomFactor);
        at.translate(-cursor.x, -cursor.y);
        rect.transform(at);

        setCanvasPosition(rect.getBounds2D().getX(), rect.getBounds2D().getY());
        setZoomFactor(getZoomFactor() * zoomFactor);
    }

    public Double[] getSelectedSurfaceDimensions() {
        if (selectedSurface != null) {
            Double[] dimensions = new Double[2];
            dimensions[0] = selectedSurface.getBounds().width;
            dimensions[1] = selectedSurface.getBounds().height;
            return dimensions;
        }
        return null;
    }

    public Double getSelectedSurfaceGroutThickness() {
        if (selectedSurface != null) {
            return selectedSurface.getGrout().getThickness();
        } else {
            return null;
        }
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public Double[] getHoveredSurfaceDimesions() {
        if (hoveredSurface != null) {
            Double[] dim = new Double[2];
            dim[0] = hoveredSurface.getBounds().width;
            dim[1] = hoveredSurface.getBounds().height;
            return dim;
        }
        return null;
    }

    public enum EditionAction {
        Idle,
        CreatingRectangularSurface,
        CreatingFreeSurface,
    }
}
