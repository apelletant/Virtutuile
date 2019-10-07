package com.virtutuile.graphics.components.panels;

import com.virtutuile.graphics.components.buttons.VButton;
import com.virtutuile.graphics.wrap.MapList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class VTopToolbar extends JToolBar {

    private MapList<TargetButton, VButton> _buttons = new MapList<>();

    /**
     * VTopToolbar constructor
     */
    public VTopToolbar() {
        super();
        this.setName("Toolbar");
        this.setBackground(new Color(66, 66, 66));
        this.setBorder(null);
        this.setButtonOnToolbar();
        this.setFloatable(true);
        System.out.println(this.getLayout());
    }

    /**
     * A Method to set buttons on the toolbar
     *
     * @return void
     */
    private void setButtonOnToolbar() {
        //New Canvas Button
        this._buttons.put(TargetButton.NewCanvas, new VButton("New Canvas", new File("assets/icons/file.png")));

        //Load Canvas Button
        this._buttons.put(TargetButton.LoadCanvas, new VButton("Load Canvas", new File("assets/icons/folder-open.png")));

        //Save Canvas Button
        this._buttons.put(TargetButton.SaveCanvas, new VButton("Save Canvas", new File("assets/icons/save.png")));

        //Draw VShape Button
        this._buttons.put(TargetButton.DrawShape, new VButton("Draw Shape", new File("assets/icons/pencil-alt.png")));

        //Canvas Settings Button
        this._buttons.put(TargetButton.CanvasSettings, new VButton("Canvas Settings", new File("assets/icons/cog.png")));

        //Canvas infos button
        this._buttons.put(TargetButton.CanvasInfos, new VButton("Canvas Infos", new File("assets/icons/info.png")));

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
        DrawShape,
        CanvasSettings,
        CanvasInfos,
    }
}
