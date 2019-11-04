package com.virtutuile.systeme.components.pattern;

import com.virtutuile.systeme.components.VShape;
import com.virtutuile.systeme.components.VTile;
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
        double[] tileSize = {this._pattern.getTiles().get(0).getDimensions().width, this._pattern.getTiles().get(0).getDimensions().height};
        double y = 0;
        double x = 0;

        /*System.out.println("Shape: " + shape.getBounds().getWidth() + "x" + shape.getBounds().getHeight());
        System.out.println("Shape origin: {" + origin[0] + ", " + origin[1] + "}");
        System.out.println("Tile: " + tileSize[0] + "x" + tileSize[1]);*/
        while(y < shape.getBounds().getHeight() + tileSize[1] + shape.getGrout().getThickness()) {
            /*System.out.println("while (y: " + y + " < " + (shape.getBounds().getHeight() + tileSize[1] + shape.getGrout().getThickness()) + ")");*/
            y = y + shape.getGrout().getThickness();
            while (x < shape.getBounds().getWidth() + tileSize[0] + shape.getGrout().getThickness()) {
                /*System.out.println("while (x: " + x + " < " + (shape.getBounds().getWidth() + tileSize[1] + shape.getGrout().getThickness()) + ")");*/
                int i = 0;
                x = x + shape.getGrout().getThickness();
                while (i != this._pattern.getTiles().size()) {
                    VTile tile = this._pattern.getTiles().get(i).copy();
                    /*System.out.println("Y + o --> " + (y + origin[1]));
                    System.out.println("Y --> " + y);
                    System.out.println("OY --> " + origin[1]);*/
                    tile.setOrigin(new VCoordinate(x + origin[0], (y + origin[1])));
                    System.out.println("X: " + tile.getOrigin().longitude + ", Y: " +  tile.getOrigin().latitude);
                    this._tiles.add(tile);
                    i++;
                }
                x = x + tileSize[0] ;
            }
            y = y + tileSize[1] + adjust[1];
            /*System.out.println("y: " + y);*/
            x = adjust[0];
        }
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
