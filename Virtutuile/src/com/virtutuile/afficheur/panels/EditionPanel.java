package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.panels.subEdition.GroutPanel;
import com.virtutuile.afficheur.panels.subEdition.PatternPanel;
import com.virtutuile.afficheur.panels.subEdition.SurfacePanel;
import com.virtutuile.afficheur.panels.subEdition.SubPanel;
import com.virtutuile.afficheur.swing.PanelEvents;
import com.virtutuile.domaine.Controller;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Vector;

public class EditionPanel extends PanelEvents {

    private Controller controller;
    private UnorderedMap<PanelType, SubPanel> subPanels;
    private Vector<SubPanel> activePanels;

    public EditionPanel(Controller controller) {
        super();
        setOpaque(true);
        setName("Edition Panel");
        setBackground(Constants.EDITIONPANEL_BACKGROUND);
        setForeground(Constants.EDITIONPANEL_FONT_COLOR);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        subPanels = new UnorderedMap<>() {{
           add(new SurfacePanel("Surface", controller), PanelType.Surface);
           add(new PatternPanel("Pattern", controller), PanelType.Pattern);
           add(new GroutPanel("Grout", controller), PanelType.Grout);
        }};

        activePanels = new Vector<>();
        activePanels.add(subPanels.get(PanelType.Surface));
    }

    //TODO: Développer la méthode en fonction d'une variable qui sera renvoyé par qlq d'autre
    public void persistPanels() {

    }

    enum PanelType {
        Surface,
        Pattern,
        Grout,
    }

}
