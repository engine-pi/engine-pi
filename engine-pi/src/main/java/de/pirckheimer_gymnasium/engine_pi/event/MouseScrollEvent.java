/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/event/MouseWheelEvent.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi.event;

import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

/**
 * Beschreibt eine Bewegung des Mausrads. Wird vom {@link MouseScrollListener}
 * genutzt.
 *
 * @author Michael Andonie
 * @see MouseScrollListener
 */
@API
public class MouseScrollEvent
{
    /**
     * Die Rotation des Mausrades. Bei Mäusen mit Präzession auch in Bruchteilen
     * eines „Klicks“.
     */
    private final double wheelRotation;

    @Internal
    public MouseScrollEvent(double wheelRotation)
    {
        this.wheelRotation = wheelRotation;
    }

    /**
     * Gibt die Anzahl an „Klicks“ aus, die das Mausrad bewegt wurde.
     *
     * @return Die Anzahl an „Klicks“, die das Mausrad bewegt wurde.<br>
     *         <b>Negative Werte:</b> Das Rad wurde weg vom Benutzer gedreht.
     *         <b>Positive Werte:</b> Das Rad wurde hin zum Benutzer gedreht.
     * @see #getPreciseWheelRotation()
     */
    @API
    public int getWheelRotation()
    {
        return (int) wheelRotation;
    }

    /**
     * Gibt die Anzahl an „Klicks“ aus, die das Mausrad bewegt wurde. Wenn die
     * benutzte Maus auch Zwischenschritte erlaubt, werden auch
     * "Klick-Bruchteile" mit eingerechnet.
     *
     * @return Die Anzahl an „Klicks“, die das Mausrad bewegt wurde.<br>
     *         <b>Negative Werte:</b> Das Rad wurde weg vom Benutzer gedreht.
     *         <b>Positive Werte:</b> Das Rad wurde hin zum Benutzer gedreht.
     * @see #getWheelRotation()
     */
    @API
    public double getPreciseWheelRotation()
    {
        return wheelRotation;
    }
}
