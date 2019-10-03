package com.virtutuile.constants;

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

    private UIConstants(){
        //this prevents even the native class from
        //calling this ctor as well :
        throw new AssertionError();
    }}
