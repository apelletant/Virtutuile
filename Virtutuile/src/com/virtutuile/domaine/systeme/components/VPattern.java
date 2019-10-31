package com.virtutuile.domaine.systeme.components;

import java.util.Vector;

public class VPattern {
    private Vector<VTileSpec> _tiles;
    private VGrout _grout;

    public Vector<VTileSpec> getTiles() {
        return this._tiles;
    }

    public void setTiles(Vector<VTileSpec> tiles) {
        this._tiles = tiles;
    }

    public VGrout getGrout() {
        return this._grout;
    }

    public void setGrout(VGrout grout) {
        this._grout = grout;
    }
}
