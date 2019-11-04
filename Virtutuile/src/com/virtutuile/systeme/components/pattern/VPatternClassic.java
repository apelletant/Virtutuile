package com.virtutuile.systeme.components.pattern;

import com.virtutuile.systeme.components.VTile;
import com.virtutuile.systeme.units.VDimensions;

public class VPatternClassic extends VPattern {

    //TODO: Définir les dimensions de la tuile ailleurs #edtion type de materiau
    public VPatternClassic() {
        this._adjust = new double[]{0, 0};
        this._tiles.add(new VTile());
        this._tiles.get(0).setDimensions(new VDimensions(30, 10));
    }
}
