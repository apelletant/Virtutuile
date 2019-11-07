package com.virtutuile.afficheur.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BorderedPanel extends Panel {

    protected final BorderedPanel margin = this;
    protected Panel border;
    protected Panel content;
    private SizingPolicy sizingPolicy = SizingPolicy.ContentBox;

    public BorderedPanel() {
        super();

        setLayout(new GridLayout(0, 1));
        border = new Panel();
        border.setLayout(new GridLayout(0, 1));
        content = new Panel();
        content.setLayout(new BoxLayout(content, BoxLayout.LINE_AXIS));
        border.add(content);
        super.add(border);
    }

    public JComponent getBoxComponent() {
        return content;
    }

    public Insets getPadding() {
        return content.getBorder().getBorderInsets(content);
    }

    public void setPadding(int padding) {
        setPadding(new Insets(padding, padding, padding, padding));
    }

    public void setPadding(Insets padding) {
        Dimension minSize = super.getMinimumSize();
        minSize.width += padding.left + padding.right;
        minSize.height += padding.top + padding.bottom;

        if (sizingPolicy == SizingPolicy.ContentBox) {
            Insets border = this.border.getInsets();
            minSize.height += border.top + border.bottom;
            minSize.width += border.left + border.right;
            Insets pad = content.getInsets();
            minSize.height += pad.top + pad.bottom;
            minSize.width += pad.left + pad.right;
        }
        super.setMinimumSize(new Dimension(minSize));
        content.setBorder(new EmptyBorder(padding));
    }

    public void setPadding(int vertical, int horizontal) {
        setPadding(new Insets(vertical, horizontal, vertical, horizontal));
    }

    public void setPadding(int top, int left, int bottom, int right) {
        setPadding(new Insets(top, left, bottom, right));
    }

    public Insets getMargin() {
        return content.getBorder().getBorderInsets(content);
    }

    public void setMargin(int margin) {
        setBorder(new EmptyBorder(margin, margin, margin, margin));
    }

    public void setMargin(int vertical, int horizontal) {
        setBorder(new EmptyBorder(vertical, horizontal, vertical, horizontal));
    }

    public void setMargin(int top, int left, int bottom, int right) {
        setBorder(new EmptyBorder(top, left, bottom, right));
    }

    @Override
    public Color getBackground() {
        if (this.content == null)
            return super.getBackground();
        return content.getBackground();
    }

    @Override
    public void setBackground(Color bg) {
        if (this.content == null)
            super.setBackground(bg);
        else {
            border.setOpaque(true);
            border.setBackground(bg);
            content.setOpaque(true);
            content.setBackground(bg);
        }
    }

    public void sizingPolicy(SizingPolicy sizing) {
        sizingPolicy = sizing;
    }

    @Override
    public Component add(Component comp) {
        return content.add(comp);
    }

    @Override
    public Component add(String name, Component comp) {
        return content.add(name, comp);
    }

    @Override
    public Component add(Component comp, int index) {
        return content.add(comp, index);
    }    @Override
    public void setPreferredSize(Dimension preferredSize) {
        if (this.content == null) {
            super.setPreferredSize(preferredSize);
        } else {
            getSizingBox().setPreferredSize(preferredSize);
        }
    }

    @Override
    public void add(Component comp, Object constraints) {
        content.add(comp, constraints);
    }    @Override
    public Dimension getPreferredSize() {
        if (this.content == null) {
            return super.getPreferredSize();
        }
        return getSizingBox().getPreferredSize();
    }

    @Override
    public void add(Component comp, Object constraints, int index) {
        content.add(comp, constraints, index);
    }

    @Override
    public void setMaximumSize(Dimension maximumSize) {
        if (this.content == null) {
            super.setMaximumSize(maximumSize);
        } else {
            getSizingBox().setMaximumSize(maximumSize);
        }
    }

    private Panel getTarget(TargetEntity targetEntity) {
        switch (targetEntity) {
            case Border:
                return border;
            case Margin:
                return margin;
            case Content:
                return content;
            default:
                throw new AssertionError("Unknown sizing policy");
        }
    }

    @Override
    public Dimension getMaximumSize() {
        if (this.content == null) {
            return super.getMaximumSize();
        } else {
            return getSizingBox().getMaximumSize();
        }
    }

    public enum TargetEntity {
        Border,
        Content,
        Margin,
    }
    @Override
    public void setMinimumSize(Dimension minimumSize) {
        if (this.content == null) {
            super.setMinimumSize(minimumSize);
        } else {
            getSizingBox().setMinimumSize(minimumSize);
        }
    }

    public enum SizingPolicy {
        ContentBox,
        MarginBox,
    }

    @Override
    public Dimension getMinimumSize() {
        if (this.content == null) {
            return super.getMinimumSize();
        } else {
            return getSizingBox().getMinimumSize();
        }
    }

    private Panel getSizingBox() {
        switch (sizingPolicy) {
            case MarginBox:
                return margin;
            case ContentBox:
                return content;
            default:
                throw new AssertionError("Unknown sizing policy");
        }
    }
}
