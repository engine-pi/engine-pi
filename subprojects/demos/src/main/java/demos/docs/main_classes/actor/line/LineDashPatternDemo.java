/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package demos.docs.main_classes.actor.line;

import pi.Controller;
import pi.Scene;
import pi.actor.Line;
import pi.actor.Text;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/line.md

/**
 * Demonstriert verschiedene Strichmuster der Figur ({@link Line}).
 *
 * @author Josef Friedrich
 */
public class LineDashPatternDemo extends Scene
{
    int yCursor = 8;

    public LineDashPatternDemo()
    {
        createDashedLine(1);
        createDashedLine(0.5);
        createDashedLine(0.25);
        createDashedLine(1, 1);
        createDashedLine(1, 2);
        createDashedLine(0.5, 0.5);
        createDashedLine(0.5, 1);
        createDashedLine(0.1, 0.2, 0.3);
    }

    private void createDashedLine(double... dashPattern)
    {
        add(new Text(dashPattern).height(0.5).anchor(-11, yCursor - 0.25));
        add(new Line(-8, yCursor, 8, yCursor).dashPattern(dashPattern));
        yCursor--;
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new LineDashPatternDemo());
    }
}
