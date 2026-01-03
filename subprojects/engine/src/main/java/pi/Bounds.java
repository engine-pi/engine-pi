/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/Bounds.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
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
package pi;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.debug.ToStringFormatter;

/**
 * Ein <b>nicht-grafisches Rechteck</b> auf der Zeichenebene, das eine
 * allgemeine Fläche beschreibt.
 *
 * @param x Die {@code x}-Koordinate der <i>unteren linken Ecke</i> des
 *     Rechtecks.
 * @param y Die {@code y}-Koordinate der <i>unteren linken Ecke</i> des
 *     Rechtecks.
 * @param width Die <b>Breite</b> des Rechtecks.
 * @param height Die <b>Höhe</b> des Rechtecks.
 *
 * @author Michael Andonie
 * @author Josef Friedrich
 */
public record Bounds(double x, double y, double width, double height)
{
    /**
     * Gleicht das eigene Zentrum mit der Mitte eines anderen {@link Bounds} ab.
     *
     * @param bounds Das {@link Bounds}, an dessen Mitte auch die Mitte dieses
     *     Rechtecks angeglichen werden soll.
     */
    @API
    public Bounds withCenterAtBoundsCenter(Bounds bounds)
    {
        return withCenterPoint(bounds.center());
    }

    /**
     * Gibt ein neues {@link Bounds} mit derselben Höhe und Breite zurück, das
     * seinen Mittelpunkt genau im angegebenen Zentrum hat.
     *
     * @param point Das Zentrum des zurückzugebenden {@link Bounds}-Rechtecks.
     *
     * @return Ein {@link Bounds} mit der gleichen Höhe und Breite wie dieses,
     *     jedoch so verschoben, dass es mit seiner Mitte im angegebenen Zentrum
     *     liegt.
     */
    @API
    public Bounds withCenterPoint(Vector point)
    {
        return moveBy(point.subtract(center()));
    }

    /**
     * Berechnet den Mittelpunkt dieses {@link Bounds}-Rechtecks in der
     * Zeichenebene.
     *
     * @return Der Punkt mit den Koordinaten, der im Zentrum des Rechtecks
     *     liegt.
     */
    @API
    @Getter
    public Vector center()
    {
        return new Vector(x + (width / 2), y + (height / 2));
    }

    /**
     * Berechnet ein neues {@link Bounds} mit denselben Maßen wie dieses, jedoch
     * um einen bestimmten Vector verschoben.
     *
     * @param v Der Vector, der die Verschiebung des neuen Objektes von diesem
     *     beschreibt.
     *
     * @return Ein neues {@link Bounds}-Objekt, das dieselben Maße wie dieses
     *     hat, jedoch um die entsprechende Verschiebung verschoben ist.
     */
    @API
    public Bounds moveBy(Vector v)
    {
        return new Bounds(x + v.x(), y + v.y(), width, height);
    }

    /**
     * Berechnet aus diesem und einem weiteren {@link Bounds} ein neues, dass
     * die beiden genau fasst.
     *
     * @param bounds Das zweite Rechteck für die Berechnung
     *
     * @return Ein neues {@link Bounds}, dass die beiden Rechtecke genau
     *     umfasst.
     */
    @API
    public Bounds smallestCommon(Bounds bounds)
    {
        double x, y, dX, dY;
        x = Math.min(bounds.x, this.x);
        y = Math.min(bounds.y, this.y);
        if (bounds.x + bounds.width > this.x + this.width)
        {
            dX = (bounds.x + bounds.width) - x;
        }
        else
        {
            dX = (this.x + this.width) - x;
        }
        if (bounds.y + bounds.height > this.y + this.height)
        {
            dY = (bounds.y + bounds.height) - y;
        }
        else
        {
            dY = (this.y + this.height) - y;
        }
        return new Bounds(x, y, dX, dY);
    }

    /**
     * Berechnet, ob dieses Rechteck über einer Grenze liegt und wenn
     * <b>nicht</b>, dann berechnet es eines, das gerade so an der Untergrenze
     * liegt.
     *
     * @param lowerBound Die Grenze, auf der das Ergebnis maximal liegen darf.
     *
     * @return Ein {@link Bounds} derselben Höhe und Breite wie dieses, das in
     *     jedem Fall über, oder auf der Grenze liegt, wenn es passt, ist es
     *     <code>this</code>.
     */
    @API
    public Bounds above(double lowerBound)
    {
        if (y + height < lowerBound)
        {
            return this;
        }
        else
        {
            return new Bounds(x, lowerBound - height, width, height);
        }
    }

    /**
     * Berechnet, ob dieses Rechteck unter einer Grenze liegt, und wenn
     * <b>nicht</b>, dann berechnet es eines, das gerade so an der Obergrenze
     * liegt.
     *
     * @param upperBound Die Grenze, auf der das Ergebnis maximal liegen darf.
     *
     * @return Ein {@link Bounds} derselben Höhe und Breite wie dieses, das in
     *     jedem Fall unter oder auf der Grenze liegt, wenn es passt, ist es
     *     <code>this</code>.
     */
    @API
    public Bounds below(double upperBound)
    {
        if (y > upperBound)
        {
            return this;
        }
        else
        {
            return new Bounds(x, upperBound, width, height);
        }
    }

