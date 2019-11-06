package com.virtutuile.systeme.components.pattern;

import com.virtutuile.systeme.components.shape.VTile;
import com.virtutuile.systeme.units.VDimensions;

import java.awt.geom.Rectangle2D;

public class VPatternClassic extends VPattern {

    public VPatternClassic() {
        this._adjust = new double[]{0, 0};
        this._tiles.add(new VTile(new Rectangle2D.Double(0,0, this.defaultDimensions.width, this.defaultDimensions.height)));
    }
}
