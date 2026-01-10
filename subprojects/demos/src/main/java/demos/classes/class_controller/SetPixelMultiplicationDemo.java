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
package demos.classes.class_controller;

import pi.Controller;
import pi.Image;
import pi.Scene;
import pi.Text;
import pi.actor.Actor;

public class SetPixelMultiplicationDemo extends Scene
{
    static
    {
        Controller.instantMode(false);
        Controller.config.graphics.pixelMultiplication(4);
    }

    public SetPixelMultiplicationDemo()
    {
        add(new Text("Text").anchor(0, -1));
        Actor image = new Image(
                "Pixel-Adventure-1/Main Characters/Pink Man/Jump (32x32).png",
                1, 1);
        focus(image);
        add(image);
        backgroundColor("white");
    }

    public static void main(String[] args)
    {
        Controller.start(new SetPixelMultiplicationDemo(), 125, 200);
    }
}
