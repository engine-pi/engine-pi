/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/dude/Platform.java
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
package de.pirckheimer_gymnasium.demos.small_games.dude;

import de.pirckheimer_gymnasium.engine_pi.actor.Tile;
import de.pirckheimer_gymnasium.engine_pi.actor.TileMap;
import de.pirckheimer_gymnasium.engine_pi.actor.TileRegistration;

/**
 * Einfache Plattform. Herzlichen Dank an <a href=
 * "https://www.gameart2d.com/free-graveyard-platformer-tileset.html">Billard
 * Art 2D</a> für die kostenfreien Tiles.
 */
public class Platform extends TileRegistration
{
    private static final int SIZE = 2;

    private static final double FRICTION = 0.5;

    public Platform(int tileCount)
    {
        super(tileCount, 1, SIZE);
        if (tileCount < 2)
        {
            throw new IllegalArgumentException(
                    "Number of tiles must be at least 2");
        }
        setFixtures(
                "R 0, " + (SIZE - 0.5) + "," + (SIZE * tileCount) + ", 0.5");
        String basePath = "dude/tiles/";
        setLeftTile(TileMap.createFromImage(basePath + "platform_l.png"));
        setMiddleTiles(TileMap.createFromImage(basePath + "platform_m.png"));
        setRightTile(TileMap.createFromImage(basePath + "platform_r.png"));
        makeStatic();
        setFriction(FRICTION);
        setElasticity(0);
    }

    private void setLeftTile(Tile tile)
    {
        setTile(0, 0, tile);
    }

    private void setMiddleTiles(Tile tile)
    {
        for (int x = 1; x < getTileCountX() - 1; x++)
        {
            setTile(x, 0, tile);
        }
    }

    private void setRightTile(Tile tile)
    {
        setTile(getTileCountX() - 1, 0, tile);
    }
}
