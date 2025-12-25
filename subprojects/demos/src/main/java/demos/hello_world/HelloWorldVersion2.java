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
import pi.actor.Circle;
import pi.actor.Rectangle;
import pi.actor.Text;

public class HelloWorldVersion2 extends Scene
{
    public HelloWorldVersion2()
    {
        Text helloworld = new Text("Hello, World!", 2);
        helloworld.setCenter(0, 1);
        add(helloworld);
        helloworld.setColor("black");
        // Ein grünes Rechteck als Hintergrund
        Rectangle background = new Rectangle(12, 3);
        background.setColor("green");
        background.setCenter(0, 1);
        background.setLayerPosition(-1);
        // Ein blauer Kreis
        Circle circle = new Circle(8);
        circle.setColor("blue");
        circle.setCenter(0, 1);
        circle.setLayerPosition(-2);
        add(background, circle);
        getCamera().setMeter(20);
    }

    public static void main(String[] args)
    {
        Game.start(new HelloWorldVersion2(), 400, 300);
    }
}
