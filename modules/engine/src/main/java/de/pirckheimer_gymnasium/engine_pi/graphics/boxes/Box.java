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

import java.awt.Graphics2D;

/**
 * Eine <b>Box</b> beschreibt eine rechteckige graphische Fläche, die weitere
 * Kindboxen enthalten kann.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public abstract class Box
{

    /**
     * Die <b>x</b>-Koordinate der linken oberen Ecke in Pixel.
     *
     * @since 0.38.0
     */
    protected int x = Integer.MIN_VALUE;

    /**
     * Die <b>y</b>-Koordinate der linken oberen Ecke in Pixel.
     *
     * @since 0.38.0
     */
    protected int y = Integer.MIN_VALUE;

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
     *
     * @param x
     * @param y
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

    /* Getter */

    /**
     * Gibt die <b>x</b>-Koordinate der linken oberen Ecke in Pixel zurück.
     *
     * @return Die <b>x</b>-Koordinate der linken oberen Ecke in Pixel.
     *
     * @throws RuntimeException Falls die x<b>-Koordinate noch nicht gesetzt
     *     wurde. </b>
     *
     * @since 0.38.0
     */
    public int x()
    {
        if (x == Integer.MIN_VALUE)
        {
            throw new RuntimeException(
                    "Die x-Koordinate wurde noch nicht gesetzt.");
        }
        return x;
    }

    /**
     * Gibt die <b>y</b>-Koordinate der linken oberen Ecke in Pixel zurück.
     *
     * @return Die <b>y</b>-Koordinate der linken oberen Ecke in Pixel.
     *
     * @since 0.38.0
     */
    public int y()
    {
        if (y == Integer.MIN_VALUE)
        {
            throw new RuntimeException(
                    "Die y-Koordinate wurde noch nicht gesetzt.");
        }
        return y;
    }

    /**
     * Gibt die <b>Breite</b> der Box in Pixel zurück.
     *
     * @return Die <b>Breite</b> der Box in Pixel.
     *
     * @since 0.38.0
     */
    abstract int width();

    /**
     * Gibt die <b>Höhe</b> der Box in Pixel zurück.
     *
     * @return Die <b>Höhe</b> der Box in Pixel.
     *
     * @since 0.38.0
     */
    abstract int height();

    /* Methods */

    /**
     * Berechnet rekursiv alle Ankerpunkte (linkes oberes Eck) der
     * untergeordneten Kindboxen. Die inneren Blattboxen brauchen diese Methode
     * nicht zu implementieren.
     *
     * @since 0.38.0
     */
    void calculateAnchors()
    {

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
     * Berechnet die <b>Ankerpunkte</b> und <b>zeichnet</b> die Box.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     *
     * @since 0.38.0
     */
    public void render(Graphics2D g)
    {
        calculateAnchors();
        draw(g);
    }
}
