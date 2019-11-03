package com.virtutuile.afficheur;

import com.virtutuile.afficheur.inputs.VButton;
import com.virtutuile.afficheur.panels.VBottomToolbar;
import com.virtutuile.afficheur.panels.VEditionPanel;
import com.virtutuile.afficheur.panels.VEditor;
import com.virtutuile.afficheur.panels.VTopToolbar;
import com.virtutuile.afficheur.swing.panels.MouseEventKind;
import com.virtutuile.domaine.VEditorEngine;
import com.virtutuile.domaine.managers.VPainterManager;
import com.virtutuile.systeme.singletons.VApplicationStatus;

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

        _editorEngine = new VEditorEngine();
        _toolBar = new VTopToolbar();
        _editionPanel = new VEditionPanel(_editorEngine);
        _bottomToolbar = new VBottomToolbar();
        _editor = new VEditor(_editorEngine);

        setupWindow();
        setupContainer();
        setupEvents();
        setVisible(true);
    }

    private void setupEvents() {
        setupTopToolbarEvents();
        VApplicationStatus.getInstance().setOnPanelChange((state) -> {
            this._editionPanel.persistPanels();
            revalidate();
            repaint();
        });
    }

    private void setupTopToolbarEvents() {
        VApplicationStatus applicationStatus = VApplicationStatus.getInstance();

        VButton draw = _toolBar.getButton(VTopToolbar.TargetButton.DrawShape);
        VButton config = _toolBar.getButton(VTopToolbar.TargetButton.CanvasSettings);

        draw.addMouseEventListener(MouseEventKind.MouseLClick, (mouseEvent) -> {
            if (applicationStatus.getActivePanels().contains(VApplicationStatus.VPanelType.DrawShape)) {
                applicationStatus.removeActivePanel(VApplicationStatus.VPanelType.DrawShape, false);
                applicationStatus.removeActivePanel(VApplicationStatus.VPanelType.PatternManagement, false);
                draw.setActive(false);
                applicationStatus.doing = VApplicationStatus.VActionState.Idle;
            } else {
                applicationStatus.setActivePanel(VApplicationStatus.VPanelType.DrawShape, false);
                config.setActive(false);
                draw.setActive(true);
                applicationStatus.doing = VApplicationStatus.VActionState.CreatingRectShape;
                applicationStatus.manager = VApplicationStatus.VActionManager.Shape;
            }
            this._editionPanel.persistPanels();
            revalidate();
            repaint();
        });

        config.addMouseEventListener(MouseEventKind.MouseLClick, (me) -> {
            if (applicationStatus.getActivePanels().contains(VApplicationStatus.VPanelType.Settings)) {
                applicationStatus.removeActivePanel(VApplicationStatus.VPanelType.Settings, false);
                config.setActive(false);
            } else {
                draw.setActive(false);
                applicationStatus.setActivePanel(VApplicationStatus.VPanelType.Settings, false);
                config.setActive(true);
            }
            this._editionPanel.persistPanels();
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
