/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/actor/DistanceJoint.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2020 Michael Andonie and contributors.
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

/**
 * Eine <b>Stabverbindung</b> zwingt zwei Punkte auf zwei Körpern dazu, in einem
 * festen Abstand zueinander zu bleiben. Man kann sich das wie einen masselosen,
 * starren Stab vorstellen.
 *
 * @author Michael Andonie
 * @author Niklas Keller
 *
 * @see Joint
 * @see PrismaticJoint
 * @see RevoluteJoint
 * @see RopeJoint
 * @see WeldJoint
 */
public class DistanceJoint extends
        Joint<de.pirckheimer_gymnasium.jbox2d.dynamics.joints.DistanceJoint>
{
    @Override
    protected void updateCustomProperties(
            de.pirckheimer_gymnasium.jbox2d.dynamics.joints.DistanceJoint joint)
    {
        // nothing to do
    }
}
