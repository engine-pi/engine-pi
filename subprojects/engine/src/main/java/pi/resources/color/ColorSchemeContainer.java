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

import java.util.LinkedHashMap;
import java.util.Map;

import pi.annotations.Getter;

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

    /**
     * Wir verwenden {@link LinkedHashMap}, damit die Einfügereihenfolge
     * erhalten bleibt und die Farbschemata sortiert ausgegeben werden können.
     */
    private final Map<String, ColorScheme> schemes = new LinkedHashMap<>();

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
     * <b>Gibt</b> die <b>Singleton/Einzelner-Instanz</b>
     * {@link ColorSchemeContainer} zurück.
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
     * <b>Setzt</b> das angegebene Farbschema als Standard und fügt es zum
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
     * <b>Gibt</b> das aktuelle Farbschema aus der Konfiguration <b>zurück</b>.
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
     * <b>Ruft</b> ein Farbschema anhand seines Namens <b>sicher</b> <b>ab</b>.
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
     * <b>Löscht</b> alle Farbschemata aus diesem Farbschemataspeicher.
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
        currentScheme = 0;

    }

    /**
     * <b>Setzt</b> den Farbschemataspeicher auf seinen <b>Standardzustand</b>
     * <b>zurück</b>.
     *
     * <p>
     * Löscht alle vorhandenen Farbschemen und lädt alle vordefinierten
     * Farbschemen erneut in den Farbschemataspeicher.
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

    /**
     * Gibt die Anzahl der Farbschemas in diesem Container zurück.
     *
     * @return die Anzahl der gespeicherten Farbschemas
     *
     * @since 0.42.0
     */
    public int size()
    {
        return schemes.size();
    }

    /**
     * Gibt ein Array mit den Namen aller Farbschemen in diesem Container
     * zurück.
     *
     * <p>
     * Die Reihenfolge der Namen entspricht der Reihenfolge der Einträge in der
     * zugrunde liegenden Map-Struktur.
     * </p>
     *
     * @return Ein String-Array mit den Namen aller gespeicherten Farbschemen.
     *     Das Array hat die gleiche Länge wie die Anzahl der Schemen.
     *
     * @since 0.42.0
     */
    @Getter
    public String[] names()
    {
        String[] names = new String[size()];
        int counter = 0;
        for (var entry : schemes.entrySet())
        {
            names[counter] = entry.getValue().name();
            counter++;
        }
        return names;
    }

    /**
     * Dieser Zähler wird für die Methode {@link #next()} benötigt.
     */
    private int currentScheme = 0;

    /**
     * Gibt das aktuelle Farbschema zurück und wechselt zum <b>nächsten</b>
     * Schema.
     *
     * <p>
     * Die Farbschemen werden in der Einfügereihenfolge ausgegeben. Wenn das
     * letzte Schema in der Sammlung erreicht ist, wird wieder zum ersten Schema
     * gewechselt (zirkuläres Verhalten).
     * </p>
     *
     * @return das aktuelle {@link ColorScheme} vor dem Wechsel zum nächsten
     *
     * @since 0.42.0
     */
    public ColorScheme next()
    {
        ColorScheme scheme = get(names()[currentScheme]);
        if (currentScheme == size() - 1)
        {
            currentScheme = 0;
        }
        else
        {
            currentScheme++;
        }
        return scheme;
    }
}
