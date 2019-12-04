package com.virtutuile.domaine.entities;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.domaine.entities.surfaces.Surface;
import com.virtutuile.domaine.entities.surfaces.Tile;
import com.virtutuile.domaine.entities.tools.PolygonTransformer;
import com.virtutuile.shared.Pair;
import com.virtutuile.shared.UnorderedMap;
import com.virtutuile.shared.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.UUID;

public class Meta {

    private UnorderedMap<UUID, Surface> surfaces;
    private UnorderedMap<String, Tile> typeOfTiles;

    private EditionAction doing;

    private Surface selectedSurface;
    private Surface hoveredSurface;
    private Tile hoveredTile;
    private boolean isSelectedSurfaceCanBeResized;
    private boolean shouldDisplayCuttedTiles = false;

    private Point2D clicked;
    private Point2D hover;
    private boolean mousePressed;
    private boolean isGridActivated;
    private Dimension canvasSize = new Dimension();

    private double zoomFactor = Constants.NORMAL_ZOOM;
    private Point2D.Double canvasPosition = new Point2D.Double();

    private Double gridSize = 100d;

    public Meta() {
        selectedSurface = null;
        hoveredSurface = null;
        hoveredTile = null;
        surfaces = new UnorderedMap<>();
        doing = EditionAction.Idle;
        clicked = null;
        hover = null;
        mousePressed = false;
        isGridActivated = false;
        typeOfTiles = new UnorderedMap<>();

        createNewTile(20, 10, Constants.DEFAULT_SHAPE_FILL_COLOR, "Small", false, 10);
        createNewTile(40, 20, Constants.DEFAULT_SHAPE_FILL_COLOR, "Medium", false, 10);
        createNewTile(80, 40, Constants.DEFAULT_SHAPE_FILL_COLOR, "Large", false, 10);

    }

