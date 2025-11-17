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
package de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleGraphics;

/**
 * Zeichnet ein Plus-Zeichen, um die Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.Turtle#setDirection(double)
 * Turtle#setDirection(double)} zu demonstieren.
 *
 * <pre>
 * {@code
 * private void setDirection(double direction)
 * {
 *     turtle.lowerPen();
 *     turtle.setPosition(0, 0);
 *     turtle.setDirection(direction);
 *     turtle.forward(4);
 *     turtle.liftPen();
 * }
 *
 * public void draw()
 * {
 *     setDirection(0);
 *     setDirection(90);
 *     setDirection(180);
 *     setDirection(270);
 * }
 * }
 * </pre>
 *
 * @author Josef Friedrich
 */
public class SetDirectionTurtleGraphics extends TurtleGraphics
{

    public SetDirectionTurtleGraphics()
    {
        initalState.speed(2);
    }

    private void setDirection(double direction)
    {
        turtle.lowerPen();
        turtle.setPosition(0, 0);
        turtle.setDirection(direction);
        turtle.forward(4);
        turtle.liftPen();
    }

    public void draw()
    {
        setDirection(0);
        setDirection(90);
        setDirection(180);
        setDirection(270);
    }

    public static void main(String[] args)
    {
        new SetDirectionTurtleGraphics().start();
    }
}
