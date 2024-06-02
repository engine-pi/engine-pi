/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/userinput/PaintingCircles.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
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
package rocks.friedrich.engine_omega.demos.input.mouse;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.event.MouseButton;
import rocks.friedrich.engine_omega.event.MouseClickListener;

public class PaintingCirclesDemo extends Scene implements MouseClickListener
{
    public PaintingCirclesDemo()
    {
        addMouseClickListener(this);
    }

    private void paintCircleAt(double mX, double mY, double diameter)
    {
        Circle circle = new Circle(diameter);
        circle.setCenter(mX, mY);
        add(circle);
    }

    @Override
    public void onMouseDown(Vector position, MouseButton mouseButton)
    {
        paintCircleAt(position.getX(), position.getY(), 1);
    }

    public static void main(String[] args)
    {
        Game.start(600, 400, new PaintingCirclesDemo());
    }
}
