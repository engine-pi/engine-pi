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

import java.awt.Color;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/FramedTextBoxDemo.java

/**
 * Legt einen <b>Rahmen</b> um eine enthaltene Kind-Box.
 *
 * <p>
 * Die Konzeption der Klasse ist inspiriert von dem
 * <a href="https://en.wikipedia.org/wiki/CSS_box_model">CSS-Box-Model</a>.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class FramedTextBox extends CombinedChildBox
{
    private MarginBox margin;

    private BorderBox border;

    private BackgroundBox background;

    private MarginBox padding;

    private TextLineBox text;

    public FramedTextBox(String content)
    {
        text = new TextLineBox(content);
        padding = new MarginBox(text);
        background = new BackgroundBox(padding);
        border = new BorderBox(background);
        margin = new MarginBox(border);
        addChild(margin);
    }

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
    public FramedTextBox backgroundColor(Color backgroundColor)
    {
        background.color(backgroundColor);
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
    public FramedTextBox margin(int margin)
    {
        this.margin.margin(margin);
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
    public FramedTextBox padding(int padding)
    {
        this.padding.margin(padding);
        return this;
    }

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
     * @see BorderBox#thickness(int)
     *
     * @since 0.38.0
     */
    public FramedTextBox borderThickness(int thickness)
    {
        this.border.thickness(thickness);
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
    public FramedTextBox borderColor(Color borderColor)
    {
        this.border.color(borderColor);
        return this;
    }

    /**
     * Setzt den <b>Inhalt</b> und berechnet dabei die Abmessungen neu.
     *
     * @param content Der <b>Inhalt</b> der Textbox als Zeichenkette.
     *
     * @see TextLineBox#content(String)
     *
     * @since 0.39.0
     */
    public FramedTextBox content(String content)
    {
        this.text.content(content);
        return this;
    }

    public FramedTextBox textColor(Color color)
    {
        this.text.color(color);
        return this;
    }

}
