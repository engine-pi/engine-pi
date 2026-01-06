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
package pi.graphics.boxes;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import pi.annotations.Setter;
import pi.debug.ToStringFormatter;

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
     * Alle <b>Kinder-Boxen</b>, die diese Box enthält.
     *
     * @since 0.41.0
     */
    protected List<Box> childs = new ArrayList<Box>();

    /**
     * Die <b>übergeordnete</b> Box, in der diese Box enthalten ist. Dieses
     * Attribut kann {@code null} sein, wenn keine Elternbox vorhanden ist.
     *
     * @since 0.38.0
     */
    protected Box parent;

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
     * Gibt an, ob bei dieser Box die Abmessungen gesetzt werden können oder ob
     * die Abmessungen nur automatisch bestimmt werden können.
     */
    protected boolean supportsDefinedDimension = false;

    /**
     * Ist dieses Attribut wahr, so wird die
     * {@link #calculateDimension()}-Methode zweimal hintereinander rekursive
     * aufgerufen.
     *
     * <p>
     * Falls in der {@link #calculateDimension()}-Methode die Abmessungen von
     * Kinder-Boxen verändert werden, ist ein zweiter Messdurchgang nötig.
     * </p>
     *
     * @see GridBox#calculateDimension()
     */
    protected boolean measureDimensionTwice = false;

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
     * Die Box wird nicht gezeichnet, wenn sie deaktiviert ist.
     */
    protected boolean disabled = false;

    /**
     * Liefert einen Iterator über die direkten Kinder dieser Box.
     *
     * <p>
     * Standardmäßig sind Boxen ohne Kinder definiert — Container-Subklassen
     * sollten diese Methode überschreiben, um tatsächliche Kinder zu liefern.
     * </p>
     *
     * @return Ein Iterator über die direkten Kind-Boxen (leer wenn keine).
     *
     * @since 0.40.0
     */
    @Override
    public Iterator<Box> iterator()
    {
        return Collections.unmodifiableList(childs).iterator();
    }

    /* Setter */

    @Setter
    public Box width(int width)
    {
        if (supportsDefinedDimension)
        {
            this.definedWidth = width;
        }
        return this;
    }

    @Setter
    public Box height(int height)
    {
        if (supportsDefinedDimension)
        {
            this.definedHeight = height;
        }
        return this;
    }

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
    @Setter
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
    @Setter
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
    @Setter
    public Box anchor(int x, int y)
    {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Setzt den Deaktiviert-Status.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    public Box disabled(boolean disabled)
    {
        this.disabled = disabled;
        return this;
    }

    /**
     * <b>Deaktiviert</b> die Box.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.42.0
     */
    public Box disable()
    {
        this.disabled = true;
        return this;
    }

    /**
     * <b>Aktiviert</b> die Box.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.42.0
     */
    public Box enable()
    {
        disabled = false;
        return this;
    }

    /**
     * <b>Schaltet</b> zwischen dem Status <b>deaktiviert</b> und
     * <b>aktiviert</b> <b>hin- und her</b>.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.42.0
     */
    public Box toggle()
    {
        disabled = !disabled;
        return this;
    }

    /* Methods */

    /**
     * Gibt die <b>Anzahl an Kinder-Boxen</b> zurück.
     *
     * @return Die <b>Anzahl an Kinder-Boxen</b>.
     */
    public abstract int numberOfChilds();

    /**
     * Berechnet rekursiv die <b>Abmessung</b> (die Höhe und Breite) der eigenen
     * Box.
     *
     * <h4>Single-Child-Code-Beispiel</h4>
     *
     * <pre>
     * {@code
     * protected void calculateDimension()
     * {
     *     width = child.width + 2 * margin;
     *     height = child.height + 2 * margin;
     * }
     * }
     * </pre>
     *
     * <h4>Multiple-Child-Code-Beispiel</h4>
     *
     * <pre>
     * {@code
     * protected void calculateDimension()
     * {
     *     int maxWidth = 0;
     *     for (Box child : childs)
     *     {
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
     * Berechnet rekursiv die <b>Abmessung</b> (die Höhe und Breite) aller
     * Kind-Boxen und dann die Abmessung eigenen Box.
     *
     * <p>
     * Falls das Flag {@code measureDimensionTwice} gesetzt ist, werden diese
     * Berechnungen zweimal durchgeführt.
     * </p>
     */
    protected void measureDimension()
    {
        for (Box child : childs)
        {
            child.measureDimension();
        }
        calculateDimension();
        // Zum Beispiel nötig in der Klasse GridBox
        if (measureDimensionTwice)
        {
            for (Box child : childs)
            {
                child.measureDimension();
            }
            calculateDimension();
        }
    }

    /**
     * Berechnet rekursiv alle <b>Ankerpunkte</b> (linkes oberes Eck) der
     * untergeordneten Kinder-Boxen. Die inneren Blattboxen brauchen diese
     * Methode nicht zu implementieren.
     *
     * <h4>Single-Child-Code-Beispiel</h4>
     *
     * <pre>
     * {@code
     * protected void calculateAnchors()
     * {
     *     child.x = x + margin;
     *     child.y = y + margin;
     * }
     * }
     * </pre>
     *
     * <h4>Multiple-Child-Code-Beispiel</h4>
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
     *     }
     * }
     * }
     * </pre>
     *
     * @since 0.38.0
     */
    protected abstract void calculateAnchors();

    protected void measureAnchors()
    {
        calculateAnchors();
        for (Box child : childs)
        {
            child.measureAnchors();
        }
    }

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
        measureDimension();
        measureAnchors();
        measured = true;
    }

    /**
     * <b>Zeichnet</b> die eigene Box.
     *
     * <pre>
     * {@code
     * void draw(Graphics2D g)
     * {
     *     Color oldColor = g.getColor();
     *     g.setColor(color);
     *     g.fillRect(x, y, width, height);
     *     g.setColor(oldColor);
     * }
     * }
     * </pre>
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     *
     * @since 0.38.0
     */
    abstract void draw(Graphics2D g);

    /**
     * Ruft rekursive alle {@link #draw(Graphics2D)}-Methoden der Kinder-Boxen
     * auf.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     *
     * @since 0.42.0
     */
    private void doDrawing(Graphics2D g)
    {
        if (disabled)
        {
            return;
        }
        draw(g);
        for (Box box : childs)
        {
            box.doDrawing(g);
        }
    }

    /**
     * Setzt den Messstatus dieser Box auf „nicht gemessen“ zurück.
     *
     * <p>
     * Dies zwingt die Box, ihre Dimensionen bei der nächsten Messung neu zu
     * berechnen.
     * </p>
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     */
    public Box remeasure()
    {
        measured = false;
        return this;
    }

    /**
     * Berechnet die <b>Abmessungen</b> und <b>Ankerpunkte</b> und
     * <b>zeichnet</b> die Box.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     *
     * @since 0.38.0
     */
    public Box render(Graphics2D g)
    {
        if (!measured)
        {
            measure();
        }
        doDrawing(g);
        return this;
    }

    /**
     * Gibt einen vorkonfigurierten {@link ToStringFormatter} aus.
     *
     * @since 0.42.0
     */
    public ToStringFormatter toStringFormatter()
    {
        ToStringFormatter formatter = new ToStringFormatter(this);

        if ((width == 0 || width == definedWidth) && definedWidth > 0)
        {
            formatter.append("dWidth", definedWidth);
        }
        else if (width > 0 && definedWidth == 0)
        {
            formatter.append("width", width);
        }

        if ((height == 0 || height == definedHeight) && definedHeight > 0)
        {
            formatter.append("dHeight", definedHeight);
        }
        else if (height > 0 && definedHeight == 0)
        {
            formatter.append("height", height);
        }

        if (x != 0)
        {
            formatter.append("x", x);
        }

        if (y != 0)
        {
            formatter.append("y", y);
        }
        return formatter;
    }

    /**
     * @since 0.42.0
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     */
    public Box debug()
    {
        System.out.println("");
        debug(0);
        return this;
    }

    /**
     * @since 0.42.0
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     */
    private Box debug(int depth)
    {
        System.out.println(" ".repeat(depth * 2) + this);
        for (Box box : childs)
        {
            box.debug(depth + 1);
        }
        return this;
    }
}
