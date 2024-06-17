/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/Vector.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2018 Michael Andonie and contributors.
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

import org.jbox2d.common.Vec2;

import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

/**
 * Beschreibt einen <b>zweidimensionalen Vektor</b> auf der Zeichenebene. Diese
 * Klasse wird für alle Positions- und Richtungsangaben genutzt.
 *
 * @author Michael Andonie
 */
@API
@SuppressWarnings("StaticVariableOfConcreteClass")
public final class Vector implements Cloneable
{
    @Internal
    public static Vector of(Vec2 vector)
    {
        return new Vector(vector.x, vector.y);
    }

    /**
     * Konstante für einen „bewegungslosen“ Vektor (0, 0).
     */
    @API
    public static final Vector NULL = new Vector(0, 0);

    /**
     * Konstante für eine einfache Verschiebung nach rechts (1, 0).
     */
    @API
    public static final Vector RIGHT = new Vector(1, 0);

    /**
     * Konstante für eine einfache Verschiebung nach links (-1, 0).
     */
    @API
    public static final Vector LEFT = new Vector(-1, 0);

    /**
     * Konstante für eine einfache Verschiebung nach oben (0, -1).
     */
    @API
    public static final Vector UP = new Vector(0, 1);

    /**
     * Konstante für eine einfache Verschiebung nach unten (0, 1).
     */
    @API
    public static final Vector DOWN = new Vector(0, -1);

    /**
     * Der kontinuierliche DeltaX-Wert des Punktes. Die anderen Koordinaten sind
     * ggf. nur gerundet.
     */
    private final double x;

    /**
     * Der kontinuierliche DeltaY-Wert des Punktes. Die anderen Koordinaten sind
     * ggf. nur gerundet.
     */
    private final double y;

    /**
     * Erzeugt einen neuen Vektor.
     *
     * @param x Der Bewegungsanteil in <code>x</code>-Richtung.
     * @param y Der Bewegungsanteil in <code>y</code>-Richtung.
     */
    @API
    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Erzeugt einen Vektor als Bewegung von einem <b>Ausgangspunkt</b> zu einem
     * <b>Zielpunkt</b>.
     *
     * @param start Der Ausgangspunkt.
     * @param end   Der Zielpunkt.
     */
    @API
    public Vector(Vector start, Vector end)
    {
        this.x = end.getX() - start.getX();
        this.y = end.getY() - start.getY();
    }

    /**
     * Berechnet anhand eines <b>Winkels</b> den entsprechenden Vektor.
     *
     * @param angle Der Winkel in Grad.
     *
     * @return Der Vektor, der sich aus einem Winkel berechnet.
     */
    public static Vector ofAngle(double angle)
    {
        double rad = Math.toRadians(angle);
        return new Vector(Math.cos(rad), Math.sin(rad));
    }

    /**
     * Gibt den Bewegungsanteil in <code>x</code>-Richtung zurück.
     *
     * @return Der Bewegungsanteil in <code>x</code>-Richtung.
     */
    @API
    public double getX()
    {
        return x;
    }

    /**
     * Gibt den Bewegungsanteil in <code>y</code>-Richtung zurück.
     *
     * @return Der Bewegungsanteil in <code>y</code>-Richtung.
     */
    @API
    public double getY()
    {
        return y;
    }

    /**
     * Gibt eine <b>Normierung</b> des Vektors aus. Dies ist ein Vektor, der
     *
     * <ul>
     * <li>in die selbe Richtung wie der ursprüngliche Vektor zeigt.</li>
     * <li>eine Länge von (möglichst) exakt 1 hat.</li>
     * </ul>
     *
     * @return Normierter Vektor zu diesem Vektor.
     */
    @API
    public Vector normalize()
    {
        return divide(getLength());
    }

    /**
     * Teilt die effektive Länge des Vektors durch eine Zahl und kürzt dadurch
     * seine Effektivität.
     *
     * @param divisor Hierdurch wird die Länge des Vektors auf der Zeichenebene
     *                geteilt.
     * @return Das Vektor-Objekt, das eine Bewegung in dieselbe Richtung
     *         beschreibt, allerdings in der Länge gekürzt um den angegebenen
     *         Divisor.
     * @throws java.lang.ArithmeticException Falls <code>divisor</code>
     *                                       <code>0</code> ist.
     * @see #multiply(double)
     */
    @API
    public Vector divide(double divisor)
    {
        if (divisor == 0)
        {
            throw new ArithmeticException("Der Divisor für das Teilen war 0");
        }
        return new Vector(x / divisor, y / divisor);
    }

