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

/**
 * Die Kamera steuert welchen Ausschnitt der Spielfläche angezeigt wird.
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
 * Hierdurch wird automatisch der gesamte Fokusapparat (auf den Bereich zwischen
 * den Punkten (0|0) und (1500|1000)) eingestellt. Bei spezielleren
 * Fokuswünschen lässt sich dies ebenfalls arrangieren durch die einzelnen
 * Methoden, mit denen alle vier {@link Bounds} (N, S, O, W) einzeln verstellt
 * und (de)aktiviert werden können.
 * </p>
 *
 * <p>
 * <b>!!Achtung!!</b>
 * </p>
 *
 * <p>
 * Bei den Fokuseinstellungen sollte immer ein Bereich gewählt werden, der die
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
    public static final double DEFAULT_METER = 30;

    /**
     * Aktuelle Position des Mittelpunkts der Kamera.
     */
    private Vector position;

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
     * Der aktuelle Anzahl an Pixel eines Meters.
     */
    private double meter = DEFAULT_METER;

    /**
     * Die aktuelle Drehung in Grad.
     */
    private double rotation = 0;

    /**
     * Konstruktor erstellt eine neue Kamera mit Fokus auf <code>(0, 0)</code>.
     */
    @Internal
    public Camera()
    {
        this.position = new Vector(0, 0);
    }

    /**
     * Setzt den Fokus der Kamera auf ein Objekt.
     * <p>
     * Dieses Objekt ist ab dann im „Zentrum“ der Kamera. Die Art des Fokus
     * (rechts, links, oben, unten, mittig, etc.) kann über die Methode
     * {@link #setOffset(Vector)} geändert werden. Soll das Fokusverhalten
     * beendet werden, kann einfach {@code null} übergeben werden, dann bleibt
     * die Kamera bis auf Weiteres in der aktuellen Position.
     *
     * @param focus Der Fokus.
     */
    @API
    public void setFocus(Actor focus)
    {
        this.focus = focus;
    }

    /**
     * Gibt an, ob die Kamera ein Fokus-Objekt verfolgt oder „steif“ ist.
     *
     * @return <code>true</code>, wenn die Kamera ein Fokus-Objekt hat, sonst
     *         <code>false</code>.
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
     * absoluten Bildzentrum liegt, wenn der Fokusverzug mit folgender Methode
     * gesetzt wurde: <code>camera.setOffset(new Vector(0, -100));</code>
     *
     * @param offset Der Vektor, um den ab sofort die Kamera vom Zentrum des
     *               Fokus verschoben wird.
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
     */
    @API
    public boolean hasBounds()
    {
        return bounds != null;
    }

    /**
     * Setzt die Anzahl an Pixel, die einem Meter entsprechen und setzt somit
     * den „Zoom“ der Kamera.
     *
     * <p>
     * Die Anzahl an Pixel eines Meters bestimmt wie „nah“ oder „fern“ die
     * Kamera an der Zeichenebene ist. Der Standardwert eines Meters ist
     * <code>30</code> Pixel. Große Werte zoomen rein, kleine Werte raus.
     * </p>
     *
     * @param pixelCount Die neue Anzahl an Pixel, die einem Meter entsprechen.
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
     * Gibt die Anzahl an Pixel aus, die einem Meter entsprechen.
     *
     * @return Die Anzahl an Pixel aus, die einem Meter entsprechen.
     */
    @API
    public double getMeter()
    {
        return meter;
    }

    /**
     * Verschiebt die Kamera um einen bestimmten Wert in <code>x</code>- und
     * <code>y</code>-Richtung (relativ).
     *
     * @param x Die Verschiebung in <code>x</code>-Richtung.
     * @param y Die Verschiebung in <code>y</code>-Richtung.
     */
    @API
    public void moveBy(double x, double y)
    {
        moveBy(new Vector(x, y));
    }

    @API
    public void moveBy(Vector vector)
    {
        position = position.add(vector);
    }

    /**
     * Verschiebt das Zentrum der Kamera zur angegebenen Position (absolute
     * Verschiebung). Von nun an ist der Punkt mit den eingegebenen Koordinaten
     * im Zentrum des Bildes.
     *
     * @param x Die <code>x</code>-Koordinate des Zentrums des Bildes.
     * @param y Die <code>y</code>-Koordinate des Zentrums des Bildes.
     */
    @API
    public void moveTo(int x, int y)
    {
        moveTo(new Vector(x, y));
    }

    @API
    public void moveTo(Vector vector)
    {
        position = vector;
    }

    @API
    public void rotateBy(double degree)
    {
        rotation += degree;
    }

    @API
    public void rotateTo(double degree)
    {
        rotation = degree;
    }

    /**
     * Setzt die aktuelle Position der Kamera.
     *
     * @param position Die neue Position der Kamera.
     */
    @API
    public void setPosition(Vector position)
    {
        this.position = position;
    }

    /**
     * Setzt die aktuelle Position der Kamera.
     *
     * @param x Die neue X-Koordinate des Kamerazentrums.
     * @param y Die neue Y-Koordinate des Kamerazentrums.
     */
    @API
    public void setPostion(double x, double y)
    {
        setPosition(new Vector(x, y));
    }

    /**
     * Die aktuelle Position der Kamera.
     *
     * @return Die aktuelle Position der Kamera.
     */
    @API
    public Vector getPosition()
    {
        return moveIntoBounds(position.add(offset));
    }

    /**
     * Gibt die Position eines Punktes in der Welt an, relativ zu seiner aktuell
     * zu zeichnenden Position und in Pixel.
     *
     * @param locationInWorld Ein Punkt in der Welt.
     * @param pixelPerMeter   Umrechnungsfaktor pixelPerMeter
     *
     * @return Die zu zeichnende Position des Punktes in Pixel.
     *
     * @hidden
     */
    @Internal
    public Point toScreenPixelLocation(Vector locationInWorld,
            double pixelPerMeter)
    {
        Vector cameraRelativeLocInPx = position.multiply(pixelPerMeter);
        Vector frameSize = Game.getFrameSizeInPixels();
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
            position = focus.getCenter();
        }
        position = moveIntoBounds(position);
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
        double x = Math.max(bounds.getX(),
                Math.min(position.getX(), bounds.getX() + bounds.getWidth()));
        double y = Math.max(bounds.getY(),
                Math.min(position.getY(), bounds.getY() + bounds.getHeight()));
        return new Vector(x, y);
    }
}
