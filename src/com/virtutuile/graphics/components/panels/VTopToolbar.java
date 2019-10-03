package com.virtutuile.graphics.components.panels;

import com.virtutuile.graphics.components.buttons.VButton;
import com.virtutuile.graphics.wrap.MouseEventKind;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

public class VTopToolbar extends JToolBar {

    private Vector<Pair<String, VButton>> _buttons = new Vector<>();

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
     * @param buttonName     button key
     * @param actionListener action listener to apply
     */
    public void setActionListener(String buttonName, ActionListener actionListener) {
//        this._buttonsToolbar.get(buttonName).addActionListener(actionListener)
    }

    /**
     * A Method to set buttons on the toolbar
     *
     * @return void
     */
    private void setButtonOnToolbar() {
        //New Canvas Button
        VButton newCanvas = new VButton("New Canvas", new File("assets/icons/file.png"));
        newCanvas.addEventListener(MouseEventKind.MouseClick, (me) -> System.out.println("New Canvas clicked"));
        this._buttons.add(new Pair<>("newCanvas", newCanvas));

        //Load Canvas Button
        this._buttons.add(new Pair<>("loadCanvas", new VButton("Load Canvas", new File("assets/icons/folder-open.png"))));

        //Save Canvas Button
        this._buttons.add(new Pair<>("saveCanvas", new VButton("Save Canvas", new File("assets/icons/save.png"))));

        //Draw VShape Button
        this._buttons.add(new Pair<>("drawShape", new VButton("Draw VShape", new File("assets/icons/pencil-alt.png"))));

        //Canvas Settings Button
        this._buttons.add(new Pair<>("canvasSettings", new VButton("Canvas Settings", new File("assets/icons/cog.png"))));

        //Canvas infos button
        VButton canvasInfo = new VButton("Canvas Infos", new File("assets/icons/info.png"));
        this._buttons.add(new Pair<>("canvasInfos", canvasInfo));

        for (Pair<String, VButton> button : this._buttons) {
            if (button.getKey().equals("canvasInfos"))
                continue;
            this.add(button.getValue());
        }
        this.add(Box.createHorizontalGlue());
        this.add(Box.createVerticalGlue());
        this.add(canvasInfo);
    }
}
