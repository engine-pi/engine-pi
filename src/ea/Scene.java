/*
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2017 Michael Andonie and contributors.
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

package ea;

import ea.actor.Actor;
import ea.collision.CollisionListener;
import ea.internal.ano.API;
import ea.internal.ano.NoExternalUse;
import ea.internal.phy.WorldHandler;
import ea.keyboard.KeyListener;
import ea.mouse.MouseButton;
import ea.mouse.MouseClickListener;
import ea.actor.ActorGroup;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RopeJoint;

import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Scene implements FrameUpdateListener, MouseClickListener, KeyListener {
    /**
     * Die Kamera des Spiels. Hiermit kann der sichtbare Ausschnitt der Zeichenebene
     * bestimmt und manipuliert werden.
     */
    private final Camera camera;

    /**
     * Die Liste aller angemeldeten KeyListener.
     */
    private final Collection<KeyListener> keyListeners = new CopyOnWriteArraySet<>();

    /**
     * Die Liste aller angemeldeten MouseClickListener.
     */
    private final Collection<MouseClickListener> mouseClickListeners = new CopyOnWriteArraySet<>();

    /**
     * Die Liste aller angemeldeten FrameUpdateListener.
     */
    private final Collection<FrameUpdateListener> frameUpdateListeners = new CopyOnWriteArraySet<>();

    /**
     * Die Liste aller angemeldeten PeriodicTask.
     */
    private final Set<PeriodicTask> tickers = new HashSet<>();

    /**
     * Der Wurzel-ActorGroup. An ihm müssen direkt oder indirekt (über weitere ActorGroup) alle
     * <code>Actor</code>-Objekte angemeldet werden, die gezeichnet werden sollen.
     */
    private final ActorGroup root;

    /**
     * Die Physics der Szene.
     */
    private final WorldHandler worldHandler;

    public Scene() {
        this.root = new ActorGroup();
        this.camera = new Camera();
        this.worldHandler = new WorldHandler();
        this.root.onAttach(this);
        this.addFrameUpdateListener(this.camera);
    }

    @NoExternalUse
    public void render(Graphics2D g, BoundingRechteck bounds) {
        this.root.renderBasic(g, bounds);

        if (EngineAlpha.isDebug()) {
            this.renderDebug(g);
        }
    }

    @API
    public Camera getCamera() {
        return this.camera;
    }

    @NoExternalUse
    private void renderDebug(Graphics2D g) {
        // Display Joints
        Joint j = root.getPhysicsHandler().worldHandler().getWorld().getJointList();

        while (j != null) {
            renderJoint(j, g);
            j = j.m_next;
        }
    }

    @NoExternalUse
    private void renderJoint(Joint j, Graphics2D g) {
        final int CIRC_RAD = 10; // (Basis-)Radius für die Visualisierung von Kreisen
        final int RECT_SID = 12; // (Basis-)Breite für die Visualisierung von Rechtecken

        Vec2 anchorA = new Vec2(), anchorB = new Vec2();
        j.getAnchorA(anchorA);
        j.getAnchorB(anchorB);

        Vector aOnZE = root.getPhysicsHandler().worldHandler().fromVec2(anchorA);
        Vector bOnZE = root.getPhysicsHandler().worldHandler().fromVec2(anchorB);

        if (j instanceof RevoluteJoint) {
            g.setColor(Color.blue);
            g.drawOval((int) aOnZE.getRealX() - (CIRC_RAD / 2), (int) aOnZE.getRealY() - (CIRC_RAD / 2), CIRC_RAD, CIRC_RAD);
        } else if (j instanceof RopeJoint) {
            g.setColor(Color.cyan);
            g.drawRect((int) aOnZE.getRealX() - (CIRC_RAD / 2), (int) aOnZE.getRealY() - (CIRC_RAD / 2), RECT_SID, RECT_SID);
            g.drawRect((int) bOnZE.getRealX() - (CIRC_RAD / 2), (int) bOnZE.getRealY() - (CIRC_RAD / 2), RECT_SID, RECT_SID);
            g.drawLine((int) aOnZE.getRealX(), (int) aOnZE.getRealY(), (int) bOnZE.getRealX(), (int) bOnZE.getRealY());
        } else if (j instanceof DistanceJoint) {
            g.setColor(Color.orange);
            g.drawRect((int) aOnZE.getRealX() - (CIRC_RAD / 2), (int) aOnZE.getRealY() - (CIRC_RAD / 2), RECT_SID, RECT_SID);
            g.drawRect((int) bOnZE.getRealX() - (CIRC_RAD / 2), (int) bOnZE.getRealY() - (CIRC_RAD / 2), RECT_SID, RECT_SID);
            g.drawLine((int) aOnZE.getRealX(), (int) aOnZE.getRealY(), (int) bOnZE.getRealX(), (int) bOnZE.getRealY());
        }
    }

    public WorldHandler getWorldHandler() {
        return worldHandler;
    }

    @Override
    public void onFrameUpdate(int frameDuration) {
        for (FrameUpdateListener listener : this.frameUpdateListeners) {
            listener.onFrameUpdate(frameDuration);
        }
    }

    @API
    public void add(Actor... rooms) {
        for (Actor room : rooms) {
            this.root.add(room);
        }
    }

    @API
    public void remove(Actor... rooms) {
        for (Actor room : rooms) {
            this.root.remove(room);
        }
    }

    public void addMouseClickListener(MouseClickListener mouseClickListener) {
        this.mouseClickListeners.add(mouseClickListener);
    }

    public void removeMouseClickListener(MouseClickListener mouseClickListener) {
        this.mouseClickListeners.remove(mouseClickListener);
    }

    public void addKeyListener(KeyListener keyListener) {
        this.keyListeners.add(keyListener);
    }

    public void removeKeyListener(KeyListener keyListener) {
        this.keyListeners.remove(keyListener);
    }

    public void addFrameUpdateListener(FrameUpdateListener frameUpdateListener) {
        this.frameUpdateListeners.add(frameUpdateListener);
    }

    public void removeFrameUpdateListener(FrameUpdateListener frameUpdateListener) {
        this.frameUpdateListeners.remove(frameUpdateListener);
    }

    @API
    public <E extends Actor> void addCollisionListener(CollisionListener<E> listener, Actor actor, E collider) {
        WorldHandler.spezifischesKollisionsReagierbarEingliedern(listener, actor, collider);
    }

    @API
    public void addCollisionListener(CollisionListener<Actor> listener, Actor actor) {
        WorldHandler.allgemeinesKollisionsReagierbarEingliedern(listener, actor);
    }

    @Override
    public void onKeyDown(int key) {
        for (KeyListener listener : keyListeners) {
            listener.onKeyDown(key);
        }
    }

    @Override
    public void onKeyUp(int key) {
        for (KeyListener listener : keyListeners) {
            listener.onKeyUp(key);
        }
    }

    @Override
    public void onMouseDown(Point position, MouseButton button) {
        for (MouseClickListener listener : mouseClickListeners) {
            listener.onMouseDown(position, button);
        }
    }

    @Override
    public void onMouseUp(Point position, MouseButton button) {
        for (MouseClickListener listener : mouseClickListeners) {
            listener.onMouseUp(position, button);
        }
    }

    @API
    public Point getMousePosition() {
        java.awt.Point mouse = Game.getMousePosition();
        Point position = camera.getPosition();

        float rotation = camera.getRotation();

        float mx = mouse.x;
        float my = mouse.y;

        return new Point(
                position.x + (((float) Math.cos(rotation) * mx - (float) Math.sin(rotation) * my)) / camera.getZoom(),
                position.y + (((float) Math.sin(rotation) * mx + (float) Math.cos(rotation) * my)) / camera.getZoom()
        );
    }
}