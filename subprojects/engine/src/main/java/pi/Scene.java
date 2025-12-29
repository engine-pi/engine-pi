/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/Scene.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2023 Michael Andonie and contributors.
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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

import pi.actor.Actor;
import pi.actor.ActorAdder;
import pi.annotations.API;
import pi.annotations.Internal;
import pi.event.EventListeners;
import pi.event.FrameUpdateListener;
import pi.event.FrameUpdateListenerRegistration;
import pi.event.KeyStrokeListener;
import pi.event.KeyStrokeListenerRegistration;
import pi.event.MouseButton;
import pi.event.MouseClickListener;
import pi.event.MouseClickListenerRegistration;
import pi.event.MouseScrollEvent;
import pi.event.MouseScrollListener;
import pi.event.MouseScrollListenerRegistration;
import pi.graphics.RenderSource;
import pi.graphics.SceneInfoOverlay;
import pi.physics.WorldHandler;
import pi.resources.color.ColorContainer;
import de.pirckheimer_gymnasium.jbox2d.common.Vec2;
import de.pirckheimer_gymnasium.jbox2d.dynamics.joints.DistanceJoint;
import de.pirckheimer_gymnasium.jbox2d.dynamics.joints.Joint;
import de.pirckheimer_gymnasium.jbox2d.dynamics.joints.PrismaticJoint;
import de.pirckheimer_gymnasium.jbox2d.dynamics.joints.RevoluteJoint;
import de.pirckheimer_gymnasium.jbox2d.dynamics.joints.RopeJoint;

/**
 * Mithilfe von <b>Szenen</b> können verschiedene <b>Ansichten</b> eines Spiels
 * erstellt werden, ohne beim Szenenwechsel alle grafischen Objekte entfernen
 * und wieder neu erzeugen zu müssen.
 */
