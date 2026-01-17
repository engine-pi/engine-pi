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
package pi.graphics.geom;

import de.pirckheimer_gymnasium.jbox2d.common.Vec2;
import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.debug.ToStringFormatter;
import pi.util.TextUtil;

/**
 * Beschreibt einen <b>zweidimensionalen Vektor</b> auf der Zeichenebene. Diese
 * Klasse wird für alle Positions- und Richtungsangaben genutzt.
 *
 * @author Michael Andonie
 *
 * @see <a href=
 *     "https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/util/geom/Vector2D.java">Vector2D
 *     der LITIENGINE</a>
 */
@API
@SuppressWarnings("StaticVariableOfConcreteClass")
public final class Vector implements Cloneable
{
    /**
     * @hidden
     */
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
     * @param x Der Bewegungsanteil in {@code x}-Richtung.
     * @param y Der Bewegungsanteil in {@code y}-Richtung.
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
     * @param end Der Zielpunkt.
     */
    @API
    public Vector(Vector start, Vector end)
    {
        this.x = end.x() - start.x();
        this.y = end.y() - start.y();
    }

    /**
     * Berechnet anhand eines <b>Winkels</b>, der in Grad angegeben ist, den
     * entsprechenden Vektor der Länge {@code 1}.
     *
     * <ul>
     * <li>Der Vektor für {@code 0} Grad ist {@code [x = 1; y = 0]}.</li>
     * <li>Der Vektor für {@code 90} Grad ist {@code [x = 0; y = 1]}.</li>
     * <li>Der Vektor für {@code 180} bzw. {@code -180} Grad ist
     * {@code [x = -1; y = 0]}.</li>
     * <li>Der Vektor für {@code 270} bzw. {@code -90} Grad ist
     * {@code [x = 0; y = -1]}.</li>
     * <li>Der Vektor für {@code 360} Grad ist {@code [x = 1; y = 0]}.</li>
     * </ul>
     *
     * @param angle Der <b>Winkel</b> in <b>Grad</b>.
     *
     * @return Der Vektor mit der Länge {@code 1}, der sich aus einem Winkel
     *     berechnet.
     */
    public static Vector ofAngle(double angle)
    {
        // Umrechnung des Winkels von Grad ins Bogenmaß
        double rad = Math.toRadians(angle);
        return new Vector(Math.cos(rad), Math.sin(rad));
    }

    /**
     * Gibt den Bewegungsanteil in {@code x}-Richtung zurück.
     *
     * @return Der Bewegungsanteil in {@code x}-Richtung.
     */
    @API
    @Getter
    public double x()
    {
        return x;
    }

    /**
     * Gibt den Bewegungsanteil in {@code x}-Richtung als Ganzzahl zurück.
     *
     * @param scaleFactor Der Skalierungsfaktor, mit dem die
     *     {@code x}-Koordinate multipliziert wird.
     *
     * @return Der gerundete skalierte Bewegungsanteil in {@code x}-Richtung.
     *
     * @since 0.36.0
     */
    @API
    @Getter
    public int x(double scaleFactor)
    {
        return (int) Math.round(x * scaleFactor);
    }

    /**
     * Gibt den Bewegungsanteil in {@code y}-Richtung zurück.
     *
     * @return Der Bewegungsanteil in {@code y}-Richtung.
     */
    @API
    @Getter
    public double y()
    {
        return y;
    }

    /**
     * Gibt den Bewegungsanteil in {@code y}-Richtung multipliziert mit Pixel
     * per Meter zurück.
     *
     * @param scaleFactor Der Skalierungsfaktor, mit dem die
     *     {@code y}-Koordinate multipliziert wird.
     *
     * @return Der gerundete skalierte Bewegungsanteil in {@code y}-Richtung.
     *
     * @since 0.36.0
     */
    @API
    @Getter
    public int y(double scaleFactor)
    {
        return (int) Math.round(y * scaleFactor);
    }

