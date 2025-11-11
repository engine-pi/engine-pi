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

import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Eine <b>Box</b> beschreibt eine rechteckige graphische Fläche, die weitere
 * Kinder-Boxen enthalten kann.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public abstract class Box
{

    /**
     * Zeigt an, ob die Box bereits ausgemessen wurde.
     */
    private boolean measured;

    /**
     * Die <b>Breite</b> der Box in Pixel.
     *
     * @since 0.40.0
     */
    protected int width;

    /**
     * Die <b>Höhe</b> der Box in Pixel.
     *
     * @since 0.40.0
     */
    protected int height;

    /**
     * Die <b>x</b>-Koordinate der linken oberen Ecke in Pixel.
     *
     * @since 0.38.0
     */
    protected int x;

    /**
     * Die <b>y</b>-Koordinate der linken oberen Ecke in Pixel.
     *
     * @since 0.38.0
     */
    protected int y;

    /**
     * Die <b>übergeordnet</b> Box, in der diese Box enthalten ist. Dieses
     * Attribut kann {@code null} sein, wenn keine Elternbox vorhanden ist.
     *
     * @since 0.38.0
     */
    protected Box parent;

    /* Setter */

    /**
     * Setzt die <b>x</b>-Koordinate der linken oberen Ecke in Pixel.
     *
     * @param x Die <b>x</b>-Koordinate der linken oberen Ecke in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.38.0
     */
    public Box x(int x)
    {
        this.x = x;
        return this;
    }

    /**
     * Setzt die <b>y</b>-Koordinate der linken oberen Ecke in Pixel.
     *
     * @param y Die <b>y</b>-Koordinate der linken oberen Ecke in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.38.0
     */
    public Box y(int y)
    {
        this.y = y;
        return this;
    }

    /**
     * Setzt die <b>x</b>- und <b>y</b>-Koordinate der linken oberen Ecke in
     * Pixel.
     *
     * @param x Die <b>x</b>-Koordinate der linken oberen Ecke in Pixel.
     * @param y Die <b>y</b>-Koordinate der linken oberen Ecke in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.38.0
     */
    public Box anchor(int x, int y)
    {
        this.x = x;
        this.y = y;
        return this;
    }

    /* Methods */

    /**
     * Berechnet rekursive die Höhe und Breite.
     */
    protected abstract void calculateDimension();

    /**
     * Berechnet rekursiv alle Ankerpunkte (linkes oberes Eck) der
     * untergeordneten Kinder-Boxen. Die inneren Blattboxen brauchen diese
     * Methode nicht zu implementieren.
     *
     * @since 0.38.0
     */
    protected abstract void calculateAnchors();

    /**
     * Bestimmt rekursive zu erst die Abmessngen (Höhe und Breite) und
     * anschließend die Ankerpunkte (x, y) der Kind-Boxen.
     */
    void measure()
    {
        calculateDimension();
        calculateAnchors();
        measured = true;
    }

    /**
     * <b>Zeichnet</b> die Box.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     *
     * @since 0.38.0
     */
    abstract void draw(Graphics2D g);

    /**
     * Berechnet die <b>Abmessungen</b> und <b>Ankerpunkte</b> und
     * <b>zeichnet</b> die Box.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     *
     * @since 0.38.0
     */
    public void render(Graphics2D g)
    {
        if (!measured)
        {
            measure();
        }
        draw(g);
    }

    /* static instantiation methods */

    /**
     * Erzeugt einen neuen Rahmen durch die Angabe der enthaltenen Kind-Box.
     *
     * @param child Die <b>Kind-Box</b>, die umrahmt werden soll.
     *
     * @since 0.39.0
     *
     * @see FrameBox#FrameBox(Box)
     */
    public static FrameBox frame(Box child)
    {
        return new FrameBox(child);
    }

    /**
     * Erzeugt eine neue <b>horizontale</b> Box.
     *
     * @param childs Die Kinder-Boxen, die <b>horizontal</b> von links nach
     *     rechts angeordnet werden sollen.
     *
     * @since 0.39.0
     *
     * @see HorizontalBox#HorizontalBox(Box...)
     */
    public static HorizontalBox horizontal(Box... childs)
    {
        return new HorizontalBox(childs);
    }

    /**
     * Erzeugt eine <b>Text</b>box.
     *
     * @param content Der <b>Inhalt</b> der Textbox als Zeichenkette.
     *
     * @since 0.39.0
     *
     * @see TextBox#TextBox(String)
     */
    public static TextBox text(String content)
    {
        return new TextBox(content);
    }

    /**
     * Erzeugt eine <b>Text</b>box.
     *
     * @param content Der <b>Inhalt</b> der Textbox als Zeichenkette.
     * @param font Die <b>Schriftart</b>, in der der Inhalt dargestellt werden
     *     soll.
     *
     * @since 0.39.0
     *
     * @see TextBox#TextBox(String, Font)
     */
    public static TextBox text(String content, Font font)
    {
        return new TextBox(content, font);
    }

    /**
     * Erzeugt eine neue <b>vertikale</b> Box.
     *
     * @param childs Die Kinder-Boxen, die <b>vertikal</b> von oben nach unten
     *     angeordnet werden sollen.
     *
     * @since 0.39.0
     *
     * @see VerticalBox#VerticalBox(Box...)
     */
    public static VerticalBox vertical(Box... childs)
    {
        return new VerticalBox(childs);
    }
}
