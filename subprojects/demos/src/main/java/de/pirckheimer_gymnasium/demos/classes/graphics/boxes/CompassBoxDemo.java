/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.CompassBox;
import pi.graphics.boxes.GridBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/CompassBox.java

public class CompassBoxDemo extends Graphics2DComponent
{
    private GridBox<CompassBox> getCompasses(int numberOfCompasses, int size,
            int directionDelta)
    {
        int direction = 0;
        CompassBox[] compasses = new CompassBox[numberOfCompasses];
        for (int i = 0; i < numberOfCompasses; i++)
        {
            compasses[i] = new CompassBox(size).direction(direction)
                    .showOuterCircle();
            direction += directionDelta;
        }
        GridBox<CompassBox> grid = new GridBox<>(compasses);
        grid.padding(5);
        return grid;
    }

    public void render(Graphics2D g)
    {
        getCompasses(4, 100, 90).x(200).y(200).render(g).debug();

        getCompasses(9, 20, 30).x(500).y(300).render(g).debug();

        new CompassBox(200).direction(90).showOuterCircle().x(10).y(10)
                .render(g).debug();
    }

    public static void main(String[] args)
    {
        new CompassBoxDemo().show();
    }
}
