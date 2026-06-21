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

import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.event.FrameListener;

public class TextContentUpdateDemo extends Scene implements FrameListener
{
    private Text text;

    public TextContentUpdateDemo()
    {
        info().title("Demo um Textinhalt zu setzen.")
            .description("Um einen Fehler zu beheben. Text.content");

        text = new Text("initial");
        add(text);

        // Fehler
        repeat(1, () -> text.content("Update"));
        backgroundColor("#666666");
    }

    @Override
    public void onFrame(double pastTime)
    {
        // Geht
        // text.content("Update");
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new TextContentUpdateDemo());
    }
}
