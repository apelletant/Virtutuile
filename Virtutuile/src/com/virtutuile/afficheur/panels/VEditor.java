package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.swing.panels.MouseEventKind;
import com.virtutuile.afficheur.swing.panels.VPanelEvents;
import com.virtutuile.moteur.VEditorEngine;
import com.virtutuile.moteur.managers.VPainterManager;
import com.virtutuile.systeme.constants.UIConstants;
import com.virtutuile.systeme.constants.VPhysicsConstants;
import com.virtutuile.systeme.units.VProperties;

import java.awt.*;

public class VEditor extends VPanelEvents {
    Point p;
    private VEditorEngine _editorEngine;

    public VEditor(VEditorEngine editorEngine) {
        this.setBackground(UIConstants.DRAW_BACKGROUND);
        this.setName("Toolbar");
        this.setBorder(null);
        this._editorEngine = editorEngine;

        addEventListener(MouseEventKind.MousePress, (mouseEvent) -> {
            VProperties properties = new VProperties() {{
                coordinates.add(VPhysicsConstants.pointToCoordinates(mouseEvent.getPoint()));
            }};
            editorEngine.mouseLClick(properties);
            repaint();
        });
        addEventListener(MouseEventKind.MouseDrag, (mouseEvent) -> {
            editorEngine.mouseDrag(VPhysicsConstants.pointToCoordinates(mouseEvent.getPoint()));
            repaint();
        });

        addEventListener(MouseEventKind.MouseMove, (me) -> {
            editorEngine.mouseHover(VPhysicsConstants.pointToCoordinates(me.getPoint()));
            repaint();
        });

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this._editorEngine.paint(VPainterManager.getInstance().getPainter(g));
    }
}
