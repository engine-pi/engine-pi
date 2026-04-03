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
    final int START = -8;

    final int END = 8;

    int yCursor = 7;

    public LineDashPatternDemo()
    {

        add(new Text(".dashed(true)").anchor(START, yCursor));
        yCursor--;
        createDashedLine(0.05);
        createDashedLine(0.1);
        createDashedLine(0.25);
        createDashedLine(0.5);
        createDashedLine(1);
        yCursor--;
        add(new Text(".dashPattern(...)").anchor(START, yCursor));
        yCursor--;

        createDashPatternLine(1);
        createDashPatternLine(0.5);
        createDashPatternLine(0.25);
        createDashPatternLine(1, 1);
        createDashPatternLine(1, 2);
        createDashPatternLine(0.5, 0.5);
        createDashPatternLine(0.5, 1);
        createDashPatternLine(0.1, 0.2, 0.3);

    }

    private void createDashedLine(double strokeWidth)
    {
        add(new Text(strokeWidth).height(0.5)
            .anchor(START - 3, yCursor - 0.25));
        add(new Line(START, yCursor, END, yCursor).strokeWidth(strokeWidth)
            .dashed(true));
        yCursor--;
    }

    private void createDashPatternLine(double... dashPattern)
    {
        add(new Text(dashPattern).height(0.5).anchor(-11, yCursor - 0.25));
        add(new Line(START, yCursor, END, yCursor).dashPattern(dashPattern));
        yCursor--;
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new LineDashPatternDemo());
    }
}
