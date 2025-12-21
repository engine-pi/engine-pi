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
package pi.debug;

import pi.util.TextUtil;

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
 * <pre>
 * {@code
 * @Override
 * public String toString()
 * {
 *     ToStringFormatter formatter = new ToStringFormatter("Image");
 *     formatter.add("width", width, "m");
 *     formatter.add("height", height, "m");
 *     if (pixelPerMeter > 0)
 *     {
 *         formatter.add("pixelPerMeter", pixelPerMeter);
 *     }
 *     if (isFlippedHorizontally())
 *     {
 *         formatter.add("flippedHorizontally");
 *     }
 *     return formatter.format();
 * }
 * }
 * </pre>
 *
 * @see <a href=
 *     "https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/commons/lang3/builder/ToStringBuilder.java">org.apache.commons.lang3.builder.ToStringBuilder</a>
 *
 * @author Josef Friedrich
 *
 * @since 0.25.0
 */
public class ToStringFormatter
{
    private final HashMap<String, Object> map = new LinkedHashMap<>();

    private final String className;

    private String hashCode;

    /**
     * <pre>
     * {@code
     * @Override
     * public String toString()
     * {
     *     ToStringFormatter formatter = new ToStringFormatter("Image");
     *     formatter.add("width", width, "m");
     *     formatter.add("height", height, "m");
     *     if (pixelPerMeter > 0)
     *     {
     *         formatter.add("pixelPerMeter", pixelPerMeter);
     *     }
     *     if (isFlippedHorizontally())
     *     {
     *         formatter.add("flippedHorizontally");
     *     }
     *     return formatter.format();
     * }
     * }
     * </pre>
     *
     * @param className Der Name der <b>Klasse</b>.
     */
    public ToStringFormatter(String className)
    {
        this.className = className;
    }

    public ToStringFormatter(Object object)
    {
        className = object.getClass().getSimpleName();
        hashCode = Integer.toHexString(System.identityHashCode(object));
    }

    /**
     * Fügt ein <b>Schlüssel-Wert-Paar</b> hinzu.
     *
     * @param key Der Name des <b>Schlüssels</b> bzw. des Attributs.
     * @param value Der <b>Wert</b> des Schlüssels in einem beliebigen Datentyp.
     */
    public void add(String key, Object value)
    {
        map.put(key, value);
    }

    /**
     * Fügt ein Schlüssel-Wert-Paar hinzu, dessen Wert eine <b>Ganzzahl</b> ist.
     *
     * @param key Der Name des <b>Schlüssels</b> bzw. des Attributs.
     * @param value Der <b>Wert</b> des Schlüssels als Ganzzahl.
     */
    public void add(String key, int value)
    {
        map.put(key, value);
    }

    /**
     * Fügt ein Schlüssel-Wert-Paar hinzu, dessen Wert eine
     * <b>Gleitkommazahl</b> ist, die gerundet wird.
     *
     * @param key Der Name des <b>Schlüssels</b> bzw. des Attributs.
     * @param value Der <b>Wert</b> des Schlüssels als Gleitkommazahl, die
     *     gerundet werden soll.
     */
    public void add(String key, double value)
    {
        map.put(key, TextUtil.roundNumber(value));
    }

    /**
     * Fügt ein Schlüssel-Wert-Paar hinzu, dessen Wert eine
     * <b>Gleitkommazahl</b> ist, die gerundet wird.
     *
     * @param key Der Name des <b>Schlüssels</b> bzw. des Attributs.
     * @param value Der <b>Wert</b> des Schlüssels als Gleitkommazahl, die
     *     gerundet werden soll.
     */
    public void add(String key, String value)
    {
        map.put(key, "\"" + value + "\"");
    }

    /**
     * Fügt ein Schlüssel-Wert-Paar mit <b>Einheit</b> hinzu.
     *
     * @param key Der Name des <b>Schlüssels</b> bzw. des Attributs.
     * @param value Der <b>Wert</b> des Schlüssels in einem beliebigen Datentyp.
     * @param unit Eine zusätzliche Zeichenkette, die an den Wert angehängt
     *     wird, und als <b>Einheit</b> dienen kann.
     */
    public void add(String key, Object value, String unit)
    {
        map.put(key, String.format("%s%s", value, unit));
    }

    /**
     * Fügt lediglich einen <b>Schlüssel</b> beziehungsweise ein Attribut hinzu,
     * das den Wert zugewiesen bekommt.
     *
     * @param key Der Name des <b>Schlüssels</b> bzw. des Attributs.
     */
    public void add(String key)
    {
        map.put(key, "true");
    }

    private String objectName()
    {
        String objectName = AnsiColor.magenta(className);
        if (hashCode != null)
        {
            objectName += "@" + AnsiColor.yellow(hashCode);
        }
        return objectName;
    }

    /**
     * Gibt die formatierte Zeichenkette aus.
     *
     * @return Die formatierte Zeichenkette.
     */
    public String format()
    {
        ArrayList<String> entries = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            entries.add(
                    entry.getKey() + "=" + AnsiColor.blue(entry.getValue()));
        }
        return String.format("%s [%s]", objectName(),
                String.join(", ", entries));
    }

    /**
     * @see #format()
     */
    public String toString()
    {
        return format();
    }
}
