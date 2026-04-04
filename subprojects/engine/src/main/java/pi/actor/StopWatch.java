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

import java.awt.Font;

import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
import pi.event.FrameUpdateListener;
import pi.util.TimeUtil;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/stop-watch.md

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/StopWatchDemo.java

/**
 * Eine <b>Stoppuhr</b>, die die verstrichene Zeit anzeigt und verwaltet.
 *
 * <p>
 * Die StopWatch erweitert die {@link Text}-Klasse und implementiert
 * {@link FrameUpdateListener}, um die Anzeige bei jedem Einzelbild zu
 * aktualisieren. Sie unterstützt das Starten, Stoppen, Pausieren und
 * Zurücksetzen der Zeit. Außerdem kann das das Zeitformat im
 * {@link java.util.Formatter printf}-Stil gesetzt werden.
 * </p>
 *
 * <p>
 * Beispiel:
 * </p>
 *
 * <pre>
 * {@code
 * StopWatch watch = new StopWatch();
 * watch.format("%02d:%02d:%02d.%03d"); // Setzt das Zeitformat
 * watch.start(); // Zeitmessung beginnt
 * watch.stop(); // Zeitmessung pausiert
 * watch.reset(); // Zurücksetzen auf 0
 * }
 * </pre>
 *
 * @since 0.45.0
 */
public class StopWatch extends Text implements FrameUpdateListener
{
    private long startTimestamp;

    public StopWatch()
    {
        super("");
        startTimestamp = 0;
        time = 0;
        isRunning = false;
        contentToZeroMilliSeconds();
        font(new Font(Font.MONOSPACED, Font.PLAIN, 1000));
        update();
    }

    /* time */

    /**
     * Das <b>Zeitintervall</b> in Millisekunden.
     */
    private long time;

    /**
     * Gibt das <b>Zeitintervall</b> in Millisekunden zurück.
     *
     * @return Das <b>Zeitintervall</b> in Millisekunden.
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public long time()
    {
        if (isRunning)
        {
            return System.currentTimeMillis() - startTimestamp;
        }
        else
        {
            return time;
        }
    }

    /**
     * Setzt das <b>Zeitintervall</b> in Millisekunden.
     *
     * @param time Das <b>Zeitintervall</b> in Millisekunden.
     *
     * @return Eine Referenz auf die eigene Instanz der Stoppuhr, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Stoppuhr durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code watch.format(..).time(..).start()}.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public StopWatch time(long time)
    {
        if (!isRunning)
        {
            this.time = time;
        }
        else
        {
            startTimestamp = System.currentTimeMillis() - time;
        }
        return this;
    }

    /* format */

    /**
     * Das <b>Zeitformat</b> im {@link java.util.Formatter printf}-Stil,
     * beispielsweise {@code %02d:%02d:%02d.%03d}.
     *
     * @since 0.45.0
     */
    private String format = "%02d:%02d:%02d.%03d";

    /**
     * Gibt das <b>Zeitformat</b> im {@link java.util.Formatter printf}-Stil
     * zurück.
     *
     * @return Das <b>Zeitformat</b> im {@link java.util.Formatter printf}-Stil,
     *     beispielsweise {@code %02d:%02d:%02d.%03d}.
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public String format()
    {
        return format;
    }

    /**
     * Setzt das <b>Zeitformat</b> im {@link java.util.Formatter printf}-Stil
     * für die Stoppuhr.
     *
     * @param format Das <b>Zeitformat</b> im {@link java.util.Formatter
     *     printf}-Stil, beispielsweise {@code %02d:%02d:%02d.%03d}.
     *
     * @return Eine Referenz auf die eigene Instanz der Stoppuhr, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Stoppuhr durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code watch.format(..).time(..).start()}.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public StopWatch format(String format)
    {
        this.format = format;
        return this;
    }

    /* isRunning */

    private boolean isRunning;

