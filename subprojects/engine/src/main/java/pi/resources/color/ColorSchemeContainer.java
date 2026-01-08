/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package pi.resources.color;

import static pi.Controller.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Ein Speicher für <b>Farbschemata</b>.
 *
 * <p>
 * Diese Klasse verwaltet eine Sammlung von Farbschemata und ermöglicht deren
 * Verwaltung und Abruf anhand von Namen. Sie wird automatisch mit allen
 * vordefinierten Farbschemata aus der Aufzählung {@link PredefinedColorScheme}
 * initialisiert.
 * </p>
 *
 * <p>
 * Die Klasse bietet folgende Funktionalität:
 * </p>
 *
 * <ul>
 * <li>Speichern und Abrufen von Farbschemata anhand ihrer Namen.</li>
 * <li>Sichere Abfrage mit Fallback auf das GNOME-Standardschema.</li>
 * <li>Groß-/Kleinschreibung wird bei der Namensabfrage ignoriert.</li>
 * </ul>
 *
 * @since 0.42.0
 *
 * @see PredefinedColorScheme
 * @see ColorScheme
 */
public class ColorSchemeContainer
{
    private static ColorSchemeContainer container;

    private final Map<String, ColorScheme> schemes = new HashMap<>();

    /**
     * Initialisiert einen neuen ColorSchemeContainer mit allen vordefinierten
     * Farbschemata.
     *
     * <p>
     * Der Konstruktor durchläuft alle verfügbaren Werte der Aufzählung
     * {@link PredefinedColorScheme} und fügt die entsprechenden Farbschemata
     * zum Container hinzu.
     * </p>
     */
    private ColorSchemeContainer()
    {
        reset();
    }

    /**
     * Gibt die Singleton/Einzelner-Instanz {@link ColorSchemeContainer} zurück.
     *
     * <p>
     * Wenn noch keine Instanz existiert, wird eine neue erstellt. Bei weiteren
     * Aufrufen wird immer die gleiche Instanz zurückgegeben.
     * </p>
     *
     * @return Die Singleton/Einzelner-Instanz des {@link ColorSchemeContainer}.
     */
    public static ColorSchemeContainer getInstance()
    {
        if (container == null)
        {
            container = new ColorSchemeContainer();
        }
        return container;
    }

    /**
     * <b>Fügt</b> ein Farbschema zum Container hinzu.
     *
     * @param scheme Das hinzuzufügende Farbschema.
     *
     * @since 0.42.0
     */
    public void add(ColorScheme scheme)
    {
        schemes.put(scheme.name().toLowerCase(), scheme);
    }

    /**
     * Setzt das angegebene Farbschema als Standard und fügt es zum
     * Farbschemaspeicher hinzu.
     *
     * @param scheme Das hinzuzufügende Farbschema.
     */
    public void set(ColorScheme scheme)
    {
        config.graphics.colorScheme(scheme.name());
        add(scheme);
    }

    /**
     * <b>Ruft</b> ein Farbschema anhand seines <b>Namens</b> ab.
     *
     * @param name Der Name des Farbschemas (Groß-/Kleinschreibung wird
     *     ignoriert)
     *
     * @return Das <b>Farbschema</b> mit dem angegebenen Namen.
     *
     * @throws RuntimeException Wenn kein Farbschema mit dem angegebenen Namen
     *     existiert
     *
     * @since 0.42.0
     */
    public ColorScheme get(String name)
    {
        name = name.toLowerCase();
        if (!schemes.containsKey(name))
        {
            throw new RuntimeException("Unbekanntes Farbschema : " + name);
        }
        return schemes.get(name);
    }

    /**
     * Gibt das aktuelle Farbschema aus der Konfiguration zurück.
     *
     * @return das Farbschema, das in der Grafikkonfiguration festgelegt ist
     *
     * @since 0.42.0
     */
    public ColorScheme get()
    {
        return getSafe(config.graphics.colorScheme());
    }

    /**
     * Ruft ein Farbschema anhand seines Namens sicher ab.
     *
     * <p>
     * Bei der Suche wird die Groß-/Kleinschreibung ignoriert. Wenn das
     * angeforderte Farbschema nicht existiert, wird das vordefinierte
     * Gnome-Farbschema zurückgegeben.
     * </p>
     *
     * @param name der Name des Farbschemas (Groß-/Kleinschreibung wird
     *     ignoriert)
     *
     * @return das angeforderte Farbschema oder das GNOME-Standardschema, falls
     *     nicht vorhanden
     *
     * @since 0.42.0
     */
    public ColorScheme getSafe(String name)
    {
        name = name.toLowerCase();
        if (!schemes.containsKey(name))
        {
            return PredefinedColorScheme.GNOME.getScheme();
        }
        return schemes.get(name);
    }

    /**
     * <b>Löscht</b> alle Farbschemata aus diesem Container.
     *
     * <p>
     * Nach dem Aufruf dieser Methode sind keine Farbschemata mehr vorhanden.
     * </p>
     *
     * @since 0.42.0
     */
    public void clear()
    {
        schemes.clear();
    }

    /**
     * Setzt den Container auf seinen <b>Standardzustand</b> zurück.
     *
     * <p>
     * Löscht alle vorhandenen Farbschemen und lädt alle vordefinierten
     * Farbschemen erneut in den Container.
     * </p>
     *
     * @since 0.42.0
     */
    public void reset()
    {
        clear();
        for (PredefinedColorScheme predefinedScheme : PredefinedColorScheme
                .values())
        {
            add(predefinedScheme.getScheme());
        }
    }

}
