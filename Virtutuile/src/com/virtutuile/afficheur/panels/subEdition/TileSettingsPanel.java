package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.inputs.ColorPicker;
import com.virtutuile.afficheur.inputs.TextInput;
import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.swing.events.InputEventKind;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class TileSettingsPanel extends SubPanel {

    private ColorPicker colorPicker = null;
    private UnitInput width = null;
    private UnitInput height = null;
    /*private TextInput tileName = null;*/

    private String selectedTile = null;

    private UnorderedMap<String, Button> tilesType = new UnorderedMap<>();

    public TileSettingsPanel(String name, MainWindow mainWindow) {
        super(name, mainWindow);
        this.setButtonsOnPanel();
        this.setEvents();
        this.persistLayout();
    }

    @Override
    protected void setEvents() {
        width.addInputListener(InputEventKind.OnChange, (value, self) -> {
            mainWindow.getController().setWidthForTile(value, selectedTile);
            mainWindow.repaint();
        });

        height.addInputListener(InputEventKind.OnChange, (value, self) -> {
            mainWindow.getController().setHeightForTile(value, selectedTile);
            mainWindow.repaint();
        });

        /*tileName.addInputListener(InputEventKind.OnChange, (value, self) -> {
            mainWindow.getController().renameTile(value, selectedTile);
            repaint();
        });*/

        tilesType.forEach((name, value) -> {
            value.addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
                if (!value.isActive()) {
                    tilesType.forEach((keyBis, button) -> {
                        button.setActive(false);
                    });
                    selectedTile = name;
                    value.setActive(true);
                    repaint();

                    Double[] dimension = mainWindow.getController().getTileDimension(name);
                    height.setValue(dimension[1]);
                    width.setValue(dimension[0]);
                    /*tileName.setText(selectedTile);*/
                }
            });
        });
    }

    @Override
    protected void setButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setSize(100, 100);

        setColorPickerOnPanel(line);
        setTextInputsOnPanel(line);
        setTypeOfTileButtonsOnPanel();
        setEvents();
    }

    private void setTypeOfTileButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));

        tilesType.put("Small", new Button("Small", AssetLoader.loadImage("/icons/shape-edit-add-square.png")));
        tilesType.put("Medium", new Button("Medium", AssetLoader.loadImage("/icons/shape-edit-add-square.png")));
        tilesType.put("Large", new Button("Large", AssetLoader.loadImage("/icons/shape-edit-add-square.png")));

        tilesType.forEach((key, value) -> {
            line.add(value);
        });
        rows.add(line);
    }

    private void setColorPickerOnPanel(JPanel line) {
        colorPicker = new ColorPicker();
        colorPicker.setMaximumSize(new Dimension(500,200));
        colorPicker.setMinimumSize(new Dimension(500,200));
        colorPicker.setPreferredSize(new Dimension(500,200));
        AbstractColorChooserPanel[] panels = colorPicker.getChooserPanels();
        colorPicker.setChooserPanels(new AbstractColorChooserPanel[] { panels[0] });
        line.add(colorPicker);
        rows.add(line);
    }

    private void setTextInputsOnPanel(JPanel line) {
        width = new UnitInput("Width");
        height = new UnitInput("Height");
        /*tileName = new TextInput("Tile name");*/

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(width);
        line.add(height);
        /*line.add(tileName);*/
        rows.add(line);
    }

    public UnorderedMap<String, Button> getTilesType() {
        return tilesType;
    }
}
