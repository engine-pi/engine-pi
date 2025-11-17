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
package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.util.function.Supplier;

import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

/**
 * Hilfsklasse, um eine <b>Reihe</b> von <b>Turtle-Grafik</b> zu zeichnen.
 *
 * <p>
 * Turle-Grafiken werden oft rekursiv erstellt. Die Klasse ermöglicht es, ein
 * und denselben Algorithmus mehrmals zeichnen zu lassten und bei jeder
 * Wiederholung Attribute zum verändern, wie zum Beispiel die Rekursionstiefe.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public abstract class TurtleGraphicsSeries extends TurtleGraphics
{

    /**
     * @since 0.40.0
     */
    protected Supplier<Boolean> beforeRepeat;

    /**
     * @since 0.40.0
     */
    protected Supplier<Boolean> afterRepeat;

    /**
     * Gibt an wie of eine Turtle-Grafik gezeichnet werden soll. {@code -1}
     * zeichnet die Grafik in einer endlos Schleife.
     *
     * @since 0.40.0
     */
    protected int numberOfSeries = -1;

    /**
     * @since 0.40.0
     */
    public TurtleGraphicsSeries onRepeat(Supplier<Boolean> before,
            Supplier<Boolean> after)
    {
        this.beforeRepeat = before;
        this.afterRepeat = after;
        return this;
    }

    /**
     * Wird ganz am Anfang der Grafik-Reihe ausgeführt.
     *
     * @since 0.40.0
     */
    protected void onSeriesStart()
    {

    }

    /**
     * Wird bevor jedem Zeichenvorgang ausgeführt.
     *
     * @since 0.40.0
     */
    protected void onDrawStart()
    {

    }

    /**
     * Wird nach jedem Zeichenvorgang ausgeführt.
     *
     * @since 0.40.0
     */
    protected void onDrawEnd()
    {

    }

    /**
     * Wird ganz am Ende der Grafik-Reihe ausgeführt.
     *
     * @since 0.40.0
     */
    protected void onSeriesEnd()
    {

    }

    /**
     * @since 0.40.0
     */
    @Internal
    @Override
    public void run()
    {
        onSeriesStart();
        int counter = 0;
        this.onRepeat(afterRepeat);
        // -1 zeichnet die Grafik unendlich oft
        while (numberOfSeries == -1 || counter < numberOfSeries)
        {
            if (beforeRepeat != null && !beforeRepeat.get())
            {
                break;
            }
            counter++;
            onDrawStart();
            draw();
            onDrawEnd();
            if (afterRepeat != null && !afterRepeat.get())
            {
                break;
            }
        }
        onSeriesEnd();
    }

}
