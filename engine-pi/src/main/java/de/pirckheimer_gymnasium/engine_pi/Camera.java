/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/Camera.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2014 Michael Andonie and contributors.
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

import java.awt.Point;

import de.pirckheimer_gymnasium.engine_pi.actor.Actor;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.debug.ToStringFormatter;

/**
 * Die <b>Kamera</b> steuert, welcher <b>Ausschnitt</b> der Spielfläche
 * angezeigt wird.
 *
 * <p>
 * Sie kann ein Objekt fokussieren und ihm so folgen. Hierbei besteht auch die
 * Möglichkeit, diesen Fokus in Grenzen zu halten. Und zwar durch die
 * Fokus-Bounds. Diese 4 Grenzwerte können individuell verstellt und aktiviert
 * werden. auch kann man den von der Kamera darzustellenden Bereich durch eine
 * einzige Methode definieren, in dem man den Bereich als Bounds beschreibt.
 * </p>
 *
 * <pre>{@code
 * Bounds bounds = new Bounds(0, 0, 1500, 1000);
 * camera.setBounds(bounds);
 * }</pre>
 *
 * <p>
 * Hierdurch wird der gesamte Fokus auf den Bereich zwischen den Punkten (0|0)
 * und (1500|1000) eingestellt.
 * </p>
 *
 * <p>
 * <b>!!Achtung!!</b>
 * </p>
 *
 * <p>
 * Bei den Fokus-Einstellungen sollte immer ein Bereich gewählt werden, der die
 * Größe des Anzeigefensters (oder Vollbildes) bei weitem übersteigt.<br>
 * Allgemein wirken diese {@link Bounds} auch ohne aktivierten Fokus, jedoch ist
 * dies meist weniger sinnvoll.
 * </p>
 *
 * @author Michael Andonie
 */
public final class Camera
{
    /**
     * Der Standardwert für die Anzahl an Pixel eines Meters.
     */
    public static final double DEFAULT_METER = 32;

    public static final double DEFAULT_ZOOM_FACTOR = 0.05;

    /**
     * Aktuelle Position des Mittelpunkts der Kamera.
     */
    private Vector center;

    /**
     * Die {@link Bounds} der Kamera (sofern vorhanden), die sie in der Bewegung
     * einschränken.
     */
    private Bounds bounds;

    /**
     * Der eventuelle Fokus der Kamera.
     */
    private Actor focus = null;

    /**
     * Der Kameraverzug.
     */
    private Vector offset = Vector.NULL;

    /**
     * Die aktuelle Pixelanzahl eines Meters.
     */
    private double meter = DEFAULT_METER;

    /**
     * Die aktuelle Drehung in Grad.
     */
    private double rotation = 0;

    /**
     * Konstruktor erstellt eine neue Kamera mit Fokus auf <code>(0, 0)</code>.
     *
     * @hidden
     */
    @Internal
    public Camera()
    {
        this.center = new Vector(0, 0);
    }

    /**
     * Setzt den <b>Fokus</b> der Kamera auf eine <b>Figur</b>.
     *
     * <p>
     * Dieses Objekt ist ab dann im „Zentrum“ der Kamera. Die Art des Fokus
     * (rechts, links, oben, unten, mittig, etc.) kann über die Methode
     * {@link #setOffset(Vector)} geändert werden. Soll das Fokusverhalten
     * beendet werden, kann einfach {@code null} übergeben werden, dann bleibt
     * die Kamera bis auf Weiteres in der aktuellen Position.
     *
     * @param focus Die Figur, die fokussiert werden soll.
     */
    @API
    public void setFocus(Actor focus)
    {
        this.focus = focus;
    }

    /**
     * Entfernt die fokussierte Figur von der Kamera.
     *
     * @since 0.28.0
     */
    @API
    public void removeFocus()
    {
        this.focus = null;
    }

    /**
     * Gibt an, ob die Kamera ein Fokus-Objekt verfolgt oder „steif“ ist.
     *
     * @return <code>true</code>, wenn die Kamera ein Fokus-Objekt hat, sonst
     *     <code>false</code>.
     *
     * @see #setFocus(Actor)
     */
    @API
    public boolean hasFocus()
    {
        return focus != null;
    }

    /**
     * Setzt einen Kameraverzug. Der Standardwert hierfür ist
     * <code>(0, 0)</code>.
     * <p>
     * Der Verzug ist ein Vektor, um den der {@link #focus Fokus} verschoben
     * wird. Das heißt, dass eine Figur im Fokus um 100 Pixel tiefer als im
     * absoluten Bildzentrum liegt, wenn der Fokus-Verzug mit folgender Methode
     * gesetzt wurde: <code>camera.setOffset(new Vector(0, -100));</code>
     *
     * @param offset Der Vektor, um den ab sofort die Kamera vom Zentrum des
     *     Fokus verschoben wird.
     */
    @API
    public void setOffset(Vector offset)
    {
        this.offset = offset;
    }

