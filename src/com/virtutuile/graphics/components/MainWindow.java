package com.virtutuile.graphics.components;

import com.virtutuile.engine.VPhysicalEngine;
import com.virtutuile.graphics.components.panels.VTopToolbar;
import com.virtutuile.graphics.components.panels.editionpanel.VEditionPanel;

import com.virtutuile.engine.VEditorEngine;

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
        setVisible(true);
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
        container.add(_editionPanel, BorderLayout.EAST);
        container.add(_editor, BorderLayout.CENTER);

    }
}
