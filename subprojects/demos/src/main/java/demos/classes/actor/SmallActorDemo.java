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
package demos.classes.actor;

import pi.Controller;
import pi.Rectangle;
import pi.Scene;

/**
 * Demonstriert wie klein eine Figur gezeichnet werden kann.
 *
 * @author Josef Friedrich
 *
 * @since 0.48.0
 */
public class SmallActorDemo extends Scene
{
    public SmallActorDemo()
    {
        Rectangle r1 = new Rectangle(0.02, 0.03);
        Rectangle r2 = new Rectangle(0.02, 0.03);
        r2.anchor(0.04, 0);

        add(r1, r2);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.config.graphics.pixelPerMeter(1000);
        Controller.start(new SmallActorDemo());
    }
}
