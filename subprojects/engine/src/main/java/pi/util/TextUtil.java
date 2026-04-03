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
package pi.util;

import static pi.util.TextAlignment.CENTER;
import static pi.util.TextAlignment.LEFT;
import static pi.util.TextAlignment.RIGHT;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

/**
 * Eine Sammlung von statischen Hilfsmethoden um <b>Text</b> und
 * <b>Zeichenketten</b> zu bearbeiten.
 */
public class TextUtil
{
    private TextUtil()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * <b>Rundet</b> eine Zahl auf eine Nachkommastelle.
     *
     * @param number Eine Zahl, die gerundet werden soll.
     *
     * @return Die gerundete Zahl als Zeichenkette.
     */
    public static String roundNumber(Object number)
    {
        return roundNumber(number, 1);
    }

    /**
     * Formatiert eine übergebene Zahl auf die angegebene Anzahl
     * Nachkommastellen und gibt das Ergebnis als String zurück. Es werden
     * fehlende Nachkommastellen mit Nullen aufgefüllt.
     *
     * @param number Eine darstellbare Zahl (Instanz von
     *     {@link java.lang.Number} oder kompatibel). Wenn das Objekt kein
     *     unterstützter Zahlentyp ist, wirft {@link java.text.DecimalFormat}
     *     eine {@link IllegalArgumentException}.
     * @param decimalPlaces Anzahl der gewünschten Nachkommastellen (>= 0). Bei
     *     0 werden keine Dezimalstellen ausgegeben.
     *
     * @return String-Repräsentation der Zahl mit exakt {@code decimalPlaces}
     *     Nachkommastellen.
     *
     * @throws IllegalArgumentException falls {@code decimalPlaces} kleiner 0
     *     ist oder das Objekt nicht als Zahl formatiert werden kann.
     */
    public static String roundNumber(Object number, int decimalPlaces)
    {
        if (decimalPlaces < 0)
        {
            throw new IllegalArgumentException("decimalPlaces must be >= 0");
        }
        StringBuilder pattern = new StringBuilder("0");
        if (decimalPlaces > 0)
        {
            pattern.append('.');
            for (int i = 0; i < decimalPlaces; i++)
            {
                pattern.append('0');
            }
        }
        DecimalFormat df = new DecimalFormat(pattern.toString());
        return df.format(number);
    }

    /**
     * Gibt die <b>maximale Zeilenbreite</b> eines gegebenen Texts zurück.
     *
     * @param text Der Text, von dem die maximale Zeilenbreite bestimmt werden
     *     soll.
     *
     * @return Die Anzahl an Zeichen, die die längste Zeile beinhaltet.
     *
     * @since 0.23.0
     */
    public static int getLineWidth(String text)
    {
        final int[] width = { 0 };
        text.lines().forEach((String line) -> {
            if (line.length() > width[0])
            {
                width[0] = line.length();
            }
        });
        return width[0];
    }

    /**
     * Gibt die Anzahl der Zeilen zurück.
     *
     * @param text Der Text, von dem die Anzahl der Zeilen bestimmt werden soll.
     *
     * @return Die Anzahl der Zeilen.
     *
     * @since 0.23.0
     */
    public static int getLineCount(String text)
    {
        return (int) text.lines().count();
    }

    /**
     * Teilt einen Text in die einzelnen Zeilen auf. Der Text muss
     * Zeilenumbrüche enthalten.
     *
     * @param text Der Text, der in die einzelnen Zeilen aufgeteilt werden soll.
     *
     * @return Die einzelnen Zeilen des Textes.
     *
     * @since 0.23.0
     */
    public static String[] splitLines(String text)
    {
        return text.lines().toArray(String[]::new);
    }

    /**
     * <b>Richtet</b> den gegebenen Text gemäß einer bestimmten
     * <b>Zeilenbreite</b> und einer gewünschten <b>Textausrichtung</b>
     * <b>aus</b>.
     *
     * @param text Die Zeichenkette, die ausgerichtet werden soll.
     * @param width Die Anzahl an Zeichen, die jede Zeile lang sein soll.
     * @param alignment Ob die Zeichen links-, rechtsbündig oder zentriert
     *     ausgerichtet werden soll.
     *
     * @return Eine Zeichenkette, in der je nach Ausrichtung Leerzeichen
     *     eingefügt wurden.
     *
     * @since 0.23.0
     */
    public static String align(String text, int width, TextAlignment alignment)
    {
        return text.lines().map((String line) -> {
            line = line.trim();
            int length = line.length();
            int spaces = width - length;
            if (alignment == RIGHT)
            {
                return " ".repeat(spaces) + line;
            }
            else if (alignment == CENTER)
            {
                int left = spaces / 2;
                return " ".repeat(left) + line;
            }
            return line;
        }).collect(Collectors.joining("\n"));
    }

    /**
     * <b>Richtet</b> den gegebenen Text gemäß einer gewünschten
     * <b>Textausrichtung</b> <b>aus</b>.
     *
     * @param text Die Zeichenkette, die ausgerichtet werden soll.
     * @param alignment Ob die Zeichen links-, rechtsbündig oder zentriert
     *     ausgerichtet werden soll.
     *
     * @return Eine Zeichenkette, in der je nach Ausrichtung Leerzeichen
     *     eingefügt wurden.
     *
     * @since 0.23.0
     */
    public static String align(String text, TextAlignment alignment)
    {
        return align(text, getLineWidth(text), alignment);
    }

