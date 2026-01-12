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
package demos.docs.main_classes.actor.hello_world;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/main-classes/actor/hello-world.md

import pi.Controller;
import pi.Scene;
import pi.Circle;
import pi.Rectangle;
import pi.Text;

public class HelloWorldVersion2 extends Scene
{
    public HelloWorldVersion2()
    {
        Text helloworld = new Text("Hello, World!", 2);
        helloworld.center(0, 1);
        add(helloworld);
        helloworld.color("black");
        // Ein grünes Rechteck als Hintergrund
        Rectangle background = new Rectangle(12, 3);
        background.color("green");
        background.center(0, 1);
        background.layerPosition(-1);
        // Ein blauer Kreis
        Circle circle = new Circle(8);
        circle.color("blue");
        circle.center(0, 1);
        circle.layerPosition(-2);
        add(background, circle);
        camera().meter(20);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new HelloWorldVersion2(), 400, 300);
    }
}
