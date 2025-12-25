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
package demos.classes.event;

import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;

class Scene1 extends Scene
{
    public Scene1()
    {
        addText("Scene 1").setColor("red");
    }
}

class Scene2 extends Scene
{
    public Scene2()
    {
        addText("Scene 2").setColor("green");
    }
}

/**
 * Demonstriert die statische Methode
 * {@link pi.Game#addSceneLaunchListener(pi.event.SceneLaunchListener)}.
 */
public class GlobalSceneLaunchListenerDemo
{
    public static Scene scene1 = new Scene1();

    public static Scene scene2 = new Scene2();

    public static void main(String[] args)
    {
        Game.addSceneLaunchListener((scene, previous) -> {
            System.out.println("scene: " + scene);
            System.out.println("previous: " + previous);
        });

        Game.addKeyStrokeListener((event) -> {
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_1 -> {
                Game.transitionToScene(scene1);
            }
            case KeyEvent.VK_2 -> {
                Game.transitionToScene(scene2);
            }
            }
        });
        Game.start(scene1);
    }
}