    /**
     * Bricht den gegebenen Text nach einer bestimmten <b>Zeilenbreite</b> um.
     * Außerdem kann die <b>Textausrichtung</b> angegeben werden.
     *
     * <p>
     * Nach einem Code-Beispiel auf <a href=
     * "https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-string-algorithms-3/src/main/java/com/baeldung/wrappingcharacterwise/Wrapper.java">baeldung.com</a>.
     * </p>
     *
     * @param text Der Text, der nach einer bestimmten Zeilenbreite umgebrochen
     *     werden soll.
     * @param width Die maximale Zeilenbreite.
     * @param alignment Die Textausrichtung.
     *
     * @return Der neu formatierte Text, in den möglicherweise neue
     *     Zeilenumbrüche eingefügt wurden.
     *
     * @throws IllegalArgumentException Falls es ein längeres Wort als die
     *     Zeilenbreite gibt.
     *
     * @since 0.23.0
     */
    public static String wrap(String text, int width, TextAlignment alignment)
    {
        if (width < 1)
        {
            width = getLineWidth(text);
        }
        StringBuilder stringBuilder = new StringBuilder(text);
        int index = 0;
        while (stringBuilder.length() > index + width)
        {
            int lastLineReturn = stringBuilder.lastIndexOf("\n", index + width);
            if (lastLineReturn > index)
            {
                index = lastLineReturn;
            }
            else
            {
                index = stringBuilder.lastIndexOf(" ", index + width);
                if (index == -1)
                {
                    throw new IllegalArgumentException(
                            "Kann den Text nicht umbrechen, da es ein Wort gibt, das länger als die Zeilenbreite ist: "
                                    + stringBuilder.substring(0, width));
                }
                stringBuilder.replace(index, index + 1, "\n");
                index++;
            }
        }
        return align(stringBuilder.toString(), width, alignment);
    }

    /**
     * Bricht den gegebenen Text nach einer bestimmten <b>Zeilenbreite</b>, und
     * zwar <b>linksbündig</b> um.
     *
     * @param text Der Text, der nach einer bestimmten Zeilenbreite umgebrochen
     *     werden soll.
     * @param width Die maximale Zeilenbreite.
     *
     * @return Der neu formatierte Text, in den möglicherweise neue
     *     Zeilenumbrüche eingefügt wurden.
     *
     * @throws IllegalArgumentException Falls es ein längeres Wort als die
     *     Zeilenbreite gibt.
     *
     * @since 0.23.0
     */
    public static String wrap(String text, int width)
    {
        return wrap(text, width, LEFT);
    }

    /**
     * Konvertiert ein Objekt zu einer Zeichenkette. Für Arrays, Listen, Sets
     * und Karten werden die einzelnen Elemente in die resultierende
     * Zeichenkette konvertiert.
     *
     * <p>
     * Beispiele:
     * </p>
     * <ul>
     * <li>{@code null} → {@code "null"}
     * <li>{@code "text"} → {@code "text"}
     * <li>{@code [1, 2, 3]} → {@code "[1, 2, 3]"}
     * <li>{@code [1.5, 2.5]} → {@code "[1.5, 2.5]"}
     * <li>{@code List.of("a", "b")} → {@code "[a, b]"}
     * <li>{@code Map.of("key", "value")} → {@code "{key=value}"}
     * </ul>
     *
     * @param object Das Objekt, das konvertiert werden soll.
     *
     * @return Die Zeichenketten-Repräsentation des Objekts mit konvertierten
     *     Elementen für Sammlungstypen.
     *
     * @since 0.45.0
     */
    public static String convertToString(Object object)
    {
        if (object == null)
        {
            return "null";
        }

        if (object instanceof java.util.Map<?, ?> map)
        {
            StringBuilder sb = new StringBuilder("{");
            var entries = map.entrySet().iterator();
            while (entries.hasNext())
            {
                var entry = entries.next();
                sb.append(convertToString(entry.getKey()))
                    .append("=")
                    .append(convertToString(entry.getValue()));
                if (entries.hasNext())
                {
                    sb.append(", ");
                }
            }
            sb.append("}");
            return sb.toString();
        }

        if (object instanceof java.util.Set<?> set)
        {
            StringBuilder sb = new StringBuilder("[");
            var iterator = set.iterator();
            while (iterator.hasNext())
            {
                sb.append(convertToString(iterator.next()));
                if (iterator.hasNext())
                {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }

        if (object instanceof java.util.List<?> list)
        {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++)
            {
                sb.append(convertToString(list.get(i)));
                if (i < list.size() - 1)
                {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }

        if (object.getClass().isArray())
        {
            StringBuilder sb = new StringBuilder("[");
            int length = Array.getLength(object);
            for (int i = 0; i < length; i++)
            {
                sb.append(convertToString(Array.get(object, i)));
                if (i < length - 1)
                {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }

        return object.toString();
    }
}
