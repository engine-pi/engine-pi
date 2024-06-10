/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/event/KeyListenerContainer.java
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

public interface KeyListenerContainer
{
    EventListeners<KeyListener> getKeyListeners();

    /**
     * Fügt einen {@link KeyListener} zum Objekt hinzu. Die Klassen
     * {@link de.pirckheimer_gymnasium.engine_pi.actor.Actor Actor},
     * {@link de.pirckheimer_gymnasium.engine_pi.Scene Scene} und
     * {@link de.pirckheimer_gymnasium.engine_pi.Layer Layer} implementieren die
     * Schnittstelle {@link KeyListenerContainer} und stellen daher diese
     * Methode zur Verfügung.
     *
     * Der {@link KeyListener} kann auf mehrere Arten implementiert werden:
     *
     * <ol>
     * <li>Als normale Klasse:
     *
     * <pre>{@code
     * class MyKeylistener implements KeyListener
     * {
     *     @Override
     *     public void onKeyDown(KeyEvent e)
     *     {
     *         // Code here
     *     }
     * }
     * obj.addKeyListener(new MyKeylistener());
     * }</pre>
     *
     * </li>
     *
     * <li>Als anonyme Klasse:
     *
     * <pre>{@code
     * obj.addKeyListener(new KeyListener()
     * {
     *     @Override
     *     public void onKeyDown(KeyEvent e)
     *     {
     *         // Code here
     *     }
     * });
     * }</pre>
     *
     * </li>
     *
     * <li>Oder als Lambda-Ausdruck:
     *
     * <pre>{@code
     * obj.addKeyListener(e -> {
     *     // Code here
     * });
     * }</pre>
     *
     * </li>
     * </ol>
     *
     * @param listener Ein Objekt der Klasse {@link KeyListener}.
     */
    @API
    default void addKeyListener(KeyListener listener)
    {
        getKeyListeners().add(listener);
    }

    /**
     * Entfernt einen {@link KeyListener} vom Objekt.
     *
     * @param listener Ein Objekt der Klasse {@link KeyListener}.
     */
    @API
    default void removeKeyListener(KeyListener listener)
    {
        getKeyListeners().remove(listener);
    }
}
