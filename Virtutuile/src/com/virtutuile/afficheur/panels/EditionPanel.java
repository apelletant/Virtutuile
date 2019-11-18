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
            add(new SurfacePanel("Surface", controller), PanelType.Surface);
            add(new GroutPanel("Grout", controller), PanelType.Grout);
            add(new TilePanel("Tile", controller), PanelType.Tile);
            add(new PatternPanel(   "Pattern", controller), PanelType.Pattern);
        }};

        setOpaque(true);
        setName("Edition Panel");
        setBackground(Constants.EDITIONPANEL_BACKGROUND);
        setForeground(Constants.EDITIONPANEL_FONT_COLOR);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setPreferredSize(new Dimension(600, 1080));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /*
        activePanels.add(subPanels.get(PanelType.Surface));
        activePanels = new Vector<>();*/
    }

    enum PanelType {
        Surface,
        Pattern,
        Grout,
        Tile,
        Test
    }

}
