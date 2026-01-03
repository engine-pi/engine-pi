/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/scenes/MainScene.java
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
package demos.tutorials.scenes;

import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;
import pi.Vector;
import pi.Rectangle;
import pi.Text;
import pi.animation.CircleAnimation;
import pi.event.FrameUpdateListener;
import pi.event.KeyStrokeListener;
import pi.event.MouseButton;
import pi.event.MouseClickListener;

public class MainScene extends Scene implements KeyStrokeListener
{
    private final PauseMenu pauseMenu;

    public MainScene()
    {
        pauseMenu = new PauseMenu(this);
        Rectangle toAnimate = new Rectangle(5, 2);
        toAnimate.center(0, -5);
        toAnimate.color("orange");
        CircleAnimation animation = new CircleAnimation(toAnimate,
                new Vector(0, 0), 8, true, true);
        addFrameUpdateListener(animation);
        add(toAnimate);
        addKeyStrokeListener(this);
        Text info = new Text("Pause mit P");
        info.center(-7, -5);
        add(info);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        if (keyEvent.getKeyCode() == KeyEvent.VK_P)
        {
            gotoPause();
        }
    }

    private void gotoPause()
    {
        Game.transitionToScene(pauseMenu);
    }

    private static class PauseMenu extends Scene
    {
        private final Scene mainScene;

        public PauseMenu(Scene mainScene)
        {
            this.mainScene = mainScene;
            MenuItem back = new MenuItem(new Vector(0, -5), "Zurück");
            add(back, back.label);
            Text headline = new Text("Mach mal Pause.", 2);
            headline.center(0, 3);
            add(headline);
        }

        private class MenuItem extends Rectangle
                implements MouseClickListener, FrameUpdateListener
        {
            private final Text label;

            public MenuItem(Vector center, String labelText)
            {
                super(10, 1.5);
                label = new Text(labelText, 1);
                label.layerPosition(1);
                label.color("black");
                label.center(center);
                layerPosition(0);
                color("blueGreen");
                center(center);
            }

            @Override
            public void onMouseDown(Vector clickLoc, MouseButton mouseButton)
            {
                if (contains(clickLoc))
                {
                    Game.transitionToScene(mainScene);
                }
            }

            @Override
            public void onFrameUpdate(double pastTime)
            {
                if (contains(Game.mousePosition()))
                {
                    color("blue");
                }
                else
                {
                    color("blueGreen");
                }
            }
        }
    }

    public static void main(String[] args)
    {
        Game.start(new MainScene(), 600, 400);
    }
}
