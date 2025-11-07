package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Folgt dem
 * <a href="https://en.wikipedia.org/wiki/CSS_box_model">CSS-Box-Model</a>
 */
public class RectangleBox extends SingleChildBoxContainer
{

    Color backgroundColor;

    /**
     * Der Außenabstand in Pixel.
     */
    int margin = 0;

    /**
     * Der Innenabstand in Pixel.
     */
    int padding = 0;

    /**
     * Die Dicke der Linie
     */
    int borderSize = 0;

    Color borderColor;

    public RectangleBox(Box child)
    {
        super(child);
    }

    /* Setter */

    public RectangleBox backgroundColor(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public RectangleBox margin(int margin)
    {
        this.margin = margin;
        return this;
    }

    public RectangleBox padding(int padding)
    {
        this.padding = padding;
        return this;
    }

    public RectangleBox borderSize(int borderSize)
    {
        if (borderColor == null)
        {
            borderColor = colors.get("grey");
        }
        this.borderSize = borderSize;
        return this;
    }

    public RectangleBox borderColor(Color borderColor)
    {
        if (borderSize == 0)
        {
            borderSize = 1;
        }
        this.borderColor = borderColor;
        return this;
    }

    /* Getter */

    private int outerContentSize()
    {
        return margin + padding + borderSize;
    }

    @Override
    public int width()
    {
        return child.width() + 2 * outerContentSize();
    }

    @Override
    public int height()
    {
        return child.height() + 2 * outerContentSize();
    }

    @Override
    public void calculateAnchors()
    {
        child.x = x() + outerContentSize();
        child.y = y() + outerContentSize();
    }

    @Override
    public void render(Graphics2D g)
    {
        calculateAnchors();
        if (backgroundColor != null)
        {
            Color oldColor = g.getColor();
            g.setColor(backgroundColor);
            int outer = margin + borderSize;
            g.fillRect(x() + outer, y() + outer, width() - 2 * outer,
                    height() - 2 * outer);
            g.setColor(oldColor);
        }

        if (borderColor != null && borderSize > 0)
        {

            // die Methode g.drawRect() macht Antialising (siehe unten)

            // ---
            // | |
            // ---
            Color oldColor = g.getColor();
            g.setColor(borderColor);
            // oben
            g.fillRect(// x
                    x + margin,
                    // y
                    y + margin,
                    // width
                    width() - 2 * margin,
                    // height
                    borderSize);
            // rechts
            g.fillRect(// x
                    x + outerContentSize() + child.width() + padding,
                    // y
                    y + margin + borderSize,
                    // width
                    borderSize,
                    // height
                    child.height() + 2 * padding);
            // unten
            g.fillRect(// x
                    x + margin,
                    // y
                    y + outerContentSize() + child.height() + padding,
                    // width
                    width() - 2 * margin,
                    // height
                    borderSize);
            // links
            g.fillRect(// x
                    x + margin,
                    // y
                    y + margin + borderSize,
                    // width
                    borderSize,
                    // height
                    child.height() + 2 * padding);
            g.setColor(oldColor);
        }

        // Lösung mit der Methode g.drawRect(): macht Antialising
        // setzt die Linie irgendwie mittig
        // if (borderColor != null && borderSize > 0)
        // {
        // Color oldColor = g.getColor();
        // g.setColor(borderColor);
        // Stroke oldStroke = g.getStroke();
        // g.setStroke(new BasicStroke(borderSize));
        // g.drawRect(x() + margin, y() + margin, width() - 2 * margin,
        // height() + 2 * margin);
        // g.setColor(oldColor);
        // g.setStroke(oldStroke);
        // }
        child.render(g);
    }
}
