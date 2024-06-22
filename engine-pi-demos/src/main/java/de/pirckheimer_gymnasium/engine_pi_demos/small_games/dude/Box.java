/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/dude/Box.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi_demos.small_games.dude;

import de.pirckheimer_gymnasium.engine_pi.Random;
import de.pirckheimer_gymnasium.engine_pi.actor.Image;

public class Box extends Image
{
    private static final double SIZE = 1;

    private static final double RESTITUTION = .3;

    public Box()
    {
        this(Random.range(9));
    }

    public Box(int type)
    {
        super(getBoxPath(type), SIZE, SIZE);
        makeDynamic();
        // setMass(MASS_IN_KG);
        setElasticity(RESTITUTION);
    }

    public static String getBoxPath(int type)
    {
        if (type < 0 || type > 9)
        {
            throw new RuntimeException("Box-Typ existiert nicht");
        }
        String prefix = type <= 4 ? "dude/box/obj_box00"
                : "dude/box/obj_crate00";
        return prefix + (type % 5 + 1) + ".png";
    }
}
