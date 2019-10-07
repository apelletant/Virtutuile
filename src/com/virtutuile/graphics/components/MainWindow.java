package com.virtutuile.graphics.components;

import com.virtutuile.engine.VEditorState;
import com.virtutuile.engine.VPhysicalEngine;
import com.virtutuile.graphics.components.buttons.VButton;
import com.virtutuile.graphics.components.panels.VTopToolbar;
import com.virtutuile.graphics.components.panels.editionpanel.VEditionPanel;

import com.virtutuile.engine.VEditorEngine;
import com.virtutuile.graphics.wrap.MouseEventKind;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private VTopToolbar _toolBar;
    private VEditionPanel _editionPanel;
    private VEditor _editor;
    private VEditorEngine _ee;
    private VPhysicalEngine _pe;

    public MainWindow() {
        super();

        _toolBar = new VTopToolbar();
        _editionPanel = new VEditionPanel();
        _pe = new VPhysicalEngine();
        _ee = new VEditorEngine(_pe);
        _editor = new VEditor(_ee);

        setupWindow();
        setupContainer();
        setupEvents();
        setVisible(true);
    }

    private void setupEvents() {
        setupTopToolbarEvents();
    }

    private void setupTopToolbarEvents() {
        VButton draw = _toolBar.getButton(VTopToolbar.TargetButton.DrawShape);
        draw.addEventListener(MouseEventKind.MousePress, (me) -> {
            switch (_ee.getState()) {
                case Idle:
                    _ee.setState(VEditorState.CreatingRectShape);
                    draw.setActive(true);
                    break;
                case CreatingFreeShape:
                case CreatingRectShape:
                    _ee.setState(VEditorState.Idle);
                    draw.setActive(false);
            }
        });

        VButton save = _toolBar.getButton(VTopToolbar.TargetButton.SaveCanvas);
        save.addEventListener(MouseEventKind.MouseLClick, (me) -> {
            if (_editionPanel.isPanelActive(VEditionPanel.SHAPE_MANAG)) {
                _editionPanel.removePanelsActive(VEditionPanel.SHAPE_MANAG);
                save.setActive(false);
            } else {
                _editionPanel.addPanelsActive(VEditionPanel.SHAPE_MANAG);
                save.setActive(true);
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
        container.add(_editor, BorderLayout.CENTER);
    }
}
