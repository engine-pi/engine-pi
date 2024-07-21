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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Formatiert die Textausgabe der {@link Object#toString()}-Methoden.
 *
 * @author Josef Friedrich
 *
 * @since 0.25.0
 */
public class ToStringFormatter
{
    private HashMap<String, Object> map = new LinkedHashMap<>();

    private String className;

    public ToStringFormatter(String className)
    {
        this.className = className;
    }

    public void add(String key, Object value)
    {
        map.put(key, value);
    }

    public void add(String key, Object value, String unit)
    {
        map.put(key, String.format("%s%s", value, unit));
    }

    public void add(String key)
    {
        map.put(key, "true");
    }

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
