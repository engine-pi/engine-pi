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
package de.pirckheimer_gymnasium.engine_pi.debug;

import de.pirckheimer_gymnasium.engine_pi.util.TextUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Hilft die Textausgabe der {@link Object#toString()}-Methoden zu formatieren.
 *
 * <p>
 * Wird die Figur Image mit der Methode {@code System.out.println(String)}
 * ausgegeben so erscheint folgende Zeichenkette:
 * {@code Image [width=1.0m, height=1.0m, imageWidth=8px, imageHeight=8px, pixelPerMeter=8.0]}
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.25.0
 */
public class ToStringFormatter
{
    private final HashMap<String, Object> map = new LinkedHashMap<>();

    private final String className;

    /**
     * @param className Der Name der Klasse
     */
    public ToStringFormatter(String className)
    {
        this.className = className;
    }

    /**
     * Fügt ein Schlüssel-Wert-Paar hinzu.
     *
     * @param key Der Name des Schlüssels bzw. des Attributs.
     * @param value Der Wert des Schlüssels in einem beliebigen Datentyp.
     */
    public void add(String key, Object value)
    {
        map.put(key, value);
    }

    /**
     * Fügt ein Schlüssel-Wert-Paar hinzu, dessen Wert eine Gleitkommazahl ist,
     * die gerundet wird.
     *
     * @param key Der Name des Schlüssels bzw. des Attributs.
     * @param value Der Wert des Schlüssels als Gleitkommazahl, die gerundet
     *     werden soll.
     */
    public void add(String key, double value)
    {
        map.put(key, TextUtil.roundNumber(value));
    }

    /**
     * Fügt ein Schlüssel-Wert-Paar mit Einheit hinzu.
     *
     * @param key Der Name des Schlüssels bzw. des Attributs.
     * @param value Der Wert des Schlüssels in einem beliebigen Datentyp.
     * @param unit Eine zusätzliche Zeichenkette, die an den Wert angehängt
     *     wird, und als Einheit dienen kann.
     */
    public void add(String key, Object value, String unit)
    {
        map.put(key, String.format("%s%s", value, unit));
    }

    /**
     * Fügt lediglich einen Schlüssel beziehungsweise ein Attribut hinzu, das
     * den Wert zugewiesen bekommt.
     *
     * @param key Der Name des Schlüssels bzw. des Attributs.
     */
    public void add(String key)
    {
        map.put(key, "true");
    }

    /**
     * @return Die formatierte Zeichenkette.
     */
    public String format()
    {
        ArrayList<String> entries = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            entries.add(entry.getKey() + "=" + entry.getValue());
        }
        return String.format("%s [%s]", className, String.join(", ", entries));
    }
}
