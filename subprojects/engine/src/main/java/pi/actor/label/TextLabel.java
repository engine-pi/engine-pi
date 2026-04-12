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
package pi.actor.label;

import java.awt.Color;

import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.graphics.boxes.TextBlockBox;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/ActorLabelDemo.java

/**
 * Eine <b>Beschriftung</b> für eine Figur in Form eines <b>Textes</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class TextLabel extends BoxLabel<TextBlockBox>
{
    /**
     * Erzeugt eine mehrzeilige <b>Beschriftung</b>.
     *
     * @param content Der <b>Inhalt</b> der Beschriftung. Es können mehrere
     *     Eingabewerte angegeben werden. Jeder Eingabewert wird in eine eigene
     *     Zeile gesetzt.
     *
     * @since 0.45.0
     */
    public TextLabel(Object... content)
    {
        box = new TextBlockBox(content).hAlign(hAlign);
        box.measure();
    }
    /* content */

    /**
     * Gibt den <b>Inhalt</b> der Beschriftung als Zeichenkette zurück.
     *
     * @return Der <b>Inhalt</b> der Beschriftung als Zeichenkette.
     *
     * @since 0.46.0
     *
     * @see TextBlockBox#content()
     */
    @API
    @Getter
    public String content()
    {
        return box.content();
    }

    /**
     * Setzt den <b>Inhalt</b> der Beschriftung.
     *
     * @param content Der <b>Inhalt</b> der Beschriftung. Es können mehrere
     *     Eingabewerte angegeben werden. Jeder Eingabewert wird in eine eigene
     *     Zeile gesetzt.
     *
     * @return Eine Referenz auf die eigene Instanz der Beschriftung, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der Beschriftung
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code label.content(..).font(..)}.
     *
     * @see TextBlockBox#content(Object...)
     *
     * @since 0.46.0
     */
    @API
    @Setter
    @ChainableMethod
    public TextLabel content(Object... content)
    {
        box.content(content);
        return this;
    }

    /* color */

    /**
     * Gibt die <b>Farbe</b> des Textes zurück.
     *
     * @return Die <b>Farbe</b> des Textes.
     *
     * @see TextBlockBox#color()
     *
     * @since 0.46.0
     */
    @API
    @Getter
    public Color color()
    {
        return box.color();
    }

    /**
     * Setzt die <b>Farbe</b> der Beschriftung als {@link Color}-Objekt.
     *
     * @param color Die <b>Farbe</b> der Beschriftung
     *
     * @return Eine Referenz auf die eigene Instanz der Beschriftung, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der Beschriftung
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code label.content(..).font(..)}.
     *
     * @since 0.46.0
     *
     * @see TextBlockBox#color(Color)
     */
    @API
    @Setter
    @ChainableMethod
    public TextLabel color(Color color)
    {
        box.color(color);
        return this;
    }

    /**
     * Setzt die <b>Farbe</b> der Beschriftung als Zeichenkette.
     *
     * @param color Ein Farbname ({@link pi.resources.color.ColorContainer siehe
     *     Auflistung}) oder eine Farbe in hexadezimaler Codierung (z.B.
     *     {@code #ff0000}).
     *
     * @return Eine Referenz auf die eigene Instanz der Beschriftung, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der Beschriftung
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code label.content(..).font(..)}.
     *
     * @since 0.46.0
     */
    @API
    @Setter
    @ChainableMethod
    public TextLabel color(String color)
    {
        box.color(color);
        return this;
    }
}
