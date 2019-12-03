package com.virtutuile.domaine;

import com.virtutuile.domaine.entities.Meta;
import com.virtutuile.domaine.entities.surfaces.PrimarySurface;
import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.domaine.entities.surfaces.Tile;
import com.virtutuile.domaine.entities.tools.ColorTransformer;
import com.virtutuile.domaine.entities.tools.PolygonTransformer;
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

        if (meta.getHoveredSurface() != null) {
            drawHoveredTile();
        }

        surfaces.forEach((surface) -> {
            Polygon poly = paintSurface(surface);
            if (surface.getPatternGroup() != null
                    && surface.getPatternGroup().getTiles().size() > 0) {
                paintTiles(surface.getPatternGroup().getTiles());
            }
            paintSurfaceGizmos(surface, poly);
        });
    }

    public void paintTiles(Vector<Tile> tiles) {
        for (Tile tile : tiles) {
            int[][] tilePts = meta.points2DToRawPoints(tile.getVertices());
            Polygon poly = new Polygon(tilePts[0], tilePts[1], tilePts[0].length);
            if (meta.displayCuttedTiles() && tile.isCutted())
                paintPolygon(poly, ColorTransformer.Invert(tile.getFillColor()), tile.getBorderColor());
            else
                paintPolygon(poly, tile.getFillColor(), tile.getBorderColor());
        }
    }

    public Polygon paintSurface(PrimarySurface surface) {
        if (surface == null)
            return null;

        int[][] polygonPoints = meta.points2DToRawPoints(surface.getVertices());
        Polygon poly = new Polygon(polygonPoints[0], polygonPoints[1], polygonPoints[0].length);

        paintPolygon(poly, surface.getFillColor(), surface.getBorderColor());
        return poly;
    }

    public void paintSurfaceGizmos(PrimarySurface surface, Polygon poly) {
        if (surface.isMouseHover() || surface.isSelected()) {
            drawBoundingBox(poly.getBounds());
            drawHandles(poly.xpoints, poly.ypoints);
        }
    }

    public void paintPolygon(Polygon poly, Color fill, Color stroke) {
        graphics2D.setColor(fill);
        graphics2D.fillPolygon(poly);
        graphics2D.setColor(stroke);
        graphics2D.setStroke(new BasicStroke(com.virtutuile.afficheur.Constants.DEFAULT_SHAPE_BORDER_THICKNESS));
        graphics2D.drawPolygon(poly);
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
        Color col = new Color(0, 0, 0);
        graphics2D.setColor(col);
        graphics2D.setStroke(new BasicStroke(1));

        Point2D.Double canvasPos = meta.getCanvasPosition();
        Point canvasPosInt = new Point(meta.centimetersToPixels(canvasPos.x), meta.centimetersToPixels(canvasPos.y));

        Dimension canvasDim = meta.getCanvasSize();

        canvasPosInt.x = canvasPosInt.x - (canvasPosInt.x % canvasDim.width);
        canvasPosInt.y = canvasPosInt.y - (canvasPosInt.y % canvasDim.height);

        //TODO ne plus utiliser de pixel pour le calcul car on doit pouvoir etre entre o et 1
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


    private void drawHoveredTile() {
        Tile tile = meta.getHoveredTile();

        Color color = Constants.Gizmos.TileHOverDisplayBox.BORDER_COLOR;
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(10));

        if (tile != null) {
            graphics2D.drawRect(Constants.Gizmos.TileHOverDisplayBox.ORIGIN_POS_X,
                                Constants.Gizmos.TileHOverDisplayBox.ORIGIN_POS_Y,
                                Constants.Gizmos.TileHOverDisplayBox.WIDTH,
                                Constants.Gizmos.TileHOverDisplayBox.HEIGHT);
            graphics2D.setColor(Constants.Gizmos.TileHOverDisplayBox.BACKGROUND_COLOR);
            graphics2D.fillRect(Constants.Gizmos.TileHOverDisplayBox.ORIGIN_POS_X,
                                Constants.Gizmos.TileHOverDisplayBox.ORIGIN_POS_Y,
                                Constants.Gizmos.TileHOverDisplayBox.WIDTH,
                                Constants.Gizmos.TileHOverDisplayBox.HEIGHT);

            Tile hoveredShadow = movePolygonToHoveredDIsplayBox(true, new Tile(tile));

            Point2D[] tileVertices = hoveredShadow.getVertices();
            int[] pointsX = new int[tileVertices.length];
            int[] pointsY = new int[tileVertices.length];

            for (int i = 0; i < tileVertices.length; i++) {
                pointsX[i] = (int)hoveredShadow.getVertices()[i].getX();
                pointsY[i] = (int)hoveredShadow.getVertices()[i].getY();
            }


            graphics2D.setColor(Constants.Gizmos.TileHOverDisplayBox.LINE_COLOR);
            graphics2D.setStroke(new BasicStroke(1));
            graphics2D.drawPolygon(pointsX, pointsY, pointsX.length);

//            for (int i = 0; i < pointsX.length; i++) {
//                System.out.println("points X:");
//                System.out.println(pointsX[i]);
//                System.out.println("points Y:");
//                System.out.println(pointsY[i]);
//            }
        }
    }

    private Tile movePolygonToHoveredDIsplayBox(boolean verticesX, Tile tile) {
        /**
        AffineTransform transform = new AffineTransform();

        transform.translate(0, 0);
        tile.getPolygon().transform(transform);


        int maxWidth = Constants.Gizmos.TileHOverDisplayBox.ORIGIN_POS_X + Constants.Gizmos.TileHOverDisplayBox.WIDTH;
        int maxHeight = Constants.Gizmos.TileHOverDisplayBox.ORIGIN_POS_Y + Constants.Gizmos.TileHOverDisplayBox.HEIGHT;

        Double width = tile.getBounds().width;
        Double height = tile.getBounds().height;

        Double ratio = maxWidth / width;
        Double ratioY = maxHeight / height;

        if (ratio > ratioY) {
            ratio = ratioY;
        }

        System.out.printf("width before = %f\n", width);
        System.out.printf("height before = %f\n", height);

        width = width * ratio;
        height = height * ratio;

        System.out.printf("width after = %f\n", width);
        System.out.printf("height after = %f\n", height);
        transform.scale(width, height);
        transform.translate(Constants.Gizmos.TileHOverDisplayBox.ORIGIN_POS_X + 25, Constants.Gizmos.TileHOverDisplayBox.ORIGIN_POS_Y + 25);
        tile.getPolygon().transform(transform);
        */
        return tile;
    }

/**
    let width, height;

    ratio = maxX / width;
    ratioY = maxY / height;

    if (ratio > ratioY) {
        ratio = ratioY;
    }

    return {
        w: width * ratio,
                h: height * ratio,
    }
*/

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