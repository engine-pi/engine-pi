/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;

/**
 * Ein Beobachter, der auf Szenenwechsel reagiert.
 *
 * @author Josef Friedrich
 *
 * @since 0.15.0
 */
public interface SceneLaunchListener
{
    /**
     * @param next     Die nächste Szene, die gestartet werden soll.
     * @param previous Die vorhergehende Szene, die durch die nächste Szene
     *                 ersetzt werden soll. Wird das Spiel gestartet, so ist
     *                 dieser Parameter {@code null}.
     */
    @API
    void onSceneLaunch(Scene next, Scene previous);
}
