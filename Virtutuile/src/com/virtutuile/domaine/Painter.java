package com.virtutuile.domaine;

import com.virtutuile.domaine.entities.surfaces.PrimarySurface;
import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.shared.Vector2D;

import java.awt.*;
import java.util.Vector;

public class Painter {

    public static final int GIZ_BOUNDS = 0x0001;
    private Graphics graphics;
    private Graphics2D graphics2D;

    private int gizmos;
    private Dimension size;

    public void paintAll(Vector<Surface> surfaces, Graphics gfx) {
        graphics = gfx;
        graphics2D = (Graphics2D) gfx;

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
        graphics2D.setColor(surface.fillColor());
        fillPolygon(surface.getPolygonFromPath2D());
        graphics2D.setColor(surface.getBorderColor());
        paintPolygon(surface.getPolygonFromPath2D(), surface.getBorderThickness());
        if (surface.isMouseHover() || surface.isSelected()) {
            drawGizmos(surface);
        }
    }

    public void fillPolygon(Polygon polygon) {
        graphics2D.fillPolygon(polygon);
    }

    public void paintPolygon(Polygon polygon, int thickness) {
        graphics2D.setStroke(new BasicStroke(thickness));
        graphics2D.drawPolygon(polygon);
    }

    public void drawGizmos(PrimarySurface surface) {
        if ((getGizmos() & GIZ_BOUNDS) != 0) {
            drawBoundingBox(surface.getPolygon().getBounds());
        }
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

    /**
     * Draw the bounding boxes like following :
     * A--------------------B
     * |                    |
     * |                    |
     * D--------------------C
     * <p>
     * Began
     * |                    |
     * -A--------------------B-
     * |                    |
     * |                    |
     * -D--------------------C-
     * |                    |
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


    public boolean isGizmoActive(int gizmo) {
        return (gizmos & gizmo) != 0;
    }

    public int activeGizmos(int active) {
        gizmos = active;
        return gizmos;
    }

    public int deactiveGizmos(int deactive) {
        gizmos &= (gizmos ^ deactive);
        return gizmos;
    }

    public int getGizmos() {
        return gizmos;
    }

    public Dimension getSize() {
        return size;
    }

    public Painter setSize(Dimension size) {
        this.size = size;
        return this;
    }
}