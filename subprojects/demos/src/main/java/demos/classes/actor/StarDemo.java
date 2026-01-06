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
import pi.actor.Star;

/**
 * Demonstriert die Figur <b>Stern</b> ({@link Star}).
 *
 * @author Josef Friedrich
 */
public class StarDemo extends ActorBaseScene
{
    public StarDemo()
    {
        Star star = new Star();
        star.position(2, 2);
        star.makeDynamic();
        star.rotateBy(45);
        add(star);
    }

    public static void main(String[] args)
    {
        Controller.debug();
        Controller.start(new StarDemo());
    }
}
