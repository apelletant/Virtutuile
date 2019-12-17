package com.virtutuile.afficheur.panels;

import com.virtutuile.afficheur.MainWindow;
import com.virtutuile.afficheur.inputs.Button;
import com.virtutuile.afficheur.swing.BorderedPanel;
import com.virtutuile.afficheur.swing.events.MouseEventKind;
import com.virtutuile.afficheur.tools.AssetLoader;
import com.virtutuile.shared.UnorderedMap;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;

public class Toolbar extends BorderedPanel {

    private MainWindow mainWindow;
    private UnorderedMap<TargetButton, Button> buttons = new UnorderedMap<>();

    /**
     * VTopToolbar constructor
     */
    public Toolbar(MainWindow mainWindow) {
        super();
        this.mainWindow = mainWindow;
        setName("Toolbar");
        setBackground(new Color(66, 66, 66));

        setBorder(null);
        setButtonOnToolbar();
        setEvents();
    }

    private void setEvents() {
        Button edition = buttons.get(TargetButton.SurfaceManagement);
        Button tileSettings = buttons.get(TargetButton.TileSettings);
        Button infos = buttons.get(TargetButton.CanvasInfos);
        Button loadCanvas = buttons.get(TargetButton.LoadCanvas);
        Button saveCanvas = buttons.get(TargetButton.SaveCanvas);
        Button undo = buttons.get(TargetButton.Undo);
        Button redo = buttons.get(TargetButton.Redo);
        Button newCanvas = buttons.get(TargetButton.NewCanvas);

        edition.setActive(true);
        edition.addMouseEventListener(MouseEventKind.MouseLClick, (mouseEvent -> {
            if (edition.isActive()) {
                edition.setActive(false);
                mainWindow.getEditionPanel().removeAllSurfacePanels();
            } else {
                edition.setActive(true);
                tileSettings.setActive(false);
                infos.setActive(false);
                mainWindow.getEditionPanel().removeTileSettingsPanel();
                mainWindow.getEditionPanel().removeInfoPanel();
                mainWindow.getEditionPanel().addAllPanels();
            }
            mainWindow.revalidate();
            mainWindow.repaint();
        }));

        tileSettings.addMouseEventListener(MouseEventKind.MouseLClick, (mouseEvent -> {
            if (tileSettings.isActive()) {
                tileSettings.setActive(false);
                mainWindow.getEditionPanel().removeTileSettingsPanel();
            } else {
                tileSettings.setActive(true);
                edition.setActive(false);
                infos.setActive(false);
                mainWindow.getEditionPanel().removeAllSurfacePanels();
                mainWindow.getEditionPanel().removeInfoPanel();
                mainWindow.getEditionPanel().addTileSettingsPanel();
            }
            mainWindow.revalidate();
            mainWindow.repaint();
        }));

        infos.addMouseEventListener(MouseEventKind.MouseLClick, (mouseEvent -> {
            if (infos.isActive()) {
                infos.setActive(false);
                mainWindow.getEditionPanel().removeInfoPanel();
            } else {
                infos.setActive(true);
                edition.setActive(false);
                tileSettings.setActive(false);
                mainWindow.getEditionPanel().removeAllSurfacePanels();
                mainWindow.getEditionPanel().removeTileSettingsPanel();
                mainWindow.getEditionPanel().addInfoPanel();
            }
            mainWindow.revalidate();
            mainWindow.repaint();
        }));

        loadCanvas.addMouseEventListener(MouseEventKind.MouseLClick, (mouseEvent -> {
            JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            fc.setDialogTitle("Select canvas file to load");
            fc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Virtutuile file extentions \"vtt\"", "vtt");
            fc.addChoosableFileFilter(filter);

            int returnValue = fc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                mainWindow.getController().loadCanvas(fc.getSelectedFile().getPath());
                mainWindow.refreshGUI();
            }
            mainWindow.revalidate();
            mainWindow.repaint();
        }));

        saveCanvas.addMouseEventListener(MouseEventKind.MouseLClick, (mouseEvent -> {
            JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            fc.setDialogTitle("Save your project");
            fc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Virtutuile file extentions \"vtt\"", "vtt");
            fc.addChoosableFileFilter(filter);

            int returnValue = fc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                mainWindow.getController().saveCanvas(fc.getSelectedFile().getPath());
            }
            mainWindow.revalidate();
            mainWindow.repaint();
        }));

        undo.addMouseEventListener(MouseEventKind.MouseLClick, (mouseEvent -> {
            mainWindow.getController().undo();
            mainWindow.repaint();
        }));

        redo.addMouseEventListener(MouseEventKind.MouseLClick, (mouseEvent -> {
            mainWindow.getController().redo();
            mainWindow.repaint();
        }));

        newCanvas.addMouseEventListener(MouseEventKind.MouseLClick, (mouseEvent -> {
            mainWindow.getController().newCanvas();
            mainWindow.refreshGUI();
            mainWindow.getEditionPanel().getTileSettingsPanel().rethinkMenu();
            mainWindow.getEditionPanel().getTilePanel().rethinkMenu();
            mainWindow.getEditionPanel().getInfoPanel().rethinkMenu();
            mainWindow.repaint();
        }));
    }

    /**
     * A Method to set buttons on the toolbar
     *
     * @return void
     */
    private void setButtonOnToolbar() {
        //New Canvas Button
        buttons.put(TargetButton.NewCanvas, new Button("New Canvas", AssetLoader.loadImage("/icons/file.png")));

        //Load Canvas Button
        buttons.put(TargetButton.LoadCanvas, new Button("Load Canvas", AssetLoader.loadImage("/icons/folder-open.png")));

        //Save Canvas Button
        buttons.put(TargetButton.SaveCanvas, new Button("Save Canvas", AssetLoader.loadImage("/icons/save.png")));

        //Undo Canvas Button
        buttons.put(TargetButton.Undo, new Button("Undo", AssetLoader.loadImage("/icons/undo-alt.png")));

        //Redo Canvas Button
        buttons.put(TargetButton.Redo, new Button("Redo", AssetLoader.loadImage("/icons/redo-alt.png")));

        //Canvas Settings Button
        buttons.put(TargetButton.SurfaceManagement, new Button("Surfaces", AssetLoader.loadImage("/icons/pencil-alt.png")));

        //Canvas Settings Button
        buttons.put(TargetButton.TileSettings, new Button("Tile Settings", AssetLoader.loadImage("/icons/cog.png")));

        //Canvas infos button
        buttons.put(TargetButton.CanvasInfos, new Button("Canvas Infos", AssetLoader.loadImage("/icons/info.png")));

        buttons.forEach((key, value) -> {
            if (key == TargetButton.CanvasInfos)
                return;
            add(value);
        });
        add(Box.createHorizontalGlue());
        add(Box.createVerticalGlue());
        add(buttons.get(TargetButton.CanvasInfos));
    }

    public Button getButton(TargetButton name) {
        return buttons.get(name);
    }

    public enum TargetButton {
        NewCanvas,
        LoadCanvas,
        SaveCanvas,
        Undo,
        Redo,
        SurfaceManagement,
        TileSettings,
        CanvasInfos,
    }
}
