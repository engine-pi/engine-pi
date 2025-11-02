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
package de.pirckheimer_gymnasium.engine_pi.util;

import java.util.concurrent.TimeUnit;

/**
 * Bietet Hilfsmethoden für <b>zeitbezogene</b> Operationen an.
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class TimeUtil
{
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
}
