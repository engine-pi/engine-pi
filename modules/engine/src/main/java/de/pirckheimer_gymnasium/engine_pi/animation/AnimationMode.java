/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/animation/AnimationMode.java
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
package de.pirckheimer_gymnasium.engine_pi.animation;

/**
 * Definiert die verschiedenen Modi für Animationen.
 */
public enum AnimationMode
{
    /**
     * Die Animation läuft einmal ab und stoppt.
     */
    SINGLE,

    /**
     * Die Animation wird fortlaufend wiederholt.
     */
    REPEATED,

    /**
     * Die Animation läuft vorwärts und rückwärts ab (Hin- und Her-Modus).
     */
    PING_PONG
}
