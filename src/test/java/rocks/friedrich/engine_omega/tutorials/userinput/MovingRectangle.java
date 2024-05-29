/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/userinput/MovingRectangle.java
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
package rocks.friedrich.engine_omega.tutorials.userinput;

import java.awt.Color;
import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;
import rocks.friedrich.engine_omega.tutorials.util.Util;

public class MovingRectangle extends Scene
{
    public MovingRectangle()
    {
        add(new Character());
    }

    private class Character extends Rectangle implements KeyListener
    {
        public Character()
        {
            super(2, 2);
            setCenter(0, 0);
            setColor(Color.RED);
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent)
        {
            switch (keyEvent.getKeyCode())
            {
            case KeyEvent.VK_W -> moveBy(0, 0.5);
            case KeyEvent.VK_A -> moveBy(-0.5, 0);
            case KeyEvent.VK_S -> moveBy(0, -0.5);
            case KeyEvent.VK_D -> moveBy(0.5, 0);
            }
        }
    }

    public static void main(String[] args)
    {
        Game.start(600, 400, new MovingRectangle());
        Util.addScreenshotKey("rectA");
    }
}
