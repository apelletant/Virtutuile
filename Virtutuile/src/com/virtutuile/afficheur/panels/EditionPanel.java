package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.panels.subEdition.*;
import com.virtutuile.afficheur.swing.PanelEvents;
import com.virtutuile.domaine.Controller;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class EditionPanel extends PanelEvents {

    private MainWindow mainWindow;
    private TileSettingsPanel tileSettingsPanel;
    private UnorderedMap<PanelType, SubPanel> subPanels;

    public EditionPanel(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;
        subPanels = new UnorderedMap<>() {{
            put(PanelType.Surface, new SurfacePanel("Surface", mainWindow));
            put(PanelType.Grout, new GroutPanel("Grout", mainWindow));
            put(PanelType.Tile, new TilePanel("Tile", mainWindow));
            put(PanelType.Pattern, new PatternPanel("Pattern", mainWindow));
        }};
        subPanels.forEach((key, value) -> {
            add(value, key);
        });
        tileSettingsPanel = new TileSettingsPanel("Tile Settings", mainWindow);

        setOpaque(true);
        setName("Edition Panel");
        setBackground(Constants.EDITIONPANEL_BACKGROUND);
        setForeground(Constants.EDITIONPANEL_FONT_COLOR);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setPreferredSize(new Dimension(600, 1080));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    }

    public void surfaceGetSurfaceDimensions() {
        SurfacePanel surface = (SurfacePanel) subPanels.get(PanelType.Surface);
        if (surface != null) {
            surface.retrieveSurfaceDimensions();
        }
    }

    public void surfaceGetGroutThickness() {
        GroutPanel grout = (GroutPanel) subPanels.get(PanelType.Grout);
        if (grout != null) {
            grout.retrieveGroutThickness();
        }
    }

    public void removeAllSurfacePanels() {
        subPanels.forEach((key, value) -> {
            remove(value);
        });
    }

    public void addAllPanels() {
        subPanels.forEach((key, value) -> {
            add(value);
        });
    }

    public TileSettingsPanel getTileSettingsPanel() {
        return tileSettingsPanel;
    }

    public void addTileSettingsPanel() {
        add(tileSettingsPanel);
    }

    public void removeTileSettingsPanel() {
        remove(tileSettingsPanel);
    }

    enum PanelType {
        Surface,
        Pattern,
        Grout,
        Tile
    }

}
