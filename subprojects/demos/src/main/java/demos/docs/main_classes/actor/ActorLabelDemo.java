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
package demos.docs.main_classes.actor;

import static pi.Controller.config;

import pi.Controller;
import pi.Scene;
import pi.actor.Actor.Label;
import pi.actor.ActorCreator;
import pi.actor.Rectangle;

/**
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class ActorLabelDemo extends Scene
{

    Label label;

    public ActorLabelDemo()
    {
        info("label (Beschriftung)");
        ActorCreator.createCage(this);

        label = new Label("Das ist eine Beschriftung", "(label)");

        add(new Rectangle(4, 2).makeDynamic()
            .applyForce(80000, 110000)
            .restitution(1)
            .rotateBy(42)
            .label(label));
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        config.debug.enabled(true).renderAABBs(true);
        Controller.start(new ActorLabelDemo());
    }
}
