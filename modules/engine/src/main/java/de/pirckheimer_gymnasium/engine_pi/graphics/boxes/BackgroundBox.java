package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Unterlegt eine Kind-Box mit einer <b>Hintergrundfarbe</b>.
 *
 * @since 0.40.0
 */
public class BackgroundBox extends SingleChildBoxContainer
{
    /**
     * Die <b>Hintergrundfarbe</b>.
     *
     * @since 0.38.0
     */
    Color color = colors.get("white");

    public BackgroundBox(Box child)
    {
        super(child);
    }

    /* Setter */

    /**
     * Setzt die <b>Hintergrundfarbe</b>.
     *
     * @param color Die <b>Hintergrundfarbe</b>.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.40.0
     */
    public BackgroundBox color(Color color)
    {
        this.color = color;
        return this;
    }
    /* Getter */

    @Override
    protected void calculateDimension()
    {
        child.calculateDimension();
        width = child.width;
        height = child.height;
    }

    @Override
    protected void calculateAnchors()
    {
        child.x = x;
        child.y = y;
        child.calculateAnchors();
    }

    @Override
    void draw(Graphics2D g)
    {
        Color oldColor = g.getColor();
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(oldColor);
        child.draw(g);
    }
}
