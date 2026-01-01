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
package pi;

import pi.annotations.API;
import pi.annotations.Setter;
import pi.configuration.GameConfiguration;
import pi.configuration.GraphicsConfiguration;

/**
 *
 */
public class Controller
{
    private static final Configuration config = Configuration.get();

    private static final GameConfiguration gameConfig = config.game();

    private static final GraphicsConfiguration graphicsConfig = config
            .graphics();

    /**
     * Aktiviert oder deaktiviert den <b>Instant-Modus</b>.
     *
     * <p>
     * Im sogenannten <b>Instant-Modus</b> werden die erzeugten Figuren
     * <b>sofort</b> einer Szene hinzugefügt und diese Szene wird dann umgehend
     * gestartet.
     * </p>
     *
     * <p>
     * Der <b>Instant-Modus</b> der Engine Pi startet ein Spiel, ohne das viel
     * Code geschrieben werden muss.
     * </p>
     *
     *
     * @param instantMode Der Aktivierungsstatus des Instant-Modus, also
     *     {@code true} falls der <b>Instant-Modus</b> aktiviert werden soll,
     *     sonst {@code false}.
     *
     * @see GameConfiguration#instantMode(boolean)
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public static void instantMode(boolean instantMode)
    {
        gameConfig.instantMode(instantMode);
    }

    /**
     * Setzt die <b>Abmessung</b>, also die <b>Breite</b> und die <b>Höhe</b>,
     * des Fensters in Pixel.
     *
     * @param windowWidth Die <b>Breite</b> des Fensters in Pixel.
     * @param windowHeight Die <b>Höhe</b> des Fensters in Pixel.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public static void windowDimension(int windowWidth, int windowHeight)
    {
        graphicsConfig.windowDimension(windowWidth, windowHeight);
    }
}
