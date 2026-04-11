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

import static pi.Controller.fonts;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.function.Consumer;

import demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.BorderBox;
import pi.graphics.boxes.Box;
import pi.graphics.boxes.TextLineBox;
import pi.graphics.boxes.VerticalBox;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextLineBox.java

public class TextLineBoxDemo extends Graphics2DComponent
{
    Font defaultFont = fonts.defaultFont().deriveFont(64f);

    private Box text(String content, Consumer<TextLineBox> consumer)
    {
        var text = new TextLineBox(content);
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

        // Um zu Testen, ob die Box direkt gerendert werden kann.
        new TextLineBox("as standalone box").anchor(10, 10).render(g);

        new VerticalBox<>(text("default"),
                text("different fontSize", box -> box.fontSize(24)),
                text("custom Font", box -> box.font("Monospaced")),
                text("custom color", box -> box.color("orange")),
                text("custom content", box -> box.content("updated content")))
                    .anchor(10, 100)
                    .render(g)
                    .debug();

        String content = "Lorem ipsum dolor sit esse";
        new VerticalBox<>(
                // Nur Breite
                text(content, box -> box.width(200)),
                // anderer Wert
                text(content, box -> box.width(100)),
                // Nur Höhe
                text(content, box -> box.height(50)),

                // anderer Wert
                text(content, box -> box.height(20)),
                // Höhe und Breite
                text(content, box -> box.width(200).height(50)),
                text(content, box -> box.width(50).height(50))).anchor(10, 300)
                    .render(g)
                    .debug();
    }

    public static void main(String[] args)
    {
        new TextLineBoxDemo().open();
    }
}
