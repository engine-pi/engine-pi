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

import demos.classes.actor.ActorBaseScene;
import pi.Controller;
import pi.actor.Line;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/line.md

/**
 * Demonstriert die Figur <b>Linie</b> ({@link Line}).
 *
 * @author Josef Friedrich
 */
public class LinePhysicsDemo extends ActorBaseScene
{
    public LinePhysicsDemo()
    {
        backgroundColor("blue");
        ground.color("green");
        Line line = new Line(-9, -8, -9, -4);
        line.makeDynamic()
            .applyImpulse(25, 120)
            .applyRotationImpulse(-7)
            .color("brown");
        line.end2.arrow(true);
        add(line);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new LinePhysicsDemo());
    }
}
