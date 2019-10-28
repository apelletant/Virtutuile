package com.virtutuile.systeme.components;

import java.util.Vector;

public class VPattern {
    private Vector<VTileSpec> _tiles;
    private VGrout _grout;

    public Vector<VTileSpec> tiles() {
        return this._tiles;
    }

    public void tiles(Vector<VTileSpec> tiles) {
        this._tiles = tiles;
    }

    public VGrout grout() {
        return this._grout;
    }

    public void grout(VGrout grout) {
        this._grout = grout;
    }
}
