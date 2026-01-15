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
package demos.docs.events.key_event;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/events/key-event.md

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.event.KeyStrokeListener;

public class KeyEventDisplayDemo extends Scene
{
    public KeyEventDisplayDemo()
    {
        add(new KeyText());
    }

    private class KeyText extends Text implements KeyStrokeListener
    {
        public KeyText()
        {
            super("Press a key", 1);
            center(0, 0);
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent)
        {
            String text = KeyEvent.getKeyText(keyEvent.getKeyCode());
            text = text.replace(" ", "_");
            text = text.toUpperCase();
            content("VK_" + text);
            center(0, 0);
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new KeyEventDisplayDemo());
    }
}
