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

public interface KeyStrokeListenerRegistration
{
    EventListeners<KeyStrokeListener> getKeyStrokeListeners();

    /**
     * Fügt einen statisch {@link KeyStrokeListener} hinzu, d. h. dieser
     * KeyStrokeListener gilt global über das ganze Spiel und ist unabhängig von
     * der aktuellen Szene.
     *
     * <p>
     * Der {@link KeyStrokeListener} kann auf mehrere Arten implementiert
     * werden:
     * </p>
     *
     * <ol>
     * <li>Als normale Klasse:
     *
     * <pre>{@code
     * class MyKeyStrokelistener implements KeyStrokeListener
     * {
     *     @Override
     *     public void onKeyDown(KeyEvent e)
     *     {
     *         // Code here
     *     }
     * }
     * obj.addKeyStrokeListener(new MyKeyStrokelistener());
     * }</pre>
     *
     * </li>
     *
     * <li>Als anonyme Klasse:
     *
     * <pre>{@code
     * obj.addKeyStrokeListener(new KeyStrokeListener()
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
     * obj.addKeyStrokeListener(e -> {
     *     // Code here
     * });
     * }</pre>
     *
     * </li>
     * </ol>
     *
     * @author Josef Friedrich
     *
     * @param listener Ein Objekt der Klasse {@link KeyStrokeListener}.
     *
     * @see KeyStrokeListenerRegistration#addKeyStrokeListener(KeyStrokeListener)
     */
    @API
    default void addKeyStrokeListener(KeyStrokeListener listener)
    {
        getKeyStrokeListeners().add(listener);
    }

    /**
     * Entfernt einen {@link KeyStrokeListener} vom Objekt.
     *
     * @param listener Ein Objekt der Klasse {@link KeyStrokeListener}.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.Game#removeKeyStrokeListener(KeyStrokeListener)
     */
    @API
    default void removeKeyStrokeListener(KeyStrokeListener listener)
    {
        getKeyStrokeListeners().remove(listener);
    }
}
