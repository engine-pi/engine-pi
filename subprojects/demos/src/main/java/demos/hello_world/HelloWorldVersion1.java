/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials//.java
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
package demos.hello_world;

import pi.Game;
import pi.Scene;
import pi.actor.Text;

public class HelloWorldVersion1 extends Scene
{
    public HelloWorldVersion1()
    {
        Text helloWorld = new Text("Hello, World!", 2);
        helloWorld.setColor("white");
        helloWorld.setCenter(0, 1);
        add(helloWorld);
        Game.debug();
    }

    public static void main(String[] args)
    {
        Game.start(new HelloWorldVersion1(), 400, 300);
    }
}
