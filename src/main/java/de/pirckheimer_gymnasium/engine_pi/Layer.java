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
package de.pirckheimer_gymnasium.engine_pi;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jbox2d.dynamics.Body;

import de.pirckheimer_gymnasium.engine_pi.actor.Actor;
import de.pirckheimer_gymnasium.engine_pi.actor.ActorCreator;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.event.EventListenerBundle;
import de.pirckheimer_gymnasium.engine_pi.event.EventListeners;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListenerRegistration;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListenerRegistration;
import de.pirckheimer_gymnasium.engine_pi.event.MouseClickListenerRegistration;
import de.pirckheimer_gymnasium.engine_pi.event.MouseWheelListener;
import de.pirckheimer_gymnasium.engine_pi.event.MouseWheelListenerRegistration;
import de.pirckheimer_gymnasium.engine_pi.physics.BodyHandler;
import de.pirckheimer_gymnasium.engine_pi.physics.NullHandler;
import de.pirckheimer_gymnasium.engine_pi.physics.PhysicsData;
import de.pirckheimer_gymnasium.engine_pi.physics.PhysicsHandler;
import de.pirckheimer_gymnasium.engine_pi.physics.WorldHandler;

/**
 * Eine Ebene bieten die Möglichkeit, {@link Actor}-Objekte vor und hinter der
 * Zeichenebene mit zusätzlichen Eigenschaften (wie Parallaxe) zu rendern.
 *
 * @author Michael Andonie
 */
