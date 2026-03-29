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
package demos.docs.main_classes.actor.text;

import java.awt.Font;

import pi.Controller;
import pi.Scene;
import pi.actor.Rectangle;
import pi.actor.Text;
import pi.graphics.geom.Direction;
import pi.graphics.geom.Vector;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/text.md

/**
 * Demonstiert, wie sich die Figur <b>Text</b> in einer Physik-Simulation
 * verhält.
 *
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class TextPhysicsDemo extends Scene
{
    public TextPhysicsDemo()
    {
        backgroundColor("blue");
        gravityOfEarth();

        add(new Text("Text").font(new Font(Font.SERIF, 1, 10))
            .height(6)
            .density(1)
            .restitution(0.95)
            .center(9, 7)
            .rotateBy(60)
            .makeDynamic()
            .applyImpulse(new Vector(-100, 0)));

        add(new Rectangle(15, 1).center(0, -7).makeStatic());
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new TextPhysicsDemo());
        Controller.windowPosition(Direction.UP_RIGHT);
    }
}
