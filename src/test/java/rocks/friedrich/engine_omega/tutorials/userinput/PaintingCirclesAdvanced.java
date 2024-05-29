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
package rocks.friedrich.engine_omega.tutorials.userinput;

import java.awt.Color;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.actor.Text;
import rocks.friedrich.engine_omega.event.MouseButton;
import rocks.friedrich.engine_omega.event.MouseClickListener;
import rocks.friedrich.engine_omega.tutorials.util.Util;

public class PaintingCirclesAdvanced extends Scene implements MouseClickListener
{
    static Color activeColor = Color.WHITE;

    static double activeDiameter = 1;

    public PaintingCirclesAdvanced()
    {
        this.addMouseClickListener(this);
        ColorRect white = new ColorRect(Color.WHITE);
        ColorRect blue = new ColorRect(Color.BLUE);
        ColorRect pink = new ColorRect(Color.PINK);
        ColorRect orange = new ColorRect(Color.ORANGE);
        white.setPosition(-8, 5);
        blue.setPosition(-6, 5);
        pink.setPosition(-4, 5);
        orange.setPosition(-2, 5);
        add(white, blue, pink, orange);
        SizeText sizeText1 = new SizeText(1);
        SizeText sizeText2 = new SizeText(2);
        SizeText sizeText3 = new SizeText(3);
        sizeText1.setPosition(0, 5);
        sizeText2.setPosition(2, 5);
        sizeText3.setPosition(4, 5);
        add(sizeText1, sizeText2, sizeText3);
    }

    private void paintCircleAt(double mX, double mY, double diameter,
            Color color)
    {
        Circle circle = new Circle(diameter);
        circle.setCenter(mX, mY);
        circle.setColor(color);
        this.add(circle);
    }

    @Override
    public void onMouseDown(Vector position, MouseButton mouseButton)
    {
        if (position.getY() < 5)
        {
            paintCircleAt(position.getX(), position.getY(), activeDiameter,
                    activeColor);
        }
    }

    private class ColorRect extends Rectangle implements MouseClickListener
    {
        private final Color color;

        public ColorRect(Color color)
        {
            super(1, 1);
            this.color = color;
            this.setColor(color);
        }

        @Override
        public void onMouseDown(Vector position, MouseButton mouseButton)
        {
            if (this.contains(position))
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
            this.setColor(Color.CYAN);
        }

        @Override
        public void onMouseDown(Vector position, MouseButton mouseButton)
        {
            if (this.contains(position))
            {
                activeDiameter = diameter;
            }
        }
    }

    public static void main(String[] args)
    {
        Game.start(600, 400, new PaintingCirclesAdvanced());
        // Game.setDebug(true);
        Util.addScreenshotKey("User Input Circles Advanced");
    }
}
