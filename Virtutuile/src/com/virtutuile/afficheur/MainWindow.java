package com.virtutuile.afficheur;

import com.virtutuile.afficheur.panels.BottomToolbar;
import com.virtutuile.afficheur.panels.Canvas;
import com.virtutuile.afficheur.panels.EditionPanel;
import com.virtutuile.afficheur.panels.Toolbar;
import com.virtutuile.domaine.Controller;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private Controller controller;
    private Canvas canvas;
    private EditionPanel editionPanel;
    private Toolbar toolBar;
    private BottomToolbar bottomToolbar;

    public MainWindow() {
        controller = new Controller();
        canvas = new Canvas(controller, this);
        editionPanel = new EditionPanel(controller);
        toolBar = new Toolbar(controller);
        bottomToolbar = new BottomToolbar(controller);

        setUpWindow();
        setUpContainer();
        setVisible(true);

    }

    private void setUpWindow() {
        setTitle("VirtuTuile");
        setSize(1920, 1080);
        setBounds(0, 0, 1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void setUpContainer() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.setBackground(new Color(39, 39, 39));

        container.add(toolBar, BorderLayout.NORTH);
        container.add(canvas);
        container.add(BorderLayout.EAST, new JScrollPane(editionPanel));
        container.add(bottomToolbar, BorderLayout.SOUTH);
    }

    public Controller getController() {
        return controller;
    }
}