    /**
     * Gibt die Länge des Vektors aus.
     *
     * @return Länge des Vektors.
     */
    @API
    public double getLength()
    {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Berechnet die Gegenrichtung des Vektors.
     *
     * @return Ein neues Vektor-Objekt, das genau die Gegenbewegung zu dem
     *         eigenen beschreibt.
     */
    @API
    public Vector negate()
    {
        return new Vector(-this.x, -this.y);
    }

    /**
     * Berechnet die Gegenrichtung des Vektors in X-Richtung.
     *
     * @return Ein neues Vektor-Objekt, das genau die Gegenbewegung zu dem
     *         eigenen beschreibt.
     */
    @API
    public Vector negateX()
    {
        return new Vector(-this.x, this.y);
    }

    /**
     * Berechnet die Gegenrichtung des Vektors in Y-Richtung.
     *
     * @return Ein neuer Vektor, der genau die Gegenbewegung zum eigenen Vektor
     *         beschreibt.
     */
    @API
    public Vector negateY()
    {
        return new Vector(this.x, -this.y);
    }

    /**
     * Berechnet die effektive Bewegung, die dieser Vektor und ein weiterer
     * zusammen ausüben.
     *
     * @param x Die Änderung in X-Richtung.
     * @param y Die Änderung in Y-Richtung.
     *
     * @return Ein neues Vektor-Objekt, das die Summe der beiden ursprünglichen
     *         Bewegungen darstellt.
     */
    @API
    public Vector add(double x, double y)
    {
        return new Vector(this.x + x, this.y + y);
    }

    /**
     * Berechnet die effektive Bewegung, die dieser Vector und ein weiterer
     * zusammen ausüben.
     *
     * @param vector Ein zweiter Vektor.
     * @return Ein neues Vektor-Objekt, das die Summe der beiden ursprünglichen
     *         Bewegungen darstellt.
     */
    @API
    public Vector add(Vector vector)
    {
        return new Vector(this.x + vector.x, this.y + vector.y);
    }

    /**
     * Berechnet die Differenz zwischen diesem und einem weiteren Vektor.
     *
     * @param x Änderung in X-Richtung
     * @param y Änderung in Y-Richtung
     * @return Die Differenz der beiden Vektoren (<code>"this - v"</code>)
     */
    @API
    public Vector subtract(double x, double y)
    {
        return new Vector(this.x - x, this.y - y);
    }

    /**
     * Berechnet die Differenz zwischen diesem und einem weiteren Vektor.
     *
     * @param vector zweiter Vektor
     * @return Die Differenz der beiden Vektoren (<code>"this - v"</code>)
     */
    @API
    public Vector subtract(Vector vector)
    {
        return new Vector(this.x - vector.x, this.y - vector.y);
    }

    /**
     * Berechnet eine rotierte Version.
     *
     * @param degree Rotation in Grad
     * @return Ein neues Vektor-Objekt, das entsprechend der Gradzahl rotiert
     *         wurde.
     */
    @API
    public Vector rotate(double degree)
    {
        double angle = Math.toRadians(degree);
        return new Vector( //
                Math.cos(angle) * x + Math.sin(angle) * y, //
                -Math.sin(angle) * x + Math.cos(angle) * y //
        );
    }

    /**
     * Gibt den Vektor an, der den Punkt, den dieser Vektor beschreibt, zu dem
     * Punkt verschieben würde, den ein weiterer Vektor beschreibt.
     *
     * @param vector Ein weiterer Vektor.
     * @return Der Vektor, der <code>(this.x|this.y)</code> verschieben würde zu
     *         <code>(v.x|v.y)</code>.
     */
    @API
    public Vector getDistance(Vector vector)
    {
        return vector.subtract(this);
    }

    /**
     * Multipliziert die effektiven Längen beider Anteile des Vektors
     * (<code>x</code> und <code>y</code>) mit einem festen Faktor. <br>
     * Dadurch entsteht ein neuer Vektor mit anderen Werten, welcher
     * zurückgegeben wird.
     *
     * @param factor Der Faktor, mit dem die <code>x</code>- und
     *               <code>y</code>-Werte des Vektors multipliziert werden
     * @return Der Vektor mit den multiplizierten Werten
     * @see #divide(double)
     */
    @API
    public Vector multiply(double factor)
    {
        return new Vector(x * factor, y * factor);
    }

    /**
     * Multipliziert die effektive Länge des X-Anteils des Vektors mit einem
     * festen Faktor. <br>
     * Dadurch entsteht ein neuer Vektor mit anderen Werten, welcher
     * zurückgegeben wird.
     *
     * @param factor Der Faktor, mit dem der x-Wert des Vektors multipliziert
     *               wird
     * @return Der Vektor mit den multiplizierten Werten
     * @see #multiply(double)
     */
    @API
    public Vector multiplyX(double factor)
    {
        return new Vector(x * factor, y);
    }

    /**
     * Multipliziert die effektive Länge des X-Anteils des Vektors mit einem
     * festen Faktor. <br>
     * Dadurch entsteht ein neuer Vektor mit anderen Werten, welcher
     * zurückgegeben wird.
     *
     * @param factor Der Faktor, mit dem der x-Wert des Vektors multipliziert
     *               wird
     * @return Der Vektor mit den multiplizierten Werten
     * @see #multiply(double)
     */
    @API
    public Vector multiplyY(double factor)
    {
        return new Vector(x, y * factor);
    }

    /**
     * Berechnet das <b>Skalarprodukt</b> von diesem Vektor mit einem weiteren.
     * Das Skalarprodukt für zweidimensionale Vektoren ist:
     * <code>(a, b) o (c, d) = a * b + c * d</code>
     *
     * @param vector zweiter Vektor
     * @return Skalarprodukt dieser Vektoren mit dem Vektor <code>v</code>.
     */
    @API
    public double getScalarProduct(Vector vector)
    {
        return this.x * vector.x + this.y * vector.y;
    }

    /**
     * Berechnet, ob dieser Vektor keine Wirkung hat. Dies ist der Fall, wenn
     * beide Komponenten (<code>x</code> und <code>y</code>) 0 sind.
     *
     * @return <code>true</code>, wenn dieser keine Auswirkungen macht, sonst
     *         <code>false</code>.
     */
    @API
    public boolean isNull()
    {
        return x == 0 && y == 0;
    }

    /**
     * Gibt zurück, ob dieser Vektor <i>echt ganzzahlig</i> ist, also ob seine
     * <b>tatsächlichen Delta-Werte</b> beide Ganzzahlen sind.
     *
     * @return <code>true</code>, wenn <b>beide</b> Delta-Werte dieses Punktes
     *         ganzzahlig sind, sonst <code>false</code>.
     */
    @API
    public boolean isIntegral()
    {
        return x == (int) x && y == (int) y;
    }

    /**
     * Berechnet die Richtung des Vektors, in die er wirkt.
     *
     * @return Der Wert der Konstanten, die diese Bewegung widerspiegelt.
     */
    @API
    public Direction getDirection()
    {
        if (x == 0 && y == 0)
        {
            return Direction.NONE;
        }
        if (x == 0)
        {
            return y > 0 ? Direction.DOWN : Direction.UP;
        }
        if (y == 0)
        {
            return x > 0 ? Direction.RIGHT : Direction.LEFT;
        }
        if (y < 0)
        {
            return x < 0 ? Direction.UP_LEFT : Direction.UP_RIGHT;
        }
        return x > 0 ? Direction.DOWN_LEFT : Direction.DOWN_RIGHT;
    }

    /**
     * Prüft, ob ein beliebiges Objekt gleich diesem Vektor ist. <br>
     * <br>
     * Zwei Vektoren gelten als gleich, wenn <code>x</code> und <code>y</code>
     * der beiden Vektoren übereinstimmen.
     *
     * @param object Das auf Gleichheit mit diesem zu überprüfende Objekt.
     * @return <code>true</code>, wenn beide Vektoren gleich sind, sonst
     *         <code>false</code>.
     */
    @API
    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (object instanceof Vector v)
        {
            return x == v.x && y == v.y;
        }
        return false;
    }

