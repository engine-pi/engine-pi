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
import pi.physics.FixtureBuilder;

/**
 * Demonstriert die Figur <b>Rechteck</b> ({@link Rectangle}).
 *
 * @author Josef Friedrich
 */
public class RectangleDemo extends ActorBaseScene
{
    public RectangleDemo()
    {
        // Breite und Höhe ist gleich 1 Meter
        Rectangle r1 = new Rectangle();
        // Durch Angabe von Breite und Höhe.
        Rectangle r2 = new Rectangle(2, 2);
        r2.position(3, 0);
        //
        Rectangle r3 = new Rectangle(3, 3, () -> {
            return FixtureBuilder.rectangle(2, 2);
        });
        r3.position(7, 0);
        add(r1, r2, r3);
    }

    public static void main(String[] args)
    {
        Controller.debug();
        Controller.start(new RectangleDemo());
    }
}
