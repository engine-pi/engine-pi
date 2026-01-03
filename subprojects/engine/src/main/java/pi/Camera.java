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
package pi;

import java.awt.Point;

import pi.actor.Actor;
import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/CameraDemo.java
// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/camera/CameraDemo.java
// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/main-classes/camera.md

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
 * camera.bounds(bounds);
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
 * @author Josef Friedrich
 */
public final class Camera
{
    /**
     * Der Standardwert für die Anzahl an Pixel eines Meters.
     */
    public static final double DEFAULT_METER = 32;

    public static final double DEFAULT_ZOOM_FACTOR = 0.05;

    /**
     * Die aktuelle Position des Mittelpunkts der Kamera.
     */
    private Vector focus;

    /**
     * Die {@link Bounds} der Kamera (sofern vorhanden), die sie in der Bewegung
     * einschränken.
     */
    private Bounds bounds;

    /**
     * Der eventuelle Fokus der Kamera.
     */
    private Actor actorInFocus = null;

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
        focus = new Vector(0, 0);
    }

    /**
     * Die aktuelle Position der Kamera.
     *
     * @return Die aktuelle Position der Kamera.
     */
    @API
    @Getter
    public Vector focus()
    {
        return moveIntoBounds(focus.add(offset));
    }

    /**
     * <b>Verschiebt</b> das Zentrum der Kamera <b>zur angegebenen Position</b>
     * (absolute Verschiebung). Von nun an ist der Punkt mit den eingegebenen
     * Koordinaten im Zentrum des Bildes.
     *
     * @param focus Das neue Zentrum der Kamera.
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     */
    @API
    @Setter
    public Camera focus(Vector focus)
    {
        this.focus = focus;
        return this;
    }

    /**
     * <b>Verschiebt</b> das Zentrum der Kamera <b>zur angegebenen Position</b>
     * (absolute Verschiebung). Von nun an ist der Punkt mit den eingegebenen
     * Koordinaten im Zentrum des Bildes.
     *
     * @param x Die {@code x}-Koordinate des Zentrums des Bildes.
     * @param y Die {@code y}-Koordinate des Zentrums des Bildes.
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     */
    @API
    public Camera focus(double x, double y)
    {
        focus(new Vector(x, y));
        return this;
    }

    /**
     * Setzt den <b>Fokus</b> der Kamera auf eine <b>Figur</b>.
     *
     * <p>
     * Dieses Objekt ist ab dann im „Zentrum“ der Kamera. Die Art des Fokus
     * (rechts, links, oben, unten, mittig, etc.) kann über die Methode
     * {@link #offset(Vector)} geändert werden. Soll das Fokusverhalten beendet
     * werden, kann einfach {@code null} übergeben werden, dann bleibt die
     * Kamera bis auf Weiteres in der aktuellen Position.
     *
     * @param actor Die Figur, die fokussiert werden soll.
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     */
    @API
    @Setter
    public Camera focus(Actor actor)
    {
        actorInFocus = actor;
        return this;
    }

    /**
     * <b>Entfernt</b> die fokussierte <b>Figur</b> von der Kamera.
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     *
     * @since 0.28.0
     */
    @API
    public Camera removeFocus()
    {
        this.actorInFocus = null;
        return this;
    }

    /**
     * <b>Verschiebt</b> die Kamera um einen bestimmten Vektor (<b>relativ</b>).
     *
     * @param delta Die Verschiebung als Vektor.
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     */
    @API
    public Camera moveFocus(Vector delta)
    {
        focus = focus.add(delta);
        return this;
    }

    /**
     * <b>Verschiebt</b> die Kamera um einen bestimmten Wert in {@code x}- und
     * {@code y}-Richtung (<b>relativ</b>).
     *
     * @param deltaX Die Verschiebung in {@code x}-Richtung.
     * @param deltaY Die Verschiebung in {@code y}-Richtung.
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     */
    @API
    public Camera moveFocus(double deltaX, double deltaY)
    {
        moveFocus(new Vector(deltaX, deltaY));
        return this;
    }

    /**
     * Gibt an, ob die Kamera ein Fokus-Objekt verfolgt oder „steif“ ist.
     *
     * @return <code>true</code>, wenn die Kamera ein Fokus-Objekt hat, sonst
     *     <code>false</code>.
     *
     * @see #focus(Actor)
     */
    @API
    public boolean hasFocus()
    {
        return actorInFocus != null;
    }

    /**
     * Setzt einen Kameraverzug. Der Standardwert hierfür ist
     * <code>(0, 0)</code>.
     *
     * <p>
     * Der Verzug ist ein Vektor, um den der {@link #actorInFocus Fokus}
     * verschoben wird. Das heißt, dass eine Figur im Fokus um 100 Pixel tiefer
     * als im absoluten Bildzentrum liegt, wenn der Fokus-Verzug mit folgender
     * Methode gesetzt wurde: <code>camera.offset(new Vector(0, -100));</code>
     * </p>
     *
     * @param offset Der Vektor, um den ab sofort die Kamera vom Zentrum des
     *     Fokus verschoben wird.
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     */
    @API
    @Setter
    public Camera offset(Vector offset)
    {
        this.offset = offset;
        return this;
    }

    /**
     * Gibt den Verzug der Kamera aus.
     *
     * @return Der aktuelle Verzug der Kamera.
     *
     * @see #offset(Vector)
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
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     *
     * @see #hasBounds()
     */
    @API
    @Setter
    public Camera bounds(Bounds bounds)
    {
        this.bounds = bounds;
        return this;
    }

    /**
     * Gibt an, ob die Kamera durch {@link Bounds} in ihrer Bewegung beschränkt
     * ist.
     *
     * @return <code>true</code> falls ja, sonst <code>false</code>.
     *
     * @see #bounds(Bounds)
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
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     *
     * @see Scene#meter(double)
     */
    @API
    @Setter
    public Camera meter(double pixelCount)
    {
        if (pixelCount <= 0)
        {
            throw new IllegalArgumentException(
                    "Der Kamerazoom kann nicht kleiner oder gleich 0 sein.");
        }
        this.meter = pixelCount;
        return this;
    }

    /**
     * Gibt die Anzahl an Pixel aus, die einem <b>Meter</b> entsprechen.
     *
     * <p>
     * Ist die Pixelvervielfältigung aktiviert, wird der Faktor der
     * Pixelvervielfältigung mit der Pixelanzahl multipliziert
     * </p>
     *
     * @return Die Anzahl an Pixel aus, die einem Meter entsprechen.
     *
     * @see Game#pixelMultiplication()
     */
    @API
    @Getter
    public double meter()
    {
        if (Game.isPixelMultiplication())
        {
            return meter * Game.pixelMultiplication();
        }
        return meter;
    }

    /**
     * Die Kamera bewegt sich näher an das Spielfeld. Die Ansicht wird
     * vergrößert.
     *
     * @param factor 1 verdoppelt die Pixelanzahl eines Meters, 0 keine
     *     Veränderung.
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     *
     * @since 0.28.0
     */
    @API
    public Camera zoomIn(double factor)
    {
        meter += meter * factor;
        return this;
    }

    /**
     * Die Kamera bewegt sich um den Standard-Zoomfaktor näher an das Spielfeld.
     * Die Ansicht wird vergrößert.
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     *
     * @since 0.28.0
     */
    @API
    public Camera zoomIn()
    {
        zoomIn(DEFAULT_ZOOM_FACTOR);
        return this;
    }

    /**
     * Die Kamera entfernt sich vom Spielfeld. Die Ansicht wird verkleinert.
     *
     * @param factor 0.5 halbiert die Pixelanzahl eines Meters, 0 keine
     *     Veränderung.
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     *
     * @since 0.28.0
     */
    @API
    public Camera zoomOut(double factor)
    {
        meter -= meter * factor;
        return this;
    }

    /**
     * Die Kamera entfernt sich um den Standard-Zoomfaktor vom Spielfeld. Die
     * Ansicht wird verkleinert.
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     *
     * @since 0.28.0
     */
    @API
    public Camera zoomOut()
    {
        zoomOut(DEFAULT_ZOOM_FACTOR);
        return this;
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
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     */
    @API
    public Camera rotateBy(double angle)
    {
        rotation += angle;
        rotation = rotation % 360;
        return this;
    }

    /**
     * Setzt den Rotationswert der Kamera. {@code rotateTo(90)} dreht die Kamera
     * beispiels um 90 Grad <b>gegen</b> den Uhrzeigersinn,
     * {@code rotateTo(-90)} um 90 Grad <b>im</b> Uhrzeigersinn.
     *
     * @param angle Der Winkel (in <b>Grad</b>), um die Kamera <b>von seiner
     *     Ausgangsposition bei Initialisierung</b> rotiert werden soll.
     *
     * @return Eine Referenz auf die eigene Instanz der Kamera, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Kamera durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code camera.offset(..).focus(..)}.
     */
    @API
    public Camera rotateTo(double angle)
    {
        rotation = angle % 360;
        return this;
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
        Vector cameraRelativeLocInPx = focus.multiply(pixelPerMeter);
        Vector frameSize = Game.windowSize();
        return new Point((int) (frameSize.x() / 2 + cameraRelativeLocInPx.x()),
                (int) (frameSize.y() / 2 + cameraRelativeLocInPx.y()));
    }

    /**
     * Implementiert den {@link pi.event.FrameUpdateListener} nicht, da die
     * Kamera an einem anderen Zeitpunkt aktualisiert wird.
     *
     * @see pi.loop.GameLoop#run()
     *
     * @hidden
     */
    @Internal
    public void onFrameUpdate()
    {
        if (hasFocus())
        {
            focus = actorInFocus.center();
        }
        focus = moveIntoBounds(focus);
    }

    /**
     * Gibt die aktuelle Drehung zurück.
     *
     * @return Die aktuelle Drehung.
     */
    @Getter
    @API
    public double rotation()
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
                Math.min(position.x(), bounds.x() + bounds.width()));
        double y = Math.max(bounds.y(),
                Math.min(position.y(), bounds.y() + bounds.height()));
        return new Vector(x, y);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("Camera");
        formatter.append("meter", meter);
        formatter.append("focus", focus);
        if (rotation != 0)
        {
            formatter.append("rotation", rotation);
        }
        if (actorInFocus != null)
        {
            formatter.append("actorInFocus", actorInFocus);
        }
        return formatter.format();
    }
}
