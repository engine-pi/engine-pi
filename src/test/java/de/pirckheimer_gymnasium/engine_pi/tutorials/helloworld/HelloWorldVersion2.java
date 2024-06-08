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
package de.pirckheimer_gymnasium.engine_pi.tutorials.helloworld;

import java.awt.Color;

import de.pirckheimer_gymnasium.engine_pi.tutorials.util.Util;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;

public class HelloWorldVersion2 extends Scene
{
    public HelloWorldVersion2()
    {
        Text helloworld = new Text("Hello World", 2);
        helloworld.setCenter(0, 1);
        this.add(helloworld);
        // Game.setDebug(true);
        helloworld.setColor(Color.BLACK);
        Rectangle background = new Rectangle(10, 3);
        background.setColor(Color.PINK);
        background.setCenter(0, 1);
        // background.setLayerPosition(-1);
        Circle circle = new Circle(5);
        circle.setColor(Color.GRAY);
        circle.setCenter(0, 1);
        // circle.setLayerPosition(-2);
        this.add(background, circle);
        // getCamera().setZoom(0.5);
    }

    public static void main(String[] args)
    {
        Scene helloWorld = new HelloWorldVersion2();
        Game.start(400, 300, helloWorld);
        Util.makeScreenshot("nolayer");
    }
}