    public boolean createNewTile(double width, double height, Color color, String name, boolean deletable, int packageSize) {
        if (typeOfTiles.containsKey(name)) {
            return false;
        }
        if (color == null) {
            color = Constants.DEFAULT_SHAPE_FILL_COLOR;
        }
        typeOfTiles.put(name, new Tile(width, height, color, name, deletable, packageSize));
        return true;
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

    public Point2D pointToPoints2D(Point point) {
        return new Vector2D(point).add(canvasPosition).multiply(zoomFactor).toPoint2D();
    }

    public Point point2DToPoint(Point2D coordinates) {
        return new Vector2D(coordinates).divide(zoomFactor).subtract(canvasPosition).toPoint();
    }

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
        zoom = zoom * -1;
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

    public Double[] getSelectedSurfaceProperties() {
        if (selectedSurface != null) {
            Double[] dimensions = new Double[4];
            dimensions[0] = selectedSurface.getBounds().width;
            dimensions[1] = selectedSurface.getBounds().height;
            dimensions[2] = selectedSurface.getBounds().x;
            dimensions[3] = selectedSurface.getBounds().y;
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

    public Double getGridSize() {
        return gridSize;
    }

    public void setGridSize(Double gridSize) {
        this.gridSize = gridSize;
    }

    public Double[] getHoveredSurfaceDimension() {
        if (hoveredSurface != null) {
            Double[] dim = new Double[2];
            dim[0] = hoveredSurface.getBounds().width;
            dim[1] = hoveredSurface.getBounds().height;
            return dim;
        }
        return null;
    }

    public void setSelectedSurfaceWidth(double value) {
        if (selectedSurface != null) {
            selectedSurface.setWidth(value);
        }
    }

    public void setSelectedSurfaceHeight(double value) {
        if (selectedSurface != null) {
            selectedSurface.setHeight(value);
        }
    }

    public void recalcPattern() {
        if (selectedSurface != null
                && selectedSurface.getPatternGroup() != null) {
            selectedSurface.getPatternGroup().recalcPattern(selectedSurface);
        }
    }

    public void setGroutColor(Color color) {
        if (selectedSurface != null
                && selectedSurface.getGrout() != null) {
            selectedSurface.getGrout().setColor(color);
            if (selectedSurface.getPatternGroup() != null) {
                selectedSurface.setFillColor(color);
            }
        }
    }

    public void setGroutThickness(String value) {
        if (selectedSurface != null
                && selectedSurface.getGrout() != null) {
            selectedSurface.getGrout().setThickness(Double.parseDouble(value));
            if (selectedSurface.getPatternGroup() != null) {
                selectedSurface.getPatternGroup().recalcPattern(selectedSurface);
            }
        }
    }

    public Double[] getTileDimension(String type) {
        Double[] dimensions = new Double[2];
        dimensions[0] = typeOfTiles.get(type).getBounds().getWidth();
        dimensions[1] = typeOfTiles.get(type).getBounds().getHeight();
        return dimensions;
    }

    public void setWidthForTile(String value, String name) {
        typeOfTiles.get(name).setWidth(Double.parseDouble(value));
        surfaces.forEach((key, surface) -> {
            if (surface.getPatternGroup() != null
                    && surface.getTypeOfTile() != null
                    && surface.getTypeOfTile().getName().equals(name)) {
                surface.getPatternGroup().recalcPattern(surface);
            }
        });
    }

    public void setHeightForTile(String value, String name) {
        typeOfTiles.get(name).setHeight(Double.parseDouble(value));
        surfaces.forEach((key, surface) -> {
            if (surface.getPatternGroup() != null
                    && surface.getTypeOfTile() != null
                    && surface.getTypeOfTile().getName().equals(name)) {
                surface.getPatternGroup().recalcPattern(surface);
            }
        });
    }

    public void renameTile(String newName, String oldName) {
        /*System.out.println("old name : " + oldName + ", new name: " + newName);
        typeOfTiles.get(oldName).setName(newName);
        Tile tmp = typeOfTiles.get(oldName).copy();
        typeOfTiles.remove(oldName);
        typeOfTiles.put(newName, tmp);*/
    }

    public String[] getTypeOfTiles() {
        String[] types = new String[typeOfTiles.size()];
        final int[] i = {0};
        typeOfTiles.forEach((key, value) -> {
            types[i[0]] = key;
            i[0]++;
        });
        return types;
    }

    public void setTypeOfTileOnSurface(String typeOfTile) {
        if (selectedSurface != null) {
            typeOfTiles.forEach((name, tile) -> {
                if (name.equals(typeOfTile)) {
                    selectedSurface.setTypeOfTile(tile);
                    if (selectedSurface.getPatternGroup() != null) {
                        selectedSurface.getPatternGroup().changeTileType(selectedSurface, tile);
                    }
                }
            });
        }
    }

    public boolean isSurfaceSelected() {
        if (selectedSurface != null) {
            return true;
        }
        return false;
    }

    public void setTypeOfTileColor(String typeOfTile, Color color) {
        /*System.out.println("tuile: " + typeOfTile + ", color: " + color.toString());*/
        if (typeOfTiles != null && typeOfTiles.containsKey(typeOfTile)) {
            typeOfTiles.get(typeOfTile).setFillColor(color);
            typeOfTiles.get(typeOfTile).setColor(color);
        }

        assert typeOfTiles != null;
        typeOfTiles.forEach((name, tile) -> {
            if (name.equals(typeOfTile)) {
                surfaces.forEach((surfaceName, surface) -> {
                    if (surface.getPatternGroup() != null) {
                        surface.getPatternGroup().recalcPattern(surface);
                    }
                });
            }
        });
    }

    private Surface transformToOneSurface(Path2D.Double[] polygons) {
        Surface[] surfaces = new Surface[polygons.length];
        Surface returnedSurface = null;

        for (int i = 0; i < polygons.length; i++) {
            surfaces[i] = new Surface(polygons[i], false);
        }

        for (Surface surface : surfaces) {
            for (Surface surfaceContain : surfaces) {
                if (surface != surfaceContain) {
                    if (surface.getId() != surfaceContain.getId()) {
                        if (surface.contains(surfaceContain)) {
                            surfaceContain.setHole(true);
                        }
                    }
                }
            }
        }

        for (Surface surface : surfaces) {
            if (!surface.isHole()) {
                returnedSurface = surface;
            }
        }

        for (Surface surface : surfaces) {
            if (surface != returnedSurface) {
                assert returnedSurface != null;
                returnedSurface.addPath(surface.getVertices());
            }
        }

        return returnedSurface;
    }

    public void mergeSurfaces() {
        boolean merged = false;
        Surface oldSelectedSurface = selectedSurface;
        Surface secondSurface = null;
        Surface mergedSurface = null;

        if (selectedSurface != null) {
            Iterator<Pair<UUID, Surface>> iterator = surfaces.iterator();
            do {
                Pair<UUID, Surface> pair = iterator.next();
                if (selectedSurface.getId() != pair.getKey()) {
                    Point2D[] secondSurfaceVertices = pair.getValue().getVertices();
                    for (Point2D testedSurfaceVertex : secondSurfaceVertices) {
                        if (selectedSurface.containsOrIntersect(testedSurfaceVertex)) {
                            Path2D.Double[] polygons = PolygonTransformer.merge(selectedSurface.getPolygon(), pair.getValue().getPolygon());
                            if (polygons.length > 1) {
                                mergedSurface = transformToOneSurface(polygons); //la
                            } else {
                                mergedSurface = new Surface(polygons[0], false);
                            }
                            surfaces.put(mergedSurface.getId(), mergedSurface);
                            merged = true;
                            secondSurface = pair.getValue();
                            break;
                        }
                    }
                    if (merged) {
                        if (oldSelectedSurface.getPatternGroup() != null) {
                            mergedSurface.setTypeOfTile(oldSelectedSurface.getTypeOfTile());
                            mergedSurface.getGrout().setThickness(oldSelectedSurface.getGrout().getThickness());
                            mergedSurface.getGrout().setColor(oldSelectedSurface.getGrout().getColor());
                            mergedSurface.applyPattern(oldSelectedSurface.getPatternGroup().getPattern().getName(), getDefaultTile());
                        } else if (secondSurface.getPatternGroup() != null) {
                            mergedSurface.setTypeOfTile(secondSurface.getTypeOfTile());
                            mergedSurface.getGrout().setThickness(secondSurface.getGrout().getThickness());
                            mergedSurface.getGrout().setColor(secondSurface.getGrout().getColor());
                            mergedSurface.applyPattern(secondSurface.getPatternGroup().getPattern().getName(), getDefaultTile());
                        }
                        break;
                    }
                }
            } while(iterator.hasNext());
            if (merged) {
                selectedSurface = null;
                surfaces.remove(oldSelectedSurface.getId());
                surfaces.remove(secondSurface.getId());
            }
        }
    }


    public Point2D updatePosToMagnetic(Point point) {
        Point2D.Double mousePosCM = (Point2D.Double) pointToPoints2D(point);
        Point2D.Double screen = (Point2D.Double) updateCoordsToMagnetic(canvasPosition);

        hover = updateCoordsToMagnetic((Point2D.Double) hover);
        hover = new Point2D.Double(hover.getX() + canvasPosition.x - screen.x, hover.getY() + canvasPosition.y - screen.y);

        Point2D.Double ret = (Point2D.Double) updateCoordsToMagnetic(mousePosCM);
        ret.x += canvasPosition.x - screen.x;
        ret.y += canvasPosition.y - screen.y;

        return ret;
    }

    private Point2D updateCoordsToMagnetic(Point2D.Double point) {
        if (point.x % gridSize <= gridSize / 2) {
            point.x -= point.x % gridSize;
        } else {
            point.x += gridSize - point.x % gridSize;
        }

        if (point.y % gridSize <= gridSize / 2) {
            point.y -= point.y % gridSize;
        } else {
            point.y += gridSize - point.y % gridSize;
        }

        return point;
    }

    public Surface updateSurfacePosToMagneticPos() {
        Surface surf = selectedSurface;

        Rectangle2D.Double bounds = surf.getBounds();

        Point2D newBounds = updateCoordsToMagnetic(new Point2D.Double(bounds.x, bounds.y));

        selectedSurface.move(new Point2D.Double(bounds.x, bounds.y), newBounds);
        return surf;
    }

    public void setPatternStartPosition(String name) {
        if (selectedSurface != null
                && selectedSurface.getPatternGroup() != null) {
            if (name.equals("Center")) {
                selectedSurface.getPatternGroup().setCentered(true);
            } else {
                selectedSurface.getPatternGroup().setCentered(false);
            }
            selectedSurface.getPatternGroup().recalcPattern(selectedSurface);
        }
    }

    public void setSelectedSurfaceLongitude(Double longitude) {
        if (selectedSurface != null) {
            Rectangle2D.Double b = selectedSurface.getBounds();
            selectedSurface.move(new Point2D.Double(b.x, b.y), new Point2D.Double(longitude, b.y));
        }
    }

    public void setSelectedSurfaceLatitude(Double latitude) {
        if (selectedSurface != null) {
            Rectangle2D.Double b = selectedSurface.getBounds();
            selectedSurface.move(new Point2D.Double(b.x, b.y), new Point2D.Double(b.x, latitude));
        }
    }

    public boolean deleteTile(String selectedTile) {
        if (typeOfTiles.containsKey(selectedTile)
                && typeOfTiles.get(selectedTile).isDeletable()) {
            typeOfTiles.remove(selectedTile);
            return true;
        }
        return false;
    }

    public Integer[] getSurfaceTileProperties() {
        return getSurfaceTileProperties(selectedSurface);
    }

    public int[] getAllSurfaceTileProperties() {
        int[] result = new int[]{0, 0};

        if (surfaces.size() != 0) {
            Iterator<Pair<UUID, Surface>> iterator = surfaces.iterator();
            for (Pair<UUID, Surface> pair = iterator.next(); iterator.hasNext(); pair = iterator.next()) {
                Integer[] resCase = getSurfaceTileProperties(pair.getValue());
                if (resCase != null) {
                    result[0] += resCase[0];
                    result[1] += resCase[1];
                }
            }
        }
        return result;
    }

    private Integer[] getSurfaceTileProperties(Surface surface) {
        Integer[] result = new Integer[2];
        if (surface != null
                && surface.getPatternGroup() != null) {
            result[0] = surface.getPatternGroup().getTiles().size();
            result[1] = surface.getPatternGroup().getCuttedTiles();
        }
        return result;
    }

    public Integer getUsedPackageOnSurface() {
        if (selectedSurface != null
                && selectedSurface.getPatternGroup() != null
                && selectedSurface.getTypeOfTile() != null) {
            double resDouble =  (double)selectedSurface.getPatternGroup().getTiles().size() / (double)selectedSurface.getTypeOfTile().getPackageSize();
            return (int) Math.ceil(resDouble);
        }
        return 0;
    }

    public Integer getUsedPackageFor(String tileType) {
        int res = 0;
        double resDouble = 0;

        /*if (surfaces.size() != 0) {
            Iterator<Pair<UUID, Surface>> iterator = surfaces.iterator();
            for (Pair<UUID, Surface> pair = iterator.next(); iterator.hasNext(); pair = iterator.next()) {
                if (pair.getValue().getTypeOfTile().getName().equals(tileType)
                        && pair.getValue().getPatternGroup() != null) {
                    resDouble += (double)pair.getValue().getPatternGroup().getTiles().size() / (double)pair.getValue().getTypeOfTile().getPackageSize();
                }
            }
            res = (int) Math.ceil(resDouble);
        }
        return res;*/
        if (surfaces != null
                && surfaces.size() != 0
                && typeOfTiles.containsKey(tileType)) {
            Iterator<Pair<UUID, Surface>> iterator = surfaces.iterator();
            do {
                Pair<UUID, Surface> pair = iterator.next();
                if (pair.getValue().getTypeOfTile() != null && pair.getValue().getTypeOfTile().getName().equals(tileType)
                        && pair.getValue().getPatternGroup() != null) {
                    resDouble += (double) pair.getValue().getPatternGroup().getTiles().size() / (double) pair.getValue().getTypeOfTile().getPackageSize();
                }
            } while (iterator.hasNext());
            res = (int) Math.ceil(resDouble);
        }
        return res;
    }

    public Integer[] getTotalTileFor(String tileName) {
        //getSurfaceTileProperties

        Integer[] result = new Integer[]{0, 0};

        if (surfaces != null
                && surfaces.size() != 0
                && typeOfTiles.containsKey(tileName)) {
            Iterator<Pair<UUID, Surface>> iterator = surfaces.iterator();
            do {
                Pair<UUID, Surface> pair = iterator.next();
                if (pair.getValue().getTypeOfTile() != null && pair.getValue().getTypeOfTile().getName().equals(tileName)
                        && pair.getValue().getPatternGroup() != null) {
                    result[0] += pair.getValue().getPatternGroup().getTiles().size();
                    result[1] += pair.getValue().getPatternGroup().getCuttedTiles();
                }
            } while (iterator.hasNext());
        }
        return result;
    }

    public boolean displayCuttedTiles() {
        return shouldDisplayCuttedTiles;
    }

    public void displayCuttedTiles(boolean value) {
        shouldDisplayCuttedTiles = value;
    }

    public void deleteThisTile(String tileName) {
        if (surfaces != null
                && surfaces.size() != 0) {
            Iterator<Pair<UUID, Surface>> iterator = surfaces.iterator();
            do {
                Pair<UUID, Surface> pair = iterator.next();
                if (pair.getValue().getTypeOfTile() != null && pair.getValue().getTypeOfTile().getName().equals(tileName)) {
                    pair.getValue().setPatternGroup(null);
                    pair.getValue().setTypeOfTile(getDefaultTile());
                    pair.getValue().setFillColor(pair.getValue().getFillColor());
                }
            } while (iterator.hasNext());
        }
    }

    public Tile getDefaultTile() {
        if (typeOfTiles.size() != 0) {
            Iterator<Pair<String, Tile>> iterator = typeOfTiles.iterator();
            for (Pair<String, Tile> pair = iterator.next(); iterator.hasNext(); pair = iterator.next()) {
                return pair.getValue();
            }
        }
        return null;
    }

    public int getPackageSizeFor(String tileName) {
        if (typeOfTiles.get(tileName) != null) {
            return typeOfTiles.get(tileName).getPackageSize();
        }
        return 0;
    }

    public void setPackageSizeFor(String tileName, int packageSize) {
        if (typeOfTiles.get(tileName) != null) {
            typeOfTiles.get(tileName).setPackageSize(packageSize);
        }
    }

    public Tile getHoveredTile() {
        return hoveredTile;
    }

    public void setHoveredTile(Tile tile) {
        this.hoveredTile = tile;
    }

    public void makeSurfaceHole() {
        if (selectedSurface != null) {

        }
    }

public enum EditionAction {
    Idle,
    CreatingRectangularSurface,
    CreatingFreeSurface,
}

}
