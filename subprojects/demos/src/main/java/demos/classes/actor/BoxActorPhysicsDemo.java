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
package demos.classes.actor;

import pi.Controller;
import pi.Rectangle;
import pi.Scene;
import pi.actor.BoxActor;
import pi.graphics.boxes.FramedTextBox;
import pi.graphics.boxes.HorizontalBox;
import pi.graphics.geom.Vector;

/**
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class BoxActorPhysicsDemo extends Scene
{
    public BoxActorPhysicsDemo()
    {
        backgroundColor("white");
        gravityOfEarth();

        BoxActor<HorizontalBox<FramedTextBox>> actor = new BoxActor<>(
                new HorizontalBox<>(
                        new FramedTextBox("Box 1").border.color("blue"),
                        new FramedTextBox("Box 2").border.color("red")));

        actor.size(8, 5)
            .applyImpulse(new Vector(10, 0))
            .makeDynamic()
            .center(5, 10);
        add(actor);

        add(new Rectangle(15, 1).color("magenta").center(0, -7).makeStatic());

    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new BoxActorPhysicsDemo());
    }
}
