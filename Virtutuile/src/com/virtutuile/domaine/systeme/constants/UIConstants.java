package com.virtutuile.domaine.systeme.constants;

import com.virtutuile.domaine.systeme.tools.AssetLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.function.Consumer;

public final class UIConstants {

    public static final Color TOOLBAR_BACKGROUND = new Color(66, 66, 66);
    public static final Color TOOLBAR_FONT_COLOR = new Color(255, 255, 255);

    public static final Color EDITIONPANEL_BACKGROUND = new Color(66, 66, 66);
    public static final Color EDITIONPANEL_FONT_COLOR = new Color(255, 255, 255);

    public static final Dimension SUBPANEL_SIZE = new Dimension(550, 300);
    public static final Color SUBPANEL_BACKGROUND = new Color(66, 66, 66);

    public static final Dimension BUTTON_SIZE = new Dimension(130, 80);
    public static final Dimension BUTTON_ICON_SIZE = new Dimension(45, 45);
    public static final Color BUTTON_NORMAL_COLOR = TOOLBAR_BACKGROUND;
    public static final Color BUTTON_HOVER_COLOR = new Color(94, 94, 94);
    public static final Color BUTTON_ACTIVE_COLOR = new Color(128, 128, 128);

    public static final Color DRAW_BACKGROUND = new Color(123, 123, 123);

    public static final Color NORMAL_SHAPE_FILL_COLOR = new Color(175, 175, 175);
    public static final Color NORMAL_SHAPE_BORDER_COLOR = Color.black;

    public static final Color INPUT_COLOR = DRAW_BACKGROUND;
    public static final Color INPUT_COLOR_INVALID = new Color(255, 89, 79, 255);
    public static final Color INPUT_BACKGROUND_LIGHTER = new Color(89, 89, 89);
    public static final Color INPUT_BACKGROUND_DARKER = new Color(51, 51, 51);

    public static final Color DEFAULT_SHAPE_FILL_COLOR = new Color(255, 215, 171);
    public static final int DEFAULT_SHAPE_BORDER_THICKNESS = 2;

    public static final class Mouse {
        public enum VCursor {
            Default,
            Pointer,
            Hand,
            Move,
            Rotate,
            RotateCW,
            RotateCCW,
            Resize,
            ResizeWE,
            ResizeNS,
            ResizeNESW,
            ResizeNWSE,
            Zoom,
            ZoomIn,
            ZoomOut,
        }

        private static final HashMap<VCursor, Consumer<JPanel>> _cursorSet = new HashMap<>() {{
            put(VCursor.Default, (self) -> self.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)));
            put(VCursor.Pointer, (self) -> self.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)));
            put(VCursor.Move, (self) -> self.setCursor(new Cursor(Cursor.MOVE_CURSOR)));
            put(VCursor.Hand, (self) -> self.setCursor(new Cursor(Cursor.HAND_CURSOR)));
            put(VCursor.Rotate, (self) -> self.setCursor(AssetLoader.loadCursor("/cursors/Rotate.png")));
            put(VCursor.RotateCW, (self) -> self.setCursor(AssetLoader.loadCursor("/cursors/RotateCW.png")));
            put(VCursor.RotateCCW, (self) -> self.setCursor(AssetLoader.loadCursor("/cursors/RotateCCW.png")));
            put(VCursor.Resize, (self) -> self.setCursor(AssetLoader.loadCursor("/cursors/Resize.png")));
            put(VCursor.ResizeNS, (self) -> self.setCursor(AssetLoader.loadCursor("/cursors/ResizeNS.png")));
            put(VCursor.ResizeWE, (self) -> self.setCursor(AssetLoader.loadCursor("/cursors/ResizeWE.png")));
            put(VCursor.ResizeNESW, (self) -> self.setCursor(AssetLoader.loadCursor("/cursors/ResizeNESW.png")));
            put(VCursor.ResizeNWSE, (self) -> self.setCursor(AssetLoader.loadCursor("/cursors/ResizeNWSE.png")));
            put(VCursor.Zoom, (self) -> self.setCursor(AssetLoader.loadCursor("/cursors/Zoom.png")));
            put(VCursor.ZoomIn, (self) -> self.setCursor(AssetLoader.loadCursor("/cursors/ZoomIn.png")));
            put(VCursor.ZoomOut, (self) -> self.setCursor(AssetLoader.loadCursor("/cursors/ZoomOut.png")));
        }};

        public static final void SetCursor(JPanel panel, VCursor cursor) {
            if (_cursorSet.containsKey(cursor)) {
                _cursorSet.get(cursor).accept(panel);
            } else {
                _cursorSet.get(VCursor.Default).accept(panel);
            }
        }
    }

    public static final class Gizmos {

        public static final class BoundingBoxes {

            public static final int STROKE = 1;
            public static final Color BOX_COLOR = new Color(122, 161, 235);
            public static final int EXPANSION_LENGTH = 12;
        }

        public static final class Handles {
            public static final Dimension SIZE = new Dimension(8, 8);
            public static final Color BACKGROUND_COLOR = Color.WHITE;
            public static final Color BORDER_COLOR = Color.BLACK;
            public static final int BORDER_STROKE = 2;

            private Handles() {
                throw new AssertionError();
            }
        }

        private Gizmos() {
            throw new AssertionError();
        }
    }

    private UIConstants() {
        throw new AssertionError();
    }
}