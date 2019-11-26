package com.virtutuile.domaine;

import com.virtutuile.domaine.entities.Meta;
import com.virtutuile.domaine.entities.surfaces.PrimarySurface;
import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.shared.Vector2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Vector;

public class Painter {

    private Graphics2D graphics2D;

    private Dimension size;

    private Meta meta;

    public Painter(Meta meta) {
        this.meta = meta;
    }

    public void paintAll(Vector<Surface> surfaces, Graphics gfx) {
        graphics2D = (Graphics2D) gfx;

        if (meta.isGridActivated()) {
            drawMagneticGrid();
        }

        surfaces.forEach((surface) -> {
            paint(surface);
            if (surface.getPatternGroup() != null
                    && surface.getPatternGroup().getTiles().size() > 0) {
                surface.getPatternGroup().getTiles().forEach((tile) -> {
                    paint(tile);
                });
            }
        });
    }

    public void paint(PrimarySurface surface) {
        if (surface == null)
            return;

        int[][] polygonPoints = meta.points2DToRawPoints(surface.getVertices());
        Polygon poly = new Polygon(polygonPoints[0], polygonPoints[1], polygonPoints[0].length);

        graphics2D.setColor(surface.fillColor());
/*        System.out.println("in painter: " + surface.fillColor());*/
        graphics2D.fillPolygon(poly.xpoints, poly.ypoints, poly.npoints);

        graphics2D.setColor(surface.getBorderColor());
        graphics2D.setStroke(new BasicStroke(surface.getBorderThickness()));
        graphics2D.drawPolygon(poly.xpoints, poly.ypoints, poly.npoints);

        if (surface.isMouseHover() || surface.isSelected()) {
            drawBoundingBox(poly.getBounds());
            drawHandles(poly.xpoints, poly.ypoints);
        }
    }

    public void drawGizmos(PrimarySurface surface) {
        drawBoundingBox(surface.getPolygon().getBounds());
        drawHandles(surface.getPolygonFromPath2D().xpoints, surface.getPolygonFromPath2D().ypoints);
    }

    private void drawHandles(int[] xPoints, int[] yPoints) {
        final Dimension size = Constants.Gizmos.Handles.SIZE;

        for (int i = 0; i < xPoints.length; ++i) {
            Point anchor = new Point();

            anchor.x = xPoints[i] - Math.floorDiv(size.width, 2);
            anchor.y = yPoints[i] - Math.floorDiv(size.height, 2);
            graphics2D.setColor(Constants.Gizmos.Handles.BACKGROUND_COLOR);
            graphics2D.fillRect(anchor.x, anchor.y, size.width, size.height);
            graphics2D.setStroke(new BasicStroke(Constants.Gizmos.Handles.BORDER_STROKE));
            graphics2D.setColor(Constants.Gizmos.Handles.BORDER_COLOR);
            graphics2D.drawRect(anchor.x, anchor.y, size.width, size.height);
        }
    }

    private void drawMagneticGrid() {
        if (meta.centimetersToPixels(meta.getGridSize()) < 20) {
            return;
        }

        Color col = new Color(0, 0, 0);
        graphics2D.setColor(col);
        graphics2D.setStroke(new BasicStroke(1));

        Point2D.Double canvasPos = meta.getCanvasPosition();
        Point canvasPosInt = new Point(meta.centimetersToPixels(canvasPos.x), meta.centimetersToPixels(canvasPos.y));

        Dimension canvasDim = meta.getCanvasSize();

        canvasPosInt.x = canvasPosInt.x - (canvasPosInt.x % canvasDim.width);
        canvasPosInt.y = canvasPosInt.y - (canvasPosInt.y % canvasDim.height);

        for (int i = canvasPosInt.x; i <= canvasDim.width; i++) {
            for (int j = canvasPosInt.y; j <= canvasDim.height; j++) {
                if (i % meta.centimetersToPixels(meta.getGridSize())  == 0) {
                    graphics2D.drawLine(i, j, i, canvasDim.width);
                }
                if (j % meta.centimetersToPixels(meta.getGridSize()) == 0) {
                    graphics2D.drawLine(canvasDim.height, j, i, j);
                }
            }
        }
    }

    /**
     * Draw the bounding boxes like following :
     *   A--------------------B
     *   |                    |
     *   |                    |
     *   D--------------------C
     * <p>
     * Began
     *   |                    |
     * --A--------------------B--
     *   |                    |
     *   |                    |
     * --D--------------------C--
     *   |                    |
     *
     * @param box
     */
    private void drawBoundingBox(Rectangle box) {
        float[] dashes = {10f};

        graphics2D.setStroke(new BasicStroke(1.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f, dashes, 0.0f));

        graphics2D.setColor(Constants.Gizmos.BoundingBoxes.BOX_COLOR);
        graphics2D.drawRect(box.x, box.y, box.width, box.height);
        graphics2D.setStroke(new BasicStroke(Constants.Gizmos.BoundingBoxes.STROKE));
        Point corner = new Point(box.x, box.y);
        Point expansion = Vector2D.from(corner).tarnslateDeg(180, Constants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        expansion = Vector2D.from(corner).tarnslateDeg(270, Constants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        corner.x += box.width;
        expansion = Vector2D.from(corner).tarnslateDeg(270, Constants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        expansion = Vector2D.from(corner).tarnslateDeg(0, Constants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        corner.y += box.height;
        expansion = Vector2D.from(corner).tarnslateDeg(0, Constants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        expansion = Vector2D.from(corner).tarnslateDeg(90, Constants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        corner.x -= box.width;
        expansion = Vector2D.from(corner).tarnslateDeg(180, Constants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
        expansion = Vector2D.from(corner).tarnslateDeg(90, Constants.Gizmos.BoundingBoxes.EXPANSION_LENGTH).toPoint();
        graphics2D.drawLine(corner.x, corner.y, expansion.x, expansion.y);
    }

    public Dimension getSize() {
        return size;
    }

    public Painter setSize(Dimension size) {
        this.size = size;
        return this;
    }
}