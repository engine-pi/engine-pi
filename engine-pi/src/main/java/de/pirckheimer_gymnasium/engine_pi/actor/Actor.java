/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/actor/Actor.java
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
package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.dynamics.joints.RopeJointDef;
import org.jbox2d.dynamics.joints.WeldJointDef;

import de.pirckheimer_gymnasium.engine_pi.Bounds;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Layer;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.animation.ValueAnimator;
import de.pirckheimer_gymnasium.engine_pi.animation.interpolation.EaseInOutDouble;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.debug.DebugConfiguration;
import de.pirckheimer_gymnasium.engine_pi.event.CollisionEvent;
import de.pirckheimer_gymnasium.engine_pi.event.CollisionListener;
import de.pirckheimer_gymnasium.engine_pi.event.EventListenerBundle;
import de.pirckheimer_gymnasium.engine_pi.event.EventListeners;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListenerRegistration;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListenerRegistration;
import de.pirckheimer_gymnasium.engine_pi.event.MouseClickListener;
import de.pirckheimer_gymnasium.engine_pi.event.MouseClickListenerRegistration;
import de.pirckheimer_gymnasium.engine_pi.event.MouseScrollListener;
import de.pirckheimer_gymnasium.engine_pi.event.MouseScrollListenerRegistration;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureData;
import de.pirckheimer_gymnasium.engine_pi.physics.NullHandler;
import de.pirckheimer_gymnasium.engine_pi.physics.PhysicsData;
import de.pirckheimer_gymnasium.engine_pi.physics.PhysicsHandler;
import de.pirckheimer_gymnasium.engine_pi.physics.WorldHandler;
import de.pirckheimer_gymnasium.engine_pi.resources.ColorContainer;
import de.pirckheimer_gymnasium.engine_pi.util.ColorUtil;
import de.pirckheimer_gymnasium.engine_pi.util.Graphics2DUtil;
import de.pirckheimer_gymnasium.engine_pi.util.TextUtil;

/**
 * Jedes Objekt auf der Zeichenebene ist ein {@link Actor}.
 *
 * <p>
 * Dies ist die absolute Superklasse aller grafischen Objekte. Umgekehrt kann
 * somit jedes grafische Objekt die folgenden Methoden nutzen.
 *
 * @author Michael Andonie
 * @author Niklas Keller
 */
