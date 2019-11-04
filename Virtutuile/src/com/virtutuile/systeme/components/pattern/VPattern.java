package com.virtutuile.systeme.components.pattern;

import com.virtutuile.systeme.components.VTile;

import java.util.Vector;

public abstract class VPattern {
    protected int[] _offset_x ;
    protected int[] _offset_y ;
    protected double[] _adjust;
    protected Vector<VTile> _tiles = new Vector<>();

    public Vector<VTile> getTiles() {
        return this._tiles;
    }

    public void setTiles(Vector<VTile> tiles) {
        this._tiles = tiles;
    }
    public int[] getOffset_x() {
        return _offset_x;
    }

    public int[] getOffset_y() {
        return _offset_y;
    }

    public double[] getAdjust() {
        return _adjust;
    }
}
