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
package demos.classes.actor;

import static pi.Controller.colors;

import pi.Controller;
import pi.Random;
import pi.Scene;
import pi.actor.Text;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/TextNg.java

/**
 * @author Josef Friedrich
 */
public class TextRandomDemo extends Scene
{
    public TextRandomDemo()
    {
        createRandomText();

        repeat(2, () -> {
            createRandomText();
        });
        backgroundColor(colors.getSafe("#333333"));
    }

    private void createRandomText()
    {
        clear();
        for (int i = 0; i < Random.range(10, 100); i++)
        {
            String font = Random.systemFont();
            Text text = new Text(font);

            text.height(Random.range(0.2, 3.0))
                .font(font)
                .style(Random.fontStyleAsEnum())
                .color(colors.random())
                .anchor(Random.vector(this))
                .rotation(Random.range(0, 360));
            add(text);
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new TextRandomDemo());
    }
}