@SuppressWarnings("OverlyComplexClass")
public abstract class Actor implements KeyStrokeListenerRegistration,
        MouseClickListenerRegistration, MouseScrollListenerRegistration,
        FrameUpdateListenerRegistration
{
    private <T> Supplier<T> createParentSupplier(Function<Layer, T> supplier)
    {
        return () -> {
            Layer layer = getLayer();
            if (layer == null)
            {
                return null;
            }
            return supplier.apply(layer);
        };
    }

    /**
     * Gibt an, ob das Objekt zurzeit überhaupt sichtbar sein soll.<br>
     * Ist dies nicht der Fall, so wird die Zeichenroutine direkt übergangen.
     */
    private boolean visible = true;

    /**
     * Die Farbe der Figur.
     */
    protected Color color;

    /**
     * Z-Index des Objekts, je höher, desto weiter im Vordergrund wird das
     * Objekt gezeichnet.
     */
    private int layerPosition = 1;

    /**
     * Opacity = Durchsichtigkeit des Objekts
     * <p>
     * <ul>
     * <li><code>0.0</code> entspricht einem komplett durchsichtigen Bild.</li>
     * <li><code>1.0</code> entspricht einem undurchsichtigem Bild.</li>
     * </ul>
     */
    private double opacity = 1;

    /**
     * Der JB2D-Handler für dieses spezifische Objekt.
     */
    private PhysicsHandler physicsHandler;

    private final EventListenerBundle listeners = new EventListenerBundle();

    private final EventListeners<KeyStrokeListener> keyStrokeListeners = new EventListeners<>(
            createParentSupplier(Layer::getKeyStrokeListeners));

    private final EventListeners<MouseClickListener> mouseClickListeners = new EventListeners<>(
            createParentSupplier(Layer::getMouseClickListeners));

    private final EventListeners<MouseScrollListener> mouseScrollListeners = new EventListeners<>(
            createParentSupplier(Layer::getMouseScrollListeners));

    private final EventListeners<FrameUpdateListener> frameUpdateListeners = new EventListeners<>(
            createParentSupplier(Layer::getFrameUpdateListeners));

    /**
     * Erstellt ein neues Objekt.
     *
     * @param defaultFixtureSupplier Ein Supplier, der die Default-Shape für
     *                               dieses Objekt generiert. Die ist in der
     *                               Regel ein optimal gelegtes Rechteck
     *                               parallel zu den Axen bei Rotationswinkel 0.
     */
    public Actor(Supplier<FixtureData> defaultFixtureSupplier)
    {
        this.physicsHandler = new NullHandler(new PhysicsData(
                () -> Collections.singletonList(defaultFixtureSupplier.get())));
        EventListeners.registerListeners(this);
    }

    /**
     * Fügt einen Beobachter hinzu, der ausgeführt wird, sobald das Objekt
     * angemeldet wurde.
     *
     * @param listener Beobachter-Implementierung
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor addMountListener(Runnable listener)
    {
        listeners.mount.add(listener);
        if (isMounted())
        {
            listener.run();
        }
        return this;
    }

    /**
     * Entfernt einen Beobachter, der ausgeführt wird, sobald das Objekt
     * angemeldet wurde.
     *
     * @param listener Beobachter-Implementierung
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor removeMountListener(Runnable listener)
    {
        listeners.mount.remove(listener);
        return this;
    }

    /**
     * Fügt einen Beobachter hinzu, der ausgeführt wird, sobald das Objekt
     * abgemeldet wurde.
     *
     * @param listener Beobachter-Implementierung
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor addUnmountListener(Runnable listener)
    {
        listeners.unmount.add(listener);
        return this;
    }

    /**
     * Entfernt einen Beobachter, der ausgeführt wird, sobald das Objekt
     * abgemeldet wurde.
     *
     * @param listener Beobachter-Implementierung
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor removeUnmountListener(Runnable listener)
    {
        listeners.unmount.remove(listener);
        return this;
    }

    /**
     * Setzt die Ebenenposition dieses Objekts. Je größer, desto weiter vorne
     * wird das Objekt gezeichnet.
     *
     * @param position Der Ebenen-Index.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #getLayerPosition()
     */
    @API
    public final Actor setLayerPosition(int position)
    {
        this.layerPosition = position;
        return this;
    }

    /**
     * Gibt die Ebenenposition zurück. Je größer, desto weiter vorne wird das
     * Objekt gezeichnet.
     *
     * @return Der Ebenen-Index.
     * @see #setLayerPosition(int)
     */
    @API
    public final int getLayerPosition()
    {
        return this.layerPosition;
    }

    /**
     * Setzt die Sichtbarkeit des Objektes.
     *
     * @param visible Ob das Objekt sichtbar sein soll oder nicht.<br>
     *                Ist dieser Wert <code>false</code>, so wird es nicht
     *                gezeichnet.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #isVisible()
     * @see #toggleVisible()
     */
    @API
    public final Actor setVisible(boolean visible)
    {
        this.visible = visible;
        return this;
    }

    /**
     * Wechselt zwischen den Zuständen sichtbar und nicht sichtbar hin und her.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #isVisible()
     * @see #setVisible(boolean)
     *
     * @since 0.27.0
     */
    @API
    public final Actor toggleVisible()
    {
        this.visible = !visible;
        return this;
    }

    /**
     * Gibt an, ob das Objekt sichtbar ist.
     *
     * @return Ist <code>true</code>, wenn das Objekt zurzeit sichtbar ist.
     *
     * @see #setVisible(boolean)
     */
    @API
    public final boolean isVisible()
    {
        return visible;
    }

    /**
     * Gibt die aktuelle Durchsichtigkeit des {@link Actor}-Objekts zurück.
     *
     * @return Gibt die aktuelle Durchsichtigkeit des {@link Actor}-Objekts
     *         zurück.
     */
    @API
    public final double getOpacity()
    {
        return opacity;
    }

    /**
     * Setzt die Durchsichtigkeit des Objekts.
     *
     * @param opacity
     *                <ul>
     *                <li><code>0.0</code> entspricht einem komplett
     *                durchsichtigen (transparenten) Objekt.</li>
     *                <li><code>1.0</code> entspricht einem undurchsichtigem
     *                Objekt.</li>
     *                </ul>
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor setOpacity(double opacity)
    {
        this.opacity = opacity;
        return this;
    }

    /**
     * Gibt die Farbe der Figur zurück.
     *
     * @return Die Farbe der Figur.
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Gibt die Komplementärfarbe der Figur zurück.
     *
     * @return Die Komplementärfarbe der Figur.
     */
    public Color getComplementaryColor()
    {
        return ColorUtil.getComplementary(color);
    }

    /**
     * Setzt die <b>Farbe</b> der Figur auf eine bestimmte Farbe.
     *
     * @param color Die neue Farbe.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public Actor setColor(Color color)
    {
        this.color = color;
        return this;
    }

    /**
     * Setzt die <b>Farbe</b> der Figur auf eine bestimmte Farbe, die als
     * <b>Zeichenkette</b> angegeben werden kann.
     *
     * @param color Ein Farbname, ein Farbalias ({@link ColorContainer siehe
     *              Auflistung}) oder eine Farbe in hexadezimaler Codierung (z.
     *              B. {@code #ff0000}).
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.resources.ColorContainer#get(String)
     */
    @API
    public Actor setColor(String color)
    {
        this.color = Resources.COLORS.getSafe(color);
        return this;
    }

    /**
     * Prüft, ob ein bestimmter Punkt innerhalb des Objekts liegt.
     *
     * @param point Der Punkt, der auf Inhalt im Objekt getestet werden soll.
     * @return <code>true</code>, wenn der Punkt innerhalb des Objekts liegt.
     */
    @API
    public final boolean contains(Vector point)
    {
        return physicsHandler.contains(point);
    }

    /**
     * Prüft, ob dieses Objekt sich mit einem weiteren Objekt schneidet.
     *
     * <p>
     * Für die Überprüfung des Überlappens werden die internen <b>Collider</b>
     * genutzt. Je nach Genauigkeit der Collider kann die Überprüfung
     * unterschiedlich befriedigend ausfallen. Die Collider können im
     * <b>Debug-Modus</b> der Engine eingesehen werden.
     *
     * @param other Ein weiteres {@link Actor}-Objekt.
     * @return <code>true</code>, wenn dieses {@link Actor}-Objekt sich mit
     *         <code>other</code> schneidet. Sonst <code>false</code>.
     * @see de.pirckheimer_gymnasium.engine_pi.Game#setDebug(boolean)
     */
    @API
    public final boolean overlaps(Actor other)
    {
        Body a = physicsHandler.getBody();
        Body b = other.getPhysicsHandler().getBody();
        return WorldHandler.isBodyCollision(a, b);
    }

    /**
     * Git eine List bestehend aus Kollisionsereignissen zurück.
     *
     * @return Eine Liste aus Kollisionsereignissen.
     */
    public final List<CollisionEvent<Actor>> getCollisions()
    {
        return physicsHandler.getCollisions();
    }

    /**
     * Setzt das allgemeine Verhalten, dass dieses Objekt im Rahmen der
     * Physics-Engine (und Kollisionserkennungen) haben soll. Eine Erläuterung
     * der verschiedenen Verhaltenstypen finden sich in der Dokumentation von
     * {@link BodyType}.
     *
     * @param type Der neue {@link BodyType} für den {@link Actor}.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see BodyType
     */
    @API
    public final Actor setBodyType(BodyType type)
    {
        Objects.requireNonNull(type, "Typ darf nicht null sein");
        this.physicsHandler.setType(type);
        return this;
    }

    /**
     * Verwandelt den {@link Actor} in ein <b>statisches</b> Objekt.
     *
     * <p>
     * <b>Statische</b> Objekte haben keine Geschwindigkeit. Sie bewegen sich
     * nicht in der Simulation, Kräfte haben keinen Einfluss auf sie. Diese
     * Eigenschaft gehört zum Beispiel zu <i>Wänden, Böden und Decken</i>.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setBodyType(BodyType)
     */
    @API
    public final Actor makeStatic()
    {
        setBodyType(BodyType.STATIC);
        return this;
    }

    /**
     * Verwandelt den {@link Actor} in ein <b>dynamisches</b> Objekt.
     *
     * <p>
     * <b>Dynamische</b> Objekte verhalten sich wie Objekte der Newton’schen
     * Mechanik. Sie können Kräfte auf sich wirken lassen und interagieren
     * miteinander. Diese Eigenschaft gehört zum Beispiel zu <i>Billiardkugeln,
     * Spielfiguren und Wurfgeschossen</i>.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setBodyType(BodyType)
     */
    @API
    public final Actor makeDynamic()
    {
        setBodyType(BodyType.DYNAMIC);
        return this;
    }

    /**
     * Verwandelt den {@link Actor} in ein <b>kinematisches</b> Objekt.
     *
     * <p>
     * <b>Kinematische</b> Objekte können eine Geschwindigkeit haben, aber
     * reagieren nicht auf Kräfte. Sie kollidieren (im Sinne der Physics) nur
     * mit dynamischen Objekten. Diese Eigenschaft gehört zum Beispiel zu
     * <i>beweglichen Plattformen</i>.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setBodyType(BodyType)
     */
    @API
    public final Actor makeKinematic()
    {
        setBodyType(BodyType.KINEMATIC);
        return this;
    }

    /**
     * Verwandelt den {@link Actor} in einen <b>Sensor</b>.
     *
     * <p>
     * <b>Sensoren</b> nehmen nicht an der Physiksimulation teil. Sie werden von
     * der Physics so behandelt, <i>als wären sie nicht da</i>. Sie generieren
     * trotzdem Kollisionsereignisse. Dies ist die <b>Standardeinstellung</b>
     * für Actors, wenn sie erstellt werden.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}
     *
     * @see #setBodyType(BodyType)
     */
    @API
    public final Actor makeSensor()
    {
        setBodyType(BodyType.SENSOR);
        return this;
    }

    /**
     * Verwandelt den {@link Actor} in ein <b>Partikel</b>.
     *
     * <p>
     * <b>Partikel</b> nehmen wie Sensoren <b>nicht an der Physiksimulation</b>
     * teil, sie generieren trotzdem Kollisionsereignisse. Dieser Typ ist
     * hilfreich, wenn du viele Actors generieren willst, diese aber rein
     * optisch auf das Spiel wirken sollen, wie zum Beispiel Dreck, den ein Auto
     * beim Anfahren aufwühlt oder Funken, die von einer Wand nach einem Schuss
     * sprühen.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setBodyType(BodyType)
     */
    @API
    public final Actor makeParticle()
    {
        setBodyType(BodyType.PARTICLE);
        return this;
    }

    /**
     * Gibt aus, was für ein Physics-Typ dieses Objekt momentan ist.
     *
     * @return Der Physics-Typ, der das entsprechende {@link Actor}-Objekt
     *         momentan ist.
     *
     * @see BodyType
     */
    @API
    public final BodyType getBodyType()
    {
        return physicsHandler.getType();
    }

    /**
     * Setzt die Halterung (Fixture) für dieses Objekt neu. Dies hat Einfluss
     * auf die Physik (Kollisionen, Masse, etc.).
     *
     * @param code Eine Minisprache, die die Halterung definiert. Alle Werte
     *             sind in der Einheit Meter anzugeben. Die Koordinatenangaben
     *             beziehen sich dabei auf den Ankerpunkt der Figur links unten.
     *             <ul>
     *             <li>Die Formen werden getrennt durch "&amp;"</li>
     *             <li>Rechteck: <code>R0.5,0.5,4,5</code> Rechteck mit
     *             Startpunkt (0.5|0.5), Breite 4 Meter, Höhe 5 Meter</li>
     *             <li>Polygon: <code>P4,4,5,5,1,2</code> Polygon mit drei
     *             Punkten: (4|4), (5|5), (1|2)</li>
     *             <li>Kreis: <code>C1,1,4</code> Kreis mit Mittelpunkt (1|1)
     *             und Radius 4</li>
     *             </ul>
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see FixtureBuilder#fromString(String)
     * @see #setFixture(Supplier)
     * @see #setFixtures(Supplier)
     */
    @API
    public final Actor setFixtures(String code)
    {
        this.setFixtures(FixtureBuilder.fromString(code));
        return this;
    }

    /**
     * Setzt die Halterung (Fixture) der Figur neu in eine einzige alternative
     * Halterung.
     *
     * @param fixtureSupplier Der Supplier, der die neue Shape des Objektes
     *                        ausgibt.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setFixtures(Supplier)
     */
    @API
    public final Actor setFixture(Supplier<FixtureData> fixtureSupplier)
    {
        this.setFixtures(
                () -> Collections.singletonList(fixtureSupplier.get()));
        return this;
    }

    /**
     * Ändert die Halterung (Fixture) dieses Objekts durch Angabe einer Liste.
     *
     * @param fixturesSupplier Ein Supplier, der eine Liste mit allen neuen
     *                         Shapes für den Actor angibt.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setFixture(Supplier)
     */
    @API
    public final Actor setFixtures(Supplier<List<FixtureData>> fixturesSupplier)
    {
        this.physicsHandler.setFixtures(fixturesSupplier);
        return this;
    }

    /**
     * Die Basiszeichenmethode.
     *
     * <p>
     * Sie schließt eine Fallabfrage zur Sichtbarkeit ein.
     * </p>
     *
     * @param g             Das {@link Graphics2D}-Objekt, in das gezeichnet
     *                      werden soll.
     * @param r             Das Bounds, dass die Kameraperspektive
     *                      repräsentiert.<br>
     *                      Hierbei soll zunächst getestet werden, ob das Objekt
     *                      innerhalb der Kamera liegt, und erst dann gezeichnet
     *                      werden.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @Internal
    public final void renderBasic(Graphics2D g, Bounds r, double pixelPerMeter)
    {
        if (visible && this.isWithinBounds(r))
        {
            double rotation = physicsHandler.getRotation();
            Vector position = physicsHandler.getPosition();
            // ____ Pre-Render ____
            AffineTransform transform = g.getTransform();
            g.rotate(-Math.toRadians(rotation), position.getX() * pixelPerMeter,
                    -position.getY() * pixelPerMeter);
            g.translate(position.getX() * pixelPerMeter,
                    -position.getY() * pixelPerMeter);
            // Durchsichtigkeit
            Composite composite;
            if (opacity != 1)
            {
                composite = g.getComposite();
                g.setComposite(AlphaComposite
                        .getInstance(AlphaComposite.SRC_OVER, (float) opacity));
            }
            else
            {
                composite = null;
            }
            // Damit im Debug-Modus nur die Umrisse der Figuren dargestellt
            // werden können.
            if (Game.getRenderActors())
            {
                // Zeichnen der Füllungen der Figuren. Die einzelnen
                // Unterklassen müssen die render-Methode implementieren, die
                // dann das Zeichen der Füllungen übernimmt.
                render(g, pixelPerMeter);
            }
            if (Game.isDebug())
            {
                synchronized (this)
                {
                    // Visualisiere die Shape
                    Body body = physicsHandler.getBody();
                    if (body != null)
                    {
                        Fixture fixture = body.m_fixtureList;
                        while (fixture != null && fixture.m_shape != null)
                        {
                            renderShape(fixture.m_shape, g, pixelPerMeter,
                                    this);
                            fixture = fixture.m_next;
                        }
                    }
                }
            }
            // ____ Post-Render ____
            // Opacity Update
            if (composite != null)
            {
                g.setComposite(composite);
            }
            // Transform zurücksetzen
            g.setTransform(transform);
        }
    }

    /**
     * Rendert eine Shape von JBox2D nach den gegebenen Voreinstellungen im
     * {@link Graphics2D}-Objekt.
     *
     * Farbe &amp; Co. sollte im Vorfeld eingestellt sein. Diese Methode
     * übernimmt nur das direkte rendern.
     *
     * @param shape         Die Shape, die zu rendern ist.
     * @param g             Das {@link Graphics2D}-Objekt, in das gezeichnet
     *                      werden soll.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @Internal
    private static void renderShape(Shape shape, Graphics2D g,
            double pixelPerMeter, Actor actor)
    {
        if (shape == null)
        {
            return;
        }
        AffineTransform pre = g.getTransform();
        Graphics2DUtil.setAntiAliasing(g, false);
        // Den Anker der Figur einzeichnen
        g.setColor(Resources.COLORS.getSafe("yellow"));
        g.drawOval(-1, -1, 2, 2);
        if (DebugConfiguration.showPositions)
        {
            Graphics2DUtil.drawText(g, actor.getPositionformatted(), 8, 5, 5);
        }
        // Hat die Figur eine Farbe, so wird als Umriss der Komplementärfarbe
        // gewählt.
        // Hat die Figure keine Farbe, so wird der Umriss rot gezeichnet.
        g.setColor(actor.color != null ? actor.getComplementaryColor()
                : Resources.COLORS.getSafe("red"));
        if (shape instanceof PolygonShape polygonShape)
        {
            Vec2[] vec2s = polygonShape.getVertices();
            int[] xs = new int[polygonShape.getVertexCount()],
                    ys = new int[polygonShape.getVertexCount()];
            for (int i = 0; i < xs.length; i++)
            {
                xs[i] = (int) (vec2s[i].x * pixelPerMeter);
                ys[i] = (-1) * (int) (vec2s[i].y * pixelPerMeter);
            }
            g.drawPolygon(xs, ys, xs.length);
        }
        else if (shape instanceof CircleShape circleShape)
        {
            double diameter = (circleShape.m_radius * 2);
            g.drawOval(
                    (int) ((circleShape.m_p.x - circleShape.m_radius)
                            * pixelPerMeter),
                    (int) ((-circleShape.m_p.y - circleShape.m_radius)
                            * pixelPerMeter),
                    (int) (diameter * pixelPerMeter),
                    (int) (diameter * pixelPerMeter));
        }
        else
        {
            throw new RuntimeException("Konnte die Shape (" + shape
                    + ") nicht rendern, unerwartete Shape");
        }
        Graphics2DUtil.setAntiAliasing(g, true);
        g.setTransform(pre);
    }

    /**
     * Interne Methode. Prüft, ob das anliegende Objekt (teilweise) innerhalb
     * des sichtbaren Bereichs liegt.
     *
     * @param bounds Die Bounds der Kamera.
     * @return <code>true</code>, wenn das Objekt (teilweise) innerhalb des
     *         derzeit sichtbaren Bereichs liegt, sonst <code>false</code>.
     */
    @Internal
    private boolean isWithinBounds(Bounds bounds)
    {
        // FIXME : Parameter ändern (?) und Funktionalität implementieren.
        return true;
    }

    /**
     * Gibt den aktuellen, internen Physics-Handler aus.
     *
     * @return der aktuellen, internen Physics-Handler aus.
     */
    @Internal
    public final PhysicsHandler getPhysicsHandler()
    {
        return physicsHandler;
    }

    /**
     * Meldet einen neuen {@link CollisionListener} an, der auf alle Kollisionen
     * zwischen diesem Actor und dem Actor <code>collider</code> reagiert.
     *
     * @param listener Der Listener, der bei Kollisionen zwischen dem
     *                 <b>ausführenden Actor</b> und <code>collider</code>
     *                 informiert werden soll.
     * @param collider Ein weiteres {@link Actor}-Objekt.
     * @param <E>      Typ-Parameter. Sollte im Regelfall exakt die Klasse von
     *                 <code>collider</code> sein. Dies ermöglicht die Nutzung
     *                 von spezifischen Methoden aus spezialisierteren Klassen
     *                 der Actor-Hierarchie.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #addCollisionListener(CollisionListener)
     */
    @API
    public final <E extends Actor> Actor addCollisionListener(E collider,
            CollisionListener<E> listener)
    {
        WorldHandler.addSpecificCollisionListener(this, collider, listener);
        return this;
    }

    /**
     * Meldet einen neuen {@link CollisionListener} an, der auf alle Kollisionen
     * reagiert, die dieser Actor mit seiner Umwelt erlebt.
     *
     * @param <E>      Typ des anderen Objekts bei Kollisionen.
     * @param clazz    Typ des anderen Objekts bei Kollisionen.
     * @param listener Der Beobachter, der bei Kollisionen informiert werden
     *                 soll, die der <b>ausführende Actor</b> mit allen anderen
     *                 Objekten der Szene erlebt.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #addCollisionListener(Actor, CollisionListener)
     */
    @API
    public final <E extends Actor> Actor addCollisionListener(Class<E> clazz,
            CollisionListener<E> listener)
    {
        // noinspection OverlyComplexAnonymousInnerClass
        WorldHandler.addGenericCollisionListener(new CollisionListener<>()
        {
            @Override
            public void onCollision(CollisionEvent<Actor> collisionEvent)
            {
                if (clazz.isInstance(collisionEvent.getColliding()))
                {
                    // noinspection unchecked
                    listener.onCollision((CollisionEvent<E>) collisionEvent);
                }
            }

            @Override
            public void onCollisionEnd(CollisionEvent<Actor> collisionEvent)
            {
                if (clazz.isInstance(collisionEvent.getColliding()))
                {
                    // noinspection unchecked
                    listener.onCollisionEnd((CollisionEvent<E>) collisionEvent);
                }
            }
        }, this);
        return this;
    }

    /**
     * Meldet einen neuen {@link CollisionListener} an, der auf alle Kollisionen
     * reagiert, die dieser Actor mit seiner Umwelt erlebt.
     *
     * @param listener Der Listener, der bei Kollisionen informiert werden soll,
     *                 die der <b>ausführende Actor</b> mit allen anderen
     *                 Objekten der Szene erlebt.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #addCollisionListener(Actor, CollisionListener)
     */
    @API
    public final Actor addCollisionListener(CollisionListener<Actor> listener)
    {
        WorldHandler.addGenericCollisionListener(listener, this);
        return this;
    }

    /**
     * Zeichnet die Figur an der Position {@code (0|0)} mit der Rotation
     * {@code 0}.
     *
     * @param g             Das {@link Graphics2D}-Objekt, in das gezeichnet
     *                      werden soll.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @Internal
    public abstract void render(Graphics2D g, double pixelPerMeter);

    @Internal
    public final Actor setPhysicsHandler(PhysicsHandler handler)
    {
        WorldHandler worldHandler = handler.getWorldHandler();
        WorldHandler previousWorldHandler = physicsHandler.getWorldHandler();
        if (worldHandler == null)
        {
            if (previousWorldHandler == null)
            {
                return this;
            }
            Layer layer = previousWorldHandler.getLayer();
            keyStrokeListeners.invoke(layer::removeKeyStrokeListener);
            mouseClickListeners.invoke(layer::removeMouseClickListener);
            mouseScrollListeners.invoke(layer::removeMouseScrollListener);
            frameUpdateListeners.invoke(layer::removeFrameUpdateListener);
            listeners.unmount.invoke(Runnable::run);
            physicsHandler = handler;
        }
        else
        {
            if (previousWorldHandler != null)
            {
                return this;
            }
            physicsHandler = handler;
            Layer layer = worldHandler.getLayer();
            listeners.mount.invoke(Runnable::run);
            keyStrokeListeners.invoke(layer::addKeyStrokeListener);
            mouseClickListeners.invoke(layer::addMouseClickListener);
            mouseScrollListeners.invoke(layer::addMouseScrollListener);
            frameUpdateListeners.invoke(layer::addFrameUpdateListener);
        }
        return this;
    }

    /**
     * @return Gibt die Ebene zurück, an der das aktuelle Objekt angemeldet ist,
     *         sonst {@code null}.
     */
    public final Layer getLayer()
    {
        WorldHandler worldHandler = physicsHandler.getWorldHandler();
        if (worldHandler == null)
        {
            return null;
        }
        return worldHandler.getLayer();
    }

    /**
     * Entfernt das aktuelle Objekt aus seiner aktuellen Ebene, falls das Objekt
     * gerade einer Ebene zugeordnet ist.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    public final Actor remove()
    {
        Layer layer = getLayer();
        if (layer != null)
        {
            layer.remove(this);
        }
        return this;
    }

    /**
     * @return Liste der {@link MouseClickListener}.
     */
    @API
    public final EventListenerBundle getListenerBundle()
    {
        return listeners;
    }

    /**
     * @return Liste der {@link KeyStrokeListener}.
     */
    @API
    public final EventListeners<KeyStrokeListener> getKeyStrokeListeners()
    {
        return keyStrokeListeners;
    }

    /**
     * @return Liste der {@link MouseClickListener}.
     */
    @API
    public final EventListeners<MouseClickListener> getMouseClickListeners()
    {
        return mouseClickListeners;
    }

    /**
     * @return Liste der {@link MouseScrollListener}.
     */
    @API
    public final EventListeners<MouseScrollListener> getMouseScrollListeners()
    {
        return mouseScrollListeners;
    }

    /**
     * @return Liste der {@link FrameUpdateListener}.
     */
    @API
    public final EventListeners<FrameUpdateListener> getFrameUpdateListeners()
    {
        return frameUpdateListeners;
    }

    /**
     * Setzt, ob <i>im Rahmen der physikalischen Simulation</i> die Rotation
     * dieses Objekts blockiert werden soll.
     *
     * <p>
     * Das Objekt kann in jedem Fall weiterhin über einen direkten
     * Methodenaufruf rotiert werden. Der folgende Code ist immer wirksam,
     * unabhängig davon, ob die Rotation im Rahmen der physikalischen Simulation
     * blockiert ist:
     *
     * <p>
     * <code>
     * actor.getPosition.rotate(4.31);
     * </code>
     *
     * @param rotationLocked Ist dieser Wert <code>true</code>, rotiert sich
     *                       dieses Objekts innerhalb der physikalischen
     *                       Simulation <b>nicht mehr</b>. Ist dieser Wert
     *                       <code>false</code>, rotiert sich dieses Objekt
     *                       innerhalb der physikalischen Simulation.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #isRotationLocked()
     */
    @API
    public final Actor setRotationLocked(boolean rotationLocked)
    {
        physicsHandler.setRotationLocked(rotationLocked);
        return this;
    }

    /**
     * Blockiert die Rotation <i>im Rahmen der physikalischen Simulation</i>.
     *
     * <p>
     * Das Objekt kann in jedem Fall weiterhin über einen direkten
     * Methodenaufruf rotiert werden. Der folgende Code ist immer wirksam,
     * unabhängig davon, ob die Rotation im Rahmen der physikalischen Simulation
     * blockiert ist:
     *
     * <p>
     * <code>
     * actor.getPosition.rotate(4.31);
     * </code>
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #isRotationLocked()
     *
     * @since 0.27.0
     */
    @API
    public final Actor lockRotation()
    {
        physicsHandler.setRotationLocked(true);
        return this;
    }

    /**
     * Gibt an, ob die Rotation dieses Objekts derzeit innerhalb der
     * physikalischen Simulation blockiert ist.
     *
     * @return <code>true</code>, wenn die Rotation dieses Objekts derzeit
     *         innerhalb der physikalischen Simulation blockiert ist.
     *
     * @see #setRotationLocked(boolean)
     */
    @API
    public final boolean isRotationLocked()
    {
        return physicsHandler.isRotationLocked();
    }

    /**
     * Gibt die aktuelle Masse des Ziel-Objekts aus. Die Form bleibt
     * unverändert, daher ändert sich die <b>Dichte</b> in der Regel.
     *
     * @return Die Masse des Ziel-Objekts in <b>[kg]</b>.
     */
    @API
    public final double getMass()
    {
        return physicsHandler.getMass();
    }

    /**
     * Setzt die Dichte des Objekts neu. Die Form bleibt dabei unverändert,
     * daher ändert sich die <b>Masse</b> in der Regel.
     *
     * @param density Die neue Dichte des Objekts in <b>[kg/m^2]</b>.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor setDensity(double density)
    {
        physicsHandler.setDensity(density);
        return this;
    }

    /**
     * Gibt die aktuelle Dichte des Objekts an.
     *
     * @return Die aktuelle Dichte des Objekts in <b>[kg/m^2]</b>.
     */
    @API
    public final double getDensity()
    {
        return physicsHandler.getDensity();
    }

    /**
     * Setzt den Gravitationsfaktor, normalerweise im Bereich [0, 1].
     *
     * @param factor Der Gravitationsfaktor.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor setGravityScale(double factor)
    {
        physicsHandler.setGravityScale(factor);
        return this;
    }

    /**
     * Gibt den aktuellen Gravitationsfaktor des Objekts an.
     *
     * @return Den Gravitationsfaktor.
     */
    @API
    public final double getGravityScale()
    {
        return physicsHandler.getGravityScale();
    }

    /**
     * Setzt den Reibungskoeffizient für das Objekt. Dieser hat Einfluss auf die
     * Bewegung des Objekts.
     *
     * @param friction Der Reibungskoeffizient. In der Regel im Bereich <b>[0;
     *                 1]</b>.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #getFriction()
     */
    @API
    public final Actor setFriction(double friction)
    {
        physicsHandler.setFriction(friction);
        return this;
    }

    /**
     * Gibt den Reibungskoeffizienten für dieses Objekt aus.
     *
     * @return Der Reibungskoeffizient des Objekts. Ist in der Regel (in der
     *         Realität) ein Wert im Bereich <b>[0; 1]</b>.
     * @see #setFriction(double)
     */
    @API
    public final double getFriction()
    {
        return physicsHandler.getFriction();
    }

    /**
     * Setzt die Dämpfung der Rotationsgeschwindigkeit.
     *
     * @param damping Die Dämpfung der Rotationsgeschwindigkeit.
     */
    @API
    public final Actor setAngularDamping(double damping)
    {
        physicsHandler.setAngularDamping(damping);
        return this;
    }

    /**
     * Gibt die Dämpfung der Rotationsgeschwindigkeit zurück.
     *
     * @return Die Dämpfung der Rotationsgeschwindigkeit.
     */
    @API
    public final double getAngularDamping()
    {
        return physicsHandler.getAngularDamping();
    }

    /**
     * Setzt die Dämpfung der Geschwindigkeit.
     *
     * @param damping Die Dämpfung der Geschwindigkeit.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor setLinearDamping(double damping)
    {
        physicsHandler.setLinearDamping(damping);
        return this;
    }

    /**
     * Gibt die Dämpfung der Geschwindigkeit zurück.
     *
     * @return Dämpfung der Geschwindigkeit
     */
    @API
    public final double getLinearDamping()
    {
        return physicsHandler.getLinearDamping();
    }

    /**
     * Setzt die Geschwindigkeit „hart“ für dieses Objekt.
     *
     * <p>
     * Damit wird die aktuelle Bewegung (nicht aber die Rotation) des Objekts
     * ignoriert und hart auf den übergebenen Wert gesetzt.
     *
     * @param velocity Die Geschwindigkeit, mit der sich dieses Objekt ab sofort
     *                 bewegen soll. In <b>[m / s]</b>
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #getVelocity()
     */
    @API
    public final Actor setVelocity(Vector velocity)
    {
        if (velocity.isNaN())
        {
            return this;
        }
        physicsHandler.setVelocity(velocity);
        return this;
    }

    /**
     * Gibt die Geschwindigkeit aus, mit der sich dieses Objekt gerade (also in
     * diesem Frame) bewegt.
     *
     * @return Die Geschwindigkeit, mit der sich dieses Objekt gerade (also in
     *         diesem Frame) bewegt. In <b>[m / s]</b>
     *
     * @see #setVelocity(Vector)
     * @see #getAngularVelocity()
     */
    @API
    public final Vector getVelocity()
    {
        return physicsHandler.getVelocity();
    }

    /**
     * Gibt die aktuelle Drehgeschwindigkeit aus.
     *
     * @return Die aktuelle Drehgeschwindigkeit.
     *
     * @see #setAngularVelocity(double)
     * @see #getVelocity()
     * @see #getAngularDamping()
     */
    @API
    public final double getAngularVelocity()
    {
        return physicsHandler.getAngularVelocity();
    }

    /**
     * Setzt die Drehgeschwindigkeit "hart" für dieses Objekt. Damit wird die
     * aktuelle Rotation des Objekts ignoriert und hart auf den übergebenen Wert
     * gesetzt.
     *
     * @param rotationsPerSecond Die Geschwindigkeit, mit der sich dieses Objekt
     *                           ab sofort bewegen soll. In <b>[Umdrehnungen /
     *                           s]</b>
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #getAngularVelocity()
     * @see #setVelocity(Vector)
     * @see #setAngularDamping(double)
     */
    @API
    public final Actor setAngularVelocity(double rotationsPerSecond)
    {
        if (Double.isNaN(rotationsPerSecond))
        {
            return this;
        }
        physicsHandler.setAngularVelocity(rotationsPerSecond);
        return this;
    }

    /**
     * Setzt die Stoßzahl bzw. den Restitutionskoeffizienten.
     *
     * <p>
     * Mit Hilfe der <a href=
     * "https://de.wikipedia.org/wiki/Sto%C3%9F_(Physik)#Realer_Sto%C3%9F">Stoßzahl</a>
     * bzw. des <a href=
     * "https://en.wikipedia.org/wiki/Coefficient_of_restitution">Restitutionskoeffizienten</a>
     * kann angegeben werden, wie „elastisch“ ein Objekt ist. Wie bei der
     * Reibung liegt der Wertebereich zwischen 0 und 1, wobei 0 bedeutet, dass
     * das Objekt überhaupt nicht zurückfedert, und 1, dass die gesamte Energie
     * des Aufpralls erhalten bleibt. Bei Werte größer 1 prallt das Objekt bei
     * jeder Kollision immer weiter ab. Wenn zwei Objekte aufeinander prallen,
     * federt das Objekt mit dem höheren der beiden Stoßzahlen zurück. Soll ein
     * Objekt nicht abprallen, so müssen beide Objekte auf 0 gesetzt werden.
     * </p>
     *
     * @param elasticity Die Stoßzahl bzw. der Restitutionskoeffizient.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #getElasticity()
     *
     *      @jbox.2d
     *      https://github.com/jbox2d/jbox2d/blob/94bb3e4a706a6d1a5d8728a722bf0af9924dde84/jbox2d-library/src/main/java/org/jbox2d/dynamics/FixtureDef.java#L132-L137 @box.2d
     *      https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_fixture.h#L335-L338
     */
    @API
    public final Actor setElasticity(double elasticity)
    {
        if (Double.isNaN(elasticity))
        {
            throw new RuntimeException("Ungültige Stoßzahl: " + elasticity);
        }
        physicsHandler.setRestitution(elasticity);
        return this;
    }

    /**
     * Gibt die Stoßzahl bzw. den Restitutionskoeffizienten zurück.
     *
     * <p>
     * Mit Hilfe der <a href=
     * "https://de.wikipedia.org/wiki/Sto%C3%9F_(Physik)#Realer_Sto%C3%9F">Stoßzahl</a>
     * bzw. des <a href=
     * "https://en.wikipedia.org/wiki/Coefficient_of_restitution">Restitutionskoeffizienten</a>
     * kann angegeben werden, wie „elastisch“ ein Objekt ist. Wie bei der
     * Reibung liegt der Wertebereich zwischen 0 und 1, wobei 0 bedeutet, dass
     * das Objekt überhaupt nicht zurück federt, und 1, dass die gesamte Energie
     * des Aufpralls erhalten bleibt. Bei Werte größer 1 prallt das Objekt bei
     * jeder Kollision immer weiter ab. Wenn zwei Objekte aufeinander prallen,
     * federt das Objekt mit dem höheren der beiden Stoßzahlen zurück. Soll ein
     * Objekt nicht abprallen, so müssen beide Objekte auf 0 gesetzt werden.
     * </p>
     *
     * @return Die Stoßzahl bzw. den Restitutionskoeffizienten.
     *
     * @see #setElasticity(double)
     *
     *      @jbox.2d
     *      https://github.com/jbox2d/jbox2d/blob/94bb3e4a706a6d1a5d8728a722bf0af9924dde84/jbox2d-library/src/main/java/org/jbox2d/dynamics/FixtureDef.java#L125-L130 @box.2d
     *      https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_fixture.h#L330-L333
     */
    @API
    public final double getElasticity()
    {
        return physicsHandler.getRestitution();
    }

    /**
     * Wirkt ein Drehmoment auf das Objekt.
     *
     * @param torque Drehmoment, der auf das Ziel-Objekt wirken soll. In [N×m]
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor applyTorque(double torque)
    {
        if (Double.isNaN(torque))
        {
            return this;
        }
        physicsHandler.applyTorque(torque);
        return this;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    public final Actor applyRotationImpulse(double impulse)
    {
        if (Double.isNaN(impulse))
        {
            return this;
        }
        physicsHandler.applyRotationImpulse(impulse);
        return this;
    }

    /**
     * Wirkt eine Kraft auf den <i>Schwerpunkt</i> des Objekts.
     *
     * @param force Der Kraftvektor in <b>[N]</b>.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor applyForce(Vector force)
    {
        if (force.isNaN())
        {
            return this;
        }
        physicsHandler.applyForce(force);
        return this;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     */
    public final Actor applyForce(double forceX, double forceY)
    {
        applyForce(new Vector(forceX, forceY));
        return this;
    }

    /**
     * Wirkt eine Kraft auf einem bestimmten <i>Punkt in der Welt</i>.
     *
     * @param force       Kraft in <b>[N]</b>
     * @param globalPoint Ort auf der <i>Zeichenebene</i>, an dem die Kraft
     *                    wirken soll.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor applyForce(Vector force, Vector globalPoint)
    {
        if (force.isNaN() || globalPoint.isNaN())
        {
            return this;
        }
        physicsHandler.applyForce(force, globalPoint);
        return this;
    }

    /**
     * Wirkt einen Impuls auf den <i>Schwerpunkt</i> des Objekts.
     *
     * <p>
     * Beispiele:
     * </p>
     *
     * <ul>
     * <li>Hammer (300 g, 1 m/s): 0,3 Ns</li>
     *
     * <li>Golfball (45 g, 80 m/s): 3,6 Ns</li>
     *
     * <li>Pistolenkugel (9 g, 500 m/s): 4,5 Ns
     *
     * <li>Radfahrer (80 kg, 18 km/h): 400 Ns</li>
     *
     * <li>Auto (2 t, 30 km/h): 16 000 Ns</li>
     * </ul>
     *
     * @param impulse Der Impuls in <b>[Ns]</b> [(kg * m) / s], der auf den
     *                Schwerpunkt wirken soll.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor applyImpulse(Vector impulse)
    {
        if (impulse.isNaN())
        {
            return this; // ignore invalid impulses, they make box2d hang
        }
        physicsHandler.applyImpulse(impulse, physicsHandler.getCenter());
        return this;
    }

    /**
     * Wirkt einen Impuls auf den <i>Schwerpunkt</i> des Objekts.
     *
     * <p>
     * Beispiele:
     * </p>
     *
     * <ul>
     * <li>Hammer (300 g, 1 m/s): 0,3 Ns</li>
     *
     * <li>Golfball (45 g, 80 m/s): 3,6 Ns</li>
     *
     * <li>Pistolenkugel (9 g, 500 m/s): 4,5 Ns
     *
     * <li>Radfahrer (80 kg, 18 km/h): 400 Ns</li>
     *
     * <li>Auto (2 t, 30 km/h): 16 000 Ns</li>
     * </ul>
     *
     * @param impulseX Der Impuls in x-Richtung in <b>[Ns]</b> [(kg * m) / s],
     *                 der auf den Schwerpunkt wirken soll.
     * @param impulseY Der Impuls in x-Richtung in <b>[Ns]</b> [(kg * m) / s],
     *                 der auf den Schwerpunkt wirken soll.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    public final Actor applyImpulse(double impulseX, double impulseY)
    {
        applyImpulse(new Vector(impulseX, impulseY));
        return this;
    }

    /**
     * Wirkt einen Impuls an einem bestimmten <i>Punkt in der Welt</i>.
     *
     * <p>
     * Beispiele:
     * </p>
     *
     * <ul>
     * <li>Hammer (300 g, 1 m/s): 0,3 Ns</li>
     *
     * <li>Golfball (45 g, 80 m/s): 3,6 Ns</li>
     *
     * <li>Pistolenkugel (9 g, 500 m/s): 4,5 Ns
     *
     * <li>Radfahrer (80 kg, 18 km/h): 400 Ns</li>
     *
     * <li>Auto (2 t, 30 km/h): 16 000 Ns</li>
     * </ul>
     *
     * @param impulse     Der Impuls in <b>[Ns]</b>.
     * @param globalPoint Der Ort auf der <i>Zeichenebene</i>, an dem der Impuls
     *                    wirken soll.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor applyImpulse(Vector impulse, Vector globalPoint)
    {
        physicsHandler.applyImpulse(impulse, globalPoint);
        return this;
    }

    /**
     * Versetzt das Objekt - unabhängig von aktuellen Kräften und
     * Geschwindigkeiten - <i>in Ruhe</i>.
     *
     * <p>
     * Damit werden alle (physikalischen) Bewegungen des Objektes zurückgesetzt.
     * Sollte eine konstante <i>Schwerkraft</i> (oder etwas Vergleichbares)
     * existieren, wo wird dieses Objekt jedoch möglicherweise aus der Ruhelage
     * wieder in Bewegung versetzt.
     * </p>
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor resetMovement()
    {
        physicsHandler.resetMovement();
        return this;
    }

    /**
     * Testet, ob das Objekt „steht“.
     *
     * <p>
     * Diese Funktion ist unter anderem hilfreich für die Entwicklung von
     * Platformern (z.B. wenn der Spieler nur springen können soll, wenn er auf
     * dem Boden steht).
     * </p>
     *
     * <p>
     * Diese Funktion ist eine <b>Heuristik</b>, sprich sie ist eine Annäherung.
     * In einer Physik-Simulation ist die Definition von „stehen“ nicht
     * unbedingt einfach. Hier bedeutet es Folgendes:
     * </p>
     *
     * <i>Ein Objekt steht genau dann, wenn alle Eigenschaften erfüllt sind:</i>
     * <ul>
     * <li>Es ist ein <b>dynamisches Objekt</b>.</li>
     * <li>Direkt unter der Mitte der minimalen <a href=
     * "https://en.wikipedia.org/wiki/Minimum_bounding_box#Axis-aligned_minimum_bounding_box">AABB</a>,
     * die das gesamte Objekt umspannt, befindet sich ein <b>statisches
     * Objekt</b>.</li>
     * </ul>
     *
     * @return {@code true}, falls das Objekt auf einem anderen Objekt steht,
     *         siehe Beschreibung.
     */
    @API
    public final boolean isGrounded()
    {
        return physicsHandler.isGrounded();
    }
    /* _ Joints _____________________________________________________________ */

    /**
     * Erstellt einen Revolute-Joint zwischen dem zugehörigen
     * {@link Actor}-Objekt und einem weiteren.
     *
     * <h4>Definition Revolute-Joint</h4>
     * <p>
     * Verbindet zwei {@link Actor}-Objekte <b>untrennbar an einem
     * Anchor-Point</b>. Die Objekte können sich ab sofort nur noch <b>relativ
     * zueinander drehen</b>.
     * </p>
     *
     * @param other  Das zweite {@link Actor}-Objekt, das ab sofort mit dem
     *               zugehörigen {@link Actor}-Objekt über einen
     *               <code>RevoluteJoint</code> verbunden sein soll.
     * @param anchor Der Ankerpunkt <b>relativ zu diesem Actor</b>. Es wird
     *               davon ausgegangen, dass beide Objekte bereits korrekt
     *               positioniert sind.
     * @return Ein <code>Joint</code>-Objekt, mit dem der Joint weiter gesteuert
     *         werden kann.
     * @see org.jbox2d.dynamics.joints.RevoluteJoint
     */
    @API
    public final RevoluteJoint createRevoluteJoint(Actor other, Vector anchor)
    {
        return WorldHandler.createJoint(this, other, (world, a, b) -> {
            RevoluteJointDef def = new RevoluteJointDef();
            def.initialize(a, b, getPosition().add(anchor).toVec2());
            def.collideConnected = false;
            return (org.jbox2d.dynamics.joints.RevoluteJoint) world
                    .createJoint(def);
        }, new RevoluteJoint());
    }

    /**
     * Erstellt eine Seilverbindung zwischen diesem und einem weiteren
     * {@link Actor}-Objekt.
     *
     * @param other       Das zweite {@link Actor}-Objekt, das ab sofort mit dem
     *                    zugehörigen {@link Actor}-Objekt über einen
     *                    <code>RopeJoint</code> verbunden sein soll.
     * @param anchorThis  Der Ankerpunkt für das zugehörige
     *                    {@link Actor}-Objekt. Der erste Befestigungspunkt des
     *                    Lassos. Die Angabe ist relativ zur Position des
     *                    zugehörigen Objekts (linke untere Ecke).
     * @param anchorOther Der Ankerpunkt für das zweite {@link Actor}-Objekt,
     *                    also <code>other</code>. Der zweite Befestigungspunkt
     *                    des Lassos. Die Angabe ist relativ zur Position des
     *                    zugehörigen Objekts (linke untere Ecke).
     * @param ropeLength  Die Länge des Lassos. Dies ist ab sofort die maximale
     *                    Länge, die die beiden Ankerpunkte der Objekte
     *                    voneinader entfernt sein können.
     * @return Ein <code>Joint</code>-Objekt, mit dem der Joint weiter gesteuert
     *         werden kann.
     * @see org.jbox2d.dynamics.joints.RopeJoint
     */
    @API
    public final RopeJoint createRopeJoint(Actor other, Vector anchorThis,
            Vector anchorOther, double ropeLength)
    {
        return WorldHandler.createJoint(this, other, (world, a, b) -> {
            RopeJointDef def = new RopeJointDef();
            def.bodyA = a;
            def.bodyB = b;
            def.localAnchorA.set(anchorThis.toVec2());
            def.localAnchorB.set(anchorOther.toVec2());
            def.collideConnected = true;
            def.maxLength = (float) ropeLength;
            return (org.jbox2d.dynamics.joints.RopeJoint) world
                    .createJoint(def);
        }, new RopeJoint());
    }

    /**
     * Erstellt einen neuen {@link PrismaticJoint} zwischen zwei Objekten.
     *
     * @param other     Objekt, das mit dem aktuellen verbunden werden soll
     * @param anchor    Verbindungspunkt
     * @param axisAngle Winkel
     * @return Objekt für die weitere Steuerung des Joints
     */
    @API
    public final PrismaticJoint createPrismaticJoint(Actor other, Vector anchor,
            double axisAngle)
    {
        return WorldHandler.createJoint(this, other, (world, a, b) -> {
            double angleInRadians = Math.toRadians(axisAngle);
            PrismaticJointDef def = new PrismaticJointDef();
            def.initialize(a, b, getPosition().add(anchor).toVec2(),
                    new Vec2((float) Math.cos(angleInRadians),
                            (float) Math.sin(angleInRadians)));
            def.collideConnected = false;
            return (org.jbox2d.dynamics.joints.PrismaticJoint) world
                    .createJoint(def);
        }, new PrismaticJoint());
    }

    /**
     * Erstellt eine Stabverbindung ({@link DistanceJoint}) zwischen diesem und
     * einem weiteren {@link Actor}-Objekt.
     *
     * @param other       Das zweite {@link Actor}-Objekt, das ab sofort mit dem
     *                    zugehörigen {@link Actor}-Objekt über einen
     *                    <code>DistanceJoint</code> verbunden sein soll.
     * @param anchorThis  Der Ankerpunkt für das zugehörige
     *                    {@link Actor}-Objekt. Der erste Befestigungspunkt des
     *                    Joints. Die Angabe ist relativ zur Position des
     *                    zugehörigen Objekts (linke untere Ecke).
     * @param anchorOther Der Ankerpunkt für das zweite {@link Actor}-Objekt,
     *                    also <code>other</code>. Der zweite Befestigungspunkt
     *                    des Joints. Die Angabe ist relativ zur Position des
     *                    zugehörigen Objekts (linke untere Ecke).
     * @return Ein {@link DistanceJoint}-Objekt, mit dem die Verbindung weiter
     *         gesteuert werden kann.
     * @see org.jbox2d.dynamics.joints.DistanceJoint
     */
    @API
    public final DistanceJoint createDistanceJoint(Actor other,
            Vector anchorThis, Vector anchorOther)
    {
        return WorldHandler.createJoint(this, other, (world, a, b) -> {
            DistanceJointDef def = new DistanceJointDef();
            def.bodyA = a;
            def.bodyB = b;
            def.localAnchorA.set(anchorThis.toVec2());
            def.localAnchorB.set(anchorOther.toVec2());
            Vector distanceBetweenBothActors = (this.getPosition()
                    .add(anchorThis))
                    .getDistance(other.getPosition().add(anchorOther));
            def.length = (float) distanceBetweenBothActors.getLength();
            return (org.jbox2d.dynamics.joints.DistanceJoint) world
                    .createJoint(def);
        }, new DistanceJoint());
    }

    /**
     * Erstellt eine Schweißnaht - besser einen Schweißpunkt - zwischen diesem
     * und einem weiteren {@link Actor}-Objekt.
     *
     * @param other       Das zweite {@link Actor}-Objekt, das ab sofort mit dem
     *                    zugehörigen {@link Actor}-Objekt über eine Schweißnaht
     *                    verbunden sein soll.
     * @param anchorThis  Der Ankerpunkt für das zugehörige
     *                    {@link Actor}-Objekt. Der erste Befestigungspunkt der
     *                    Schweißnaht. Die Angabe ist relativ zur Position des
     *                    zugehörigen Objekts (linke untere Ecke).
     * @param anchorOther Der Ankerpunkt für das zweite {@link Actor}-Objekt,
     *                    also <code>other</code>. Der zweite Befestigungspunkt
     *                    der Schweißnaht. Die Angabe ist relativ zur Position
     *                    des zugehörigen Objekts (linke untere Ecke).
     * @return Ein {@link WeldJoint}-Objekt, mit dem die Verbindung weiter
     *         gesteuert werden kann.
     * @see org.jbox2d.dynamics.joints.WeldJoint
     */
    @API
    public final WeldJoint createWeldJoint(Actor other, Vector anchorThis,
            Vector anchorOther)
    {
        return WorldHandler.createJoint(this, other, (world, a, b) -> {
            WeldJointDef def = new WeldJointDef();
            def.bodyA = a;
            def.bodyB = b;
            def.localAnchorA.set(anchorThis.toVec2());
            def.localAnchorB.set(anchorOther.toVec2());
            return (org.jbox2d.dynamics.joints.WeldJoint) world
                    .createJoint(def);
        }, new WeldJoint());
    }

    /**
     * Setzt die Position des {@link Actor}-Objektes gänzlich neu auf der
     * Zeichenebene. Das Setzen ist technisch gesehen eine Verschiebung von der
     * aktuellen Position an die neue.
     *
     * @param x Die neue <code>x</code>-Koordinate.
     * @param y Die neue <code>y</code>-Koordinate.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setPosition(Vector)
     * @see #setCenter(double, double)
     * @see #setX(double)
     * @see #setY(double)
     */
    @API
    public final Actor setPosition(double x, double y)
    {
        setPosition(new Vector(x, y));
        return this;
    }

    /**
     * Setzt die Position des Objektes gänzlich neu auf der Zeichenebene. Das
     * Setzen ist technisch gesehen eine Verschiebung von der aktuellen Position
     * an die neue.
     *
     * @param position Der neue Zielpunkt.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setPosition(double, double)
     * @see #setCenter(double, double)
     * @see #setX(double)
     * @see #setY(double)
     */
    @API
    public final Actor setPosition(Vector position)
    {
        moveBy(position.subtract(getPosition()));
        return this;
    }

    /**
     * Verschiebt das Objekt ohne Bedingungen auf der Zeichenebene.
     *
     * @param vector Der Vektor, der die Verschiebung des Objekts angibt.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see Vector
     * @see #moveBy(double, double)
     */
    @API
    public final Actor moveBy(Vector vector)
    {
        physicsHandler.moveBy(vector);
        return this;
    }

    /**
     * Verschiebt das Objekt.
     *
     * <p>
     * Hierbei wird nichts anderes gemacht, als <code>moveBy(new
     * Vector(dx, dy))</code> auszuführen. Insofern ist diese Methode dafür gut,
     * sich nicht mit der Klasse {@link Vector} auseinandersetzen zu müssen.
     *
     * @param dX Die Verschiebung in Richtung X.
     * @param dY Die Verschiebung in Richtung Y.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #moveBy(Vector)
     */
    @API
    public final Actor moveBy(double dX, double dY)
    {
        moveBy(new Vector(dX, dY));
        return this;
    }

    /**
     * Verschiebt die {@link Actor}-Figur so, dass ihr Mittelpunkt die
     * eingegebenen Koordinaten hat.
     * <p>
     * Diese Methode arbeitet nach dem Mittelpunkt des das Objekt abdeckenden
     * Bounding-Rechtecks durch den Aufruf der Methode
     * <code>{@link #setCenter(double, double)}</code>.
     *
     * @param x Die <code>x</code>-Koordinate des neuen Mittelpunktes des
     *          Objektes
     * @param y Die <code>y</code>-Koordinate des neuen Mittelpunktes des
     *          Objektes
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setCenter(Vector)
     * @see #moveBy(double, double)
     * @see #moveBy(Vector)
     * @see #setPosition(double, double)
     * @see #setPosition(Vector)
     * @see #getCenter()
     */
    @API
    public final Actor setCenter(double x, double y)
    {
        this.setCenter(new Vector(x, y));
        return this;
    }

    /**
     * Verschiebt die Actor-Figur so, dass ihr Mittelpunkt die eingegebenen
     * Koordinaten hat.
     *
     * <p>
     * Diese Methode arbeitet mit dem Mittelpunkt des das Objekt abdeckenden
     * Bounding-Rechtecks durch den Aufruf der Methode <code>getCenter()</code>.
     *
     * @param center Der neue Mittelpunkt des Objekts
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setCenter(double, double)
     * @see #moveBy(double, double)
     * @see #moveBy(Vector)
     * @see #setPosition(double, double)
     * @see #setPosition(Vector)
     * @see #getCenter()
     */
    @API
    public final Actor setCenter(Vector center)
    {
        moveBy(getCenter().negate().add(center));
        return this;
    }

    /**
     * Gibt die x-Koordinate der linken unteren Ecke zurück. Sollte das
     * Raumobjekt nicht rechteckig sein, so wird die Position der linken unteren
     * Ecke des umschließenden Rechtecks genommen.
     *
     * @return <code>x</code>-Koordinate
     *
     * @see #getY()
     * @see #getPosition()
     */
    @API
    public final double getX()
    {
        return getPosition().getX();
    }

    /**
     * Setzt die x-Koordinate der Position des Objektes gänzlich neu auf der
     * Zeichenebene. Das Setzen ist technisch gesehen eine Verschiebung von der
     * aktuellen Position an die neue.
     *
     * <p>
     * <b>Achtung!</b><br>
     * Bei <b>allen</b> Objekten ist die eingegebene Position die linke, untere
     * Ecke des Rechtecks, das die Figur optimal umfasst. Das heißt, dass dies
     * bei Kreisen z.B. <b>nicht</b> der Mittelpunkt ist! Hierfür gibt es die
     * Sondermethode {@link #setCenter(double, double)}.
     *
     * @param x neue x-Koordinate
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setPosition(double, double)
     * @see #setCenter(double, double)
     * @see #setY(double)
     */
    @API
    public final Actor setX(double x)
    {
        moveBy(x - getX(), 0);
        return this;
    }

    /**
     * Gibt die y-Koordinate der linken unteren Ecke zurück. Sollte das
     * Raumobjekt nicht rechteckig sein, so wird die Position der linken unteren
     * Ecke des umschließenden Rechtecks genommen.
     *
     * @return Die y-Koordinate
     *
     * @see #getX()
     * @see #getPosition()
     */
    @API
    public final double getY()
    {
        return getPosition().getY();
    }

    /**
     * Setzt die y-Koordinate der Position des Objektes gänzlich neu auf der
     * Zeichenebene. Das Setzen ist technisch gesehen eine Verschiebung von der
     * aktuellen Position an die neue.
     *
     * <p>
     * <b>Achtung!</b><br>
     * Bei <b>allen</b> Objekten ist die eingegebene Position die linke, untere
     * Ecke des Rechtecks, das die Figur optimal umfasst. Das heißt, dass dies
     * bei Kreisen z.B. <b>nicht</b> der Mittelpunkt ist! Hierfür gibt es die
     * Sondermethode {@link #setCenter(double, double)}.
     *
     * @param y neue <code>y</code>-Koordinate
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setPosition(double, double)
     * @see #setCenter(double, double)
     * @see #setX(double)
     */
    @API
    public final Actor setY(double y)
    {
        moveBy(0, y - getY());
        return this;
    }

    /**
     * Gibt den Mittelpunkt des Objektes in der
     * {@link de.pirckheimer_gymnasium.engine_pi.Scene Scene} aus.
     *
     * @return Die Koordinaten des Mittelpunktes des Objektes.
     * @see #getPosition()
     */
    @API
    public final Vector getCenter()
    {
        return physicsHandler.getCenter();
    }

    /**
     * Gibt die Position des Zentrums des {@link Actor}-Objekts relativ zu
     * dessen Position (Anker links unten) an.
     *
     * @return Die Position des Zentrums des {@link Actor}-Objekts relativ zu
     *         dessen Position (Anker links unkten).
     */
    @API
    public final Vector getCenterRelative()
    {
        return getCenter().subtract(getPosition());
    }

    /**
     * Gibt die Position dieses {@link Actor}-Objekts aus.
     *
     * @return Die aktuelle Position dieses {@link Actor}-Objekts.
     */
    @API
    public final Vector getPosition()
    {
        return physicsHandler.getPosition();
    }

    /**
     * Gibt die Position formatiert als Zeichenkette aus.
     *
     * @return Die Position als Zeichenkette im Format {@code 0.00|0.00}.
     */
    public final String getPositionformatted()
    {
        Vector pos = getPosition();
        return TextUtil.roundNumber(pos.getX()) + "|"
                + TextUtil.roundNumber(pos.getY());
    }

    /**
     * Rotiert das Objekt um den angegebenen Winkel.
     *
     * <p>
     * Positive Werte drehen die Figur gegen den Uhrzeigersinn, negative im
     * Uhrzeigersinn.
     * </p>
     *
     * @param degree Der Winkel (in <b>Grad</b>), um den das Objekt rotiert
     *               werden soll.
     *               <ul>
     *               <li>Werte &gt; 0 : Drehung gegen Uhrzeigersinn</li>
     *               <li>Werte &lt; 0 : Drehung im Uhrzeigersinn</li>
     *               </ul>
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     *
     * @see #setRotation(double)
     */
    @API
    public final Actor rotateBy(double degree)
    {
        physicsHandler.rotateBy(degree);
        return this;
    }

    /**
     * Gibt den Winkel aus, um den das Objekt derzeit rotiert ist.
     *
     * @return Der Winkel (in <b>Grad</b>), um den das Objekt derzeit rotiert
     *         ist. Jedes Objekt ist bei Initialisierung nicht rotiert
     *         ({@link #getRotation()} gibt direkt ab Initialisierung
     *         <code>0</code> zurück).
     */
    @API
    public final double getRotation()
    {
        return physicsHandler.getRotation();
    }

    /**
     * Setzt den Rotationswert des Objekts. Nach Erzeugung der Figur ist der
     * Rotationswert 0. {@code setRotation(90)} dreht die Figur beispiels um 90
     * Grad <b>gegen</b> den Uhrzeigersinn, {@code setRotation(-90)} um 90 Grad
     * <b>im</b> Uhrzeigersinn.
     *
     * @param degree Der Winkel (in <b>Grad</b>), um den das Objekt <b>von
     *               seiner Ausgangsposition bei Initialisierung</b> rotiert
     *               werden soll.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    @API
    public final Actor setRotation(double degree)
    {
        physicsHandler.setRotation(degree);
        return this;
    }

    /**
     * Gibt wahr zurück, falls das Objekt einer Ebene zugeordnet ist.
     *
     * @return wahr, falls das Objekt einer Ebene zugeordnet ist, sonst falsch.
     */
    @API
    public final boolean isMounted()
    {
        return getLayer() != null;
    }

    /**
     * Setzt den {@link BodyType} auf {@link BodyType#PARTICLE PARTICLE} und
     * animiert das Partikel, sodass es ausblasst und nach der Lebenszeit
     * komplett verschwindet.
     *
     * @param lifetime Die Lebenszeit in Sekunden.
     *
     * @return Das Objekt, das die Animation kontrolliert.
     */
    @API
    public final ValueAnimator<Double> animateParticle(double lifetime)
    {
        setBodyType(BodyType.PARTICLE);
        setOpacity(1);
        ValueAnimator<Double> animator = animateOpacity(lifetime, 0);
        animator.addCompletionListener(value -> remove());
        return animator;
    }

    /**
     * Animiert die Durchsichtigkeit dieses {@link Actor}-Objekts über einen
     * festen Zeitraum: Beginnend von der aktuellen Durchsichtigkeit, ändert sie
     * sich "smooth" (mit {@code EaseInOutDouble}-Interpolation) vom aktuellen
     * Durchsichtigkeits-Wert (die Ausgabe von {@link #getOpacity()}) bis hin
     * zum angegebenen Durchsichtigkeitswert.
     *
     * @param time           Die Animationszeit in Sekunden
     * @param toOpacityValue Der Durchsichtigkeit-Wert, zu dem innerhalb von
     *                       {@code time} zu interpolieren ist.
     *
     * @return Ein {@code ValueAnimator}, der diese Animation ausführt. Der
     *         Animator ist bereits aktiv, es muss nichts an dem Objekt getan
     *         werden, um die Animation auszuführen.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.animation.interpolation.EaseInOutDouble
     */
    @API
    public final ValueAnimator<Double> animateOpacity(double time,
            double toOpacityValue)
    {
        ValueAnimator<Double> animator = new ValueAnimator<>(time,
                this::setOpacity,
                new EaseInOutDouble(getOpacity(), toOpacityValue), this);
        addFrameUpdateListener(animator);
        return animator;
    }

    @Internal
    static void assertPositiveWidthAndHeight(double width, double height)
    {
        if (width <= 0 || height <= 0)
        {
            throw new IllegalArgumentException(
                    "Breite und Höhe müssen größer 0 sein! " + width + " / "
                            + height);
        }
    }

    /**
     * Weckt die Figur auf. Eine schlafende Figur hat sehr geringe CPU-Kosten,
     * deshalb wird sie von der Physics-Engine in den Schlafzustand gesetzt,
     * wenn sie zur Ruhe kommt.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    public Actor awake()
    {
        physicsHandler.setAwake(true);
        return this;
    }

    /**
     * Versetzt die Figur in den Schlafzustand. Eine schlafende Figur hat sehr
     * geringe CPU-Kosten.
     *
     * <p>
     * Das Versetzen in den Schlafzustand setzt die Geschwindigkeit und den
     * Impuls der Figur auf null.
     * </p>
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *         Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *         aneinander gekettete Setter festgelegt werden können, z. B.
     *         {@code actor.setColor(..).setPostion(..)}.
     */
    public Actor sleep()
    {
        physicsHandler.setAwake(false);
        return this;
    }
}
