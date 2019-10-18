package com.virtutuile.systeme.tools;

import com.virtutuile.afficheur.panels.VTopToolbar;

import java.awt.*;
import java.net.URL;

public class AssetLoader {
    public static final String ASSETS_PACKAGE = "/com/virtutuile/assets";

    public static Image loadImage(String path) {
        URL iconUrl  = VTopToolbar.class.getResource( ASSETS_PACKAGE + path);
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getImage(iconUrl);
    }
}
