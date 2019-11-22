package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;

public class TilePanel extends SubPanel  {

    private UnorderedMap<String, Button> tilesType;

    public TilePanel(String name, MainWindow mainWindow) {
        super(name, mainWindow);
        tilesType = new UnorderedMap<>();

        setButtonsOnPanel();
        setEvents();
        persistLayout();
    }

    public UnorderedMap<String, Button> getTilesType() {
        return tilesType;
    }

    @Override
    protected void setButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));

        String[] types = mainWindow.getController().getTypeOfTiles();
        for (String type : types) {
            tilesType.put(type, new Button(type, AssetLoader.loadImage("/icons/shape-edit-add-square.png")));
        }
        tilesType.forEach((key, value) -> {
            line.add(value);
        });
        rows.add(line);
    }

    @Override
    protected void setEvents() {
        tilesType.forEach((name, button) -> {
            button.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {

                tilesType.forEach((nameBis, buttonBis) -> {
                    buttonBis.setActive(false);
                });

                if (mainWindow.getController().isSurfaceSelected()) {
                    mainWindow.getController().setTypeOfTileOnSurface(name);
                    button.setActive(true);
                    mainWindow.repaint();
                }
            });
        });
    }

    public void setButtonSelected(String buttonName) {
        tilesType.forEach((name, button) -> {
            if (name.equals(buttonName)) {
                button.setActive(true);
            } else {
                button.setActive(false);
            }
        });
    }
}
