/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Legt einen <b>Rahmen</b> um eine enthaltene Kindbox.
 *
 * <p>
 * Die Konzeption der Klasse ist inspiriert von dem
 * <a href="https://en.wikipedia.org/wiki/CSS_box_model">CSS-Box-Model</a>.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public class FrameBox extends SingleChildBoxContainer
{

    /**
     * Die <b>Hintergrundfarbe</b> des Rahmens.
     *
     * @since 0.38.0
     */
    Color backgroundColor;

    /**
     * Der <b>Außenabstand</b> des Rahmens in Pixel.
     *
     * @since 0.38.0
     */
    int margin = 0;

    /**
     * Der <b>Innenabstand</b> des Rahmens in Pixel.
     *
     * @since 0.38.0
     */
    int padding = 0;

    /**
     * Die <b>Dicke der Linie</b> in Pixel.
     *
     * @since 0.38.0
     */
    int borderSize = 0;

    /**
     * Die <b>Farbe der Linie</b> in Pixel.
     *
     * @since 0.38.0
     */
    Color borderColor;

    /**
     * Erzeugt einen neuen Rahmen durch die Angabe der enthaltenen Kindbox.
     *
     * @param child Die Kindbox, die umrahmt werden soll.
     *
     * @since 0.38.0
     */
    public FrameBox(Box child)
    {
        super(child);
    }

    /* Setter */

    /**
     * Setzt die <b>Hintergrundfarbe</b> des Rahmens.
     *
     * @param backgroundColor Die <b>Hintergrundfarbe</b> des Rahmens.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.38.0
     */
    public FrameBox backgroundColor(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
        return this;
    }

    /**
     * Setzt den <b>Außenabstand</b> des Rahmens in Pixel.
     *
     * @param margin Der <b>Außenabstand</b> des Rahmens in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.38.0
     */
    public FrameBox margin(int margin)
    {
        this.margin = margin;
        return this;
    }

    /**
     * Setzt den <b>Innenabstand</b> des Rahmens in Pixel.
     *
     * @param padding Der <b>Innenabstand</b> des Rahmens in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.38.0
     */
    public FrameBox padding(int padding)
    {
        this.padding = padding;
        return this;
    }

    /**
     * Setzt die <b>Dicke der Linie</b> in Pixel. Ist die Linienfarbe noch nicht
     * festgelegt worden, so wird auf grau gesetzt.
     *
     * @param borderSize Die <b>Dicke der Linie</b> in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.38.0
     */
    public FrameBox borderSize(int borderSize)
    {
        if (borderColor == null)
        {
            borderColor = colors.get("grey");
        }
        this.borderSize = borderSize;
        return this;
    }

    /**
     * Setzt die <b>Farbe der Linie</b> in Pixel. Ist die Liniendicke noch nicht
     * festgelegt worden, so wird sie auf 1 Pixel gesetzt.
     *
     * @param borderColor Die <b>Farbe der Linie</b> in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.38.0
     */
    public FrameBox borderColor(Color borderColor)
    {
        if (borderSize == 0)
        {
            borderSize = 1;
        }
        this.borderColor = borderColor;
        return this;
    }

    /* Getter */

    /**
     * @since 0.38.0
     */
    private int outerContentSize()
    {
        return margin + padding + borderSize;
    }

    @Override
    int width()
    {
        return child.width() + 2 * outerContentSize();
    }

    @Override
    int height()
    {
        return child.height() + 2 * outerContentSize();
    }

    @Override
    void calculateAnchors()
    {
        child.x = x() + outerContentSize();
        child.y = y() + outerContentSize();
    }

    @Override
    void draw(Graphics2D g)
    {
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
        child.draw(g);
    }
}
