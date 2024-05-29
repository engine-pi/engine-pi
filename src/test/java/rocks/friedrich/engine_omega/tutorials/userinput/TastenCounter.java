/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/userinput/TastenCounter.java
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

import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Text;
import rocks.friedrich.engine_omega.event.KeyListener;
import rocks.friedrich.engine_omega.tutorials.util.Util;

public class TastenCounter extends Scene
{
    public TastenCounter()
    {
        this.add(new CounterText());
    }

    public static void main(String[] args)
    {
        Game.start(500, 200, new TastenCounter());
    }

    private class CounterText extends Text implements KeyListener
    {
        private int counter = 0;

        public CounterText()
        {
            super("You pressed 0 keys.", 2);
            this.setCenter(0, 0);
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent)
        {
            counter++;
            this.setContent("You pressed " + counter + " keys.");
            this.setCenter(0, 0);
            if (keyEvent.getKeyCode() == KeyEvent.VK_1)
            {
                Util.makeScreenshot("counter");
            }
        }
    }
}