    @Override
    public Vector clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }

    /**
     * Gibt die String-Repräsentation dieses Objektes aus.
     * <p>
     * Diese Methode sollte nur zu Debugging-Zwecken benutzt werden.
     *
     * @return String-Repräsentation dieses Vektors
     */
    @Override
    public String toString()
    {
        return "Vector [ x = " + x + "; y = " + y + " ]";
    }

    /**
     * Gibt die Manhattan-Länge des Vektors zurück. Diese ist für v=(a, b)
     * definiert als a+b.
     *
     * @return Die Summe von delta X und delta Y des Vektors.
     */
    @API
    public double getManhattanLength()
    {
        double length = x + y;
        return length < 0 ? -length : length;
    }

    /**
     * Berechnet den Winkel zwischen diesem Vektor und einem weiteren. Hierzu
     * wird diese Formel verwendet: <br>
     * <code>cos t = [a o b] / [|a| * |b|]</code><br>
     * <ul>
     * <li>cos ist der Kosinus</li>
     * <li>t ist der gesuchte Winkel</li>
     * <li>a und b sind die Vektoren</li>
     * <li>|a| ist die Länge des Vektors a</li>
     * </ul>
     *
     * @param other Ein zweiter Vektor.
     * @return Der Winkel zwischen diesem Vektor und dem zweiten. Ist zwischen 0
     *         und 180.
     */
    @API
    public double getAngle(Vector other)
    {
        if (this.y < other.y)
        {
            return Math.toDegrees(Math.acos(this.getScalarProduct(other)
                    / (this.getLength() * other.getLength())));
        }
        else
        {
            return (360 - Math.toDegrees(Math.acos(this.getScalarProduct(other)
                    / (this.getLength() * other.getLength()))));
        }
    }

    @Internal
    public Vec2 toVec2()
    {
        return new Vec2((float) x, (float) y);
    }

    @API
    public boolean isNaN()
    {
        return Double.isNaN(x) || Double.isNaN(y);
    }

    /**
     * Erzeugt einen neuen Vektor.
     *
     * <p>
     * Diese statische Methode kann dazu benutzt werden, um über einen
     * statischen Import dieser Methode
     * </p>
     *
     * {@code
     * import static de.pirckheimer_gymnasium.engine_pi.Vector.vector;
     * }
     *
     * <p>
     * mit etwas weniger Schreibarbeit einen neuen Vektor zu erzeugen. Anstatt
     * </p>
     *
     * {@code
     * new Vector(1, 2);
     * }
     *
     * <p>
     * kann dann
     * </p>
     *
     * {@code
     * vector(1, 2);
     * }
     *
     * <p>
     * geschrieben werden.
     * </p>
     *
     * @param x Der Bewegungsanteil in <code>x</code>-Richtung.
     * @param y Der Bewegungsanteil in <code>y</code>-Richtung.
     */
    public static Vector vector(double x, double y)
    {
        return new Vector(x, y);
    }

    /**
     * Erzeugt einen neuen Vektor.
     *
     * <p>
     * Diese statische Methode kann dazu benutzt werden, um über einen
     * statischen Import dieser Methode
     * </p>
     *
     * {@code
     * import static de.pirckheimer_gymnasium.engine_pi.Vector.v;
     * }
     *
     * <p>
     * mit etwas weniger Schreibarbeit einen neuen Vektor zu erzeugen. Anstatt
     * </p>
     *
     * {@code
     * new Vector(1, 2);
     * }
     *
     * <p>
     * kann dann
     * </p>
     *
     * {@code
     * v(1, 2);
     * }
     *
     * <p>
     * geschrieben werden.
     * </p>
     *
     * @param x Der Bewegungsanteil in <code>x</code>-Richtung.
     * @param y Der Bewegungsanteil in <code>y</code>-Richtung.
     */
    public static Vector v(double x, double y)
    {
        return new Vector(x, y);
    }
}
