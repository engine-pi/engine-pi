/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/test/java/de/gurkenlabs/litiengine/configuration/ConfigurationTests.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2025 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pi.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Eine Sammlung von statischen Hilfsmethoden für <b>Feld/Array</b>-Aufgaben.
 *
 * <p>
 * Diese Klasse stellt verschiedene Hilfsfunktionen zum Bearbeiten von Arrays
 * zur Verfügung, unter anderem für Verkettung, Aufteilung, Verbindung und
 * weitere Operationen.
 * </p>
 *
 * <p>
 * Hinweis: Diese Klasse kann nicht instanziiert werden.
 * </p>
 */
public final class ArrayUtil
{
    private static final Logger log = Logger
        .getLogger(ArrayUtil.class.getName());

    /**
     * Dieser private Konstruktor dient dazu, die Instanziierung der Klasse zu
     * verhindern. Dadurch ist es nicht möglich, Instanzen dieser Klasse zu
     * erstellen.
     *
     * @throws UnsupportedOperationException Falls versucht wird, eine Instanz
     *     der Klasse zu erzeugen.
     */
    private ArrayUtil()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Teilt die angegebene Zeichenkette mit dem definierten Trennzeichen in ein
     * Integer-Array.
     *
     * @param delimiterSeparatedString Die zu trennende Zeichenkette.
     * @param delimiter Das Trennzeichen, mit dem die Elemente getrennt werden.
     *
     * @return Ein Integer-Array mit allen getrennten Elementen der angegebenen
     *     Zeichenkette.
     */
    public static int[] splitInt(String delimiterSeparatedString,
            String delimiter)
    {
        if (delimiterSeparatedString == null
                || delimiterSeparatedString.isEmpty())
        {
            return new int[0];
        }

        final String[] split = delimiterSeparatedString.split(delimiter);
        int[] integers = new int[split.length];
        if (integers.length == 0)
        {
            return integers;
        }

        for (int i = 0; i < split.length; i++)
        {
            if (split[i] == null || split[i].isEmpty())
            {
                continue;
            }

            try
            {
                integers[i] = Integer.parseInt(split[i]);
            }
            catch (final NumberFormatException e)
            {
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return integers;
    }

    /**
     * Teilt die angegebene Zeichenkette mit dem definierten Trennzeichen in ein
     * Double-Array.
     *
     * @param delimiterSeparatedString Die zu trennende Zeichenkette.
     * @param delimiter Das Trennzeichen, mit dem die Elemente getrennt werden.
     *
     * @return Ein Double-Array mit allen getrennten Elementen der angegebenen
     *     Zeichenkette.
     */
    public static double[] splitDouble(String delimiterSeparatedString,
            String delimiter)
    {
        if (delimiterSeparatedString == null
                || delimiterSeparatedString.isEmpty())
        {
            return new double[0];
        }

        final String[] split = delimiterSeparatedString.split(delimiter);
        double[] doubles = new double[split.length];
        if (doubles.length == 0)
        {
            return doubles;
        }

        for (int i = 0; i < split.length; i++)
        {
            if (split[i] == null || split[i].isEmpty())
            {
                continue;
            }

            try
            {
                doubles[i] = Double.parseDouble(split[i]);
            }
            catch (final NumberFormatException e)
            {
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return doubles;
    }

    /**
     * Prüft, ob das Array den angegebenen Wert enthält.
     *
     * @param arr Das Array, das auf das Element geprüft werden soll.
     * @param value Der zu suchende Wert.
     *
     * @return {@code true}, wenn das Element im Array enthalten ist, sonst
     *     {@code false}.
     */
    public static boolean contains(Object[] arr, Object value)
    {
        for (Object v : arr)
        {
            if (value == null && v == null)
            {
                return true;
            }

            if (v != null && v.equals(value))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Prüft, ob das Array den angegebenen String-Wert enthält.
     *
     * @param arr Das Array, das auf den String geprüft werden soll.
     * @param argument Der zu suchende String-Wert.
     * @param ignoreCase {@code true}, wenn die Groß-/Kleinschreibung beim
     *     Vergleich ignoriert werden soll.
     *
     * @return {@code true}, wenn der String im Array enthalten ist, sonst
     *     {@code false}.
     */
    public static boolean contains(String[] arr, String argument,
            boolean ignoreCase)
    {
        if (arr == null)
        {
            return false;
        }

        for (String arg : arr)
        {
            if (arg != null && !arg.isEmpty()
                    && (ignoreCase && arg.equalsIgnoreCase(argument)
                            || !ignoreCase && arg.equals(argument)))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Entfernt das angegebene Element aus dem Eingabe-Array und gibt ein neues,
     * bereinigtes Array ohne {@code null}-Einträge zurück.
     *
     * @param <T> Der Typ der Array-Elemente.
     * @param input Das ursprüngliche Array.
     * @param deleteItem Das zu entfernende Element.
     *
     * @return Ein neues Array ohne das angegebene Element und ohne
     *     {@code null}-Einträge.
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] remove(T[] input, T deleteItem)
    {
        List<T> result = new ArrayList<>();

        for (T item : input)
        {
            if (!deleteItem.equals(item))
            {
                result.add(item);
            }
        }

        result.removeAll(Collections.singleton(null));
        return result.toArray((T[]) Array
            .newInstance(input.getClass().getComponentType(), result.size()));
    }

    /**
     * Fügt das angegebene Element am Ende des Eingabe-Arrays hinzu und liefert
     * ein neues Array mit der Länge {@code input.length + 1} zurück.
     *
     * @param <T> Der Typ der Array-Elemente.
     * @param input Das ursprüngliche Array.
     * @param addItem Das am Ende hinzuzufügende Element.
     *
     * @return Ein neues Array mit dem am Ende angehängten Element.
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] append(T[] input, T addItem)
    {
        List<T> result = new ArrayList<>(Arrays.asList(input));
        result.add(addItem);

        return result.toArray((T[]) Array
            .newInstance(input.getClass().getComponentType(), result.size()));
    }
}
