package com.virtutuile.graphics.wrap.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VBorderedPanel extends VPanel {

    protected final VBorderedPanel _margin = this;
    protected VPanel _border;
    protected VPanel _content;
    private SizingPolicy _sizingPolicy = SizingPolicy.ContentBox;
    public VBorderedPanel() {
        super();

        setLayout(new BorderLayout());
        _border = new VPanel();
        _border.setLayout(new BorderLayout());
        _content = new VPanel();
        _content.setLayout(new FlowLayout());
        _border.add(_content, BorderLayout.CENTER);
        super.add(_border, BorderLayout.CENTER);
    }

    public JComponent getBoxComponent() {
        return this._content;
    }

    public Insets getPadding() {
        return _content.getBorder().getBorderInsets(_content);
    }

    public void setPadding(int padding) {
        setPadding(new Insets(padding, padding, padding, padding));
    }

    public void setPadding(Insets padding) {
        Dimension minSize = super.getMinimumSize();
        minSize.width += padding.left + padding.right;
        minSize.height += padding.top + padding.bottom;

        if (_sizingPolicy == SizingPolicy.ContentBox) {
            Insets border = _border.getInsets();
            minSize.height += border.top + border.bottom;
            minSize.width += border.left + border.right;
            Insets pad = _content.getInsets();
            minSize.height += pad.top + pad.bottom;
            minSize.width += pad.left + pad.right;
        }
        super.setMinimumSize(new Dimension(minSize));
        _content.setBorder(new EmptyBorder(padding));
    }

    public void setPadding(int vertical, int horizontal) {
        setPadding(new Insets(vertical, horizontal, vertical, horizontal));
    }

    public void setPadding(int top, int left, int bottom, int right) {
        setPadding(new Insets(top, left, bottom, right));
    }

    public Insets getMargin() {
        return _content.getBorder().getBorderInsets(_content);
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
        if (this._content == null)
            return super.getBackground();
        return _content.getBackground();
    }

    @Override
    public void setBackground(Color bg) {
        if (this._content == null)
            super.setBackground(bg);
        else {
            _border.setOpaque(true);
            _border.setBackground(bg);
            _content.setOpaque(true);
            _content.setBackground(bg);
        }
    }

    public void sizingPolicy(SizingPolicy sizing) {
        _sizingPolicy = sizing;
    }

    @Override
    public Component add(Component comp) {
        return _content.add(comp);
    }

    @Override
    public Component add(String name, Component comp) {
        return _content.add(name, comp);
    }

    @Override
    public Component add(Component comp, int index) {
        return _content.add(comp, index);
    }    @Override
    public void setPreferredSize(Dimension preferredSize) {
        if (this._content == null) {
            super.setPreferredSize(preferredSize);
        } else {
            getSizingBox().setPreferredSize(preferredSize);
        }
    }

    @Override
    public void add(Component comp, Object constraints) {
        _content.add(comp, constraints);
    }    @Override
    public Dimension getPreferredSize() {
        if (this._content == null) {
            return super.getPreferredSize();
        }
        return getSizingBox().getPreferredSize();
    }

    @Override
    public void add(Component comp, Object constraints, int index) {
        _content.add(comp, constraints, index);
    }    @Override
    public void setMaximumSize(Dimension maximumSize) {
        if (this._content == null) {
            super.setMaximumSize(maximumSize);
        } else {
            getSizingBox().setMaximumSize(maximumSize);
        }
    }

    private VPanel getTarget(TargetEntity targetEntity) {
        switch (targetEntity) {
            case Border:
                return _border;
            case Margin:
                return _margin;
            case Content:
                return _content;
            default:
                throw new AssertionError("Unknown sizing policy");
        }
    }    @Override
    public Dimension getMaximumSize() {
        if (this._content == null) {
            return super.getMaximumSize();
        } else {
            return getSizingBox().getMaximumSize();
        }
    }

    public enum TargetEntity {
        Border,
        Content,
        Margin,
    }    @Override
    public void setMinimumSize(Dimension minimumSize) {
        if (this._content == null) {
            super.setMinimumSize(minimumSize);
        } else {
            getSizingBox().setMinimumSize(minimumSize);
        }
    }

    public enum SizingPolicy {
        ContentBox,
        MarginBox,
    }    @Override
    public Dimension getMinimumSize() {
        if (this._content == null) {
            return super.getMinimumSize();
        } else {
            return getSizingBox().getMinimumSize();
        }
    }













    private VPanel getSizingBox() {
        switch (_sizingPolicy) {
            case MarginBox:
                return _margin;
            case ContentBox:
                return _content;
            default:
                throw new AssertionError("Unknown sizing policy");
        }
    }
}
