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
package demos.docs.main_classes.actor.text_block;

import pi.Controller;
import pi.Scene;
import pi.actor.TextBlock;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/text-block.md

/**
 * Demonstiert, wie sich die Figur {@link TextBlock} in einer Physik-Simulation
 * verhält.
 *
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class TextBlockLineSpacingDemo extends Scene
{
    public TextBlockLineSpacingDemo()
    {

        info().title("TextBlock: Zeilenabstand")
            .description(
                "Der Zeilenabstand eines TextBlocks kann mit der lineSpacing-Methode eingestellt werden.");
        backgroundColor("purple");

        double[] lineSpacings = { 0.5, 1, 1.5, 2 };
        for (int i = 0; i < lineSpacings.length; i++)
        {
            double lineSpacing = lineSpacings[i];
            add(new TextBlock("Zeile 1\nZeile 2\nZeile 3")
                .lineSpacing(lineSpacing)
                .width(3)
                .center(-9 + i * 6, 0));
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new TextBlockLineSpacingDemo(), 800, 300);
    }
}
