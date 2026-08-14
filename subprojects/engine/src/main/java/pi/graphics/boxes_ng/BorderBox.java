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
package pi.graphics.boxes_ng;

import static pi.Controller.colors;
import static pi.util.MathUtil.round;

import java.awt.Color;
import java.awt.Graphics2D;

import pi.annotations.ChainableMethod;
import pi.annotations.Setter;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/boxes_ng/BorderBoxDemo.java

/**
 * Legt einen <b>Rahmen</b> um eine enthaltene Kind-Box.
 *
 * @since 0.40.0
 */
public class BorderBox extends ChildBox
{
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

    /* thickness */

    /**
     * Die <b>Dicke der Linie</b> in Pixel.
     *
     * @since 0.40.0
     */
    double thickness = 0;

    /**
     * Gibt die <b>Linienstärke</b> des Rahmens in Pixeln zurück.
     *
     * @return Die <b>Linienstärke</b> des Rahmens in Pixeln.
     *
     * @since 0.40.0
     */
    public int thickness()
    {
        return round(thickness);
    }

    /**
     * Setzt die <b>Dicke der Linie</b> in Pixel. Ist die Linienfarbe noch nicht
     * festgelegt worden, so wird auf <em>schwarz</em> gesetzt.
     *
     * @param thickness Die <b>Dicke der Linie</b> in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.40.0
     */
    @Setter
    @ChainableMethod
    public BorderBox thickness(double thickness)
    {
        if (color == null)
        {
            color = colors.get("black");
        }
        this.thickness = thickness;
        return this;
    }

    /**
     * Setzt die <b>Linienstärke</b> des Rahmens in Metern.
     *
     * @param thickness Die <b>Linienstärke</b> des Rahmens in Metern.
     *
     * @return Eine Referenz auf die eigene Instanz der Box.
     *
     * @since 0.53.0
     */
    public BorderBox thicknessMeter(double thickness)
    {
        return thickness(thickness * pixelPerMeter);
    }

    /* color */

    /**
     * Die <b>Farbe der Linie</b> in Pixel.
     *
     * @since 0.40.0
     */
    Color color = null;

    /**
     * Setzt die <b>Farbe der Linie</b> in Pixel.
     *
     * <p>
     * Ist die Liniendicke noch nicht festgelegt worden, so wird sie auf 1 Pixel
     * gesetzt.
     * </p>
     *
     * @param color Die <b>Farbe der Linie</b> in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.40.0
     */
    @Setter
    @ChainableMethod
    public BorderBox color(Color color)
    {
        if (thickness == 0)
        {
            thickness = 1;
        }
        this.color = color;
        return this;
    }

    /**
     * Setzt die <b>Farbe der Linie</b> in Pixel.
     *
     * <p>
     * Ist die Liniendicke noch nicht festgelegt worden, so wird sie auf 1 Pixel
     * gesetzt.
     * </p>
     *
     * @param color Ein Farbname ({@link pi.resources.color.ColorContainer siehe
     *     Auflistung}) oder eine Farbe in hexadezimaler Codierung (z.B.
     *     {@code #ff0000}).
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     */
    @Setter
    @ChainableMethod
    public BorderBox color(String color)
    {
        return color(colors.get(color));
    }

    /**
     * Setzt den Umrechnungsfaktor für die Darstellung von Metern in Pixel und
     * passt zusätzlich die Rahmendicke an.
     *
     * @param pixelPerMeter Die Anzahl der Pixel pro Meter. Der Wert muss größer
     *     als {@code 0} sein.
     *
     * @return Eine Referenz auf die eigene Instanz der Box.
     *
     * @since 0.53.0
     */
    @Setter
    @ChainableMethod
    @Override
    public Box pixelPerMeter(double pixelPerMeter)
    {
        thickness = updateValue(thickness, pixelPerMeter);
        super.pixelPerMeter(pixelPerMeter);
        return this;
    }

    @Override
    protected void calculateDimension()
    {
        width = child.width + 2 * thickness;
        height = child.height + 2 * thickness;
    }

    @Override
    protected void calculateAnchors()
    {
        child.x = x + thickness;
        child.y = y - thickness;
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
        if (color != null && thickness > 0)
        {
            Color oldColor = g.getColor();
            g.setColor(color);
            // oben
            g.fillRect(// x
                x(),
                // y
                yTop(),
                // width
                width(),
                // height
                thickness());
            // rechts
            g.fillRect(// x
                x() + thickness() + child.width(),
                // y
                yTop() + thickness(),
                // width
                thickness(),
                // height
                child.height());
            // unten
            g.fillRect(// x
                x(),
                // y
                y() - thickness(),
                // width
                width(),
                // height
                thickness());
            // links
            g.fillRect(// x
                x(),
                // y
                yTop() + thickness(),
                // width
                thickness(),
                // height
                child.height());
            g.setColor(oldColor);
        }
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        var formatter = toStringFormatter();
        if (color != null)
        {
            formatter.prepend("color", color);
        }
        if (thickness > 0)
        {
            formatter.prepend("thickness", thickness);
        }
        return formatter.format();
    }
}
