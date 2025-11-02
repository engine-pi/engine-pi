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
package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public class ImageAverageColorDemo extends Scene
{
    public ImageAverageColorDemo()
    {
        getCamera().setMeter(90);
        double x = -4;
        for (String filepath : new String[] { "car/background-color-grass.png",
                "car/wheel-back.png", "car/truck-240px.png",
                "dude/background/snow.png", "dude/box/obj_box001.png",
                "dude/moon.png" })
        {
            createImageWithAverageColor(filepath, x);
            x = x + 1.2;
        }
    }

    private void createImageWithAverageColor(String filepath, double x)
    {
        var image = addImage(filepath, 1, 1).setPosition(x, 0);
        addRectangle(1.0, 1.0).setPosition(x, -1.2).setColor(image.getColor());
        addRectangle(1.0, 0.5).setPosition(x, -1.9)
                .setColor(image.getComplementaryColor());
    }

    public static void main(String[] args)
    {
        Game.start(new ImageAverageColorDemo());
    }
}
