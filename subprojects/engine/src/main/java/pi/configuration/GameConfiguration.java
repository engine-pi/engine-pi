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
package pi.configuration;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Setter;

/**
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
@ConfigurationGroupInfo(prefix = "game_")
public class GameConfiguration extends ConfigurationGroup
{
    GameConfiguration()
    {
        super();
        // Der Konstruktor sollte nicht auf „public“ gesetzt werden, sondern
        // „package private“ bleiben, damit die Konfigurationsgruppe nur in
        // diesem Paket instanziert werden kann.
    }

    /* instantMode */

    /**
     * Im sogenannten <b>Instant-Modus</b> werden die erzeugten Figuren
     * <b>sofort</b> einer Szene hinzugefügt und diese Szene wird dann umgehend
     * gestartet.
     *
     * <p>
     * Der <b>Instant-Modus</b> der Engine Pi startet ein Spiel, ohne das viel
     * Code geschrieben werden muss.
     * </p>
     *
     * @since 0.42.0
     */
    public boolean instantMode = true;

    /**
     * Gibt zurück, ob der <b>Instant-Modus</b> aktiviert ist oder nicht.
     *
     * @return {@code true} falls der <b>Instant-Modus</b> aktiviert ist, sonst
     *     {@code false}.
     *
     * @since 0.42.0
     */
    @Getter
    @API
    public boolean instantMode()
    {
        return instantMode;
    }

    /**
     * Aktiviert oder deaktiviert den <b>Instant-Modus</b>.
     *
     * @param instantMode Der Aktivierungsstatus des Instand-Modus, also
     *     {@code true} falls der <b>Instant-Modus</b> aktiviert werden soll,
     *     sonst {@code false}.
     *
     * @return Eine Referenz auf die eigene Instanz der GameConfiguration, damit
     *     nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     GameConfiguration durch aneinander gekettete Setter festgelegt werden
     *     können, z. B. {@code game().instantMode(..).xxx(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public GameConfiguration instantMode(boolean instantMode)
    {
        set("instantMode", instantMode);
        return this;
    }
}
