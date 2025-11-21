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
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Iterator;

/**
 * Eine <b>Box</b> beschreibt eine rechteckige grafische Fläche, die weitere
 * Kinder-Boxen enthalten kann.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public abstract class Box implements Iterable<Box>
{
    /**
     * Zeigt an, ob die Box bereits <b>ausgemessen</b> wurde.
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
     * Die <b>gesetzte Breite</b> in Pixel. Im Gegensatz zu {@link #width} wird
     * dieses Attribut gesetzt und nicht durch {@link #calculateDimension()}
     * berechnet.
     */
    protected int definedWidth;

    /**
     * Die <b>gesetzte Höhe</b> in Pixel. Im Gegensatz zu {@link #height} wird
     * dieses Attribut gesetzt und nicht durch {@link #calculateDimension()}
     * berechnet.
     */
    protected int definedHeight;

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
     * Die <b>übergeordnete</b> Box, in der diese Box enthalten ist. Dieses
     * Attribut kann {@code null} sein, wenn keine Elternbox vorhanden ist.
     *
     * @since 0.38.0
     */
    protected Box parent;

    /**
     * Liefert einen Iterator über die direkten Kinder dieser Box.
     *
     * Standardmäßig sind Boxen ohne Kinder definiert — Container-Subklassen
     * sollten diese Methode überschreiben, um tatsächliche Kinder zu liefern.
     *
     * @return Ein Iterator über die direkten Kind-Boxen (leer wenn keine).
     *
     * @since 0.40.0
     */
    @Override
    public Iterator<Box> iterator()
    {
        return Collections.emptyIterator();
    }

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

    public abstract int numberOfChilds();

    /**
     * Berechnet rekursiv die <b>Abmessungen</b>, d.h. die Höhe und Breite der
     * eigenen Box und aller Kind-Boxen.
     *
     * <p>
     * <b>Zuerst</b> müssen die Abmessungen der <b>Kind</b>-Boxen ermittelt
     * werden, <b>dann</b> die Abmessungen der <b>eigenen</b> Box.
     * </p>
     *
     * <h4>SingleChildContainer-Code-Beispiel</h4>
     *
     * <pre>
     * {@code
     * protected void calculateDimension()
     * {
     *     child.calculateDimension();
     *     width = child.width + 2 * margin;
     *     height = child.height + 2 * margin;
     * }
     * }
     * </pre>
     *
     * <h4>MultipleChildContainer-Code-Beispiel</h4>
     *
     * <pre>
     * {@code
     * protected void calculateDimension()
     * {
     *     int maxWidth = 0;
     *     for (Box child : childs)
     *     {
     *         child.calculateDimension();
     *         if (child.width > maxWidth)
     *         {
     *             maxWidth = child.width;
     *         }
     *         height += child.height;
     *     }
     *     width = maxWidth;
     * }
     * }
     * </pre>
     */
    protected abstract void calculateDimension();

    /**
     * Berechnet rekursiv alle <b>Ankerpunkte</b> (linkes oberes Eck) der
     * untergeordneten Kinder-Boxen. Die inneren Blattboxen brauchen diese
     * Methode nicht zu implementieren.
     *
     * <p>
     * <b>Zuerst</b> muss der <b>eigene</b> Ankerpunkt bestimmt werden,
     * <b>dann</b> die Ankerpunkte der <b>Kind</b>boxen.
     * </p>
     *
     * <h4>SingleChildContainer-Code-Beispiel</h4>
     *
     * <pre>
     * {@code
     * protected void calculateAnchors()
     * {
     *     child.x = x + margin;
     *     child.y = y + margin;
     *     child.calculateAnchors();
     * }
     * }
     * </pre>
     *
     * <h4>MultipleChildContainer-Code-Beispiel</h4>
     *
     * <pre>
     * {@code
     * protected void calculateAnchors()
     * {
     *     int yCursor = y;
     *     for (Box child : childs)
     *     {
     *         child.x = x;
     *         child.y = yCursor;
     *         yCursor += child.height;
     *         child.calculateAnchors();
     *     }
     * }
     * }
     * </pre>
     *
     * @since 0.38.0
     */
    protected abstract void calculateAnchors();

    /**
     * <b>Misst</b> alle Kind-Boxen <b>aus</b>.
     *
     * <p>
     * Bestimmt rekursiv zuerst die Abmessungen (Höhe und Breite) und
     * anschließend die Ankerpunkte (x, y) der Kind-Boxen.
     * </p>
     */
    public void measure()
    {
        calculateDimension();
        calculateAnchors();
        measured = true;
    }

    /**
     * <b>Zeichnet</b> die eigene Box sowie alle Kind-Boxen.
     *
     * <h4>SingleChildContainer-Code-Beispiel</h4>
     *
     * <pre>
     * {@code
     * void draw(Graphics2D g)
     * {
     *     Color oldColor = g.getColor();
     *     g.setColor(color);
     *     g.fillRect(x, y, width, height);
     *     g.setColor(oldColor);
     *     child.draw(g);
     * }
     * }
     * </pre>
     *
     * <h4>MultipleChildContainer-Code-Beispiel</h4>
     *
     * <pre>
     * {@code for (Box child : childs) { child.draw(g); } } </pre
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

    public Box getBox()
    {
        return this;
    }

    /* static instantiation methods */

    public static AlignBox align(Box child)
    {
        return new AlignBox(child);
    }

    /**
     * Unterlegt eine Kind-Box mit einer <b>Hintergrundfarbe</b>.
     *
     * @since 0.40.0
     */
    public static BackgroundBox background(Box child)
    {
        return new BackgroundBox(child);
    }

    /**
     * Erzeugt einen neuen Rahmen durch die Angabe der enthaltenen Kind-Box.
     *
     * @param child Die <b>Kind-Box</b>, die umrahmt werden soll.
     *
     * @since 0.40.0
     *
     * @see BorderBox#BorderBox(Box)
     */
    public static BorderBox border(Box child)
    {
        return new BorderBox(child);
    }

    public static DimensionBox dimension(Box child)
    {
        return new DimensionBox(child);
    }

    public static EmptyBox empty()
    {
        return new EmptyBox();
    }

    public static EmptyBox[] empty(int number)
    {
        EmptyBox[] boxes = new EmptyBox[number];
        for (int i = 0; i < number; i++)
        {
            boxes[i] = new EmptyBox();
        }
        return boxes;
    }

    /**
     * Erzeugt einen neuen Rahmen durch die Angabe der enthaltenen Kind-Box.
     *
     * @since 0.40.0
     */
    public static FramedTextBox framedText(String content)
    {
        return new FramedTextBox(content);
    }

    public static GridBox grid(Box... childs)
    {
        return new GridBox(childs);
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

    public static ImageBox image(BufferedImage image)
    {
        return new ImageBox(image);
    }

    public static ImageBox image(String image)
    {
        return new ImageBox(image);
    }

    /**
     * Erzeugt einen neuen <b>Außenabstand</b> durch die Angabe der enthaltenen
     * Kind-Box.
     *
     * @param child Die <b>Kind-Box</b>, die umrahmt werden soll.
     *
     * @since 0.40.0
     *
     * @see MarginBox#MarginBox(Box)
     */
    public static MarginBox margin(Box child)
    {
        return new MarginBox(child);
    }

    public static TextBlockBox textBlock(String content)
    {
        return new TextBlockBox(content);
    }

    /**
     * Erzeugt eine <b>Text</b>box.
     *
     * @param content Der <b>Inhalt</b> der Textbox als Zeichenkette.
     *
     * @since 0.39.0
     *
     * @see TextLineBox#TextLineBox(String)
     */
    public static TextLineBox textLine(String content)
    {
        return new TextLineBox(content);
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
