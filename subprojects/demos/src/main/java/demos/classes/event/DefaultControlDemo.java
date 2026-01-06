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

import pi.Controller;
import pi.Scene;
import pi.event.DefaultControl;

import java.awt.event.KeyEvent;

/**
 * Demonstriert die statischen Methoden {@link pi.Controller#defaultControl()},
 * {@link pi.Controller#defaultControl(pi.event.DefaultListener)} und
 * {@link pi.Controller#removeDefaultControl()}.
 */
public class DefaultControlDemo
{
    public static void main(String[] args)
    {
        Controller.debug();
        Controller.start(new Scene());
        Controller.addKeyStrokeListener((event) -> {
            if (event.getKeyCode() == KeyEvent.VK_SPACE)
            {
                if (Controller.defaultControl() == null)
                {
                    Controller.defaultControl(new DefaultControl());
                }
                else
                {
                    Controller.removeDefaultControl();
                }
            }
        });
    }
}