    /**
     * Gibt eine <b>Normierung</b> des Vektors aus. Dies ist ein Vektor, der
     *
     * <ul>
     * <li>in dieselbe Richtung wie der ursprüngliche Vektor zeigt.</li>
     * <li>eine Länge von (möglichst) exakt 1 hat.</li>
     * <li>Der Null-Vektor bleibt der Null-Vektor.</li>
     * </ul>
     *
     * @return Der Normierte Vektor zu diesem Vektor.
     */
    @API
    public Vector normalize()
    {
        double length = length();
        if (length == 0)
        {
            return Vector.NULL;
        }
        return divide(length);
    }

    /**
     * Teilt die effektive Länge des Vektors durch eine Zahl und kürzt dadurch
     * seine Effektivität.
     *
     * @param divisor Hierdurch wird die Länge des Vektors auf der Zeichenebene
     *     geteilt.
     *
     * @return Das Vektor-Objekt, das eine Bewegung in dieselbe Richtung
     *     beschreibt, allerdings in der Länge gekürzt um den angegebenen
     *     Divisor.
     *
     * @throws java.lang.ArithmeticException Falls <code>divisor</code>
     *     <code>0</code> ist.
     *
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
     * Gibt die <b>Länge</b> des Vektors aus.
     *
     * @return Die <b>Länge</b> des Vektors.
     */
    @API
    @Getter
    public double length()
    {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Gibt die euklidische Distanz zwischen diesem Vektor und dem angegebenen
     * Vektor zurück.
     *
     * @param other Ein weiterer Vektor.
     *
     * @return die Länge des Differenzvektors (euklidische Distanz)
     *
     * @throws NullPointerException falls other null ist
     *
     * @since 0.37.0
     */
    @Getter
    public double length(Vector other)
    {
        Vector vector = subtract(other);
        return Math.hypot(vector.x, vector.y);
    }

    /**
     * Berechnet die <b>Gegenrichtung</b> des Vektors.
     *
     * @return Ein neues Vektor-Objekt, das genau die Gegenbewegung zu dem
     *     eigenen beschreibt.
     */
    @API
    public Vector negate()
    {
        return new Vector(-this.x, -this.y);
    }

    /**
     * Berechnet die <b>Gegenrichtung</b> des Vektors in <b>X-Richtung</b>.
     *
     * @return Ein neues Vektor-Objekt, das genau die Gegenbewegung zu dem
     *     eigenen beschreibt.
     */
    @API
    public Vector negateX()
    {
        return new Vector(-this.x, this.y);
    }

    /**
     * Berechnet die <b>Gegenrichtung</b> des Vektors in <b>Y-Richtung</b>.
     *
     * @return Ein neuer Vektor, der genau die Gegenbewegung zum eigenen Vektor
     *     beschreibt.
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
     *     Bewegungen darstellt.
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
     * @param other Ein weiterer Vektor.
     *
     * @return Ein neues Vektor-Objekt, das die Summe der beiden ursprünglichen
     *     Bewegungen darstellt.
     */
    @API
    public Vector add(Vector other)
    {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    /**
     * Berechnet die Differenz zwischen diesem und einem weiteren Vektor.
     *
     * @param x Änderung in X-Richtung
     * @param y Änderung in Y-Richtung
     *
     * @return Die Differenz der beiden Vektoren (<code>"this - v"</code>)
     */
    @API
    public Vector subtract(double x, double y)
    {
        return new Vector(this.x - x, this.y - y);
    }

    /**
     * Berechnet die <b>Differenz</b> zwischen diesem und einem weiteren Vektor.
     *
     * @param other Ein weiterer Vektor.
     *
     * @return Die Differenz der beiden Vektoren (<code>this - other</code>)
     */
    @API
    public Vector subtract(Vector other)
    {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    /**
     * Berechnet eine rotierte Version.
     *
     * @param angle Der Winkel der Rotation in Grad.
     *
     * @return Ein neues Vektor-Objekt, das entsprechend der Gradzahl rotiert
     *     wurde.
     */
    @API
    public Vector rotate(double angle)
    {
        // Umrechnung des Winkels von Grad ins Bogenmaß.
        double rad = Math.toRadians(angle);
        return new Vector(Math.cos(rad) * x + Math.sin(rad) * y,
                -Math.sin(rad) * x + Math.cos(rad) * y);
    }

    /**
     * Gibt den Vektor an, der den Punkt, den dieser Vektor beschreibt, zu dem
     * Punkt verschieben würde, den ein weiterer Vektor beschreibt.
     *
     * @param other Ein weiterer Vektor.
     *
     * @return Der Vektor, der <code>(this.x|this.y)</code> verschieben würde zu
     *     <code>(v.x|v.y)</code>.
     */
    @API
    @Getter
    public Vector distance(Vector other)
    {
        return other.subtract(this);
    }

    /**
     * Multipliziert die effektiven Längen beider Anteile des Vektors ({@code x}
     * und {@code y}) mit einem festen Faktor.
     *
     * <p>
     * Dadurch entsteht ein neuer Vektor mit anderen Werten, welcher
     * zurückgegeben wird.
     * </p>
     *
     * @param factor Der Faktor, mit dem die {@code x}- und {@code y}-Werte des
     *     Vektors multipliziert werden.
     *
     * @return Der Vektor mit den multiplizierten Werten.
     *
     * @see #divide(double)
     */
    @API
    public Vector multiply(double factor)
    {
        return new Vector(x * factor, y * factor);
    }

    /**
     * <b>Multipliziert</b> die effektive Länge des <b>x</b>-Anteils des Vektors
     * mit einem festen Faktor.
     *
     * <p>
     * Dadurch entsteht ein neuer Vektor mit anderen Werten, welcher
     * zurückgegeben wird.
     * </p>
     *
     * @param factor Der Faktor, mit dem der x-Wert des Vektors multipliziert
     *     wird.
     *
     * @return Der Vektor mit den multiplizierten Werten.
     *
     * @see #multiply(double)
     */
    @API
    public Vector multiplyX(double factor)
    {
        return new Vector(x * factor, y);
    }

    /**
     * <b>Multipliziert</b> die effektive Länge des <b>y</b>-Anteils des Vektors
     * mit einem festen Faktor.
     *
     * <p>
     * Dadurch entsteht ein neuer Vektor mit anderen Werten, welcher
     * zurückgegeben wird.
     * </p>
     *
     * @param factor Der Faktor, mit dem der y-Wert des Vektors multipliziert
     *     wird.
     *
     * @return Der Vektor mit den multiplizierten Werten.
     *
     * @see #multiply(double)
     */
    @API
    public Vector multiplyY(double factor)
    {
        return new Vector(x, y * factor);
    }

    /**
     * Berechnet das <b>Skalarprodukt</b> von diesem Vektor mit einem weiteren.
     *
     * <p>
     * <b>Das Skalarprodukt für zweidimensionale Vektoren ist:
     * <code>(a, b) o (c, d) = a * b + c * d</code></b>
     * </p>
     *
     * @param other Ein weiterer Vektor.
     *
     * @return Skalarprodukt dieser Vektoren mit dem Vektor <code>v</code>.
     */
    @API
    public double getScalarProduct(Vector other)
    {
        return x * other.x + y * other.y;
    }

    /**
     * Berechnet, ob dieser Vektor keine Wirkung hat. Dies ist der Fall, wenn
     * beide Komponenten ({@code x} und {@code y}) 0 sind.
     *
     * @return <code>true</code>, wenn dieser keine Auswirkungen macht, sonst
     *     <code>false</code>.
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
     *     ganzzahlig sind, sonst <code>false</code>.
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
    @Getter
    public Direction direction()
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
     * Zwei Vektoren gelten als gleich, wenn {@code x} und {@code y} der beiden
     * Vektoren übereinstimmen.
     *
     * @param object Das auf Gleichheit mit diesem zu überprüfende Objekt.
     *
     * @return <code>true</code>, wenn beide Vektoren gleich sind, sonst
     *     <code>false</code>.
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
     * Gibt die Manhattan-Länge des Vektors zurück. Diese ist für
     * {@code v=(a, b)} definiert als {@code a+b}.
     *
     * @return Die Summe von delta X und delta Y des Vektors.
     */
    @API
    @Getter
    public double manhattanLength()
    {
        double length = x + y;
        return length < 0 ? -length : length;
    }

    /**
     * Gibt den <b>Winkel</b> dieses Vektors in <b>Grad</b> zurück.
     *
     * <p>
     * Der Winkel ist derjenige zwischen der positiven {@code x}-Achse und dem
     * Vektor (x, y), gemessen gegen den Uhrzeigersinn. Der zurückgegebene Wert
     * liegt im Bereich {@code [-180, 180]}.
     * </p>
     *
     * <p>
     * Für den Nullvektor {@code (0, 0)} wird {@code 0} zurückgegeben.
     * </p>
     *
     * @return <b>Winkel</b> des Vektors in <b>Grad</b>
     *
     * @see #radians()
     *
     * @since 0.37.0
     */
    @Getter
    public double angle()
    {
        return Math.toDegrees(radians());
    }

    /**
     * Gibt den <b>Winkel</b> dieses Vektors in <b>Bogenmaß</b> zurück.
     *
     * <p>
     * Der Winkel ist derjenige zwischen der positiven {@code x}-Achse und dem
     * Vektor (x, y), gemessen gegen den Uhrzeigersinn. Der zurückgegebene Wert
     * liegt im Bereich {@code [-π, π]}.
     * </p>
     *
     * <p>
     * Für den Nullvektor {@code (0, 0)} wird {@code 0} zurückgegeben.
     * </p>
     *
     * @return Winkel in Bogenmaß im Bereich {@code [-π, π]}
     *
     * @see #angle()
     *
     * @since 0.37.0
     */
    @Getter
    public double radians()
    {
        return Math.atan2(y, x);
    }

    /**
     * Berechnet den Winkel zwischen diesem Vektor und einem weiteren in Grad.
     *
     * <p>
     * Hierzu wird diese Formel verwendet:
     * <code>cos t = [a o b] / [|a| * |b|]</code>
     * </p>
     *
     * <ul>
     * <li>cos ist der Kosinus</li>
     * <li>t ist der gesuchte Winkel</li>
     * <li>a und b sind die Vektoren</li>
     * <li>|a| ist die Länge des Vektors a</li>
     * </ul>
     *
     * @param other Ein weiterer Vektor.
     *
     * @return Der Winkel zwischen diesem Vektor und dem zweiten.
     */
    @Getter
    public double angle(Vector other)
    {
        double degrees = Math.toDegrees(
            Math.acos(getScalarProduct(other) / (length() * other.length())));
        if (y >= other.y)
        {
            return 360 - degrees;
        }
        return degrees;
    }

    /**
     * @hidden
     */
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
     * import static pi.Vector.vector;
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
     * @param x Der Bewegungsanteil in {@code x}-Richtung.
     * @param y Der Bewegungsanteil in {@code y}-Richtung.
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
     * import static pi.Vector.v;
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
     * @param x Der Bewegungsanteil in {@code x}-Richtung.
     * @param y Der Bewegungsanteil in {@code y}-Richtung.
     */
    public static Vector v(double x, double y)
    {
        return new Vector(x, y);
    }

    /**
     * <b>Formatiert</b> den Vektor als Zeichenkette in der Form {@code (x|y)}.
     *
     * <p>
     * Die Koordinaten werden durch die Methode
     * {@link TextUtil#roundNumber(Object)} gerundet, um eine lesbare Ausgabe zu
     * erzeugen.
     * </p>
     *
     * @return Eine <b>formatierte</b> Zeichenkette, die den Vektor darstellt.
     *
     * @since 0.42.0
     */
    public String format()
    {
        return "(" + TextUtil.roundNumber(x, 2) + "|"
                + TextUtil.roundNumber(y, 2) + ")";
    }

    /**
     * Gibt die String-Repräsentation dieses Objektes aus.
     *
     * <p>
     * Diese Methode sollte nur zu Debugging-Zwecken benutzt werden.
     * </p>
     *
     * @return Die String-Repräsentation dieses Vektors.
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("Vector");
        formatter.append("x", x);
        formatter.append("y", y);
        return formatter.format();
    }
}