    /**
     * Gibt den Verzug der Kamera aus.
     *
     * @return Der aktuelle Verzug der Kamera.
     *
     * @see #setOffset(Vector)
     */
    @API
    public Vector getOffset()
    {
        return offset;
    }

    /**
     * Mit dieser Methode kann die Kamerabewegung eingeschränkt werden.
     *
     * <p>
     * Ein Rechteck gibt die Begrenzung an, die die Kameraperspektive niemals
     * übertreten wird.
     * </p>
     *
     * @param bounds Das Rechteck, das die Grenzen der Kamera angibt.
     *
     * @see #hasBounds()
     */
    @API
    public void setBounds(Bounds bounds)
    {
        this.bounds = bounds;
    }

    /**
     * Gibt an, ob die Kamera durch {@link Bounds} in ihrer Bewegung beschränkt
     * ist.
     *
     * @return <code>true</code> falls ja, sonst <code>false</code>.
     *
     * @see #setBounds(Bounds)
     */
    @API
    public boolean hasBounds()
    {
        return bounds != null;
    }

    /**
     * Setzt die <b>Anzahl an Pixel</b>, die einem <b>Meter</b> entsprechen und
     * setzt somit den „Zoom“ der Kamera.
     *
     * <p>
     * Die Anzahl an Pixel eines Meters bestimmt wie „nah“ oder „fern“ die
     * Kamera an der Zeichenebene ist. Der Standardwert eines Meters ist
     * <code>32</code> Pixel. Größer Werte zoomen näher an die Spielfläche
     * heran, kleine Werte weiter von der Spielfläche weg.
     * </p>
     *
     * @param pixelCount Die neue Anzahl an Pixel, die einem Meter entsprechen.
     *
     * @see Scene#setMeter(double)
     */
    @API
    public void setMeter(double pixelCount)
    {
        if (pixelCount <= 0)
        {
            throw new IllegalArgumentException(
                    "Der Kamerazoom kann nicht kleiner oder gleich 0 sein.");
        }
        this.meter = pixelCount;
    }

    /**
     * Die Kamera bewegt sich näher an das Spielfeld. Die Ansicht wird
     * vergrößert.
     *
     * @param factor 1 verdoppelt die Pixelanzahl eines Meters, 0 keine
     *     Veränderung.
     *
     * @since 0.28.0
     */
    @API
    public void zoomIn(double factor)
    {
        meter += meter * factor;
    }

    /**
     * Die Kamera bewegt sich um den Standard-Zoomfaktor näher an das Spielfeld.
     * Die Ansicht wird vergrößert.
     *
     * @since 0.28.0
     */
    @API
    public void zoomIn()
    {
        zoomIn(DEFAULT_ZOOM_FACTOR);
    }

    /**
     * Die Kamera entfernt sich vom Spielfeld. Die Ansicht wird verkleinert.
     *
     * @param factor 0.5 halbiert die Pixelanzahl eines Meters, 0 keine
     *     Veränderung.
     *
     * @since 0.28.0
     */
    @API
    public void zoomOut(double factor)
    {
        meter -= meter * factor;
    }

    /**
     * Die Kamera entfernt sich um den Standard-Zoomfaktor vom Spielfeld. Die
     * Ansicht wird verkleinert.
     *
     * @since 0.28.0
     */
    @API
    public void zoomOut()
    {
        zoomOut(DEFAULT_ZOOM_FACTOR);
    }

    /**
     * Gibt die Anzahl an Pixel aus, die einem Meter entsprechen.
     *
     * <p>
     * Ist die Pixelvervielfältigung aktiviert, wird der Faktor der
     * Pixelvervielfältigung mit der Pixelanzahl multipliziert
     * </p>
     *
     *
     * @return Die Anzahl an Pixel aus, die einem Meter entsprechen.
     *
     * @see Game#getPixelMultiplication()
     */
    @API
    public double getMeter()
    {
        if (Game.isPixelMultiplication())
        {
            return meter * Game.getPixelMultiplication();
        }
        return meter;
    }

    /**
     * <b>Verschiebt</b> die Kamera um einen bestimmten Vektor (<b>relativ</b>).
     *
     * @param vector Die Verschiebung als Vektor.
     */
    @API
    public void moveBy(Vector vector)
    {
        center = center.add(vector);
    }

    /**
     * <b>Verschiebt</b> die Kamera um einen bestimmten Wert in <code>x</code>-
     * und <code>y</code>-Richtung (<b>relativ</b>).
     *
     * @param x Die Verschiebung in <code>x</code>-Richtung.
     * @param y Die Verschiebung in <code>y</code>-Richtung.
     */
    @API
    public void moveBy(double x, double y)
    {
        moveBy(new Vector(x, y));
    }

    /**
     * <b>Verschiebt</b> das Zentrum der Kamera <b>zur angegebenen Position</b>
     * (absolute Verschiebung). Von nun an ist der Punkt mit den eingegebenen
     * Koordinaten im Zentrum des Bildes.
     *
     * @param vector Das neue Zentrum der Kamera.
     */
    @API
    public void moveTo(Vector vector)
    {
        center = vector;
    }

