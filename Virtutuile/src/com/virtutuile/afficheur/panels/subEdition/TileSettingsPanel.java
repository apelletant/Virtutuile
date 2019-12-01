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
import com.virtutuile.afficheur.tools.ValidationsException;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.util.Vector;

public class TileSettingsPanel extends SubPanel {

    private ColorPicker colorPicker = null;
    private UnitInput width = null;
    private UnitInput height = null;
    private TextInput numberPerPack = null;
    /*private TextInput tileName = null;*/
    private String selectedTile = null;
    private UnorderedMap<String, Button> tilesType = new UnorderedMap<>();
    private UnorderedMap<String, Button> creationButtons = new UnorderedMap<>();

    private JFrame creationFrame = null;
    private Vector<JPanel> creationFrameRows = new Vector<>();
    private TextInput tileNameCreation = null;
    private UnitInput tileNumberPerPackCreation = null;
    private ColorPicker tileColorPickerCreation = null;
    private UnitInput tileWidthCreation = null;
    private UnitInput tileHeightCreation = null;

    public TileSettingsPanel(String name, MainWindow mainWindow) {
        super(name, mainWindow);
        setUpCreationFrame();
        setButtonsOnPanel();
        setEvents();
        persistLayout();
    }

    private void setUpCreationFrame() {
        creationFrame = new JFrame();
        creationFrame.setTitle("Creation Frame");
        creationFrame.setSize(600, 400);
        creationFrame.setBounds(0, 0, 600, 400);



        tileNameCreation = new TextInput("Name");
        tileColorPickerCreation = new ColorPicker();
        tileWidthCreation = new UnitInput("Width");
        tileHeightCreation = new UnitInput("Height");

        JPanel line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(tileColorPickerCreation);
        creationFrameRows.add(line);

        line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(tileNameCreation);
        creationFrameRows.add(line);

        line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(tileWidthCreation);
        line.add(tileHeightCreation);
        creationFrameRows.add(line);

        for (JPanel layout : creationFrameRows) {
            if (!isAncestorOf(layout)) {
                creationFrame.add(layout);
            }
        }
        /*creationFrame.add(tileNameCreation);
        creationFrame.add(tileColorPickerCreation);
        creationFrame.add(tileWidthCreation);
        creationFrame.add(tileHeightCreation);*/
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

        colorPicker.addInputListener(InputEventKind.OnChange, (color, self) -> {
            mainWindow.getController().setTypeOfTileColor(selectedTile, color);
            mainWindow.repaint();
        });

        creationButtons.get("Create").addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            creationFrame.setVisible(true);
        });

        numberPerPack.addInputListener(InputEventKind.OnChange, (value, self) -> {
            mainWindow.getController().setPackageSizeFor(selectedTile, Integer.parseInt(value));
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

                    int numberPerPack = mainWindow.getController().getPackageSizeFor(name);
                    this.numberPerPack.setText(Integer.toString(numberPerPack));
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

        setTypeOfTileButtonsOnPanel();
        setColorPickerOnPanel(line);
        setTextInputsOnPanel(line);
        setCreationButtonsOnPanel();
        setEvents();
    }

    private void setCreationButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));

        creationButtons.put("Create", new Button("Create", AssetLoader.loadImage("/icons/add-type-tile.png")));
        creationButtons.put("Delete", new Button("Delete", AssetLoader.loadImage("/icons/remove-type-tile.png")));

        creationButtons.forEach((key, value) -> {
            line.add(value);
        });
        rows.add(line);
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
        numberPerPack = new TextInput("Package Size", true);
        /*tileName = new TextInput("Tile name");*/

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(width);
        line.add(height);
        /*line.add(tileName);*/
        rows.add(line);

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(numberPerPack);
        rows.add(line);
    }

    public UnorderedMap<String, Button> getTilesType() {
        return tilesType;
    }
}
