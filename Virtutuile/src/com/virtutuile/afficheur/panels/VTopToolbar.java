package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.inputs.VButton;
import com.virtutuile.afficheur.swing.panels.VBorderedPanel;
import com.virtutuile.domaine.systeme.tools.AssetLoader;
import com.virtutuile.domaine.systeme.tools.UnorderedMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VTopToolbar extends VBorderedPanel {

    private UnorderedMap<TargetButton, VButton> _buttons = new UnorderedMap<>();

    /**
     * VTopToolbar constructor
     */
    public VTopToolbar() {
        super();
        this.setName("Toolbar");
        this.setBackground(new Color(66, 66, 66));
        this.setBorder(null);
        this.setButtonOnToolbar();
    }

    /**
     * A Method to set buttons on the toolbar
     *
     * @return void
     */
    private void setButtonOnToolbar() {
        //New Canvas Button
        this._buttons.put(TargetButton.NewCanvas, new VButton("New Canvas", AssetLoader.loadImage("/icons/file.png")));

        //Load Canvas Button
        this._buttons.put(TargetButton.LoadCanvas, new VButton("Load Canvas", AssetLoader.loadImage("/icons/folder-open.png")));

        //Save Canvas Button
        this._buttons.put(TargetButton.SaveCanvas, new VButton("Save Canvas", AssetLoader.loadImage("/icons/save.png")));

        //Undo Canvas Button
        this._buttons.put(TargetButton.Undo, new VButton("Undo", AssetLoader.loadImage("/icons/undo-alt.png")));

        //Redo Canvas Button
        this._buttons.put(TargetButton.Redo, new VButton("Redo", AssetLoader.loadImage("/icons/redo-alt.png")));

        //Draw VShape Button
        this._buttons.put(TargetButton.DrawShape, new VButton("Draw Shape", AssetLoader.loadImage("/icons/pencil-alt.png")));

        //Canvas Settings Button
        this._buttons.put(TargetButton.CanvasSettings, new VButton("Canvas Settings", AssetLoader.loadImage("/icons/cog.png")));

        //Canvas infos button
        this._buttons.put(TargetButton.CanvasInfos, new VButton("Canvas Infos", AssetLoader.loadImage("/icons/info.png")));

        this._buttons.forEach((key, value) -> {
            if (key == TargetButton.CanvasInfos)
                return;
            this.add(value);
        });
        this.add(Box.createHorizontalGlue());
        this.add(Box.createVerticalGlue());
        this.add(_buttons.get(TargetButton.CanvasInfos));
    }

    /**
     * @param buttonName     button key
     * @param actionListener action listener to apply
     */
    public void setActionListener(TargetButton buttonName, ActionListener actionListener) {
//        this._buttonsToolbar.get(buttonName).addActionListener(actionListener)
    }

    public VButton getButton(TargetButton name) {
        return _buttons.get(name);
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
