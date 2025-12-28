/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package demos.classes.graphics.boxes;

import static pi.graphics.boxes.HAlign.CENTER;
import static pi.graphics.boxes.HAlign.LEFT;
import static pi.graphics.boxes.HAlign.RIGHT;

import java.awt.Graphics2D;
import java.util.function.Consumer;

import demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.BorderBox;
import pi.graphics.boxes.Box;
import pi.graphics.boxes.GridBox;
import pi.graphics.boxes.TextBlockBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java

public class TextBlockBoxDemo extends Graphics2DComponent
{
    String lorem = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";

    private Box text(String content, Consumer<TextBlockBox> consumer)
    {
        var text = new TextBlockBox(content);
        var b = new BorderBox(text).thickness(1);
        if (consumer != null)
        {
            consumer.accept(text);
        }
        return b;
    }

    private Box text(String content)
    {
        return text(content, null);
    }

    public void render(Graphics2D g)
    {
        new GridBox<>(// width(300)
                text("Breite: 300px: " + lorem, box -> box.width(300)),
                // width(200)
                text("Breite: 200px: " + lorem, box -> box.width(200)),
                // \n
                text("Manueller\nUmbruch\ndurch\n\\n"),
                // LEFT
                text("Linksbündig: " + lorem, box -> box.hAlign(LEFT)),
                // CENTER
                text("Zentriert: " + lorem, box -> box.hAlign(CENTER)),
                // RIGHT
                text("Rechtsbündig: " + lorem, box -> box.hAlign(RIGHT)))
                .padding(10)
                .anchor(10, 10)
                .render(g)
                .debug();
    }

    public static void main(String[] args)
    {
        new TextBlockBoxDemo().show();
    }
}
