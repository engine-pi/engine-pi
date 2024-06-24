/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/basics/CollisionTest.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
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
package de.pirckheimer_gymnasium.engine_pi_demos;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public class LogoDemo extends Scene
{
    private final int I_X_POSITION = 2;

    public LogoDemo()
    {
        // P
        addRectangle(1.0, 2.0);
        addTriangle(3).rotateBy(-90).setPosition(-0.25, 4);
        addCircle().setPosition(0.25, 2);
        // i
        addRectangle(1.0, 2.0).setX(I_X_POSITION);
        addCircle().setPosition(I_X_POSITION, 2.8);
    }

    public static void main(String[] args)
    {
        Game.start(500, 500, new LogoDemo());
        Game.setWindowPosition(2500, 0);
    }
}
