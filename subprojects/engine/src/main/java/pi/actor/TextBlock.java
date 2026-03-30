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
package pi.actor;

import static pi.Controller.colorScheme;

import java.awt.Color;

import pi.annotations.API;
import pi.graphics.boxes.TextBlockBox;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/TextBlockDemo.java

/**
 * Ein mehrzeiliger Textblock.
 */
public class TextBlock extends TextActor<TextBlockBox>
{
    /**
     * Erstellt einen mehrzeiligen <b>Textblock</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     */
    @API
    public TextBlock(Object content)
    {
        super(new TextBlockBox(content));
        Color color = colorScheme.get().white();
        box.color(color);
        color(color);
        update();
    }
}
