/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/collision/FroggyJump.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
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
package demos.docs.events.collision.froggy_jump;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/events/collision.md

import pi.Camera;
import pi.Controller;
import pi.Random;
import pi.Scene;
import pi.graphics.geom.Vector;

public class FroggyJump extends Scene
{
    private Frog frog;

    private static final double PLATFORM_HEIGHT = 0.5;

    public FroggyJump()
    {
        info().title("Spiel „Froggy Jump“")
            .help(
                "Tastenkürzel: a bewegt den Frosch nach links, d bewegt den Frosch nach rechts.");
        frog = new Frog();
        add(frog);
        gravityOfEarth();
        Camera camera = camera();
        camera.focus(frog);
        camera.offset(new Vector(0, 4));
        makePlatforms(10);
        makeMultiplePlatforms(40);
    }

    private void makePlatforms(int count)
    {
        for (int i = 0; i < count; i++)
        {
            Platform platform = new Platform(5, PLATFORM_HEIGHT);
            platform.anchor(0, (double) i * 4);
            add(platform);
        }
    }

    private void makeMultiplePlatforms(int count)
    {
        for (int i = 0; i < count; i++)
        {
            int numPlatforms = Random.range(2) + 1;
            for (int j = 0; j < numPlatforms; j++)
            {
                Platform platform = new Platform((double) 6 / numPlatforms,
                        PLATFORM_HEIGHT);
                platform.anchor(numPlatforms * (j + 1) * i * Random.range(),
                    (double) i * 4);
                add(platform);
            }
            if (i > 3)
            {
                for (int j = 0; j < Random.range(3); j++)
                {
                    SpikeBall.create(Random.range() * (4 + j) * i,
                        Random.range() * 4 + 0.5 + 5 * i,
                        layer());
                }
            }
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new FroggyJump(), 400, 600);
    }
}
