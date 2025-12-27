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
package pi.resources.font;

/**
 * Repräsentiert die verfügbaren Schrift<b>stile</b> bzw. Schriftschnitte.
 *
 * Verfügbare Werte:
 * <ul>
 * <li>{@link #PLAIN} — normaler Text ({@code 0})</li>
 * <li>{@link #BOLD} — fetter Text ({@code 1})</li>
 * <li>{@link #ITALIC} — kursiver Text ({@code 2})</li>
 * <li>{@link #BOLD_ITALIC} — fett und kursiv kombiniert ({@code 3})</li>
 * </ul>
 *
 * <p>
 * Dieser Aufzählungstyp dient dazu, die Schriftstile eindeutig zu benennen und
 * den zugehörigen numerischen Wert über {@link #getStyle()} bereitzustellen.
 * </p>
 *
 * @author Josef Friedrich
 */
public enum FontStyle
{
    /**
     * Der <b>normale</b> Schriftstil.
     */
    PLAIN(0),

    /**
     * Der <b>fette</b> Schriftstil.
     */
    BOLD(1),

    /**
     * Der <b>kursive</b> Schriftstil.
     */
    ITALIC(2),

    /**
     * Die Kombination aus <b>fetten</b> und <b>kursiven</b> Schriftstil.
     */
    BOLD_ITALIC(3);

    /**
     * Der aktuelle Schriftstil als Ganzzahl.
     */
    private final int style;

    /**
     * Erzeugt ein {@link FontStyle}-Objekt mit dem übergebenen Stilwert.
     *
     * @param style Eine Ganzzahl, die den Schriftstil repräsentiert.
     */
    FontStyle(int style)
    {
        this.style = style;
    }

    /**
     * Gibt den aktuellen Schriftstil als Ganzzahl zurück.
     *
     * <p>
     * Die Zuordnung ist wie folgt:
     * </p>
     *
     * <ul>
     * <li>0: {@link #PLAIN} (normal)</li>
     * <li>1: {@link #BOLD} (fett)</li>
     * <li>2: {@link #ITALIC} (kursiv)</li>
     * <li>3: {@link #BOLD_ITALIC} (fett und kursiv)</li>
     * </ul>
     *
     * @return Eine Ganzzahl, die den Schriftstil repräsentiert.
     */
    public int getStyle()
    {
        return style;
    }

    /**
     * Gibt den zu einem numerischen Stilwert passenden Aufzählungstyp
     * {@link FontStyle} zurück.
     *
     * <p>
     * Die Zuordnung ist wie folgt:
     * </p>
     *
     * <ul>
     * <li>0: {@link #PLAIN} (normal)</li>
     * <li>1: {@link #BOLD} (fett)</li>
     * <li>2: {@link #ITALIC} (kursiv)</li>
     * <li>3: {@link #BOLD_ITALIC} (fett und kursiv)</li>
     * </ul>
     *
     * @param style Der numerische Wert des Schriftstils (erwartet 0–3).
     *
     * @return Das entsprechende Wert des Aufzählungstyps {@link FontStyle}.
     *
     * @throws RuntimeException Wenn der übergebene Wert nicht einer der
     *     erwarteten Werte (0–3) ist.
     *
     * @since 0.39.0
     */
    public static FontStyle getStyle(int style)
    {
        switch (style)
        {
        case 0:
            return PLAIN;

        case 1:
            return BOLD;

        case 2:
            return ITALIC;

        case 3:
            return BOLD_ITALIC;

        default:
            throw new RuntimeException(
                    "Unbekannter numerischer Wert für einen Schriftstil: "
                            + style
                            + " Mögliche Werte sind (0: normal, 1: fett, 2: kursiv, 3: fett und kursiv).");
        }

    }
}
