package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Legt einen <b>Rahmen</b> um eine enthaltene Kind-Box.
 *
 * @since 0.40.0
 */
public class BorderBox extends ChildBox
{
    /**
     * Die <b>Dicke der Linie</b> in Pixel.
     *
     * @since 0.40.0
     */
    int thickness = 1;

    /**
     * Die <b>Farbe der Linie</b> in Pixel.
     *
     * @since 0.40.0
     */
    Color color = colors.get("black");

    /**
     * Erzeugt einen neuen Rahmen durch die Angabe der enthaltenen Kind-Box.
     *
     * @param child Die <b>Kind-Box</b>, die umrahmt werden soll.
     *
     * @since 0.40.0
     */
    public BorderBox(Box child)
    {
        super(child);
    }

    /* Setter */

    /**
     * Setzt die <b>Dicke der Linie</b> in Pixel. Ist die Linienfarbe noch nicht
     * festgelegt worden, so wird auf <em>schwarz</em> gesetzt.
     *
     * @param thickness Die <b>Dicke der Linie</b> in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.40.0
     */
    public BorderBox thickness(int thickness)
    {
        if (color == null)
        {
            color = colors.get("black");
        }
        this.thickness = thickness;
        return this;
    }

    /**
     * Setzt die <b>Farbe der Linie</b> in Pixel. Ist die Liniendicke noch nicht
     * festgelegt worden, so wird sie auf 1 Pixel gesetzt.
     *
     * @param color Die <b>Farbe der Linie</b> in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.40.0
     */
    public BorderBox color(Color color)
    {
        if (thickness == 0)
        {
            thickness = 1;
        }
        this.color = color;
        return this;
    }

    /* Getter */

    @Override
    protected void calculateDimension()
    {
        child.calculateDimension();
        width = child.width + 2 * thickness;
        height = child.height + 2 * thickness;
    }

    @Override
    protected void calculateAnchors()
    {
        child.x = x + thickness;
        child.y = y + thickness;
        child.calculateAnchors();
    }

    @Override
    void draw(Graphics2D g)
    {
        // die Methode g.drawRect() macht Antialiasing
        // Lösung mit der Methode g.drawRect():
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

        // ---
        // | |
        // ---
        Color oldColor = g.getColor();
        g.setColor(color);
        // oben
        g.fillRect(// x
                x,
                // y
                y,
                // width
                width,
                // height
                thickness);
        // rechts
        g.fillRect(// x
                x + thickness + child.width,
                // y
                y + thickness,
                // width
                thickness,
                // height
                child.height);
        // unten
        g.fillRect(// x
                x,
                // y
                y + thickness + child.height,
                // width
                width,
                // height
                thickness);
        // links
        g.fillRect(// x
                x,
                // y
                y + thickness,
                // width
                thickness,
                // height
                child.height);
        g.setColor(oldColor);
        child.draw(g);
    }
}
