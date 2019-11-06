package com.virtutuile.systeme.components.pattern;

import com.virtutuile.systeme.components.shape.VShape;
import com.virtutuile.systeme.components.shape.VTile;
import com.virtutuile.systeme.shared.PatternType;
import com.virtutuile.systeme.units.VCoordinate;

import java.util.Vector;

public class VPatternSpec {
    private VPattern _pattern = null;
    private Vector<VTile> _tiles = new Vector<>();
    private float _rotation;
    private VCoordinate _coordinate;

    public VPatternSpec(PatternType patternType, VShape shape) {
        switch (patternType) {
            case Classic:
                this._pattern = new VPatternClassic();
            default:
                break;
        }
        if (this._pattern != null) {
            this.buildPattern(shape);
        }
    }

    private void buildPattern(VShape shape) {
        double[] adjust = this._pattern._adjust;
        double[] origin = new double[]{shape.getBounds().getX(), shape.getBounds().getY()};
        double[] tileSize = {this._pattern.getTiles().get(0).getPolygon().getBounds().width, this._pattern.getTiles().get(0).getPolygon().getBounds().height};
        double y = 0;
        double x = 0;

        while(y < shape.getBounds().getHeight() + shape.getGrout().getThickness()) {
            y = y + shape.getGrout().getThickness();
            while (x < shape.getBounds().getWidth() + shape.getGrout().getThickness()) {
                int i = 0;
                x = x + shape.getGrout().getThickness();
                while (i != this._pattern.getTiles().size()) {
                    VTile tile = this._pattern.getTiles().get(i).copy();
                    double tileX = x + origin[0];
                    double tileY = y + origin[1];

                    if ((tileX + shape.getGrout().getThickness() + tileSize[0]) > origin[0] + shape.getBounds().getWidth()) {
                        double size = (origin[0] + shape.getBounds().getWidth()) - (x + origin[0] + shape.getGrout().getThickness());
                        tile.setWidthForRectTile(size);
                    }

                    if ((tileY + shape.getGrout().getThickness() + tileSize[1]) > origin[1] + shape.getBounds().getHeight()) {
                        double size = (origin[1] + shape.getBounds().getHeight()) - (y + origin[1] + shape.getGrout().getThickness());
                        tile.setHeightForRectTile(size);
                    }
                    tile.setOrigin(new VCoordinate(tileX, tileY));
                    this._tiles.add(tile);
                    i++;
                }
                x = x + tileSize[0] + adjust[0];
            }
            y = y + tileSize[1] + adjust[1];
            x = adjust[0];
        }
    }

    private void buildPatternComplex(VShape shape) {

    }

    public VPattern getPattern() {
        return this._pattern;
    }

    public void setPattern(VPattern pattern) {
        this._pattern = pattern;
    }

    public Vector<VTile> getTiles() {
        return this._tiles;
    }

    public void setTiles(Vector<VTile> tiles) {
        this._tiles = tiles;
    }

    public float getRotation() {
        return this._rotation;
    }

    public void setRotation(float rotation) {
        this._rotation = rotation;
    }

    public VCoordinate getCoordinate() {
        return this._coordinate;
    }

    public void setCoordinate(VCoordinate coordinate) {
        this._coordinate = coordinate;
    }
}
