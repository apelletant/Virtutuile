package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.Constants;
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
    private Vector<JPanel> tileLines = new Vector<>();

    private TileCreation tileCreation = null;
    private JFrame creationFrame = null;


    public TileSettingsPanel(String name, MainWindow mainWindow) {
        super(name, mainWindow);
        tileCreation = new TileCreation("Create a Tile", mainWindow);
        setUpCreationFrame();
        setButtonsOnPanel();
        setEvents();
        persistLayout();
    }

    private void setUpCreationFrame() {
        creationFrame = new JFrame();
        creationFrame.setTitle("Creation Frame");
        creationFrame.setSize(600, 520);
        creationFrame.setBounds(0, 0, 600, 520);
        creationFrame.setBackground(Constants.EDITIONPANEL_BACKGROUND);
        creationFrame.setForeground(Constants.EDITIONPANEL_BACKGROUND);
        creationFrame.add(tileCreation);
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
            tileCreation = new TileCreation("Create a Tile", mainWindow);
            creationFrame.setVisible(true);
        });

        creationButtons.get("Delete").addMouseEventListener(MouseEventKind.MouseLClick, (event) -> {
            if (mainWindow.getController().deleteTile(selectedTile)) {
                rethinkMenu();
                mainWindow.getEditionPanel().getTilePanel().rethinkMenu();
                mainWindow.getEditionPanel().getInfoPanel().rethinkMenu();
            }
        });


        numberPerPack.addInputListener(InputEventKind.OnChange, (value, self) -> {
            mainWindow.getController().setPackageSizeFor(selectedTile, Integer.parseInt(value));
        });

        /*tileName.addInputListener(InputEventKind.OnChange, (value, self) -> {
            mainWindow.getController().renameTile(value, selectedTile);
            repaint();
        });*/
        setTilesTypeEvents();
    }

    private void setTilesTypeEvents() {
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

        setColorPickerOnPanel(line);
        setTextInputsOnPanel(line);
        setCreationButtonsOnPanel();
        setTypeOfTileButtonsOnPanel();
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
        tileLines.add(line);
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
        width = new UnitInput("Width", true, "double");
        height = new UnitInput("Height", true, "double");
        numberPerPack = new TextInput("Package Size", true, "integer");
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

    public JFrame getCreationFrame() {
        return creationFrame;
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
        setTilesTypeEvents();
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
