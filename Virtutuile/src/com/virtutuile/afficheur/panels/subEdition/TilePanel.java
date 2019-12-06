package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import java.util.Vector;

public class TilePanel extends SubPanel  {

    private UnorderedMap<String, Button> tilesType;
    private Vector<JPanel> tileLines = new Vector<>();

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
        tileLines.add(line);
        rows.add(line);
    }

    // TODO IMPLEMENTS FUNCTIONE
    @Override
    public void refreshGUI() {
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

    public void rethinkMenu() {
        String[] tiles = mainWindow.getController().getTypeOfTiles();

        JPanel line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setSize(100, 100);
        line.setOpaque(false);

        int i = 0;
        int j = 0;

        tilesType.forEach((key, value) -> {
            remove(value);
        });

        tileLines.forEach(this::removeLayout);

        tilesType = new UnorderedMap<>();
        tileLines.clear();
        tileLines = new Vector<>();
        while (i != 4) {
            if (j == tiles.length) {
                if (i != 0) {
                    tileLines.add(line);
                    rows.add(line);
                }
                break;
            }
            if (i == 3) {
                rows.add(line);
                tileLines.add(line);
                line = new JPanel();
                line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
                line.setOpaque(false);
                i = 0;
            }
            tilesType.put(tiles[j], new Button(tiles[j], AssetLoader.loadImage("/icons/shape-edit-add-square.png")));
            line.add(tilesType.get(tiles[j]));
            j++;
            i++;
        }
        setEvents();
        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setSize(100, 100);

        /*setColorPickerOnPanel(line);
        setTextInputsOnPanel(line);
        setCreationButtonsOnPanel();*/
        revalidate();
        repaint();
        persistLayout();
    }

}
