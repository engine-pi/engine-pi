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
package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;

/**
 * Demonstriert die Figur <b>Kreis</b> ({@link Circle}).
 *
 * @author Josef Friedrich
 */
public class CircleDemo extends ActorBaseScene
{
    public CircleDemo()
    {
        // Konstruktor ohne Parameter
        Circle circle = new Circle();
        circle.setPosition(2, 1);
        circle.makeDynamic();
        add(circle);
        // Konstruktor mit Angabe des Durchmessers
        Circle circle2 = new Circle(2);
        circle2.setPosition(-2, 0);
        circle2.makeStatic();
        add(circle2);
        // Mit Hilfe der create... Methoden erzeugen.
        addCircle(5, 3).makeStatic();
        addCircle(3, -6, -3).makeStatic();
    }

    public static void main(String[] args)
    {
        Game.debug();
        Game.start(new CircleDemo());
    }
}