    /**
     * Prüft, ob die Stoppuhr gerade läuft.
     *
     * @return true, wenn die Stoppuhr läuft, false sonst
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public boolean isRunning()
    {
        return isRunning;
    }

    /**
     * Startet die Stoppuhr, falls sie nicht bereits läuft.
     *
     * Wenn die Stoppuhr nicht aktiv ist, wird die Startzeit gesetzt und die
     * Stoppuhr in den laufenden Zustand versetzt. Bei erneuter Aktivierung nach
     * einer Pause wird die pausierte Zeit berücksichtigt.
     *
     * @return Eine Referenz auf die eigene Instanz der Stoppuhr, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Stoppuhr durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code watch.format(..).time(..).start()}.
     *
     * @since 0.45.0
     */
    @API
    @ChainableMethod
    public StopWatch start()
    {
        if (!isRunning)
        {
            startTimestamp = System.currentTimeMillis() - time;
            isRunning = true;
        }
        return this;
    }

    /**
     * Stoppt die Stoppuhr, falls sie läuft.
     *
     * Wenn die Stoppuhr aktiv ist, wird die verstrichene Zeit berechnet und
     * gespeichert. Der Laufstatus wird auf inaktiv gesetzt.
     *
     * @return Eine Referenz auf die eigene Instanz der Stoppuhr, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Stoppuhr durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code watch.format(..).time(..).start()}.
     *
     * @since 0.45.0
     */
    @API
    @ChainableMethod
    public StopWatch stop()
    {
        if (isRunning)
        {
            time = System.currentTimeMillis() - startTimestamp;
            isRunning = false;
        }
        return this;
    }

    /**
     * Schaltet die Stoppuhr um zwischen Laufen und Stoppen.
     *
     * Wenn die Stoppuhr läuft, wird sie gestoppt. Wenn die Stoppuhr gestoppt
     * ist, wird sie gestartet.
     *
     * @return Eine Referenz auf die eigene Instanz der Stoppuhr, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Stoppuhr durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code watch.format(..).time(..).start()}.
     *
     * @since 0.45.0
     */
    @API
    @ChainableMethod
    public StopWatch toggle()
    {
        if (isRunning)
        {
            stop();
        }
        else
        {
            start();
        }
        return this;
    }

    /**
     * Setzt die Stoppuhr auf ihren Initialwert zurück.
     *
     * <p>
     * Diese Methode setzt alle internen Zustände der Stoppuhr zurück:
     * </p>
     *
     * <ul>
     * <li>Startzeit wird auf 0 gesetzt</li>
     * <li>Pausierte Zeit wird auf 0 gesetzt</li>
     * <li>Laufstatus wird auf inaktiv gesetzt</li>
     * <li>Millisekunden werden auf null zurückgesetzt</li>
     * </ul>
     *
     * @return Eine Referenz auf die eigene Instanz der Stoppuhr, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Stoppuhr durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code watch.format(..).time(..).start()}.
     *
     * @since 0.45.0
     */
    @API
    @ChainableMethod
    public StopWatch reset()
    {
        startTimestamp = 0;
        time = 0;
        isRunning = false;
        contentToZeroMilliSeconds();
        return this;
    }

    /**
     * <b>Setzt</b> die Anzeige der Stoppuhr auf null Millisekunden
     * <b>zurück</b>.
     *
     * <p>
     * Diese Methode formatiert die Zeit 0 und aktualisiert den Inhalt der
     * Stoppuhr entsprechend.
     * </p>
     *
     * @since 0.45.0
     */
    private void contentToZeroMilliSeconds()
    {
        content(formatTime(0));
    }

    /**
     * Formatiert die gegebene Zeit in Millisekunden gemäß dem definierten
     * Format.
     *
     * @param milliseconds Die Zeit in Millisekunden, die formatiert werden
     *     soll.
     *
     * @return Die formatierte Zeitzeichenkette.
     *
     * @since 0.45.0
     */
    private String formatTime(long milliseconds)
    {
        return TimeUtil.formatInterval(milliseconds, format);
    }

    /**
     * @hidden
     */
    @Override
    public void onFrameUpdate(double pastTime)
    {
        content(formatTime(time()));
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("StopWatch");
        formatter.append("time", time());
        if (!format.equals("%02d:%02d:%02d.%03d"))
        {
            formatter.append("format", format);
        }
        return formatter.format();
    }
}
