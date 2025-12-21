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

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import pi.util.TextUtil;

record Field(String name, Object value, String unit)
{

    private String formattedValue()
    {
        if (value instanceof String)
        {
            return "\"" + ((String) value).replace("\n", "\\n ") + "\"";
        }
        else if (value instanceof Double)
        {
            return TextUtil.roundNumber(value);
        }
        else if (value instanceof Color)
        {
            return String.valueOf(value).replace("java.awt.", "");
        }
        return String.valueOf(value);
    }

    public String format()
    {
        String output = name + "=" + AnsiColor.blue(formattedValue());
        if (unit != null)
        {
            output += unit;
        }
        return output;
    }
}

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

    private final String className;

    private String hashCode;

    /**
     * Eine sortiere Liste der Schlüsselnamen.
     */
    private List<String> fieldNames = new LinkedList<>();

    private final HashMap<String, Field> map = new LinkedHashMap<>();

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

    private ToStringFormatter add(boolean prepend, String fieldName,
            Object value, String unit)
    {
        if (fieldNames.contains(fieldName))
        {
            throw new RuntimeException("Ein Feld mit dem Namen " + fieldName
                    + "wurde bereits zum toString()-Formatter hinzugefügt");
        }
        map.put(fieldName, new Field(fieldName, value, unit));
        if (prepend)
        {
            fieldNames.add(0, fieldName);
        }
        else
        {
            fieldNames.add(fieldName);
        }
        return this;
    }

    /**
     * Fügt das Schlüssel-Wert-Paar eines Felds am <b>Anfang</b> der Feldliste
     * hinzu.
     *
     * @param fieldName Der Name des <b>Felds</b> bzw. des Attributs.
     * @param value Der <b>Wert</b> des Felds in einem beliebigen Datentyp.
     */
    public void prepend(String fieldName, Object value)
    {
        prepend(fieldName, value, null);
    }

    /**
     * Fügt das Schlüssel-Wert-Paar eines Felds mit <b>Einheit</b> am
     * <b>Anfang</b> der Feldliste hinzu.
     *
     * @param fieldName Der Name des <b>Felds</b> bzw. des Attributs.
     * @param value Der <b>Wert</b> des Felds in einem beliebigen Datentyp.
     * @param unit Eine zusätzliche Zeichenkette, die an den Wert angehängt
     *     wird, und als <b>Einheit</b> dienen kann.
     */
    public void prepend(String fieldName, Object value, String unit)
    {
        add(true, fieldName, value, unit);
    }

    /**
     * Fügt das Schlüssel-Wert-Paar eines Felds ans <b>Ende</b> der Feldliste
     * hinzu.
     *
     * @param fieldName Der Name des <b>Felds</b> bzw. des Attributs.
     * @param value Der <b>Wert</b> des Felds in einem beliebigen Datentyp.
     */
    public void append(String fieldName, Object value)
    {
        add(false, fieldName, value, null);
    }

    /**
     * Fügt das Schlüssel-Wert-Paar eines Felds mit <b>Einheit</b> ans
     * <b>Ende</b> der Feldliste hinzu.
     *
     * @param fieldName Der Name des <b>Felds</b> bzw. des Attributs.
     * @param value Der <b>Wert</b> des Felds in einem beliebigen Datentyp.
     * @param unit Eine zusätzliche Zeichenkette, die an den Wert angehängt
     *     wird, und als <b>Einheit</b> dienen kann.
     */
    public void append(String fieldName, Object value, String unit)
    {
        add(false, fieldName, value, unit);
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
        List<String> entries = new ArrayList<>();
        for (String fieldName : fieldNames)
        {
            Field field = map.get(fieldName);
            entries.add(field.format());
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
