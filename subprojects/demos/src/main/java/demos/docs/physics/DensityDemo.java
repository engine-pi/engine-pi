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
package demos.docs.physics;

import java.awt.event.KeyEvent;

import pi.Circle;
import pi.Controller;
import pi.Rectangle;
import pi.Scene;
import pi.Text;
import pi.event.KeyStrokeListener;

/**
 * Demonstriert die Methode {@link pi.actor.Actor#density(double)}
 */
public class DensityDemo extends Scene implements KeyStrokeListener
{
    private final Rectangle ground;

    private final Circle[] circles;

    private final Text[] densityLables;

    public DensityDemo()
    {
        info("Dichte-Demo")
                .subtitle("Demonstierte die Methode Actor#density(double)")
                .description(
                        "Drei Kreise mit unterschiedlicher Dichte fallen im selben Tempo zu Boden. "
                                + "Über die Leertaste kann ein nach oben wirkender Impuls auf die Kreise angewandt werden. "
                                + "Der linke Kreis mit der geringsten Dichte wird von dem Impuls am weitesten nach oben geschleudert.");
        circles = new Circle[3];
        densityLables = new Text[3];
        int density = 10;
        int x = -5;
        for (int i = 0; i < 3; i++)
        {
            circles[i] = createCircleWithDensity(x, density);
            densityLables[i] = createDensityLables(x, density);
            x += 5;
            density += 10;
        }
        gravity(0, -9.81);
        ground = new Rectangle(20, 1);
        ground.position(-10, -5);
        ground.makeStatic();
        add(ground);
    }

    private Circle createCircleWithDensity(double x, double density)
    {
        Circle circle = new Circle(1);
        circle.position(x, 5);
        circle.density(density);
        circle.makeDynamic();
        add(circle);
        return circle;
    }

    private Text createDensityLables(int x, int density)
    {
        Text text = new Text(density + "", 1);
        text.position(x, -7);
        text.makeStatic();
        add(text);
        return text;
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            for (Circle circle : circles)
            {
                circle.applyImpulse(0, 100);
            }
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new DensityDemo());
    }
}
