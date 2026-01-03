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

import static pi.Resources.colorScheme;

import pi.Game;
import pi.actor.Grid;

/**
 * Demonstriert die Figur <b>Gitter</b> ({@link Grid}).
 *
 * @author Josef Friedrich
 */
public class GridDemo extends ActorBaseScene
{
    public GridDemo()
    {
        Grid grid1 = new Grid(3, 3, 1);
        grid1.makeStatic();
        add(grid1);
        Grid grid2 = new Grid(5, 7, 1);
        grid2.background(colorScheme.getOrange());
        grid2.color(colorScheme.getRed());
        grid2.rotateBy(-45);
        grid2.position(4, 0);
        grid2.makeDynamic();
        add(grid2);
    }

    public static void main(String[] args)
    {
        Game.start(new GridDemo());
    }
}
