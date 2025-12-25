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
package de.pirckheimer_gymnasium.demos.classes.actor;

import pi.Game;
import pi.actor.Line;

/**
 * Demonstriert die Figur <b>Linie</b> ({@link Line}).
 *
 * @author Josef Friedrich
 */
public class LineDemo extends ActorBaseScene
{
    public LineDemo()
    {
        Line line = new Line(0, 0, 1, 1);
        // line.setPosition(2, 2);
        line.makeDynamic();
        // line.rotateBy(45);
        add(line);
    }

    public static void main(String[] args)
    {
        Game.debug();
        Game.start(new LineDemo());
    }
}
