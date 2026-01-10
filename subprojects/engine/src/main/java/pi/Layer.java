/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/Layer.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
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

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import de.pirckheimer_gymnasium.jbox2d.dynamics.Body;
import de.pirckheimer_gymnasium.jbox2d.dynamics.World;
import pi.actor.Actor;
import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.event.EventListeners;
import pi.event.FrameUpdateListener;
import pi.event.FrameUpdateListenerRegistration;
import pi.event.KeyStrokeListener;
import pi.event.KeyStrokeListenerRegistration;
import pi.event.MouseClickListener;
import pi.event.MouseClickListenerRegistration;
import pi.event.MouseScrollListener;
import pi.event.MouseScrollListenerRegistration;
import pi.graphics.geom.Bounds;
import pi.graphics.geom.Vector;
import pi.physics.BodyHandler;
import pi.physics.NullHandler;
import pi.physics.PhysicsData;
import pi.physics.PhysicsHandler;
import pi.physics.WorldHandler;

/**
 * Eine <b>Ebene</b> bieten die Möglichkeit, {@link Actor}-Objekte vor und
 * hinter der Zeichenebene mit zusätzlichen Eigenschaften (wie zum Beispiel
 * Parallaxe) zu rendern.
 *
 * @author Michael Andonie
 */
public class Layer implements KeyStrokeListenerRegistration,
        MouseClickListenerRegistration, MouseScrollListenerRegistration,
        FrameUpdateListenerRegistration
{
    private static final Comparator<? super Actor> ACTOR_COMPARATOR = Comparator
            .comparingInt(Actor::layerPosition);

    private <T> Supplier<T> createParentSupplier(Function<Scene, T> supplier)
    {
        return () -> {
            Scene scene = scene();
            if (scene == null)
            {
                return null;
            }
            return supplier.apply(scene);
        };
    }

    /**
     * Die <b>bereits in der Physics-Engine</b> registrierten Figuren.
     */
    private final List<Actor> actors;

    /**
     * Die bereits zur Ebene hinzugefügten und aber unter Umständen <b>noch
     * nicht in der Physics-Engine</b> registrierten Figuren.
     */
    private final List<Actor> addedActors;

    private double parallaxX = 1;

    private double parallaxY = 1;

    private double parallaxRotation = 1;

    private double parallaxZoom = 1;

    private double timeDistort = 1;

    /**
     * Bestimmt die Reihenfolge der Ebenen, kleinere Werte werden zuerst
     * gerendert, sind also weiter „hinten“.
     */
    private int layerPosition = -2;

    private boolean visible = true;

    /**
     * Die <b>Szene</b>, zu der diese Ebene gehört.
     */
    private Scene scene;

    private final WorldHandler worldHandler;

    private final EventListeners<KeyStrokeListener> keyStrokeListeners = new EventListeners<>(
            createParentSupplier(Scene::keyStrokeListeners));

    private final EventListeners<MouseClickListener> mouseClickListeners = new EventListeners<>(
            createParentSupplier(Scene::mouseClickListeners));

    private final EventListeners<MouseScrollListener> mouseScrollListeners = new EventListeners<>(
            createParentSupplier(Scene::mouseScrollListeners));

    private final EventListeners<FrameUpdateListener> frameUpdateListeners = new EventListeners<>();

    /**
     * Erstellt eine neue Ebene.
     */
    @API
    public Layer()
    {
        worldHandler = new WorldHandler(this);
        actors = new ArrayList<>();
        addedActors = new ArrayList<>();
        EventListeners.registerListeners(this);
    }

    /**
     * Gibt die <b>Szene</b>, zu der diese Ebene gehört, zurück.
     *
     * @return Die <b>Szene</b>, zu der diese Ebene gehört.
     */
    @Getter
    public Scene scene()
    {
        return scene;
    }

    /**
     * Setzt die <b>Szene</b>, zu der diese Ebene gehört.
     *
     * @param scene Die <b>Szene</b>, zu der diese Ebene gehört.
     *
     * @hidden
     */
    @Internal
    @Setter
    void scene(Scene scene)
    {
        if (scene != null && this.scene != null)
        {
            throw new IllegalStateException(
                    "Die Ebene wurde bereits in einer Szene angemeldet.");
        }
        if (scene != null)
        {
            keyStrokeListeners.invoke(scene::addKeyStrokeListener);
            mouseClickListeners.invoke(scene::addMouseClickListener);
            mouseScrollListeners.invoke(scene::addMouseScrollListener);
            frameUpdateListeners.invoke(scene::addFrameUpdateListener);
        }
        else
        {
            keyStrokeListeners.invoke(this.scene::removeKeyStrokeListener);
            mouseClickListeners.invoke(this.scene::removeMouseClickListener);
            mouseScrollListeners.invoke(this.scene::removeMouseScrollListener);
            frameUpdateListeners.invoke(this.scene::removeFrameUpdateListener);
        }
        this.scene = scene;
    }

    /**
     * Setzt die <b>Position</b> dieser <b>Ebene</b> relativ zu anderen Ebenen.
     *
     * @param position Die neue Position dieser Ebene. Je höher dieser Wert,
     *     desto weiter vorne ist sie.
     */
    @API
    @Setter
    public void layerPosition(int position)
    {
        this.layerPosition = position;
        if (scene != null)
        {
            scene.sortLayers();
        }
    }

    /**
     * Gibt die Position des Layers aus.
     *
     * @return Der Wert, der die Position dieses Layers repräsentiert.
     *
     * @see #layerPosition(int)
     */
    @API
    @Getter
    public int layerPosition()
    {
        return layerPosition;
    }

    /**
     * Setzt die Parallaxen-Bewegung für dieser Ebene:
     * <ul>
     * <li><code>1</code> ist keine Parallaxe (Bewegung exakt mit der
     * Kamera)</li>
     * <li>Werte zwischen <code>0</code> und <code>1</code> schaffen einen
     * entfernten Effekt: Die Bewegung ist weniger als die der Kamera</li>
     * <li><code>0</code> bedeutet, die Bewegung der Kamera hat gar keinen
     * Einfluss auf die Ebene.</li>
     * <li>Negative Werte sorgen für Bewegung entgegen der Kamera</li>
     * <li>Werte <code>&gt; 1</code> verstärken die Bewegung der Kamera (z.B.
     * für Vordergrund).</li>
     * </ul>
     *
     * @param parallaxX Der x-Wert der Parallaxen-Bewegung.
     * @param parallaxY Der y-Wert der Parallaxen-Bewegung.
     */
    @API
    @Setter
    public void parallaxPosition(double parallaxX, double parallaxY)
    {
        this.parallaxX = parallaxX;
        this.parallaxY = parallaxY;
    }

    /**
     * Setzt den Parallaxen-Zoom für diese Ebene:
     * <ul>
     * <li><code>1</code>: Normaler Zoom mit der Kamera</li>
     * <li><code>0</code>: Kamerazoom hat keinen Einfluss auf diese Ebene.</li>
     * <li><code>0 &lt; parallaxZoom &lt; 1</code>: Der Zoomeffekt tritt
     * schwächer auf.</li>
     * <li><code>parallaxZoom &gt; 1</code>: Der Zoomeffekt tritt stärker
     * auf.</li>
     * <li><code>parallaxZoom &lt; 0</code>: Der Zoomeffekt tritt betragsmäßig
     * ähnlich und umgekehrt auf.</li>
     * </ul>
     */
    @API
    @Setter
    public void parallaxZoom(double parallaxZoom)
    {
        this.parallaxZoom = parallaxZoom;
    }

    /**
     * Setzt die Parallaxe der Rotation. Diese Ebene wird um
     * <code>[kamerarotation] * parallaxRotation</code> rotiert.
     *
     * @param parallaxRotation Die Rotationsparallaxe.
     */
    @API
    @Setter
    public void parallaxRotation(double parallaxRotation)
    {
        this.parallaxRotation = parallaxRotation;
    }

    /**
     * Setzt einen Zeitverzerrungsfaktor. Die Zeit in der Physiksimulation
     * vergeht standardmäßig in Echtzeit, kann allerdings verzerrt werden.
     *
     * @param timeDistort <i>Zeit in der Simulation = Echtzeit *
     *     Verzerrungsfaktor</i> <br />
     *     <ul>
     *     <li>Werte <code>&gt;1</code> lassen die Zeit <b>schneller</b>
     *     vergehen</li>
     *     <li>Werte <code>&lt;1</code> lassen die Zeit <b>langsamer</b>
     *     vergehen</li>
     *     <li><code>1</code> lässt die Zeit in Echtzeit vergehen</li>
     *     <li>Werte <code>&lt;=0</code> sind nicht erlaubt</li>
     *     </ul>
     */
    @API
    @Setter
    public void timeDistort(double timeDistort)
    {
        if (timeDistort < 0)
        {
            throw new IllegalArgumentException(
                    "Zeitverzerrungsfaktor muss größer oder gleich 0 sein, war "
                            + timeDistort);
        }
        this.timeDistort = timeDistort;
    }

    /**
     * Setzt die Schwerkraft als Vektor, die auf <b>alle Objekte innerhalb
     * dieser Ebene</b> wirkt.
     *
     * @param gravity Die neue Schwerkraft als {@link Vector}. Die Einheit ist
     *     <b>[N]</b> bzw. <b>[m/s^2]</b>.
     *
     * @see #gravity(double, double)
     * @see Scene#gravity(Vector)
     * @see Scene#gravity(double, double)
     *
     * @jbox2d <a href=
     *     "https://github.com/jbox2d/jbox2d/blob/94bb3e4a706a6d1a5d8728a722bf0af9924dde84/jbox2d-library/src/main/java/org/jbox2d/dynamics/World.java#L997-L1004">dynamics/World.java#L997-L1004</a>
     *
     * @box2d <a href=
     *     "https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_world.h#L312-L315">b2_world.h#L312-L315</a>
     */
    @API
    @Setter
    public void gravity(Vector gravity)
    {
        world().setGravity(gravity.toVec2());
    }

    /**
     * Setzt die Schwerkraft durch zwei Eingabeparameter für die x- und
     * y-Richtung, die auf <b>alle Objekte innerhalb dieser Ebene</b> wirkt.
     *
     * @param gravityX Die neue Schwerkraft, die in X-Richtung wirken soll. Die
     *     Einheit ist <b>[N]</b> bzw. <b>[m/s^2]</b>.
     * @param gravityY Die neue Schwerkraft, die in Y-Richtung wirken soll. Die
     *     Einheit ist <b>[N]</b> bzw. <b>[m/s^2]</b>.
     *
     * @see #gravity(Vector)
     * @see Scene#gravity(Vector)
     * @see Scene#gravity(double, double)
     *
     * @jbox2d <a href=
     *     "https://github.com/jbox2d/jbox2d/blob/94bb3e4a706a6d1a5d8728a722bf0af9924dde84/jbox2d-library/src/main/java/org/jbox2d/dynamics/World.java#L997-L1004">dynamics/World.java#L997-L1004</a>
     *
     * @box2d <a href=
     *     "https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_world.h#L312-L315">b2_world.h#L312-L315</a>
     */
    @API
    @Setter
    public void gravity(double gravityX, double gravityY)
    {
        gravity(new Vector(gravityX, gravityY));
    }

    /**
     * Setzt die Schwerkraft, die auf der Erde wirkt: 9.81 <b>[N]</b> bzw.
     * <b>[m/s^2]</b> nach unten (x: 0, y: -9.81).
     *
     * @see Scene#gravityOfEarth
     */
    @API
    @Setter
    public void gravityOfEarth()
    {
        gravity(0, -9.81);
    }

    /**
     * Gibt die Schwerkraft, die momentan auf diese Ebene wirkt, als Vektor in
     * <b>[N]</b> bzw. <b>[m/s^2]</b> zurück.
     *
     * @return Die Schwerkraft, die momentan auf diese Ebene wirkt, als Vektor
     *     in <b>[N]</b> bzw. <b>[m/s^2]</b>.
     */
    @Getter
    public Vector gravity()
    {
        return Vector.of(world().getGravity());
    }

    /**
     * Setzt, ob diese Ebene <b>sichtbar</b> sein soll.
     *
     * @param visible <code>true</code>: Die Ebene ist sichtbar, wenn es an
     *     einer Szene angemeldet ist. <code>false</code>: Die Ebene ist auch
     *     dann nicht sichtbar, wenn es an einer Szene angemeldet ist.
     *
     * @see #isVisible()
     */
    @API
    @Setter
    public void visible(boolean visible)
    {
        this.visible = visible;
    }

    /**
     * Gibt an, ob dieses Layer gerade sichtbar ist.
     *
     * @return <code>true</code>: Die Ebene ist sichtbar. <code>false</code>:
     *     Die Ebene ist nicht sichtbar.
     *
     * @see #visible(boolean)
     */
    @API
    public boolean isVisible()
    {
        return visible;
    }

    /**
     * Fügt einen oder mehrere {@link Actor}-Objekte der Ebene hinzu.
     *
     * @param actors Ein oder mehrere {@link Actor}-Objekte.
     */
    @API
    public void add(Actor... actors)
    {
        addedActors.addAll(Arrays.asList(actors));
        addedActors.sort(ACTOR_COMPARATOR);
        defer(() -> {
            for (Actor actor : actors)
            {
                if (actor.isMounted())
                {
                    if (actor.layer() != this)
                    {
                        throw new IllegalArgumentException(
                                "Ein Actor kann nur an einem Layer gleichzeitig angemeldet sein");
                    }
                    else
                    {
                        return;
                    }
                }
                PhysicsHandler oldHandler = actor.physicsHandler();
                PhysicsHandler newHandler = new BodyHandler(actor,
                        oldHandler.physicsData(), worldHandler);
                actor.physicsHandler(newHandler);
                oldHandler.applyMountCallbacks(newHandler);
                this.actors.add(actor);
            }
            this.actors.sort(ACTOR_COMPARATOR);
        });
    }

    /**
     * Entferne einen oder mehrere {@link Actor Figuren} aus der Ebene.
     *
     * @param actors Ein oder mehrere {@link Actor}-Objekte.
     */
    @API
    final public void remove(Actor... actors)
    {
        for (Actor actor : actors)
        {
            addedActors.remove(actor);
        }
        defer(() -> {
            for (Actor actor : actors)
            {
                this.actors.remove(actor);
                PhysicsData physicsData = actor.physicsHandler().physicsData();
                PhysicsHandler physicsHandler = actor.physicsHandler();
                if (physicsHandler.worldHandler() == null)
                {
                    return;
                }
                Body body = physicsHandler.body();
                worldHandler.removeAllInternalReferences(body);
                worldHandler.world().destroyBody(body);
                actor.physicsHandler(new NullHandler(physicsData));
            }
        });
    }

    /**
     * <b>Entfernt alle</b> {@link Actor Figuren} aus der Ebene.
     *
     * <p>
     * Diese Methode durchläuft die Liste der Figuren und entfernt jeden
     * einzelnen von der Ebene.
     * </p>
     *
     * @since 0.42.0
     */
    @API
    public void clear()
    {
        for (Actor actor : actors)
        {
            remove(actor);
        }
    }

    /**
     * Gibt alle <b>Figuren</b> dieser Ebene, die <b>bereits in der
     * Physics-Engine</b> registriert sind, als Liste zurück.
     *
     * <p>
     * Die Figuren werden erst mit Verzögerung durch die Methode
     * {@link #defer(Runnable)} zur Ebene hinzugefügt. Sie sind also nicht
     * sofort nach dem Hinzufügen zur Ebene über diese Methode abrufbar.
     * </p>
     *
     * @return Alle Figuren dieser Ebene, die <b>bereits in der
     *     Physics-Engine</b> registriert sind.
     *
     * @since 0.37.0
     *
     * @see #addedActors()
     */
    @Getter
    public List<Actor> actors()
    {
        return actors;
    }

    /**
     * Gibt alle <b>Figuren</b>, die bereits zur Ebene hinzugefügt und aber
     * unter Umständen <b>noch nicht in der Physics-Engine</b> registriert
     * wurden, als Liste zurück.
     *
     * @return Alle <b>Figuren</b>, die bereits zur Ebene hinzugefügt und aber
     *     unter Umständen <b>noch nicht in der Physics-Engine</b> registriert
     *
     * @since 0.37.0
     *
     * @see #actors()
     */
    @Getter
    public List<Actor> addedActors()
    {
        return addedActors;
    }

    /**
     * Übersetzt einen Punkt auf diesem Layer zu der analogen, aktuellen
     * Pixelkoordinate im zum zeichnenden Frame.
     *
     * @param worldPoint Ein Punkt auf der Ebene.
     *
     * @return Ein Vektor <b>in Pixelkoordinaten</b> (nicht Meter, die y-Achse
     *     ist umgekehrt), der mit der aktuellen Kameraeinstellung dem
     *     angegebenen <code>worldPoint</code> entspricht
     *
     * @hidden
     */
    @Internal
    public Vector translateWorldPointToFramePxCoordinates(Vector worldPoint)
    {
        double pixelPerMeter = calculatePixelPerMeter();
        Vector frameSize = Controller.windowSize();
        Vector cameraPositionInPx = new Vector(frameSize.x() / 2,
                frameSize.y() / 2);
        Vector fromCamToPointInWorld = scene.camera().focus()
                .multiplyX(parallaxX).multiplyY(parallaxY).distance(worldPoint);
        return cameraPositionInPx.add(fromCamToPointInWorld.multiplyY(-1)
                .multiply(pixelPerMeter * parallaxZoom));
    }

    /**
     * Gibt die derzeit auf dem Bildschirm sichtbare Fläche der Ebene an.
     *
     * @return Die sichtbare Fläche <b>mit Angaben in Meter</b>.
     *
     * @see Controller#windowSize()
     */
    @API
    @Getter
    public Bounds visibleArea(Vector gameSizeInPixels)
    {
        Vector center = scene.camera().focus();
        double pixelPerMeter = calculatePixelPerMeter();
        return new Bounds(0, 0, gameSizeInPixels.x() / pixelPerMeter,
                gameSizeInPixels.y() / pixelPerMeter).withCenterPoint(center);
    }

    /**
     * Setzt den Kamerazoom exakt, sodass die sichtbare Breite des sichtbaren
     * Fensters einer bestimmten Länge entspricht.
     *
     * @param width Die Breite in Meter, auf die die Kamera im Fenster exakt zu
     *     setzen ist.
     *
     * @see #visibleHeight(double, Vector)
     * @see Controller#windowSize()
     */
    @API
    @Getter
    public void visibleWidth(double width, Vector gameSizeInPixels)
    {
        double desiredPixelPerMeter = gameSizeInPixels.x() / width;
        double desiredZoom = 1 + ((desiredPixelPerMeter - 1) / parallaxZoom);
        scene.camera().meter(desiredZoom);
    }

    /**
     * Setzt den Kamerazoom exakt, sodass die sichtbare Höhe des sichtbaren
     * Fensters einer bestimmten Länge entspricht.
     *
     * @param height Die Höhe in Meter, auf die die Kamera im Fenster exakt zu
     *     setzen ist.
     *
     * @see #visibleWidth(double, Vector)
     * @see Controller#windowSize()
     */
    @API
    @Setter
    public void visibleHeight(double height, Vector gameSizeInPixels)
    {
        double desiredPixelPerMeter = gameSizeInPixels.y() / height;
        double desiredZoom = 1 + ((desiredPixelPerMeter - 1) / parallaxZoom);
        scene.camera().meter(desiredZoom);
    }

    /**
     * @return Die Anzahl an Pixel, die ein Meter misst.
     */
    @API
    public double calculatePixelPerMeter()
    {
        return 1 + (scene.camera().meter() - 1) * parallaxZoom;
    }

    /**
     * @hidden
     */
    @Internal
    public void render(Graphics2D g, Camera camera, int width, int height)
    {
        if (!visible)
        {
            return;
        }
        Vector position = camera.focus();
        double rotation = -camera.rotation();
        g.setClip(0, 0, width, height);
        g.translate(width / 2, height / 2);
        double pixelPerMeter = calculatePixelPerMeter();
        g.rotate(Math.toRadians(rotation) * parallaxRotation, 0, 0);
        g.translate((-position.x() * parallaxX) * pixelPerMeter,
                (position.y() * parallaxY) * pixelPerMeter);
        // TODO: Calculate optimal bounds
        int size = Math.max(width, height);
        boolean needsSort = false;
        int previousPosition = Integer.MIN_VALUE;
        for (Actor actor : actors)
        {
            actor.renderBasic(g, new Bounds(position.x() - size,
                    position.y() - size, size * 2, size * 2), pixelPerMeter);
            if (!needsSort)
            {
                int actorPosition = actor.layerPosition();
                if (actorPosition < previousPosition)
                {
                    needsSort = true;
                }
                previousPosition = actorPosition;
            }
        }
        if (needsSort)
        {
            this.actors.sort(ACTOR_COMPARATOR);
        }
    }

    /**
     * Gibt den {@link WorldHandler} dieser Ebene aus.
     *
     * @return Der {@link WorldHandler} dieser Ebene.
     *
     * @hidden
     */
    @Internal
    @Getter
    public WorldHandler worldHandler()
    {
        return worldHandler;
    }

    @Getter
    private World world()
    {
        return worldHandler.world();
    }

    /**
     * @hidden
     */
    @Internal
    public void step(double pastTime)
    {
        synchronized (worldHandler)
        {
            worldHandler.step(pastTime * timeDistort);
        }
    }

    @API
    @Getter
    public EventListeners<FrameUpdateListener> frameUpdateListeners()
    {
        return frameUpdateListeners;
    }

    @API
    @Getter
    public EventListeners<KeyStrokeListener> keyStrokeListeners()
    {
        return keyStrokeListeners;
    }

    @API
    @Getter
    public EventListeners<MouseClickListener> mouseClickListeners()
    {
        return mouseClickListeners;
    }

    @API
    @Getter
    public EventListeners<MouseScrollListener> mouseScrollListeners()
    {
        return mouseScrollListeners;
    }

    /**
     * @hidden
     */
    @Internal
    void invokeFrameUpdateListeners(double pastTime)
    {
        double scaledSeconds = pastTime * timeDistort;
        frameUpdateListeners.invoke(frameUpdateListener -> frameUpdateListener
                .onFrameUpdate(scaledSeconds));
    }
}
