package com.virtutuile.domaine.systeme.tools;

import com.virtutuile.afficheur.panels.VTopToolbar;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;

public class AssetLoader {
    public static final String ASSETS_PACKAGE = "/com/virtutuile/assets";
    private static HashMap<String, Cursor> _alreadyLoadedCursors = new HashMap<>();

    public static Image loadImage(String path) {
        URL iconUrl = VTopToolbar.class.getResource(ASSETS_PACKAGE + path);
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getImage(iconUrl);
    }

    public static Cursor loadCursor(String path, Point hotspot) {
        if (_alreadyLoadedCursors.containsKey(path))
            return _alreadyLoadedCursors.get(path);
        Image img = loadImage(path);
        Toolkit tk = Toolkit.getDefaultToolkit();
        if (hotspot == null) {
            hotspot = new Point(img.getWidth(null) / 2, img.getHeight(null) / 2);
        }
        Cursor cursor = tk.createCustomCursor(img, hotspot, path);
        _alreadyLoadedCursors.put(path, cursor);
        return cursor;
    }

    public static Cursor loadCursor(String path) {
        return loadCursor(path, null);
    }
}
