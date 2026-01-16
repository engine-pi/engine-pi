/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/collision/FroggyJump.java
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
package demos.docs.events.collision.froggy_jump;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/events/collision.md

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.event.KeyStrokeListener;

class DeathScreen extends Scene implements KeyStrokeListener
{
    public DeathScreen()
    {
        Text message = new Text("You Died. Press any button to try again");
        message.height(.6);
        message.center(camera().focus());
        add(message);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        Controller.transitionToScene(new FroggyJump());
    }
}
