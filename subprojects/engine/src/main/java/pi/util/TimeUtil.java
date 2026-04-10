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
package pi.util;

import java.util.concurrent.TimeUnit;

/**
 * Eine Sammlung von statischen Hilfsmethoden für <b>zeitbezogene</b>
 * Operationen.
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class TimeUtil
{
    /**
     * Ein privater Konstruktur, um den öffentlichen Konstruktur zu verbergen.
     * Dadurch können von dieser Klasse keine Instanzen erstellt werden.
     */
    private TimeUtil()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * <b>Pausiert</b> den aktuellen Thread für die angegebene Anzahl von
     * Millisekunden.
     *
     * @param milliSeconds Die Dauer der Pause in <b>Millisekunden</b>.
     *
     * @throws RuntimeException wenn der Thread während des Schlafens
     *     unterbrochen wird.
     */
    public static void sleep(int milliSeconds)
    {
        if (milliSeconds < 1)
        {
            return;
        }
        try
        {
            TimeUnit.MILLISECONDS.sleep(milliSeconds);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * <b>Formatiert</b> ein <b>Zeitintervall</b> in Millisekunden in ein
     * lesbares Zeitformat.
     *
     * <p>
     * Das Intervall wird in Stunden, Minuten, Sekunden und Millisekunden
     * aufgeteilt und gemäß dem angegebenen Format-Zeichenkette formatiert.
     * </p>
     *
     * @param intervalMilliseconds Die Zeitdauer in Millisekunden.
     * @param format Eine Format-Zeichenkette mit vier Platzhaltern für Stunden
     *     ({@code %d}), Minuten ({@code %d}), Sekunden ({@code %d}) und
     *     Millisekunden ({@code %d}). Der Aufbau dieser Zeichenkette kann der
     *     Dokumentation der Java-Klasse {@link java.util.Formatter Formatter}
     *     entnommen werden. Beispiel: {@code %02d:%02d:%02d.%03d} für
     *     {@code HH:MM:SS.mmm}
     *
     * @return Eine formatierte Zeichenkette, der das Zeitintervall darstellt.
     *
     * @since 0.45.0
     */
    public static String formatInterval(final long intervalMilliseconds,
            String format)
    {
        final long hr = TimeUnit.MILLISECONDS.toHours(intervalMilliseconds);
        final long min = TimeUnit.MILLISECONDS
            .toMinutes(intervalMilliseconds - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(intervalMilliseconds
                - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        final long ms = TimeUnit.MILLISECONDS.toMillis(intervalMilliseconds
                - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min)
                - TimeUnit.SECONDS.toMillis(sec));
        return String.format(format, hr, min, sec, ms);
    }

    /**
     * <b>Formatiert</b> ein <b>Zeitintervall</b> in Millisekunden in ein
     * lesbares Zeitformat.
     *
     * Das Intervall wird im Format "HH:MM:SS.mmm" dargestellt, wobei:
     *
     * <ul>
     * <li>HH die Stunden (zweistellig mit führender Null)</li>
     * <li>MM die Minuten (zweistellig mit führender Null)</li>
     * <li>SS die Sekunden (zweistellig mit führender Null)</li>
     * <li>mmm die Millisekunden (dreistellig mit führenden Nullen)</li>
     * </ul>
     *
     * @param intervalMilliseconds Die Zeitdauer in Millisekunden.
     *
     * @return Eine formatierte Zeichenkette des Zeitintervalls im Format
     *     {@code %02d:%02d:%02d.%03d}
     *
     * @since 0.45.0
     */
    public static String formatInterval(final long intervalMilliseconds)
    {
        return formatInterval(intervalMilliseconds, "%02d:%02d:%02d.%03d");
    }
}
