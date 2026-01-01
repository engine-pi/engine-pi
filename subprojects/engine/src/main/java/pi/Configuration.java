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

import pi.configuration.MainConfiguration;

/**
 * Bietet Zugriff auf alle <b>Konfigurationen</b>
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
public final class Configuration extends MainConfiguration
{
    private static Configuration configuration;

    /**
     * Der Konstruktor ist auf privat gesetzt, damit nach dem
     * Singleton/Einzelner-Entwurfsmuster nur eine Instanz erzeugt werden kann.
     */
    private Configuration()
    {

    }

    /**
     * <b>Gibt</b> die Singleton/Einzelner-Instanz der Konfiguration zurück.
     *
     * <p>
     * Falls noch keine Instanz existiert, wird diese erstellt und geladen.
     * </p>
     *
     * @return Das global Konfigurationsobjekt.
     *
     * @since 0.42.0
     */
    public static Configuration get()
    {
        if (configuration == null)
        {
            configuration = new Configuration();
            configuration.load();
        }
        return configuration;
    }

    /**
     * Wie oft ein <b>Pixel vervielfältigt</b> werden soll.
     *
     * <p>
     * Beispielsweise verwandelt die Zahl {@code 3} ein Pixel in {@code 9 Pixel}
     * der Abmessung {@code 3x3}.
     * </p>
     */
    public static int pixelMultiplication = 1;

    public static void main(String[] args)
    {
        Configuration config = Configuration.get();
        System.out.println(config.graphics().windowHeight());
    }
}
