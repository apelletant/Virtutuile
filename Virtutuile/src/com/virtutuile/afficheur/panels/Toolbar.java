package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.Button;
import com.virtutuile.afficheur.swing.BorderedPanel;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.domaine.Controller;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import java.awt.*;

public class Toolbar extends BorderedPanel {

    private Controller controller;
    private UnorderedMap<TargetButton, Button> buttons = new UnorderedMap<>();

    /**
     * VTopToolbar constructor
     */
    public Toolbar(Controller controller) {
        super();
        this.controller = controller;
        setName("Toolbar");
        setBackground(new Color(66, 66, 66));

        setBorder(null);
        setButtonOnToolbar();
    }

    /**
     * A Method to set buttons on the toolbar
     *
     * @return void
     */
    private void setButtonOnToolbar() {
        //New Canvas Button
        buttons.put(TargetButton.NewCanvas, new Button("New Canvas", AssetLoader.loadImage("/icons/file.png")));

        //Load Canvas Button
        buttons.put(TargetButton.LoadCanvas, new Button("Load Canvas", AssetLoader.loadImage("/icons/folder-open.png")));

        //Save Canvas Button
        buttons.put(TargetButton.SaveCanvas, new Button("Save Canvas", AssetLoader.loadImage("/icons/save.png")));

        //Undo Canvas Button
        buttons.put(TargetButton.Undo, new Button("Undo", AssetLoader.loadImage("/icons/undo-alt.png")));

        //Redo Canvas Button
        buttons.put(TargetButton.Redo, new Button("Redo", AssetLoader.loadImage("/icons/redo-alt.png")));

        //Canvas Settings Button
        buttons.put(TargetButton.CanvasSettings, new Button("Canvas Settings", AssetLoader.loadImage("/icons/cog.png")));

        //Canvas infos button
        buttons.put(TargetButton.CanvasInfos, new Button("Canvas Infos", AssetLoader.loadImage("/icons/info.png")));

        buttons.forEach((key, value) -> {
            if (key == TargetButton.CanvasInfos)
                return;
            add(value);
        });
        add(Box.createHorizontalGlue());
        add(Box.createVerticalGlue());
        add(buttons.get(TargetButton.CanvasInfos));
    }

    public Button getButton(TargetButton name) {
        return buttons.get(name);
    }

    public enum TargetButton {
        NewCanvas,
        LoadCanvas,
        SaveCanvas,
        Undo,
        Redo,
        DrawShape,
        CanvasSettings,
        CanvasInfos,
    }
}
