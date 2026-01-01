/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package demos.physics.single_aspects;

import pi.Scene;
import pi.actor.Joint;
import pi.Rectangle;

abstract class BaseJointScene extends Scene
{
    protected final Rectangle a;

    protected final Rectangle b;

    protected Joint<?> joint;

    public BaseJointScene()
    {
        getCamera().setMeter(100);
        a = new Rectangle();
        a.setCenter(-1, 0);
        a.makeDynamic();

        b = new Rectangle();
        b.setCenter(1, 0);
        b.makeDynamic();
        add(a, b);
        delay(0.1, () -> {
            a.applyImpulse(1, 5);
            b.applyImpulse(-1, -5);
        });
        delay(5, () -> {
            joint.release();
        });
    }
}
