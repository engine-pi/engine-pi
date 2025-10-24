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
package de.pirckheimer_gymnasium.engine_pi;

import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

/**
 * Ein <b>nicht-grafisches Rechteck</b> auf der Zeichenebene, das eine
 * allgemeine Fläche beschreibt.
 *
 * @param x Die <code>x</code>-Koordinate der <i>unteren linken Ecke</i> des
 *     Rechtecks.
 * @param y Die <code>y</code>-Koordinate der <i>unteren linken Ecke</i> des
 *     Rechtecks.
 * @param width <b>Reelle</b> Breite des Rechtecks.
 * @param height <b>Reelle</b> Höhe des Rechtecks.
 *
 * @author Michael Andonie
 *
 * @hidden
 */
@Internal
public record Bounds(double x, double y, double width, double height)
{
    /**
     * Gleicht das eigene Zentrum mit der Mitte eines anderen {@link Bounds} ab.
     *
     * @param bounds Das {@link Bounds}, an dessen Mitte auch die Mitte dieses
     *     Rechtecks angeglichen werden soll.
     */
    public Bounds withCenterAtBoundsCenter(Bounds bounds)
    {
        return withCenterPoint(bounds.getCenter());
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
    public Bounds withCenterPoint(Vector point)
    {
        return moveBy(point.subtract(getCenter()));
    }

    /**
     * Berechnet den Mittelpunkt dieses {@link Bounds}-Rechtecks in der
     * Zeichenebene.
     *
     * @return Der Punkt mit den Koordinaten, der im Zentrum des Rechtecks
     *     liegt.
     */
    public Vector getCenter()
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
    public Bounds moveBy(Vector v)
    {
        return new Bounds(x + v.getX(), y + v.getY(), width, height);
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
    public boolean contains(Vector v)
    {
        return (v.getX() >= this.x && v.getY() >= this.y
                && v.getX() <= (x + width) && v.getY() <= (y + height));
    }

    /**
     * Berechnet die vier Eckpunkte des umfassenden {@link Bounds}.
     *
     * @return Array mit den vier Eckpunkten des umfassenden {@link Bounds}.
     */
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
    public Bounds clone()
    {
        return new Bounds(x, y, width, height);
    }

    /**
     * Gibt eine String-Repräsentation dieses Objektes aus.
     *
     * @return Die String-Repräsentation dieses Objektes. Hierin wird Auskunft
     *     über alle 4 ausschlaggebenden Zahlen (<code>x</code>, <code>y</code>,
     *     <code>getWidth</code> und <code>getHeight</code> gemacht).
     */
    @Override
    public String toString()
    {
        return "Bounding-Rectangle: getX:" + x + " getY: " + y + " getWidth: "
                + width + " getHeight: " + height;
    }

    /**
     * Gibt die <b>reelle</b> X-Koordinate der unteren linken Ecke aus.
     *
     * @return Die <b>reelle</b> X-Koordinate der unteren linken Ecke dieses
     *     BoundingRechtecks.
     *
     * @see #y ()
     * @see #width ()
     * @see #height ()
     */
    @Override
    public double x()
    {
        return x;
    }

    /**
     * Gibt die <b>reelle</b> Y-Koordinate der unteren linken Ecke aus.
     *
     * @return Die <b>reelle</b> Y-Koordinate der unteren linken Ecke dieses
     *     {@link Bounds}-Rechtecks.
     *
     * @see #x ()
     * @see #width ()
     * @see #height ()
     */
    @Override
    public double y()
    {
        return y;
    }

    /**
     * Gibt die <b>reelle</b> Breite aus.
     *
     * @return Die <b>reelle</b> Breite dieses {@link Bounds}-Rechtecks.
     *
     * @see #x ()
     * @see #y ()
     * @see #height ()
     */
    @Override
    public double width()
    {
        return width;
    }

    /**
     * Gibt die <b>reelle</b> Höhe aus.
     *
     * @return Die <b>reelle</b> Höhe dieses {@link Bounds}-Rechtecks.
     *
     * @see #x ()
     * @see #y ()
     * @see #width ()
     */
    @Override
    public double height()
    {
        return height;
    }

    /**
     * Gibt die exakte Position der linken unteren Ecke dieses
     * {@link Bounds}-Rechtecks aus.
     *
     * @return die Position des {@link Bounds}-Rechtecks, beschrieben durch den
     *     Punkt der linken unteren Ecke dieses Objekts.
     */
    public Vector getPosition()
    {
        return new Vector(x, y);
    }
}
