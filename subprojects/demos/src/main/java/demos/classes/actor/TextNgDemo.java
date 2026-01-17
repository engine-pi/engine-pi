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
import pi.Scene;
import pi.actor.TextNg;

/**
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class TextNgDemo extends Scene
{

    public TextNgDemo()
    {
        backgroundColor("green");
        add(new TextNg("Das ist ein Test").width(10).height(5));
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new TextNgDemo());
    }
}