public class Layer implements KeyStrokeListenerRegistration,
        MouseClickListenerRegistration, MouseWheelListenerRegistration,
        FrameUpdateListenerRegistration, ActorCreator
{
    private static final Comparator<? super Actor> ACTOR_COMPARATOR = Comparator
            .comparingInt(Actor::getLayerPosition);

    private <T> Supplier<T> createParentSupplier(Function<Scene, T> supplier)
    {
        return () -> {
            Scene scene = getParent();
            if (scene == null)
            {
                return null;
            }
            return supplier.apply(scene);
        };
    }

    private final List<Actor> actors;

    private double parallaxX = 1;

    private double parallaxY = 1;

    private double parallaxRotation = 1;

    private double parallaxZoom = 1;

    private double timeDistort = 1;

    /**
     * Bestimmt die Reihenfolge der Layer, kleinere Werte werden zuerst
     * gerendert, sind also weiter „hinten“.
     */
    private int layerPosition = -2;

    private boolean visible = true;

    private Scene parent;

    private final WorldHandler worldHandler;

    private final EventListenerBundle listeners = new EventListenerBundle();

    /**
     * Erstellt eine neue Ebene.
     */
    @API
    public Layer()
    {
        worldHandler = new WorldHandler(this);
        actors = new ArrayList<>();
        EventListeners.registerListeners(this);
    }

    public Scene getParent()
    {
        return parent;
    }

    public Scene getScene()
    {
        return parent;
    }

    @Internal
    void setParent(Scene parent)
    {
        if (parent != null && this.parent != null)
        {
            throw new IllegalStateException(
                    "Das Layer wurde bereits an einer Scene angemeldet.");
        }
        if (parent != null)
        {
            listeners.keyStroke.invoke(parent::addKeyStrokeListener);
            listeners.mouseClick.invoke(parent::addMouseClickListener);
            listeners.mouseWheel.invoke(parent::addMouseWheelListener);
            listeners.frameUpdate.invoke(parent::addFrameUpdateListener);
        }
        else
        {
            listeners.keyStroke.invoke(this.parent::removeKeyStrokeListener);
            listeners.mouseClick.invoke(this.parent::removeMouseClickListener);
            listeners.mouseWheel.invoke(this.parent::removeMouseWheelListener);
            listeners.frameUpdate
                    .invoke(this.parent::removeFrameUpdateListener);
        }
        this.parent = parent;
    }

    /**
     * Setzt die Position dieses Layers relativ zu anderen Layers.
     *
     * @param position Die neue Position dieser Ebene. Je höher dieser Wert,
     *                 desto weiter vorne ist sie.
     */
    @API
    public void setLayerPosition(int position)
    {
        this.layerPosition = position;
        if (parent != null)
        {
            parent.sortLayers();
        }
    }

    /**
     * Gibt die Position des Layers aus.
     *
     * @return Der Wert, der die Position dieses Layers repräsentiert.
     *
     * @see #setLayerPosition(int)
     */
    @API
    public int getLayerPosition()
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
     * Einfluss auf das Layer.</li>
     * <li>Negative Werte sorgen für Bewegung entgegen der Kamera</li>
     * <li>Werte <code>&gt; 1</code> verstärken die Bewegung der Kamera (z.B.
     * für Vordergrund).</li>
     * </ul>
     *
     * @param parallaxX Der x-Wert der Parallaxen-Bewegung.
     * @param parallaxY Der y-Wert der Parallaxen-Bewegung.
     */
    @API
    public void setParallaxPosition(double parallaxX, double parallaxY)
    {
        this.parallaxX = parallaxX;
        this.parallaxY = parallaxY;
    }

    /**
     * Setzt den Parallaxen-Zoom für diese Ebene:
     * <ul>
     * <li><code>1</code>: Normaler Zoom mit der Kamera</li>
     * <li><code>0</code>: Kamerazoom hat keinen Einfluss auf dieses Layer.</li>
     * <li><code>0 &lt; parallaxZoom &lt; 1</code>: Der Zoomeffekt tritt
     * schwächer auf.</li>
     * <li><code>parallaxZoom &gt; 1</code>: Der Zoomeffekt tritt stärker auf.
     * </li>
     * <li><code>parallaxZoom &lt; 0</code>: Der Zoomeffekt tritt betragsmäßig
     * ähnlich und umgekehrt auf.</li>
     * </ul>
     */
    @API
    public void setParallaxZoom(double parallaxZoom)
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
    public void setParallaxRotation(double parallaxRotation)
    {
        this.parallaxRotation = parallaxRotation;
    }

    /**
     * Setzt einen Zeitverzerrungsfaktor. Die Zeit in der Physiksimulation
     * vergeht standardmäßig in Echtzeit, kann allerdings verzerrt werden.
     *
     * @param timeDistort <i>Zeit in der Simulation = Echtzeit *
     *                    Verzerrungsfaktor</i> <br />
     *                    <ul>
     *                    <li>Werte <code>&gt;1</code> lassen die Zeit
     *                    <b>schneller</b> vergehen</li>
     *                    <li>Werte <code>&lt;1</code> lassen die Zeit
     *                    <b>langsamer</b> vergehen</li>
     *                    <li><code>1</code> lässt die Zeit in Echtzeit
     *                    vergehen</li>
     *                    <li>Werte <code>&lt;=0</code> sind nicht erlaubt</li>
     *                    </ul>
     */
    @API
    public void setTimeDistort(double timeDistort)
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
     *                <b>[N]</b> bzw. <b>[m/s^2]</b>.
     *
     * @see #setGravity(double, double)
     * @see Scene#setGravity(Vector)
     * @see Scene#setGravity(double, double)
     *
     * @jbox2d <a href=
     *         "https://github.com/jbox2d/jbox2d/blob/94bb3e4a706a6d1a5d8728a722bf0af9924dde84/jbox2d-library/src/main/java/org/jbox2d/dynamics/World.java#L997-L1004">dynamics/World.java#L997-L1004</a>
     * @box2d <a href=
     *        "https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_world.h#L312-L315">b2_world.h#L312-L315</a>
     */
    @API
    public void setGravity(Vector gravity)
    {
        this.worldHandler.getWorld().setGravity(gravity.toVec2());
    }

    /**
     * Setzt die Schwerkraft durch zwei Eingabeparameter für die x- und
     * y-Richtung, die auf <b>alle Objekte innerhalb dieser Ebene</b> wirkt.
     *
     * @param gravityX Die neue Schwerkraft, die in X-Richtung wirken soll. Die
     *                 Einheit ist <b>[N]</b> bzw. <b>[m/s^2]</b>.
     * @param gravityY Die neue Schwerkraft, die in Y-Richtung wirken soll. Die
     *                 Einheit ist <b>[N]</b> bzw. <b>[m/s^2]</b>.
     *
     * @see #setGravity(Vector)
     * @see Scene#setGravity(Vector)
     * @see Scene#setGravity(double, double)
     *
     * @jbox2d <a href=
     *         "https://github.com/jbox2d/jbox2d/blob/94bb3e4a706a6d1a5d8728a722bf0af9924dde84/jbox2d-library/src/main/java/org/jbox2d/dynamics/World.java#L997-L1004">dynamics/World.java#L997-L1004</a>
     * @box2d <a href=
     *        "https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_world.h#L312-L315">b2_world.h#L312-L315</a>
     */
    @API
    public void setGravity(double gravityX, double gravityY)
    {
        setGravity(new Vector(gravityX, gravityY));
    }

    /**
     * Setzt die Schwerkraft, die auf der Erde wirkt: 9.81 <b>[N]</b> bzw.
     * <b>[m/s^2]</b> nach unten (x: 0, y: -9.81).
     *
     * @see Scene#setGravityOfEarth
     */
    @API
    public void setGravityOfEarth()
    {
        setGravity(0, -9.81);
    }

    /**
     * Setzt, ob diese Ebene sichtbar sein soll.
     *
     * @param visible <code>true</code>: Die Ebene ist sichtbar, wenn es an
     *                einer Szene angemeldet ist. <code>false</code>: Die Ebene
     *                ist auch dann nicht sichtbar, wenn es an einer Szene
     *                angemeldet ist.
     *
     * @see #isVisible()
     */
    @API
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    /**
     * Gibt an, ob dieses Layer gerade sichtbar ist.
     *
     * @return <code>true</code>: Die Ebene ist sichtbar. <code>false</code>:
     *         Die Ebene ist nicht sichtbar.
     *
     * @see #setVisible(boolean)
     */
    @API
    public boolean isVisible()
    {
        return this.visible;
    }

    /**
     * Fügt einen oder mehrere {@link Actor}-Objekte der Ebene hinzu.
     *
     * @param actors Ein oder mehrere {@link Actor}-Objekte.
     */
    @API
    public void add(Actor... actors)
    {
        defer(() -> {
            for (Actor actor : actors)
            {
                if (actor.isMounted())
                {
                    if (actor.getLayer() != this)
                    {
                        throw new IllegalArgumentException(
                                "Ein Actor kann nur an einem Layer gleichzeitig angemeldet sein");
                    }
                    else
                    {
                        return;
                    }
                }
                PhysicsHandler oldHandler = actor.getPhysicsHandler();
                PhysicsHandler newHandler = new BodyHandler(actor,
                        oldHandler.getPhysicsData(), worldHandler);
                actor.setPhysicsHandler(newHandler);
                oldHandler.applyMountCallbacks(newHandler);
                this.actors.add(actor);
            }
            this.actors.sort(ACTOR_COMPARATOR);
        });
    }

    /**
     * Entferne einen oder mehrere {@link Actor}-Objekte aus der Ebene.
     *
     * @param actors Ein oder mehrere {@link Actor}-Objekte.
     */
    @API
    final public void remove(Actor... actors)
    {
        defer(() -> {
            for (Actor actor : actors)
            {
                this.actors.remove(actor);
                PhysicsData physicsData = actor.getPhysicsHandler()
                        .getPhysicsData();
                PhysicsHandler physicsHandler = actor.getPhysicsHandler();
                if (physicsHandler.getWorldHandler() == null)
                {
                    return;
                }
                Body body = physicsHandler.getBody();
                worldHandler.removeAllInternalReferences(body);
                worldHandler.getWorld().destroyBody(body);
                actor.setPhysicsHandler(new NullHandler(physicsData));
            }
        });
    }

    /**
     * Übersetzt einen Punkt auf diesem Layer zu der analogen, aktuellen
     * Pixelkoordinate im zeichnenden Frame.
     *
     * @param worldPoint Ein Punkt auf dem Layer
     *
     * @return Ein Vektor <b>in Pixelkoordinaten</b> (nicht Meter, die y-Achse
     *         ist umgekehrt), der mit der aktuellen Kameraeinstellung dem
     *         angegebenen <code>worldPoint</code> entspricht
     */
    @Internal
    public Vector translateWorldPointToFramePxCoordinates(Vector worldPoint)
    {
        double pixelPerMeter = calculatePixelPerMeter();
        Vector frameSize = Game.getWindowSize();
        Vector cameraPositionInPx = new Vector(frameSize.getX() / 2,
                frameSize.getY() / 2);
        Vector fromCamToPointInWorld = parent.getCamera().getPosition()
                .multiplyX(parallaxX).multiplyY(parallaxY)
                .getDistance(worldPoint);
        return cameraPositionInPx.add(fromCamToPointInWorld.multiplyY(-1)
                .multiply(pixelPerMeter * parallaxZoom));
    }

    /**
     * Gibt die derzeit auf dem Bildschirm sichtbare Fläche des Layers an.
     *
     * @return Die sichtbare Fläche als Bounds Objekt <b>mit Angaben in
     *         Meter</b>
     *
     * @see Game#getWindowSize()
     */
    @API
    public Bounds getVisibleArea(Vector gameSizeInPixels)
    {
        Vector center = parent.getCamera().getPosition();
        double pixelPerMeter = calculatePixelPerMeter();
        return new Bounds(0, 0, gameSizeInPixels.getX() / pixelPerMeter,
                gameSizeInPixels.getY() / pixelPerMeter) //
                .withCenterPoint(center);
    }

    /**
     * Setzt den Kamerazoom exakt, sodass die sichtbare Breite des sichtbaren
     * Fensters einer bestimmten Länge entspricht.
     *
     * @param width Die Breite in Meter, auf die die Kamera im Fenster exakt zu
     *              setzen ist.
     *
     * @see #setVisibleHeight(double, Vector)
     * @see Game#getWindowSize()
     */
    @API
    public void setVisibleWidth(double width, Vector gameSizeInPixels)
    {
        double desiredPixelPerMeter = gameSizeInPixels.getX() / width;
        double desiredZoom = 1 + ((desiredPixelPerMeter - 1) / parallaxZoom);
        parent.getCamera().setMeter(desiredZoom);
    }

    /**
     * Setzt den Kamerazoom exakt, sodass die sichtbare Höhe des sichtbaren
     * Fensters einer bestimmten Länge entspricht.
     *
     * @param height Die Höhe in Meter, auf die die Kamera im Fenster exakt zu
     *               setzen ist.
     *
     * @see #setVisibleWidth(double, Vector)
     * @see Game#getWindowSize()
     */
    @API
    public void setVisibleHeight(double height, Vector gameSizeInPixels)
    {
        double desiredPixelPerMeter = gameSizeInPixels.getY() / height;
        double desiredZoom = 1 + ((desiredPixelPerMeter - 1) / parallaxZoom);
        parent.getCamera().setMeter(desiredZoom);
    }

    @API
    public double calculatePixelPerMeter()
    {
        return 1 + (parent.getCamera().getMeter() - 1) * parallaxZoom;
    }

    @Internal
    public void render(Graphics2D g, Camera camera, int width, int height)
    {
        if (!visible)
        {
            return;
        }
        Vector position = camera.getPosition();
        double rotation = -camera.getRotation();
        g.setClip(0, 0, width, height);
        g.translate(width / 2, height / 2);
        double pixelPerMeter = calculatePixelPerMeter();
        g.rotate(Math.toRadians(rotation) * parallaxRotation, 0, 0);
        g.translate((-position.getX() * parallaxX) * pixelPerMeter,
                (position.getY() * parallaxY) * pixelPerMeter);
        // TODO: Calculate optimal bounds
        int size = Math.max(width, height);
        boolean needsSort = false;
        int previousPosition = Integer.MIN_VALUE;
        for (Actor actor : actors)
        {
            actor.renderBasic(
                    g, new Bounds(position.getX() - size,
                            position.getY() - size, size * 2, size * 2),
                    pixelPerMeter);
            if (!needsSort)
            {
                int actorPosition = actor.getLayerPosition();
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
     */
    @Internal
    public WorldHandler getWorldHandler()
    {
        return worldHandler;
    }

    @Internal
    public void step(double deltaSeconds)
    {
        synchronized (worldHandler)
        {
            worldHandler.step(deltaSeconds * timeDistort);
        }
    }

    @API
    public EventListeners<KeyStrokeListener> getKeyStrokeListeners()
    {
        return listeners.keyStroke;
    }

    @API
    public EventListenerBundle getListenerBundle()
    {
        return listeners;
    }

    @API
    public EventListeners<MouseWheelListener> getMouseWheelListeners()
    {
        return listeners.mouseWheel;
    }

    @API
    public EventListeners<FrameUpdateListener> getFrameUpdateListeners()
    {
        return listeners.frameUpdate;
    }

    @Internal
    void invokeFrameUpdateListeners(double deltaSeconds)
    {
        double scaledSeconds = deltaSeconds * timeDistort;
        listeners.frameUpdate.invoke(frameUpdateListener -> frameUpdateListener
                .onFrameUpdate(scaledSeconds));
    }
}
