package com.virtutuile.afficheur;

import java.awt.*;

public class Constants {

    private Constants() {
        throw new AssertionError();
    }

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

    // At 100% zoom -> 1px equals to 10cm
    public static final double NORMAL_ZOOM = 0.08d;
    public static final double WHEEL_TICK_RATIO = 5d;
}
