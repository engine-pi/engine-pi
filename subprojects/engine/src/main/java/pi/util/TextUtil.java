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

import static pi.graphics.boxes.HAlign.CENTER;
import static pi.graphics.boxes.HAlign.LEFT;
import static pi.graphics.boxes.HAlign.RIGHT;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import pi.graphics.boxes.HAlign;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/test/java/pi/util/TextUtilTest.java

/**
 * Eine Sammlung von statischen Hilfsmethoden um <b>Text</b> und
 * <b>Zeichenketten</b> zu bearbeiten.
 *
 * @author Josef Friedrich
 */
public class TextUtil
{
    /**
     * Ein einheitliches Standard-Zeilen-Trennzeichen.
     *
     * <p>
     * {@link System#lineSeparator()} liefert für Windows als auch für
     * Unix-Systeme unterschiedliche Trennzeichen.
     * </p>
     */
    public static final String LINE_SEPARATOR = "\n";

    /**
     * Ein privater Konstruktor, um den öffentlichen Konstruktor zu verbergen.
     * Dadurch können von dieser Klasse keine Instanzen erstellt werden.
     */
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
    public static int getLineWidth(@NonNull String text)
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
    public static int getLineCount(@NonNull String text)
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
    public static String[] splitLines(@NonNull String text)
    {
        return text.lines().toArray(String[]::new);
    }

    /**
     * <b>Normalisiert</b> die <b>Zeilentrennzeichen</b> einer Zeichenkette auf
     * {@code "\n"}.
     *
     * <p>
     * Ersetzt alle Windows-Zeilenenden ({@code "\r\n"}) und alte
     * Mac-Zeilenenden ({@code "\r"}) durch {@code "\n"}.
     * </p>
     *
     * @param text Die Zeichenkette, deren Zeilentrennzeichen normalisiert
     *     werden sollen.
     *
     * @return Die Zeichenkette mit einheitlichen {@code "\n"}-Zeilenenden.
     *
     * @since 0.47.0
     */
    public static String normalizeLineSeparator(@NonNull String text)
    {
        return text.replace("\r\n", "\n").replace("\r", "\n");
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
    public static String align(@NonNull String text, int width,
            @Nullable HAlign alignment)
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
        }).collect(Collectors.joining(LINE_SEPARATOR));
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
    public static String align(@NonNull String text, @Nullable HAlign alignment)
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
    public static String wrap(@NonNull String text, int width,
            @Nullable HAlign alignment)
    {
        if (width < 1)
        {
            width = getLineWidth(text);
        }
        StringBuilder stringBuilder = new StringBuilder(text);
        int index = 0;
        while (stringBuilder.length() > index + width)
        {
            int lastLineReturn = stringBuilder.lastIndexOf(LINE_SEPARATOR,
                index + width);
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
                stringBuilder.replace(index, index + 1, LINE_SEPARATOR);
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
     * Konvertiert einen Iterator in einen formatierten String.
     *
     * Die Elemente des Iterators werden mit Kommas und Leerzeichen getrennt und
     * von den angegebenen Begrenzer-Strings umgeben.
     *
     * @param iterator der zu konvertierende Iterator
     * @param startDelimiter der String am Anfang der Ausgabe
     * @param endDelimiter der String am Ende der Ausgabe
     *
     * @return ein formatierter String mit allen Elementen des Iterators
     *
     * @since 0.47.0
     */
    private static String convertIteratorToString(Iterator<?> iterator,
            String startDelimiter, String endDelimiter)
    {
        StringBuilder string = new StringBuilder(startDelimiter);
        while (iterator.hasNext())
        {
            string.append(convertToString(iterator.next()));
            if (iterator.hasNext())
            {
                string.append(", ");
            }
        }
        string.append(endDelimiter);
        return string.toString();
    }

    /**
     * Konvertiert einen Inhalt in einem beliebigen Datentyp zu einer
     * <b>Zeichenkette</b>.
     *
     * <p>
     * Für Felder/Arrays, {@link java.util.List Listen}, {@link java.util.Set
     * Sets} und {@link java.util.Map Maps} werden die einzelnen Elemente in die
     * resultierende Zeichenkette konvertiert.
     * </p>
     *
     * <p>
     * Außerdem werden die Zeilen-Trennzeichen normalisiert. Alle
     * Windows-Zeilenenden ({@code "\r\n"}) und alte Mac-Zeilenenden
     * ({@code "\r"}) werden durch {@code "\n"} ersetzt.
     * </p>
     *
     * <p>
     * Beispiele:
     * </p>
     * <ul>
     * <li>{@code null} → {@code ""}
     * <li>{@code "text"} → {@code "text"}
     * <li>{@code [1, 2, 3]} → {@code "[1, 2, 3]"}
     * <li>{@code [1.5, 2.5]} → {@code "[1.5, 2.5]"}
     * <li>{@code List.of("a", "b")} → {@code "[a, b]"}
     * <li>{@code Map.of("key", "value")} → {@code "{key=value}"}
     * </ul>
     *
     * @param content Der Inhalt, der konvertiert werden soll.
     *
     * @return Die Zeichenketten-Repräsentation des Inhalts mit konvertierten
     *     Elementen für Sammlungstypen.
     *
     * @since 0.45.0
     */
    public static String convertToString(Object content)
    {
        if (content == null)
        {
            return "";
        }

        if (content instanceof java.util.Map<?, ?> map)
        {
            var entries = map.entrySet().iterator();
            List<String> list = new ArrayList<>(map.size());
            while (entries.hasNext())
            {
                var entry = entries.next();
                list.add(
                    convertToString(entry.getKey()) + "=" + entry.getValue());
            }
            return convertIteratorToString(list.iterator(), "{", "}");
        }

        if (content instanceof java.util.Set<?> set)
        {
            return convertIteratorToString(set.iterator(), "[", "]");
        }

        if (content instanceof java.util.List<?> list)
        {
            return convertIteratorToString(list.iterator(), "[", "]");
        }

        if (content.getClass().isArray())
        {
            int length = Array.getLength(content);
            List<String> list = new ArrayList<>(length);
            for (int i = 0; i < length; i++)
            {
                list.add(convertToString(Array.get(content, i)));
            }
            return convertIteratorToString(list.iterator(), "[", "]");
        }

        return normalizeLineSeparator(content.toString());
    }

    /**
     * Konvertiert mehrere Inhalte zu einem <b>mehrzeiligen Text</b>.
     *
     * <p>
     * Jedes übergebene Element wird mit {@link #convertToString(Object)} in
     * eine Zeichenkette umgewandelt. Anschließend werden die Ergebnisse mit
     * {@code "\n"} (siehe {@link #LINE_SEPARATOR}) verbunden, sodass jedes
     * Element in einer eigenen Zeile steht.
     * </p>
     *
     * @param content Die Inhalte, die zeilenweise konvertiert werden sollen.
     *
     * @return Eine mehrzeilige Zeichenkette mit einem Eintrag pro Zeile. Bei
     *     einem leeren Argument-Array wird eine leere Zeichenkette
     *     zurückgegeben.
     *
     * @since 0.45.0
     */
    public static String convertToMultilineString(Object... content)
    {
        if (content == null)
        {
            return convertToString(null);
        }
        return java.util.Arrays.stream(content)
            .map(TextUtil::convertToString)
            .collect(Collectors.joining(LINE_SEPARATOR));
    }
}
