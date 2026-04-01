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
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.graphics.boxes.TextBlockBox;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/TextBlockDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/text_block/TextBlockLineSpacingDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/text_block/TextBlockPhysicsDemo.java

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

    /* lineSpacing */

    /**
     * Gibt den <b>Zeilenabstand</b>.
     *
     * <p>
     * Der Zeilenabstand ist ein Faktor, der den Abstand zwischen den Zeilen
     * relativ zum Standardabstand bestimmt. Ein Wert von {@code 1} bedeutet den
     * normalen Zeilenabstand, Werte größer als {@code 1} erhöhen den Abstand,
     * während Werte zwischen {@code 0} und {@code 1} den Abstand verringern.
     * Zum Beispiel würde ein Wert von {@code 1.5} den Zeilenabstand um
     * {@code 50%} erhöhen, während ein Wert von {@code 0.75} den Zeilenabstand
     * um {@code 25%} verringern würde.
     * </p>
     *
     * @return Der <b>Zeilenabstand</b>.
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public double lineSpacing()
    {
        return box.lineSpacing();
    }

    /**
     * Setzt den <b>Zeilenabstand</b>.
     *
     * <p>
     * Der Zeilenabstand ist ein Faktor, der den Abstand zwischen den Zeilen
     * relativ zum Standardabstand bestimmt. Ein Wert von {@code 1} bedeutet den
     * normalen Zeilenabstand, Werte größer als {@code 1} erhöhen den Abstand,
     * während Werte zwischen {@code 0} und {@code 1} den Abstand verringern.
     * Zum Beispiel würde ein Wert von {@code 1.5} den Zeilenabstand um
     * {@code 50%} erhöhen, während ein Wert von {@code 0.75} den Zeilenabstand
     * um {@code 25%} verringern würde.
     * </p>
     *
     * @param lineSpacing Der <b>Zeilenabstand</b>.
     *
     * @return Dieses Objekt für Methodenverkettung.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public TextBlock lineSpacing(double lineSpacing)
    {
        box.lineSpacing(lineSpacing);
        update();
        return this;
    }

    /* lines */

    /**
     * Gibt den <b>Textinhalt</b> der einzelnen Zeilen zurück.
     *
     * @return Jede Zeile ist ein Element des Arrays.
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public String[] lines()
    {
        return box.linesText();
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        return super.toStringFormatter().className(this).format();
    }
}
