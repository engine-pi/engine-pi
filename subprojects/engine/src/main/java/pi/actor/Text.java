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
import pi.debug.ToStringFormatter;
import pi.graphics.boxes.TextLineBox;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/text.md
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/TextDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/TextRandomDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/text/TextAllSettersDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/text/TextPhysicsDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/text/TextStyleDemo.java

/**
 * Zur Darstellung von einzeiligen <b>Texten</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class Text extends TextActor<TextLineBox>
{
    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     *
     * @since 0.42.0
     */
    @API
    public Text(Object content)
    {
        super(new TextLineBox(content));
        Color color = colorScheme.get().white();
        box.color(color);
        color(color);
        update();
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("Text");
        formatter.append("content", content());
        return formatter.format();
    }
}
