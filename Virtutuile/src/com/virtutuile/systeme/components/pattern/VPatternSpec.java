package com.virtutuile.systeme.components.pattern;

import com.virtutuile.systeme.components.VShape;
import com.virtutuile.systeme.components.VTileSpec;
import com.virtutuile.systeme.shared.PatternType;
import com.virtutuile.systeme.units.VCoordinate;

import java.util.Vector;

public class VPatternSpec {
    private VPattern _pattern = null;
    private Vector<VTileSpec> _tiles;
    private float _rotation;
    private VCoordinate _coordinate;

    public VPatternSpec(PatternType patternType, VShape shape) {
        switch (patternType) {
            case Classic:
                this._pattern = new VPatternClassic();
                System.out.println("Classic Pattern selected");
            default:
                break;
        }
        if (this._pattern != null) {
            this.buildPattern(shape);
        }
    }

    private void buildPattern(VShape shape) {
        int[] offset_x = this._pattern._offset_x;
        int[] offset_y = this._pattern._offset_y;
        int[] adjust = this._pattern._adjust;
        /*VCoordinate startingPoint

        double shapeX = shape.get;
        double shapeY;

        for(int i = 0; i  ;i++)*/
    }

    public VPattern getPattern() {
        return this._pattern;
    }

    public void setPattern(VPattern pattern) {
        this._pattern = pattern;
    }

    public Vector<VTileSpec> getTiles() {
        return this._tiles;
    }

    public void setTiles(Vector<VTileSpec> tiles) {
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
