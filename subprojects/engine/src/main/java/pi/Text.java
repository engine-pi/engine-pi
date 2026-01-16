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
package pi;

import java.awt.Font;

import pi.annotations.API;

/**
 * Zur Darstellung von <b>Texten</b>.
 *
 * <p class="development-note">
 * Diese Klasse ist identisch mit {@link pi.actor.Text}. Sie steht hier, damit
 * sie über das Hauptpaket importiert werden kann, also {@code import pi.Text;}
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class Text extends pi.actor.Text
{
    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b> in <b>normaler,
     * serifenfreier Standardschrift</b> mit <b>einem Meter Höhe</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     *
     * @since 0.27.0
     */
    @API
    public Text(Object content)
    {
        super(content, 1);
    }

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b> und <b>Höhe</b>
     * in <b>normaler, serifenfreier Standardschrift</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     * @param height Die <b>Höhe</b> des Textes in Meter.
     */
    @API
    public Text(Object content, double height)
    {
        super(content, height);
    }

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b>, <b>Höhe</b>
     * und <b>Schriftart</b> in <b>nicht fettem und nicht kursiven
     * Schriftstil</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     * @param height Die <b>Höhe</b> des Textes in Meter.
     * @param fontName Der <b>Name</b> der Schriftart, falls es sich um eine
     *     Systemschriftart handelt, oder der <b>Pfad</b> zu einer Schriftdatei.
     */
    @API
    public Text(Object content, double height, String fontName)
    {
        this(content, height, fontName, 0);
    }

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b>, <b>Höhe</b>,
     * <b>Schriftart</b> und <b>Schriftstil</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     * @param height Die <b>Höhe</b> des Textes in Meter.
     * @param fontName Der <b>Name</b> der Schriftart, falls es sich um eine
     *     Systemschriftart handelt, oder der <b>Pfad</b> zu einer Schriftdatei.
     * @param style Der Stil der Schriftart (<b>fett, kursiv, oder fett und
     *     kursiv</b>).
     *     <ul>
     *     <li>{@code 0}: Normaler Text</li>
     *     <li>{@code 1}: Fett</li>
     *     <li>{@code 2}: Kursiv</li>
     *     <li>{@code 3}: Fett und Kursiv</li>
     *     </ul>
     */
    @API
    public Text(Object content, double height, String fontName, int style)
    {
        super(content, height, fontName, style);
    }

    public Text(Object content, double height, Font font, int style)
    {
        super(content, height, font, style);
    }

    public static void main(String[] args)
    {
        new Text("Text");
    }
}
