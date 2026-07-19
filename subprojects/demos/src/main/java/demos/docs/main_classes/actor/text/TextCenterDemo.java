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

import pi.Controller;
import pi.Scene;
import pi.actor.Text;

/**
 * Demonstiert die Methode {@link pi.actor.Actor#center()} der Figur
 * <b>Text</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.52.0
 */
public class TextCenterDemo extends Scene
{
    private Text text;

    private String loadingDots = "";

    public TextCenterDemo()
    {
        info()
            .title("Demo um die center-Methode bei der Figur Text zu testen.");

        text = new Text("loading");
        add(text);
        text.center(0, 0);

        repeat(1, () -> {
            text.center(0, 0);
            text.content("loading " + loadingDots);
            loadingDots = loadingDots + ".";
        });
        backgroundColor("#666666");

    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.debug();
        Controller.start(new TextCenterDemo());
    }
}
