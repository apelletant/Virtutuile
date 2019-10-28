package com.virtutuile.afficheur;

import com.virtutuile.afficheur.inputs.VButton;
import com.virtutuile.afficheur.panels.VBottomToolbar;
import com.virtutuile.afficheur.panels.VEditionPanel;
import com.virtutuile.afficheur.panels.VEditor;
import com.virtutuile.afficheur.panels.VTopToolbar;
import com.virtutuile.afficheur.swing.panels.MouseEventKind;
import com.virtutuile.domaine.moteur.VEditorEngine;
import com.virtutuile.domaine.moteur.managers.VPainterManager;
import com.virtutuile.domaine.systeme.singletons.VApplicationStatus;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private VTopToolbar _toolBar;
    private VEditionPanel _editionPanel;
    private VBottomToolbar _bottomToolbar;
    private VEditor _editor;
    private VEditorEngine _editorEngine;
    private JTabbedPane _tabbedPane = new JTabbedPane();

    public MainWindow() {
        super();

        _toolBar = new VTopToolbar();
        _editionPanel = new VEditionPanel();
        _bottomToolbar = new VBottomToolbar();
        _editorEngine = new VEditorEngine();
        _editor = new VEditor(_editorEngine);

        setupWindow();
        setupContainer();
        setupEvents();
        setVisible(true);
    }

    private void setupEvents() {
        setupTopToolbarEvents();
        VApplicationStatus.getInstance().setOnPanelChange((state) -> {
            System.out.println("state "  + state);
            revalidate();
            repaint();
        });
    }

    private void setupTopToolbarEvents() {
        VApplicationStatus actionStatus = VApplicationStatus.getInstance();

        VButton draw = _toolBar.getButton(VTopToolbar.TargetButton.DrawShape);
        VButton config = _toolBar.getButton(VTopToolbar.TargetButton.CanvasSettings);

        draw.addMouseEventListener(MouseEventKind.MouseLClick, (mouseEvent) -> {
            if (_editionPanel.isPanelActive(VEditionPanel.DRAW_SHAPE)) {
                _editionPanel.removePanelsActive(VEditionPanel.DRAW_SHAPE);
                draw.setActive(false);
                actionStatus.doing = VApplicationStatus.VActionState.Idle;
            } else {
                _editionPanel.setPanelsActive(VEditionPanel.DRAW_SHAPE);
                config.setActive(false);
                draw.setActive(true);
                actionStatus.doing = VApplicationStatus.VActionState.CreatingRectShape;
                actionStatus.manager = VApplicationStatus.VActionManager.Shape;
            }
            revalidate();
            repaint();
        });

        config.addMouseEventListener(MouseEventKind.MouseLClick, (me) -> {
            if (_editionPanel.isPanelActive(VEditionPanel.SETTINGS)) {
                _editionPanel.removePanelsActive(VEditionPanel.SETTINGS);
                config.setActive(false);
            } else {
                draw.setActive(false);
                _editionPanel.setPanelsActive(VEditionPanel.SETTINGS);
                config.setActive(true);
            }
            revalidate();
            repaint();
        });

        VButton showbounds = _bottomToolbar.getButton(VBottomToolbar.TargetButton.ShowBounds);
        showbounds.addMouseEventListener(MouseEventKind.MouseLClick, (evt) -> {
            VPainterManager pm = VPainterManager.getInstance();

            if (pm.isGizmoActive(VPainterManager.GIZ_BOUNDS)) {
                pm.deactiveGizmos(VPainterManager.GIZ_BOUNDS);
                showbounds.setActive(false);
            } else {
                pm.activeGizmos(VPainterManager.GIZ_BOUNDS);
                showbounds.setActive(true);
            }
            repaint();
        });

    }

    private void setupWindow() {
        setTitle("VirtuTuile");
        setSize(1920, 1080);
        setBounds(0, 0, 1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Setup container specs
    private void setupContainer() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.setBackground(new Color(39, 39, 39));
        container.add(_toolBar, BorderLayout.NORTH);
        container.add(BorderLayout.EAST, new JScrollPane(this._editionPanel));
        _tabbedPane.add("Canvas 1", _editor);
        container.add(_tabbedPane, BorderLayout.CENTER);
        container.add(_bottomToolbar, BorderLayout.SOUTH);
    }
}
