/*
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
package de.pirckheimer_gymnasium.engine_pi.actor;

import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.event.EventListeners;
import de.pirckheimer_gymnasium.engine_pi.physics.WorldHandler;

/**
 * Eine Verbindung zwischen Objekten.
 *
 * @param <JointType> Der Typ der Verbindung in der Box2D-Repräsentation.
 *
 * @author Michael Andonie
 * @author Niklas Keller
 *
 * @see DistanceJoint
 * @see PrismaticJoint
 * @see RevoluteJoint
 * @see RopeJoint
 * @see WeldJoint
 */
@API
public abstract class Joint<JointType extends de.pirckheimer_gymnasium.jbox2d.dynamics.joints.Joint>
{
    private JointRegistration<JointType> joint;

    private final EventListeners<Runnable> releaseListeners = new EventListeners<>();

    /**
     * Eine Verbindung kann entfernt und neu erstellt werden, daher benötigen
     * wir diese Methode hier, damit die Verbindung neu gesetzt werden kann.
     */
    @Internal
    public final void setJoint(JointType joint, WorldHandler worldHandler)
    {
        this.joint = new JointRegistration<>(joint, worldHandler);
        updateCustomProperties(joint);
    }

    protected abstract void updateCustomProperties(JointType joint);

    @Internal
    protected final JointType getJoint()
    {
        JointRegistration<JointType> joint = this.joint;
        if (joint == null)
        {
            return null;
        }
        return joint.joint();
    }

    /**
     * Löst die Verbindung der Objekte.
     */
    @API
    public void release()
    {
        if (joint != null)
        {
            joint.worldHandler().getWorld().destroyJoint(joint.joint());
            joint = null;
        }
        releaseListeners.invoke(Runnable::run);
        releaseListeners.clear();
    }

    /**
     * Fügt einen Beobachter hinzu, der ausgeführt wird, sobald die Verbindung
     * gelöst wird.
     *
     * @param runnable Einen Beobachter, der ausgeführt wird, sobald die
     *                 Verbindung gelöst wird.
     */
    @API
    public void addReleaseListener(Runnable runnable)
    {
        releaseListeners.add(runnable);
    }

    public record JointRegistration<T>(T joint, WorldHandler worldHandler)
    {
    }
}
