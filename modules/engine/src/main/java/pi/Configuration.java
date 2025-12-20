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

/**
 * Speichert <b>Konfigurationen</b> als statische Attribute.
 *
 * <p>
 * Durch diese Klasse kann die Klasse {@link Game} etwas übersichtlicher und
 * weniger überladen gestaltet werden.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public final class Configuration
{
    /**
     * Die <b>Breite</b> des Fensters in Pixel.
     */
    public static int windowWidthPx = 800;

    /**
     * Die <b>Höhe</b> des Fensters in Pixel.
     */
    public static int windowHeightPx = 600;

    /**
     * Wie oft ein <b>Pixel vervielfältigt</b> werden soll.
     *
     * <p>
     * Beispielsweise verwandelt die Zahl {@code 3} ein Pixel in {@code 9 Pixel}
     * der Abmessung {@code 3x3}.
     * </p>
     */
    public static int pixelMultiplication = 1;

    /**
     * Im sogenannten <b>Instant-Modus</b> werden die erzeugten Figuren
     * <b>sofort</b> einer Szene hinzugefügt und diese Szene wird dann
     * <b>sofort</b> gestartet.
     */
    public static boolean instantMode = false;
}