    /**
     * <b>Verschiebt</b> das Zentrum der Kamera <b>zur angegebenen Position</b>
     * (absolute Verschiebung). Von nun an ist der Punkt mit den eingegebenen
     * Koordinaten im Zentrum des Bildes.
     *
     * @param x Die <code>x</code>-Koordinate des Zentrums des Bildes.
     * @param y Die <code>y</code>-Koordinate des Zentrums des Bildes.
     */
    @API
    public void moveTo(int x, int y)
    {
        moveTo(new Vector(x, y));
    }

    /**
     * <b>Rotiert</b> die Kamera um den angegebenen <b>Winkel</b>.
     *
     * <p>
     * Positive Werte drehen die die Kamera gegen den Uhrzeigersinn, negative im
     * Uhrzeigersinn.
     * </p>
     *
     * @param angle Der Winkel (in <b>Grad</b>), um den die Kamera rotiert
     *     werden soll.
     *     <ul>
     *     <li>Werte &gt; 0 : Drehung gegen Uhrzeigersinn</li>
     *     <li>Werte &lt; 0 : Drehung im Uhrzeigersinn</li>
     *     </ul>
     */
    @API
    public void rotateBy(double angle)
    {
        rotation += angle;
        rotation = rotation % 360;
    }

    /**
     * Setzt den Rotationswert der Kamera. {@code rotateTo(90)} dreht die Kamera
     * beispiels um 90 Grad <b>gegen</b> den Uhrzeigersinn,
     * {@code rotateTo(-90)} um 90 Grad <b>im</b> Uhrzeigersinn.
     *
     * @param angle Der Winkel (in <b>Grad</b>), um die Kamera <b>von seiner
     *     Ausgangsposition bei Initialisierung</b> rotiert werden soll.
     */
    @API
    public void rotateTo(double angle)
    {
        rotation = angle % 360;
    }

    /**
     * Setzt die aktuelle Position der Kamera.
     *
     * @param x Die neue X-Koordinate des Kamerazentrums.
     * @param y Die neue Y-Koordinate des Kamerazentrums.
     *
     * @see #setCenter(double, double)
     */
    @API
    public void setPostion(double x, double y)
    {
        setCenter(new Vector(x, y));
    }

    /**
     * Setzt die aktuelle Position der Kamera.
     *
     * @param x Die neue X-Koordinate des Kamerazentrums.
     * @param y Die neue Y-Koordinate des Kamerazentrums.
     *
     * @since 0.33.0
     *
     * @see #setPostion(double, double)
     */
    @API
    public void setCenter(double x, double y)
    {
        setCenter(new Vector(x, y));
    }

    /**
     * Setzt die aktuelle Position der Kamera.
     *
     * @param position Die neue Position der Kamera.
     */
    @API
    public void setCenter(Vector position)
    {
        this.center = position;
    }

    /**
     * Die aktuelle Position der Kamera.
     *
     * @return Die aktuelle Position der Kamera.
     */
    @API
    public Vector getCenter()
    {
        return moveIntoBounds(center.add(offset));
    }

    /**
     * Gibt die Position eines Punktes in der Welt an, relativ zu seiner aktuell
     * zu zeichnenden Position und in Pixel.
     *
     * @param locationInWorld Ein Punkt in der Welt.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     *
     * @return Die zu zeichnende Position des Punktes in Pixel.
     *
     * @hidden
     */
    @Internal
    public Point toScreenPixelLocation(Vector locationInWorld,
            double pixelPerMeter)
    {
        Vector cameraRelativeLocInPx = center.multiply(pixelPerMeter);
        Vector frameSize = Game.getWindowSize();
        return new Point(
                (int) (frameSize.getX() / 2 + cameraRelativeLocInPx.getX()),
                (int) (frameSize.getY() / 2 + cameraRelativeLocInPx.getY()));
    }

    /**
     * Implementiert den FrameUpdateListener nicht, da die Kamera an einem
     * anderen Zeitpunkt aktualisiert wird.
     */
    public void onFrameUpdate()
    {
        if (hasFocus())
        {
            center = focus.getCenter();
        }
        center = moveIntoBounds(center);
    }

    /**
     * Gibt die aktuelle Drehung zurück.
     *
     * @return Die aktuelle Drehung.
     */
    public double getRotation()
    {
        return rotation;
    }

    private Vector moveIntoBounds(Vector position)
    {
        if (!hasBounds())
        {
            return position;
        }
        double x = Math.max(bounds.x(),
                Math.min(position.getX(), bounds.x() + bounds.width()));
        double y = Math.max(bounds.y(),
                Math.min(position.getY(), bounds.y() + bounds.height()));
        return new Vector(x, y);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("Camera");
        formatter.add("meter", meter);
        formatter.add("center", center);
        if (rotation != 0)
        {
            formatter.add("rotation", rotation);
        }
        if (focus != null)
        {
            formatter.add("focus", focus);
        }
        return formatter.format();
    }
}
