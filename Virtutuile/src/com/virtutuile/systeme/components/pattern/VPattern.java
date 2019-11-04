package com.virtutuile.systeme.components.pattern;

import com.virtutuile.systeme.components.VGrout;
import com.virtutuile.systeme.components.VTileSpec;

import java.util.Vector;

public abstract class VPattern {
    protected int[] _offset_x ;
    protected int[] _offset_y ;
    protected int[] _adjust;
    protected Vector<VTileSpec> _tiles;

    public Vector<VTileSpec> getTiles() {
        return this._tiles;
    }

    public void setTiles(Vector<VTileSpec> tiles) {
        this._tiles = tiles;
    }
    public int[] getOffset_x() {
        return _offset_x;
    }

    public int[] getOffset_y() {
        return _offset_y;
    }

    public int[] getAdjust() {
        return _adjust;
    }
}
