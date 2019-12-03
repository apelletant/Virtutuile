package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.Constants;
import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.inputs.TextInput;
import com.virtutuile.afficheur.swing.Label;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.shared.Pair;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

public class InfoPanel extends SubPanel {

    private UnorderedMap<String, TextInput> totalPackage = new UnorderedMap<>();
    private UnorderedMap<String, TextInput> totalTiles = new UnorderedMap<>();
    private UnorderedMap<String, TextInput> totalCuttedTiles = new UnorderedMap<>();

    private Vector<JPanel> lineAdded = new Vector<>();
    private Button highlight = null;

    TextInput packageUsedSelected = null;
    TextInput tileUsedSelected = null;
    TextInput cuttedTilesSelected = null;


    public InfoPanel(String name, MainWindow mainWindow) {
        super(name, mainWindow);
        initTotal();
        setButtonsOnPanel();
        setEvents();
        persistLayout();
    }

    private void initTotal() {
        String[] typeOfTiles = mainWindow.getController().getTypeOfTiles();

        for (String typeOfTile : typeOfTiles) {
            totalPackage.put(typeOfTile, new TextInput("Package total for '" + typeOfTile + "'"));
            totalPackage.get(typeOfTile).setEditableFalse();

            totalTiles.put(typeOfTile, new TextInput("Tiles total for '" + typeOfTile + "'"));
            totalTiles.get(typeOfTile).setEditableFalse();

            totalCuttedTiles.put(typeOfTile, new TextInput("Cutted tiles total for '" + typeOfTile + "'"));
            totalCuttedTiles.get(typeOfTile).setEditableFalse();
        }
    }

    @Override
    protected void setButtonsOnPanel() {
        setSelectedProps();
        setTotal();

    }

    @Override
    protected void setEvents() {

    }

    private void setSelectedProps() {

        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));

        packageUsedSelected = new TextInput("Packages required for the selected surface");
        tileUsedSelected = new TextInput("Tiles required for the selected surface");
        cuttedTilesSelected = new TextInput("Tiles cutted on the selected surface");
        highlight = new Button("Highlight cutted tiles", AssetLoader.loadImage("/icons/highlight.png"));
        highlight.fixSize(new Dimension(200, 80));

        packageUsedSelected.setEditableFalse();
        tileUsedSelected.setEditableFalse();
        cuttedTilesSelected.setEditableFalse();

        line.add(packageUsedSelected);
        rows.add(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(tileUsedSelected);
        rows.add(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(cuttedTilesSelected);
        rows.add(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(highlight);
        rows.add(line);

    }

    private void setTotal() {
        JPanel line = null;

        Iterator<Pair<String, TextInput>> iterator = totalPackage.iterator();
        do {
            Pair<String, TextInput> pair = iterator.next();
            line = new JPanel();
            line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));

            line.add(pair.getValue());
            line.add(totalTiles.get(pair.getKey()));
            line.add(totalCuttedTiles.get(pair.getKey()));

            lineAdded.add(line);
            rows.add(line);
        } while (iterator.hasNext());
    }

    public void retrieveInfoSelected() {
        Integer[] tiles = mainWindow.getController().getSurfaceTileProperties();
        Integer usedPackages = mainWindow.getController().getUsedPackageOnSurface();

        if (tiles != null) {
            if (tiles[0] != null) {
                tileUsedSelected.setText(tiles[0].toString());
            }
            if (tiles[1] != null) {
                cuttedTilesSelected.setText(tiles[1].toString());
            }
        } else {
            tileUsedSelected.setText("0");
            cuttedTilesSelected.setText("0");
        }

        if (usedPackages != null) {
            packageUsedSelected.setText(usedPackages.toString());
        } else {
            packageUsedSelected.setText("0");
        }
    }

    public void retrieveGeneralTileInfo() {
        Iterator<Pair<String, TextInput>> iterator = totalPackage.iterator();
        do {
            Pair<String, TextInput> pair = iterator.next();
            Integer[] tiles = mainWindow.getController().getTotalTileFor(pair.getKey());
            Integer usedPackage = mainWindow.getController().getUsedPackageFor(pair.getKey());

            pair.getValue().setText(usedPackage.toString());

            if (tiles != null) {
                if (tiles[0] != null) {
                    totalTiles.get(pair.getKey()).setText(tiles[0].toString());
                }
                if (tiles[1] != null) {
                    totalCuttedTiles.get(pair.getKey()).setText(tiles[0].toString());
                }
            }
        } while (iterator.hasNext());
    }

    public void rethink() {
        /*String[] typeOfTiles = mainWindow.getController().getTypeOfTiles();*/
    }
}
