package com.virtutuile.afficheur;

import com.virtutuile.afficheur.inputs.VButton;
import com.virtutuile.afficheur.panels.VBottomToolbar;
import com.virtutuile.afficheur.panels.VEditionPanel;
import com.virtutuile.afficheur.panels.VEditor;
import com.virtutuile.afficheur.panels.VTopToolbar;
import com.virtutuile.afficheur.swing.panels.MouseEventKind;
import com.virtutuile.moteur.VEditorEngine;
import com.virtutuile.systeme.singletons.VActionStatus;

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
    }

    private void setupTopToolbarEvents() {
        VActionStatus actionStatus = VActionStatus.VActionStatus();

        VButton draw = _toolBar.getButton(VTopToolbar.TargetButton.DrawShape);
        draw.addEventListener(MouseEventKind.MouseLClick, (mouseEvent) -> {
            if (_editionPanel.isPanelActive(VEditionPanel.DRAW_SHAPE)) {
                _editionPanel.removePanelsActive(VEditionPanel.DRAW_SHAPE);
                draw.setActive(false);
                actionStatus.doing = VActionStatus.VActionState.Idle;
            } else {
                _editionPanel.addPanelsActive(VEditionPanel.DRAW_SHAPE);
                draw.setActive(true);
                actionStatus.doing = VActionStatus.VActionState.CreatingRectShape;
                actionStatus.manager = VActionStatus.VActionManager.Shape;
            }
            revalidate();
            repaint();
        });

        VButton config = _toolBar.getButton(VTopToolbar.TargetButton.CanvasSettings);
        config.addEventListener(MouseEventKind.MouseLClick, (me) -> {
            if (_editionPanel.isPanelActive(VEditionPanel.SETTINGS)) {
                _editionPanel.removePanelsActive(VEditionPanel.SETTINGS);
                config.setActive(false);
            } else {
                _editionPanel.addPanelsActive(VEditionPanel.SETTINGS);
                config.setActive(true);
            }
            revalidate();
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