    /**
     * Berechnet, ob dieses Rechteck rechts von einer bestimmten Grenze liegt,
     * und wenn <b>nicht</b>, dann berechnet es eines, das gerade so an der
     * linken Extremgrenze liegt.
     *
     * @param border Der Wert, den das Ergebnisrechteck maximal links sein darf
     *
     * @return Ein {@link Bounds} derselben Höhe und Breite, das in jedem rechts
     *     jenseits oder auf der Grenze liegt.<br>
     *     Wenn diese Eigenschaften bereits von diesem Objekt erfüllt werden, so
     *     wird <code>this</code> zurückgegeben.
     */
    @API
    public Bounds rightOf(double border)
    {
        if (x > border)
        {
            return this;
        }
        else
        {
            return new Bounds(border, y, width, height);
        }
    }

    /**
     * Berechnet, ob dieses Rechteck links von einer bestimmten Grenze liegt,
     * und wenn <b>nicht</b>, dann berechnet es eines, das gerade so an der
     * rechten Extremgrenze liegt.
     *
     * @param border Der Wert, den das Ergebnisrechteck maximal rechts sein darf
     *
     * @return Ein {@link Bounds} derselben Höhe und Breite, das in jedem Fall
     *     links jenseits oder auf der Grenze liegt.<br>
     *     Wenn diese Eigenschaften bereits von diesem Objekt erfüllt werden, so
     *     wird <code>this</code> zurückgegeben.
     */
    @API
    public Bounds leftOf(double border)
    {
        if (x + width < border)
        {
            return this;
        }
        else
        {
            return new Bounds(border - width, y, width, height);
        }
    }

    /**
     * Gibt ein neues {@link Bounds} mit selber Höhe und Breite, jedoch einer
     * bestimmten, zu definierenden Position.<br>
     * Diese Position ist die der <i>linken unteren Ecke</i> des
     * BoundingRechtecks.
     *
     * @param realX Die <i>X-Koordinate der linken unteren Ecke</i> des
     *     BoundingRechtecks
     * @param realY Die <i>Y-Koordinate der linken unteren Ecke</i> des
     *     BoundingRechtecks
     *
     * @return Ein neues {@link Bounds} mit der eingegebenen Position und
     *     derselben Breite und Höhe.
     */
    @API
    public Bounds atPosition(double realX, double realY)
    {
        return new Bounds(realX, realY, width, height);
    }

    /**
     * Testet, ob ein Punkt sich in dem {@link Bounds} befindet.
     *
     * @param v Der Punkt, der getestet werden soll
     *
     * @return true, wenn der Punkt in dem {@link Bounds} ist
     */
    @API
    public boolean contains(Vector v)
    {
        return (v.x() >= this.x && v.y() >= this.y && v.x() <= (x + width)
                && v.y() <= (y + height));
    }

    /**
     * Berechnet die vier Eckpunkte des umfassenden {@link Bounds}.
     *
     * @return Array mit den vier Eckpunkten des umfassenden {@link Bounds}.
     */
    @API
    public Vector[] points()
    {
        return new Vector[] { new Vector(x, y), new Vector(x + width, y),
                new Vector(x, y + height), new Vector(x + width, y + height) };
    }

    /**
     * Diese Methoden prüft, ob dieses {@link Bounds}-Rechteck ein zweites
     * vollkommen enthält.<br>
     * <i>Gemeinsame Ränder zählen <b>AUCH</b> als umschliessen!</i>
     *
     * @param inner Das innere {@link Bounds}-Rechteck. Es soll geprüft werden,
     *     ob dieses vollkommen von dem die Methode ausführenden Rechteck
     *     umschlossen wird.
     *
     * @return <code>wahr</code>, wenn das <b>ausführende
     *     {@link Bounds}-Rechteck das als Argument übergebene {@link Bounds}
     *     voll enthält</b>, sonst <code>falsch</code>.
     */
    @API
    public boolean contains(Bounds inner)
    {
        return (this.x <= inner.x && this.y <= inner.y
                && (this.x + this.width) >= (inner.x + inner.width)
                && (this.y + this.height) >= (inner.y + inner.height));
    }

    /**
     * Berechnet, ob dieses {@link Bounds} oberhalb eines zweiten ist.
     *
     * @param r Das Rechteck, bei dem dies getestet werden soll
     *
     * @return <code>wahr</code>, wenn dieses Rechteck rechts von dem anderen
     *     ist, sonst <code>falsch</code>.
     */
    @API
    public boolean above(Bounds r)
    {
        return ((this.y) < (r.y));
    }

