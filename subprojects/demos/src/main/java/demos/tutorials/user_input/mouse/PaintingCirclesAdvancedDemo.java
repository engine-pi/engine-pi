/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/userinput/PaintingCirclesAdvanced.java
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
package demos.tutorials.user_input.mouse;

import java.awt.Color;

import pi.Controller;
import pi.Scene;
import pi.Circle;
import pi.Rectangle;
import pi.Text;
import pi.event.MouseButton;
import pi.event.MouseClickListener;
import pi.graphics.geom.Vector;

public class PaintingCirclesAdvancedDemo extends Scene
        implements MouseClickListener
{
    static Color activeColor = Color.WHITE;

    static double activeDiameter = 1;

    public PaintingCirclesAdvancedDemo()
    {
        addMouseClickListener(this);
        ColorRect white = new ColorRect(Color.WHITE);
        ColorRect blue = new ColorRect(Color.BLUE);
        ColorRect redPurple = new ColorRect(Color.PINK);
        ColorRect orange = new ColorRect(Color.ORANGE);
        white.anchor(-8, 5);
        blue.anchor(-6, 5);
        redPurple.anchor(-4, 5);
        orange.anchor(-2, 5);
        add(white, blue, redPurple, orange);
        SizeText sizeText1 = new SizeText(1);
        SizeText sizeText2 = new SizeText(2);
        SizeText sizeText3 = new SizeText(3);
        sizeText1.anchor(0, 5);
        sizeText2.anchor(2, 5);
        sizeText3.anchor(4, 5);
        add(sizeText1, sizeText2, sizeText3);
    }

    private void paintCircleAt(double mX, double mY, double diameter,
            Color color)
    {
        Circle circle = new Circle(diameter);
        circle.center(mX, mY);
        circle.color(color);
        add(circle);
    }

    @Override
    public void onMouseDown(Vector position, MouseButton mouseButton)
    {
        if (position.y() < 5)
        {
            paintCircleAt(position.x(), position.y(), activeDiameter,
                    activeColor);
        }
    }

    private static class ColorRect extends Rectangle
            implements MouseClickListener
    {
        private final Color color;

        public ColorRect(Color color)
        {
            super(1, 1);
            this.color = color;
            color(color);
        }

        @Override
        public void onMouseDown(Vector position, MouseButton mouseButton)
        {
            if (contains(position))
            {
                activeColor = color;
            }
        }
    }

    private class SizeText extends Text implements MouseClickListener
    {
        private final double diameter;

        public SizeText(double diameter)
        {
            super("" + diameter, 1);
            this.diameter = diameter;
            color(Color.CYAN);
        }

        @Override
        public void onMouseDown(Vector position, MouseButton mouseButton)
        {
            if (contains(position))
            {
                activeDiameter = diameter;
            }
        }
    }

    public static void main(String[] args)
    {
        Controller.start(new PaintingCirclesAdvancedDemo(), 600, 400);
    }
}
