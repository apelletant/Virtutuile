package com.virtutuile.graphics.components;

import com.virtutuile.engine.PhysicalEngine;
import com.virtutuile.graphics.components.panels.TopToolbar;
import com.virtutuile.graphics.components.panels.editionpanel.EditionPanel;

import com.virtutuile.engine.EditorEngine;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private TopToolbar _toolBar;
    private EditionPanel _editionPanel;
    private Editor _editor;
    private EditorEngine _ee;
    private PhysicalEngine _pe;

    public MainWindow() {
        super();
        this.setTitle("VirtuTuile");
        this.setSize(1920, 1080);
        this.setBounds(0, 0, 1920, 1080);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        Container container = this.getContentPane();

        this._toolBar = new TopToolbar();
        this._editionPanel = new EditionPanel();
        _pe = new PhysicalEngine();
        _ee = new EditorEngine(_pe);
        _editor = new Editor(_ee);
        container.setLayout(new BorderLayout());
        container.setBackground(new Color(39, 39, 39));

        container.add(_toolBar, BorderLayout.NORTH);
        container.add(_editionPanel, BorderLayout.EAST);
        container.add(_editor, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