    /**
     * Sollte dieses {@link Bounds}-Rechteck nicht voll innerhalb eines
     * bestimmten anderen, äußeren Rechtecks liegen, so wird versucht, dieses
     * {@link Bounds}-Rechteck <i>in das andere mit möglichst wenig
     * Verschiebung</i> zu bringen. Diese Methode wird intern für die
     * Beschränkung des Kamera-Bereiches genutzt.
     *
     * <div class='hinweisProbleme'><b>Achtung</b>: Voraussetzung dafür, dass
     * dieser Algorithmus Sinn macht ist, dass das äußere Rechteck ausreichend
     * größer als dieses ist!</div>
     *
     * @param outer Das äußere Rechteck, innerhalb dessen sich das
     *     Ergebnis-Rechteck befinden wird (sollte das äußere ausreichend groß
     *     sein).
     *
     * @return Das Ergebnis-Rechteck, das sich im äußeren Rechteck befinden
     *     wird.
     */
    @API
    public Bounds in(Bounds outer)
    {
        double realX = this.x, realY = this.y;
        if (this.x < outer.x)
        {
            realX = outer.x;
        }
        if (this.x + this.width > outer.x + outer.width)
        {
            realX = outer.x + outer.width - this.width;
        }
        if (this.y < outer.y)
        {
            realY = outer.y;
        }
        if (this.y + this.height > outer.y + outer.height)
        {
            realY = outer.y + outer.height - this.height;
        }
        return new Bounds(realX, realY, this.width, this.height);
    }

    /**
     * Erstellt einen Klon von diesem {@link Bounds}.
     *
     * @return Ein neues {@link Bounds} mit genau demselben Zustand wie dieses.
     */
    @Override
    @API
    public Bounds clone()
    {
        return new Bounds(x, y, width, height);
    }

    /**
     * Gibt die X-Koordinate der unteren linken Ecke aus.
     *
     * @return Die X-Koordinate der unteren linken Ecke dieses
     *     BoundingRechtecks.
     *
     * @see #y ()
     * @see #width ()
     * @see #height ()
     */
    @Override
    @API
    public double x()
    {
        return x;
    }

    /**
     * Die <b>x</b>-Koordinate der <b>linken</b> Ecken.
     *
     * <p>
     * Dieser Wert entspricht {@link #x()}.
     * </p>
     *
     * @since 0.42.0
     */
    @API
    public double xLeft()
    {
        return x;
    }

    /**
     * Die <b>x</b>-Koordinate der <b>rechten</b> Ecken.
     *
     * <p>
     * Dieser Wert entspricht {@link #x()} + {@link #width()}.
     * </p>
     *
     * @since 0.42.0
     */
    @API
    public double xRight()
    {
        return x + width;
    }

    /**
     * Gibt die Y-Koordinate der unteren linken Ecke aus.
     *
     * @return Die Y-Koordinate der unteren linken Ecke dieses
     *     {@link Bounds}-Rechtecks.
     *
     * @see #x ()
     * @see #width ()
     * @see #height ()
     */
    @Override
    @Getter
    @API
    public double y()
    {
        return y;
    }

    /**
     * Die y-Koordinate der <b>oberen</b> Ecken.
     *
     * <p>
     * Dieser Wert entspricht {@link #y()} + {@link #height()}.
     * </p>
     *
     * @since 0.42.0
     */
    @API
    public double yTop()
    {
        return y + height;
    }

    /**
     * Die <b>y</b>-Koordinate der <b>untern</b> Ecken.
     *
     * <p>
     * Dieser Wert entspricht {@link #y()}
     * </p>
     *
     * @since 0.42.0
     */
    @API
    public double yBottom()
    {
        return y;
    }

    /**
     * Gibt die <b>Breite</b> aus.
     *
     * @return Die <b>Breite</b> dieses {@link Bounds}-Rechtecks.
     *
     * @see #x()
     * @see #y()
     * @see #height()
     */
    @Override
    @Getter
    @API
    public double width()
    {
        return width;
    }

    /**
     * Gibt die <b>Höhe</b> aus.
     *
     * @return Die <b>Höhe</b> dieses {@link Bounds}-Rechtecks.
     *
     * @see #x()
     * @see #y()
     * @see #width()
     */
    @Override
    @Getter
    @API
    public double height()
    {
        return height;
    }

    /**
     * Gibt die exakte <b>Position</b> der linken unteren Ecke dieses
     * {@link Bounds}-Rechtecks aus.
     *
     * @return die <b>Position</b> des {@link Bounds}-Rechtecks, beschrieben
     *     durch den Punkt der linken unteren Ecke dieses Objekts.
     */
    @Getter
    @API
    public Vector position()
    {
        return new Vector(x, y);
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter(this);
        formatter.append("x", x, "m");
        formatter.append("y", y, "m");
        formatter.append("width", width, "m");
        formatter.append("height", height, "m");
        return formatter.format();
    }
}
