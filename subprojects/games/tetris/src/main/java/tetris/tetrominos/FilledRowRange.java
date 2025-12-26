/*
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
package tetris.tetrominos;

/**
 * Diese Klasse speichert, welche Zeilen vollständig sind und daher getilgt
 * werden können.
 *
 * <p>
 * Im Attribut {@link #from} steht der <b>kleinere</b> Wert. Ist nur eine Zeile
 * ausgefüllt, so steht in {@link #from} und {@link #to} der gleiche Wert.
 * </p>
 *
 * @author Josef Friedrich
 */
public class FilledRowRange
{
    /**
     * Ab welcher y-Koordinate (einschließlich) der Bereich mit ausgefüllten
     * Zeilen reicht.
     */
    private final int from;

    /**
     * Bis zu welcher y-Koordinate (einschließlich) der Bereich mit ausgefüllten
     * Zeilen reicht.
     */
    private final int to;

    /**
     * @param from Ab welcher y-Koordinate (einschließlich) der Bereich mit
     *     ausgefüllten Zeilen reicht.
     * @param to Bis zu welcher y-Koordinate (einschließlich) der Bereich mit
     *     ausgefüllten Zeilen reicht.
     */
    public FilledRowRange(int from, int to)
    {
        this.from = from;
        this.to = to;
    }

    /**
     * Gibt zurück, <b>ab</b> welcher y-Koordinate (einschließlich) der Bereich
     * mit ausgefüllten Zeilen reicht.
     *
     * @return Ab welcher y-Koordinate (einschließlich) der Bereich mit
     *     ausgefüllten Zeilen reicht.
     */
    public int getFrom()
    {
        return from;
    }

    /**
     * Gibt zurück, <b>bis</b> zu welcher y-Koordinate (einschließlich) der
     * Bereich mit ausgefüllten Zeilen reicht.
     *
     * @return Bis zu welcher y-Koordinate (einschließlich) der Bereich mit
     *     ausgefüllten Zeilen reicht.
     */
    public int getTo()
    {
        return to;
    }

    /**
     * Gibt die Anzahl zurück, wie viele Zeilen abgebaut wurden.
     *
     * @return Die Anzahl an Zeilen, die abgebaut wurden.
     */
    public int getRowCount()
    {
        int result = to - from + 1;
        assert result > 0 && result < 5;
        return result;
    }
}
