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
package demos.tutorials.user_input;

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.event.KeyStrokeListener;

public class KeyStrokeCounterDemo extends Scene
{
    public KeyStrokeCounterDemo()
    {
        add(new CounterText());
    }

    private class CounterText extends Text implements KeyStrokeListener
    {
        private int counter = 0;

        public CounterText()
        {
            super("You pressed 0 keys.", 2);
            center(0, 0);
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent)
        {
            counter++;
            content("You pressed " + counter + " keys.");
            center(0, 0);
        }
    }

    public static void main(String[] args)
    {
        Controller.start(new KeyStrokeCounterDemo(), 700, 200);
    }
}
