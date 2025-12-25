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
package demos.classes.class_game;

import pi.Game;
import pi.Scene;
import pi.actor.Actor;

public class SetPixelMultiplicationDemo extends Scene
{
    static
    {
        Game.setPixelMultiplication(2);
    }

    public SetPixelMultiplicationDemo()
    {
        addText("Text").setPosition(0, -1);
        Actor image = addImage(
                "Pixel-Adventure-1/Main Characters/Pink Man/Jump (32x32).png",
                1, 1);
        setFocus(image);
        setBackgroundColor("white");
    }

    public static void main(String[] args)
    {
        Game.start(new SetPixelMultiplicationDemo(), 125, 200);
    }
}