public class Scene implements KeyStrokeListenerRegistration,
        MouseClickListenerRegistration, MouseScrollListenerRegistration,
        FrameUpdateListenerRegistration, ActorAdder, RenderSource
{
    private static final Color REVOLUTE_JOINT_COLOR = Color.BLUE;

    private static final Color ROPE_JOINT_COLOR = Color.CYAN;

    private static final Color DISTANCE_JOINT_COLOR = Color.ORANGE;

    private static final Color PRISMATIC_JOINT_COLOR = Color.GREEN;

    /**
     * Die <b>Kamera</b> der Szene. Hiermit kann der sichtbare Ausschnitt der
     * Zeichenebene bestimmt und manipuliert werden.
     */
    private final Camera camera;

    private final EventListeners<FrameUpdateListener> frameUpdateListeners = new EventListeners<>();

    private final EventListeners<KeyStrokeListener> keyStrokeListeners = new EventListeners<>();

    private final EventListeners<MouseClickListener> mouseClickListeners = new EventListeners<>();

    private final EventListeners<MouseScrollListener> mouseScrollListeners = new EventListeners<>();

    /**
     * Die Ebenen dieser Szene.
     */
    private final List<Layer> layers = new ArrayList<>();

    private Color backgroundColor = Color.BLACK;

    /**
     * Die Hauptebene (default-additions)
     */
    private final Layer mainLayer;

    private static final int JOINT_CIRCLE_RADIUS = 10;

    /**
     * (Basis-)Breite für die Visualisierung von Rechtecken
     */
    private static final int JOINT_RECTANGLE_SIDE = 12;

    private SceneInfoOverlay info;

    /**
     * Erzeugt eine neue Szene.
     *
     * <p>
     * Dabei wird eine neue Kamera und einen neue Ebene erzeugt. Die Ebene hat
     * die Position {@code 0}
     * </p>
     */
    public Scene()
    {
        camera = new Camera();
        mainLayer = new Layer();
        mainLayer.setLayerPosition(0);
        addLayer(mainLayer);
        EventListeners.registerListeners(this);
    }

    /**
     * @return Eine <b>Infobox</b>, die <b>über</b> eine <b>Szene</b> gelegt
     *     wird.
     *
     * @since 0.42.0
     */
    public SceneInfoOverlay info()
    {
        if (info == null)
        {
            info = new SceneInfoOverlay(this);
        }
        return info;
    }

    public SceneInfoOverlay info(String title)
    {
        return info().title(title);
    }

    public SceneInfoOverlay info(String title, String subtitle)
    {
        return info().title(title).subtitle(subtitle);
    }

    public SceneInfoOverlay info(String title, String subtitle,
            String description)
    {
        return info().title(title).subtitle(subtitle).description(description);
    }

    public SceneInfoOverlay info(String title, String subtitle,
            String description, String help)
    {
        return info().title(title).subtitle(subtitle).description(description)
                .help(help);
    }

    /**
     * Gibt sich selbst zurück.
     *
     * @return Die Szene selbst.
     */
    public Scene getScene()
    {
        return this;
    }

    /**
     * Gibt die Hauptebene dieser Szene aus.
     *
     * @return Die Hauptebene dieser Szene.
     */
    @API
    public Layer getMainLayer()
    {
        return mainLayer;
    }

    /**
     * Führt auf allen Ebenen <b>parallelisiert</b> den World-Step aus.
     *
     * @param pastTime Die Echtzeit, die seit dem letzten World-Step vergangen
     *     ist.
     *
     * @hidden
     */
    @Internal
    public final void step(double pastTime,
            Function<Runnable, Future<?>> invoker) throws InterruptedException
    {
        synchronized (layers)
        {
            Collection<Future<?>> layerFutures = new ArrayList<>(layers.size());
            for (Layer layer : layers)
            {
                Future<?> future = invoker.apply(() -> layer.step(pastTime));
                layerFutures.add(future);
            }
            for (Future<?> layerFuture : layerFutures)
            {
                try
                {
                    layerFuture.get();
                }
                catch (ExecutionException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Zeichnet eine <b>Überblendung</b> in die Szene, die unabhängig von der
     * Kameraeinstellung ist und alle Figuren der Szene überdecken kann.
     *
     * <p>
     * Diese Methode ist dazu gedacht, überschrieben zu werden.
     * </p>
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param width Die <b>Breite</b> der Zeichenfläche in Pixel.
     * @param height Die <b>Höhe</b> der Zeichenfläche in Pixel.
     */
    @API
    public void renderOverlay(Graphics2D g, int width, int height)
    {

    }

    /**
     * Ruft die zu überschreibende Methode
     * {@link #renderOverlay(Graphics2D, int, int)} auf und zeichnet die
     * Beschreibungstexte (Titel, Untertitel etc.) ein.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param width Die <b>Breite</b> der Zeichenfläche in Pixel.
     * @param height Die <b>Höhe</b> der Zeichenfläche in Pixel.
     */
    private void renderOverlays(Graphics2D g, int width, int height)
    {
        // Muss ganz zum Schluss erfolgen, damit zuerst gezeichnete
        // Überblendungen übermalt werden.
        renderOverlay(g, width, height);
    }

    @Internal
    public void render(Graphics2D g, int width, int height)
    {
        final AffineTransform base = g.getTransform();
        synchronized (layers)
        {
            for (Layer layer : layers)
            {
                layer.render(g, camera, width, height);
                g.setTransform(base);
            }
        }
        if (Game.isDebug())
        {
            renderJoints(g);
        }
        // Muss ganz zum Schluss erfolgen, damit zuerst gezeichnete Figuren
        // übermalt werden.
        if (info != null)
        {
            info.render(g, width, height);
        }
        renderOverlays(g, width, height);
    }

    /**
     * Sortiert die Ebenenen.
     *
     * <p>
     * Die Methode wird aufgerufen, wenn sich ein Ebenenzustand innerhalb dieser
     * Szene geändert. Sie stellt sicher, dass die Ebene-Liste korrekt sortiert
     * ist und aller Ebenen in der richtigen Reihenfolge gezeichnet werden.
     * </p>
     *
     * @hidden
     */
    @Internal
    final void sortLayers()
    {
        layers.sort(Comparator.comparingInt(Layer::getLayerPosition));
    }

    /**
     * <b>Fügt</b> der Szene eine neue <b>Ebene</b> <b>hinzu</b>.
     *
     * @param layer Die Ebene, die der Szene hinzugefügt werden soll.
     */
    @API
    public final void addLayer(Layer layer)
    {
        synchronized (this.layers)
        {
            layer.setParent(this);
            layers.add(layer);
            sortLayers();
        }
    }

    /**
     * <b>Entfernt</b> eine <b>Ebene</b> aus der Szene.
     */
    @API
    public final void removeLayer(Layer layer)
    {
        synchronized (this.layers)
        {
            layers.remove(layer);
            layer.setParent(null);
        }
    }

    /**
     * Gibt die sichtbare Fläche auf der <b>Hauptebene</b> aus.
     *
     * @param gameSizeInPixels Die Größe des Spielfensters in Pixel.
     *
     * @return Die sichtbare Fläche auf der Hauptebene
     *
     * @see Game#getWindowSize()
     */
    @API
    public Bounds getVisibleArea(Vector gameSizeInPixels)
    {
        return mainLayer.getVisibleArea(gameSizeInPixels);
    }

    /**
     * Gibt die sichtbare Fläche auf der <b>Hauptebene</b> aus.
     *
     * @return Die sichtbare Fläche auf der Hauptebene
     *
     * @see Game#getWindowSize()
     *
     * @since 0.42.0
     */
    @API
    public Bounds getVisibleArea()
    {
        return mainLayer.getVisibleArea(Game.getWindowSize());
    }

    /**
     * Gibt die <b>Kamera</b> der Szene aus.
     *
     * @return Die <b>Kamera</b> der Szene.
     */
    @API
    public final Camera getCamera()
    {
        return camera;
    }

    /**
     * Setzt die <b>Anzahl an Pixel</b>, die einem <b>Meter</b> entsprechen und
     * setzt somit den „Zoom“ der Kamera.
     *
     * <p>
     * Die Anzahl an Pixel eines Meters bestimmt wie „nah“ oder „fern“ die
     * Kamera an der Zeichenebene ist. Der Standardwert eines Meters ist
     * <code>32</code> Pixel. Größere Werte zoomen näher an die Spielfläche
     * heran, kleine Werte weiter von der Spielfläche weg.
     * </p>
     *
     * <p>
     * Bei dieser Methode handelt es sich um eine Abkürzung. Statt
     * {@code getCamera().setMeter(double)} braucht nur {@code setMeter(double)}
     * geschrieben werden.
     * </p>
     *
     * @param pixelCount Die neue Anzahl an Pixel, die einem Meter entsprechen.
     *
     * @see Camera#setMeter(double)
     */
    @API
    public void setMeter(double pixelCount)
    {
        camera.setMeter(pixelCount);
    }

    /**
     * Setzt den <b>Fokus</b> der Kamera auf eine <b>Figur</b>.
     *
     * <p>
     * Dieses Objekt ist ab dann im „Zentrum“ der Kamera. Die Art des Fokus
     * (rechts, links, oben, unten, mittig, etc.) kann über die Methode
     * {@link Camera#setOffset(Vector)} geändert werden. Soll das Fokusverhalten
     * beendet werden, kann einfach {@code null} übergeben werden, dann bleibt
     * die Kamera bis auf Weiteres in der aktuellen Position.
     *
     * <p>
     * Bei dieser Methode handelt es sich um eine Abkürzung. Statt
     * {@code getCamera().setFocus(Actor)} braucht nur {@code setFocus(Actor)}
     * geschrieben werden.
     * </p>
     *
     * @param focus Die Figur, die fokussiert werden soll.
     */
    @API
    public void setFocus(Actor focus)
    {
        camera.setFocus(focus);
    }

    /**
     * @hidden
     */
    @Internal
    private void renderJoints(Graphics2D g)
    {
        // Display Joints
        for (Layer layer : layers)
        {
            Joint j = layer.getWorldHandler().getWorld().getJointList();
            while (j != null)
            {
                renderJoint(j, g, layer);
                j = j.getNext();
            }
        }
    }

    /**
     * @hidden
     */
    @Internal
    private static void renderJoint(Joint j, Graphics2D g, Layer layer)
    {
        Vec2 anchorA = new Vec2(), anchorB = new Vec2();
        j.getAnchorA(anchorA);
        j.getAnchorB(anchorB);
        Vector aInPx = layer
                .translateWorldPointToFramePxCoordinates(Vector.of(anchorA));
        Vector bInPx = layer
                .translateWorldPointToFramePxCoordinates(Vector.of(anchorB));
        if (j instanceof RevoluteJoint)
        {
            g.setColor(REVOLUTE_JOINT_COLOR);
            g.drawOval((int) aInPx.getX() - (JOINT_CIRCLE_RADIUS / 2),
                    (int) aInPx.getY() - (JOINT_CIRCLE_RADIUS / 2),
                    JOINT_CIRCLE_RADIUS, JOINT_CIRCLE_RADIUS);
        }
        else if (j instanceof RopeJoint)
        {
            renderJointRectangle(g, ROPE_JOINT_COLOR, aInPx, bInPx,
                    layer.calculatePixelPerMeter());
        }
        else if (j instanceof DistanceJoint)
        {
            renderJointRectangle(g, DISTANCE_JOINT_COLOR, aInPx, bInPx,
                    layer.calculatePixelPerMeter());
        }
        else if (j instanceof PrismaticJoint)
        {
            renderJointRectangle(g, PRISMATIC_JOINT_COLOR, aInPx, bInPx,
                    layer.calculatePixelPerMeter());
        }
    }

    /**
     * @param a Der Vektor a.
     * @param b Der Vektor b.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     *
     * @hidden
     */
    @Internal
    private static void renderJointRectangle(Graphics2D g, Color color,
            Vector a, Vector b, double pixelPerMeter)
    {
        g.setColor(color);
        g.drawRect((int) a.getX() - (JOINT_CIRCLE_RADIUS / 2),
                (int) a.getY() - (JOINT_CIRCLE_RADIUS / 2),
                JOINT_RECTANGLE_SIDE, JOINT_RECTANGLE_SIDE);
        g.drawRect((int) b.getX() - (JOINT_CIRCLE_RADIUS / 2),
                (int) b.getY() - (JOINT_CIRCLE_RADIUS / 2),
                JOINT_RECTANGLE_SIDE, JOINT_RECTANGLE_SIDE);
        g.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(),
                (int) b.getY());
        Vector middle = a.add(b).divide(2);
        g.drawString(
                String.valueOf(
                        a.getDistance(b).divide(pixelPerMeter).getLength()),
                (int) middle.getX(), (int) middle.getY());
    }

    /**
     * Gibt den WorldHandler der Hauptebene aus.
     *
     * @return WorldHandler der Hauptebene.
     *
     * @hidden
     */
    @Internal
    public final WorldHandler getWorldHandler()
    {
        return mainLayer.getWorldHandler();
    }

    /**
     * Gibt die Schwerkraft, die momentan auf die Hauptebene wirkt, als Vektor
     * in <b>[N]</b> bzw. <b>[m/s^2]</b> zurück.
     *
     * @return Die Schwerkraft, die momentan auf die Hauptebene wirkt, als
     *     Vektor in <b>[N]</b> bzw. <b>[m/s^2]</b>.
     */
    public Vector getGravity()
    {
        return mainLayer.getGravity();
    }

    /**
     * Setzt die Schwerkraft als Vektor, die auf <b>alle Objekte innerhalb der
     * Hauptebene der Szene</b> wirkt.
     *
     * @param gravity Die neue Schwerkraft als {@link Vector}. Die Einheit ist
     *     <b>[N]</b>.
     *
     * @see #setGravity(double, double)
     * @see Layer#setGravity(Vector)
     * @see Layer#setGravity(double, double)
     *
     * @jbox2d <a href=
     *     "https://github.com/jbox2d/jbox2d/blob/94bb3e4a706a6d1a5d8728a722bf0af9924dde84/jbox2d-library/src/main/java/org/jbox2d/dynamics/World.java#L997-L1004">dynamics/World.java#L997-L1004</a>
     *
     * @box2d <a href=
     *     "https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_world.h#L312-L315">b2_world.h#L312-L315</a>
     */
    @API
    public void setGravity(Vector gravity)
    {
        mainLayer.setGravity(gravity);
    }

    /**
     * Setzt die Schwerkraft durch zwei Eingabeparameter für die x- und
     * y-Richtung, die auf <b>alle Objekte innerhalb der Hauptebene der
     * Szene</b> wirkt.
     *
     * @param gravityX Die neue Schwerkraft, die in X-Richtung wirken soll. Die
     *     Einheit ist <b>[N]</b>.
     * @param gravityY Die neue Schwerkraft, die in Y-Richtung wirken soll. Die
     *     Einheit ist <b>[N]</b>.
     *
     * @see #setGravity(Vector)
     * @see Layer#setGravity(Vector)
     * @see Layer#setGravity(double, double)
     *
     * @jbox2d <a href=
     *     "https://github.com/jbox2d/jbox2d/blob/94bb3e4a706a6d1a5d8728a722bf0af9924dde84/jbox2d-library/src/main/java/org/jbox2d/dynamics/World.java#L997-L1004">dynamics/World.java#L997-L1004</a> @box2d
     *     <a href=
     *     "https://github.com/erincatto/box2d/blob/411acc32eb6d4f2e96fc70ddbdf01fe5f9b16230/include/box2d/b2_world.h#L312-L315">b2_world.h#L312-L315</a>
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
     * @see Layer#setGravityOfEarth
     */
    @API
    public void setGravityOfEarth()
    {
        setGravity(0, -9.81);
    }

    /**
     * Setzt, ob die Engine-Physics für diese Szene pausiert sein soll.
     *
     * @param worldPaused <code>false</code>: Die Engine-Physik läuft normal.
     *     <code>true</code>: Die Engine-Physik läuft <b>nicht</b>. Das bedeutet
     *     u.A. keine Collision-Detection, keine Physik-Simulation etc., bis die
     *     Physik wieder mit <code>setPhysicsPaused(true)</code> aktiviert wird.
     *
     * @see #isPhysicsPaused()
     */
    @API
    public void setPhysicsPaused(boolean worldPaused)
    {
        mainLayer.getWorldHandler().setWorldPaused(worldPaused);
    }

    /**
     * Gibt an, ob die Physik dieser Szene pausiert ist.
     *
     * @return <code>true</code>: Die Physik ist pausiert. <code>false</code>:
     *     Die Physik ist nicht pausiert.
     *
     * @see #setPhysicsPaused(boolean)
     */
    @API
    public boolean isPhysicsPaused()
    {
        return mainLayer.getWorldHandler().isWorldPaused();
    }

    /**
     * Fügt einen oder mehrere {@link Actor}-Objekte der Szene hinzu.
     *
     * @param actors Ein oder mehrere {@link Actor}-Objekte.
     */
    @API
    final public void add(Actor... actors)
    {
        for (Actor actor : actors)
        {
            mainLayer.add(actor);
        }
    }

    /**
     * Entferne einen oder mehrere {@link Actor}-Objekte aus der Szene.
     *
     * @param actors Ein oder mehrere {@link Actor}-Objekte.
     */
    @API
    final public void remove(Actor... actors)
    {
        for (Actor actor : actors)
        {
            mainLayer.remove(actor);
        }
    }

    /**
     * Gibt alle <b>Figuren</b> aller Ebenen, die <b>bereits in der
     * Physics-Engine</b> registriert sind, als Liste zurück.
     *
     * <p>
     * Die Figuren werden erst mit Verzögerung durch die Methode
     * {@link #defer(Runnable)} zu den Ebenen hinzugefügt. Sie sind also nicht
     * sofort nach dem Hinzufügen über diese Methode abrufbar.
     * </p>
     *
     * @return Alle Figuren aller Ebenen, die <b>bereits in der
     *     Physics-Engine</b> registriert sind.
     *
     * @since 0.37.0
     *
     * @see Layer#getActors()
     * @see #getAddedActors()
     */
    public List<Actor> getActors()
    {
        ArrayList<Actor> actors = new ArrayList<>();
        for (Layer layer : layers)
        {
            actors.addAll(layer.getActors());
        }
        return actors;
    }

    /**
     * Gibt alle <b>Figuren</b>, die bereits zu einer Ebene hinzugefügt und aber
     * unter Umständen <b>noch nicht in der Physiks-Engine</b> registriert
     * wurden, als Liste zurück.
     *
     * @return Alle <b>Figuren</b>, die bereits zu einer Ebene hinzugefügt und
     *     aber unter Umständen <b>noch nicht in der Physiks-Engine</b>
     *     registriert
     *
     * @since 0.37.0
     *
     * @see Layer#getAddedActors()
     * @see #getActors()
     */
    public List<Actor> getAddedActors()
    {
        ArrayList<Actor> actors = new ArrayList<>();
        for (Layer layer : layers)
        {
            actors.addAll(layer.getAddedActors());
        }
        return actors;
    }

    /**
     * Gibt den Mittelpunkt der hinzugefügten Figuren aller Ebenen aus.
     *
     * <p>
     * Bei der Berechnung werden die <b>Mittelpunkte</b> aller Figuren
     * herangezogen.
     * </p>
     *
     * @since 0.37.0
     */
    public Vector getCenter()
    {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        for (Actor actor : getAddedActors())
        {

            Vector center = actor.getCenter();
            double x = center.getX();
            double y = center.getY();

            if (x < minX)
            {
                minX = x;
            }

            if (y < minY)
            {
                minY = y;
            }

            if (x > maxX)
            {
                maxX = x;
            }

            if (y > maxY)
            {
                maxY = y;
            }

        }
        return new Vector((minX + maxX) / 2, (minY + maxY) / 2);
    }

    /**
     * Setzt den Mittelpunkt der Kamera auf den Mittelpunkt der hinzugefügten
     * Figuren aller Ebenen.
     *
     * <p>
     * Bei der Berechnung werden die <b>Mittelpunkte</b> aller Figuren
     * herangezogen.
     * </p>
     *
     * @since 0.37.0
     */
    public void focusCenter()
    {
        camera.setCenter(getCenter());
    }

    @API
    public EventListeners<KeyStrokeListener> getKeyStrokeListeners()
    {
        return keyStrokeListeners;
    }

    @API
    public EventListeners<MouseClickListener> getMouseClickListeners()
    {
        return mouseClickListeners;
    }

    @API
    public EventListeners<MouseScrollListener> getMouseScrollListeners()
    {
        return mouseScrollListeners;
    }

    @API
    public EventListeners<FrameUpdateListener> getFrameUpdateListeners()
    {
        return frameUpdateListeners;
    }

    /**
     * @hidden
     */
    @Internal
    public final void invokeFrameUpdateListeners(double pastTime)
    {
        frameUpdateListeners.invoke(frameUpdateListener -> frameUpdateListener
                .onFrameUpdate(pastTime));
        synchronized (layers)
        {
            for (Layer layer : layers)
            {
                layer.invokeFrameUpdateListeners(pastTime);
            }
        }
    }

    /**
     * @hidden
     */
    @Internal
    final void invokeKeyDownListeners(KeyEvent event)
    {
        keyStrokeListeners.invoke(listener -> listener.onKeyDown(event));
    }

    /**
     * @hidden
     */
    @Internal
    final void invokeKeyUpListeners(KeyEvent event)
    {
        keyStrokeListeners.invoke(listener -> listener.onKeyUp(event));
    }

    /**
     * @hidden
     */
    @Internal
    final void invokeMouseDownListeners(Vector position, MouseButton button)
    {
        mouseClickListeners
                .invoke(listener -> listener.onMouseDown(position, button));
    }

    /**
     * @hidden
     */
    @Internal
    final void invokeMouseUpListeners(Vector position, MouseButton button)
    {
        mouseClickListeners
                .invoke(listener -> listener.onMouseUp(position, button));
    }

    /**
     * @hidden
     */
    @Internal
    final void invokeMouseScrollListeners(MouseScrollEvent event)
    {
        mouseScrollListeners
                .invoke(listener -> listener.onMouseScrollMove(event));
    }

    /**
     * Gibt die Position der Maus in der Szene als Vektor in Meter relativ zum
     * Koordinatensystem zurück.
     *
     * <p>
     * Der Positions-Vektor ist in der Einheit Meter angegeben und bezieht sich
     * auf einen Punkt des zu Grunde liegenden Koordinatensystems.
     * </p>
     *
     * @return Die Position der Maus in der Szene als Vektor in Meter.
     *
     * @see Game#getMousePosition()
     */
    @API
    public final Vector getMousePosition()
    {
        return Game.convertMousePosition(this, Game.getMousePositionInFrame());
    }

    /**
     * Gibt die <b>Hintergrundfarbe</b> zurück.
     *
     * @return Die Hintergrundfarbe.
     */
    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    /**
     * Setzt die <b>Hintergrundfarbe</b> durch Angabe eines
     * {@link Color}-Objekts.
     *
     * @param color Die Hintergrundfarbe.
     */
    public void setBackgroundColor(Color color)
    {
        backgroundColor = color;
    }

    /**
     * Setzt die <b>Hintergrundfarbe</b> als <b>Zeichenkette</b>.
     *
     * @param color Ein Farbname, ein Farbalias ({@link ColorContainer siehe
     *     Auflistung}) oder eine Farbe in hexadezimaler Codierung (z. B.
     *     {@code #ff0000}).
     *
     * @see ColorContainer#get(String)
     */
    public void setBackgroundColor(String color)
    {
        backgroundColor = Resources.colors.get(color);
    }
}
