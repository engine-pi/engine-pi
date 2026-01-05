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
package demos.classes.actor;

import static pi.Resources.colors;

import pi.Game;
import pi.Random;
import pi.Scene;
import pi.actor.Line;
import pi.actor.Line.ArrowType;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/Line.java

/**
 * Demonstriert die Figur <b>Linie</b> ({@link Line}) indem zufällig Attribute
 * gesetzt werden.
 *
 * @author Josef Friedrich
 */
public class LineRandomDemo extends Scene
{
    public LineRandomDemo()
    {
        createRandomLines();

        repeat(2, () -> {
            createRandomLines();
        });
        backgroundColor(colors.getSafe("#333333"));
    }

    private void createRandomLines()
    {
        clear();
        for (int i = 0; i < Random.range(10, 30); i++)
        {
            Line line = new Line(Random.vector(this), Random.vector(this));
            line.color(colors.random());
            line.strokeWidth(Random.range(0.01, 0.5));

            if (Random.toggle())
            {
                line.arrow1(ArrowType.CHEVERON);
            }

            if (Random.toggle())
            {
                line.arrow2(ArrowType.TRIANGLE);
            }
            add(line);
        }
    }

    public static void main(String[] args)
    {
        Game.instantMode(false);
        Game.start(new LineRandomDemo());
    }
}
