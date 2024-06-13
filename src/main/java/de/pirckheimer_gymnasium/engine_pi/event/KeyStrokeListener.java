/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/event/KeyListener.java
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

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.annotations.API;

/**
 * Diese Schnittstelle wird implementiert, um auf gedrückte Tasten reagieren zu
 * können.
 *
 * <p>
 * Diese Schnittstelle heißt nicht {@code KeyListener}, da es eine
 * {@code KeyListener}-Klasse bereits im Java-JDK
 * ({@link java.awt.event.KeyListener}) gibt.
 * </p>
 *
 * @author Niklas Keller
 */
@API
public interface KeyStrokeListener
{
    /**
     * Wird bei einem angemeldeten Listener aufgerufen, sobald eine Taste
     * gedrückt wird. Die Methode wird erst wieder aufgerufen, wenn die Key
     * losgelassen und erneut gedrückt wurde.
     *
     * @param event Das KeyEvent von AWT.
     */
    @API
    void onKeyDown(KeyEvent event);

    /**
     * Wird bei einem angemeldeten Listener aufgerufen, sobald eine Taste
     * losgelassen wurde, die vorher gedrückt war.
     *
     * @param event Das KeyEvent von AWT.
     */
    @API
    default void onKeyUp(KeyEvent event)
    {
        // default empty
    }
}
