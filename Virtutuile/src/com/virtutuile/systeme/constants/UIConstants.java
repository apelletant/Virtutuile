package com.virtutuile.systeme.constants;

import java.awt.*;

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