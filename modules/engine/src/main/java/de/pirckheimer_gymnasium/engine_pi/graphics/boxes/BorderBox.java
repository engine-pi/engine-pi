package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Legt einen <b>Rahmen</b> um eine enthaltene Kind-Box.
 *
 * @since 0.40.0
 */
public class BorderBox extends SingleChildBoxContainer
{
    /**
     * Die <b>Dicke der Linie</b> in Pixel.
     *
     * @since 0.40.0
     */
    int lineWidth = 1;

    /**
     * Die <b>Farbe der Linie</b> in Pixel.
     *
     * @since 0.40.0
     */
    Color lineColor;

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
     * festgelegt worden, so wird auf grau gesetzt.
     *
     * @param lineWidth Die <b>Dicke der Linie</b> in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.40.0
     */
    public BorderBox lineWidth(int lineWidth)
    {
        if (lineColor == null)
        {
            lineColor = colors.get("grey");
        }
        this.lineWidth = lineWidth;
        return this;
    }

    /**
     * Setzt die <b>Farbe der Linie</b> in Pixel. Ist die Liniendicke noch nicht
     * festgelegt worden, so wird sie auf 1 Pixel gesetzt.
     *
     * @param lineColor Die <b>Farbe der Linie</b> in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.40.0
     */
    public BorderBox lineColor(Color lineColor)
    {
        if (lineWidth == 0)
        {
            lineWidth = 1;
        }
        this.lineColor = lineColor;
        return this;
    }

    /* Getter */

    @Override
    protected void calculateDimension()
    {
        child.calculateDimension();
        width = child.width + 2 * lineWidth;
        height = child.height + 2 * lineWidth;
    }

    @Override
    protected void calculateAnchors()
    {
        child.x = x + lineWidth;
        child.y = y + lineWidth;
    }

    @Override
    void draw(Graphics2D g)
    {

        // die Methode g.drawRect() macht Antialising (siehe unten)

        // ---
        // | |
        // ---
        Color oldColor = g.getColor();
        g.setColor(lineColor);
        // oben
        g.fillRect(// x
                x,
                // y
                y,
                // width
                width,
                // height
                lineWidth);
        // rechts
        g.fillRect(// x
                x + lineWidth + child.width,
                // y
                y + lineWidth,
                // width
                lineWidth,
                // height
                child.height);
        // unten
        g.fillRect(// x
                x,
                // y
                y + lineWidth + child.height,
                // width
                width,
                // height
                lineWidth);
        // links
        g.fillRect(// x
                x,
                // y
                y + lineWidth,
                // width
                lineWidth,
                // height
                child.height);
        g.setColor(oldColor);

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
        child.draw(g);
    }
}
