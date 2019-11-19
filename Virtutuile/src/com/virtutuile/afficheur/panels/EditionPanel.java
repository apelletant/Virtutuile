package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.panels.subEdition.*;
import com.virtutuile.afficheur.swing.PanelEvents;
import com.virtutuile.domaine.Controller;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class EditionPanel extends PanelEvents {

    private Controller controller;
    private Vector<SubPanel> activePanels;

    private UnorderedMap<PanelType, SubPanel> subPanels;

    public EditionPanel(Controller controller) {
        super();
        subPanels = new UnorderedMap<>() {{
            put(PanelType.Surface, new SurfacePanel("Surface", controller));
            put(PanelType.Grout, new GroutPanel("Grout", controller));
            put(PanelType.Tile, new TilePanel("Tile", controller));
            put(PanelType.Pattern, new PatternPanel("Pattern", controller));
        }};
        System.out.println("size ok: " + subPanels.size());
        subPanels.forEach((key, value) -> {
            add(value, key);
        });

        setOpaque(true);
        setName("Edition Panel");
        setBackground(Constants.EDITIONPANEL_BACKGROUND);
        setForeground(Constants.EDITIONPANEL_FONT_COLOR);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setPreferredSize(new Dimension(600, 1080));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*activePanels.add(subPanels.get(PanelType.Surface));
        activePanels = new Vector<>();*/
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

    public void persistPanels() {
        subPanels.forEach((key, value) -> {
            if (activePanels.contains(key)) {
                if (!activePanels.contains(key)) {
                    add(value);
                }
            } else {
                remove(value);
            }
        });
    }

    enum PanelType {
        Surface,
        Pattern,
        Grout,
        Tile
    }

}
