/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/event/MouseWheelListenerContainer.java
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

/**
 * Eine Schnittstelle für einen Behälter, der Beobachter enthält, die auf
 * Bewegungen des Mausrads reagieren.
 */
public interface MouseWheelListenerContainer
{
    EventListeners<MouseWheelListener> getMouseWheelListeners();

    /**
     * Fügt einen Beobachter, der auf Bewegungen des Mausrads reagiert, zum
     * Behälter hinzu.
     *
     * @param mouseWheelListener Ein Beobachter, der auf Bewegungen des Mausrads
     *                           reagiert.
     */
    @API
    default void addMouseWheelListener(MouseWheelListener mouseWheelListener)
    {
        getMouseWheelListeners().add(mouseWheelListener);
    }

    /**
     * Entfernt einen Beobachter, der auf Bewegungen des Mausrads reagiert, aus
     * dem Behälter.
     *
     * @param mouseWheelListener Ein Beobachter, der auf Bewegungen des Mausrads
     *                           reagiert.
     */
    @API
    default void removeMouseWheelListener(MouseWheelListener mouseWheelListener)
    {
        getMouseWheelListeners().remove(mouseWheelListener);
    }
}
