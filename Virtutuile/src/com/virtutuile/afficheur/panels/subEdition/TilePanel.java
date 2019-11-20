package com.virtutuile.afficheur.panels.subEdition;

import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.inputs.UnitInput;
import com.virtutuile.afficheur.swing.Panel;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.domaine.Controller;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class TilePanel extends SubPanel {

    private JColorChooser colorPicker = null;
    private UnitInput width = null;
    private UnitInput height = null;
    private UnorderedMap<String, Button> tilesType = new UnorderedMap<>();

    public TilePanel(String name, Controller controller) {
        super(name, controller);
        this.setButtonsOnPanel();
        this.setEvents();
        this.persistLayout();
    }

    @Override
    protected void setEvents() {
    }

    @Override
    protected void setButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setSize(100, 100);

        setColorPickerOnPanel(line);
        setThicknessInputOnPanel(line);
        setTypeOfTileButtonsOnPanel();
    }

    private void setTypeOfTileButtonsOnPanel() {
        JPanel line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));

        /*createNewTile(20, 20, Constants.DEFAULT_SHAPE_FILL_COLOR, "Square", false);
        createNewTile(20, 10, Constants.DEFAULT_SHAPE_FILL_COLOR, "Small", false);
        createNewTile(40, 20, Constants.DEFAULT_SHAPE_FILL_COLOR, "Medium", false);
        createNewTile(80, 40, Constants.DEFAULT_SHAPE_FILL_COLOR, "Large", false);
        createNewTile(140, 80, Constants.DEFAULT_SHAPE_FILL_COLOR, "Extra Large", false);*/

        tilesType.put("Small", new Button("Small", AssetLoader.loadImage("/icons/shape-edit-add-square.png")));
        tilesType.put("Medium", new Button("Medium", AssetLoader.loadImage("/icons/shape-edit-add-square.png")));
        tilesType.put("Large", new Button("Large", AssetLoader.loadImage("/icons/shape-edit-add-square.png")));

        tilesType.forEach((key, value) -> {
            line.add(value);
        });
    }

    private void setColorPickerOnPanel(JPanel line) {
        colorPicker = new JColorChooser();
        colorPicker.setMaximumSize(new Dimension(500,200));
        colorPicker.setMinimumSize(new Dimension(500,200));
        colorPicker.setPreferredSize(new Dimension(500,200));
        AbstractColorChooserPanel[] panels = colorPicker.getChooserPanels();
        colorPicker.setChooserPanels(new AbstractColorChooserPanel[] { panels[0] });
        line.add(colorPicker);
        rows.add(line);
    }

    private void setThicknessInputOnPanel(JPanel line) {
        width = new UnitInput("Width");
        height = new UnitInput("Height");

        line = new Panel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.add(width);
        line.add(height);
        rows.add(line);
    }

    enum GroutButtonType {
        ColorPicker,
        Thickness
    }
}
