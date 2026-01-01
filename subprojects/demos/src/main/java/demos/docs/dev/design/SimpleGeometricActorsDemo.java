/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024, 2025 Josef Friedrich and contributors.
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
package demos.docs.dev.design;

import pi.Circle;
import pi.Configuration;
import pi.Rectangle;
import pi.Triangle;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/assets/docs/dev/design/SimpleGeometricActorsDemo.png

/**
 * Demonstriert die einfachen, geometrischen Figuren {@link pi.Circle Kreis},
 * {@link pi.Rectangle Rechteck} und {@link pi.Triangle Dreieck}.
 *
 * @author Josef Friedrich
 */
public class SimpleGeometricActorsDemo
{
    public static void main(String[] args)
    {
        Configuration.windowWidthPx = 700;
        Configuration.windowHeightPx = 300;
        new Circle(5).setCenter(-7, 0);
        new Rectangle(5).setCenter(0, 0);
        new Triangle(5).setCenter(7, 0);
    }
}
