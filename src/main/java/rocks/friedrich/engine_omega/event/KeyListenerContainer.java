/*
 * Engine Omega ist eine anfängerorientierte 2D-Gaming Engine.
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

package rocks.friedrich.engine_omega.event;

import rocks.friedrich.engine_omega.internal.annotations.API;

public interface KeyListenerContainer {
    EventListeners<KeyListener> getKeyListeners();

    /**
     * Fügt einen {@link KeyListener} zum Objekt hinzu.
     * Die Klassen {@link rocks.friedrich.engine_omega.actor.Actor Actor},
     * {@link rocks.friedrich.engine_omega.Scene Scene} und
     * {@link rocks.friedrich.engine_omega.Layer Layer} implementieren die
     * Schnittstelle {@link KeyListenerContainer} und stellen daher diese
     * Methode zur Verfügung.
     *
     * Der {@link KeyListener} kann auf mehrere Arten implementiert werden:
     *
     * <ol>
     * <li>Als normale Klasse:
     *
     * <pre>{@code
     *class MyKeylistener implements KeyListener {
     *    @Override
     *    public void onKeyDown(KeyEvent e) {
     *        // Code here
     *    }
     *}
     *obj.addKeyListener(new MyKeylistener());
     * }</pre>
     * </li>
     *
     * <li>Als anonyme Klasse:
     *
     * <pre>{@code
     *obj.addKeyListener(new KeyListener() {
     *    @Override
     *    public void onKeyDown(KeyEvent e) {
     *        // Code here
     *    }
     *});
     * }</pre>
     * </li>
     *
     * <li>Oder als Lambda-Ausdruck:
     *
     * <pre>{@code
     *obj.addKeyListener(e -> {
     *    // Code here
     *});
     * }</pre>
     * </li>
     * </ol>
     *
     * @param keyListener
     */
    @API
    default void addKeyListener(KeyListener keyListener) {
        getKeyListeners().add(keyListener);
    }

    /**
     * Entfernt einen {@link KeyListener} vom Objekt.
     */
    @API
    default void removeKeyListener(KeyListener keyListener) {
        getKeyListeners().remove(keyListener);
    }
}
