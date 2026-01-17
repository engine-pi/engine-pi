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
package demos.docs.main_classes.actor.text;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/main-classes/actor/text.md

import pi.Controller;
import pi.Scene;
import pi.actor.Text;

/**
 * Demonstriert <b>alle Setter</b> der Figur „Text“.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class TextAllSettersDemo extends Scene
{
    public TextAllSettersDemo()
    {
        info().title("Alle Setter der Figur „Text“")
            .description("Die Demo verwendet alle Setter der Figur „Text“.");

        Text text = new Text("Alter Inhalt").content("Alle Attribute")
            .width(15.5)
            .height(3.2)
            .font("Arial")
            .style(1)
            .color("green");
        text.center(0, 0);
        add(text);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new TextAllSettersDemo());
    }
}
