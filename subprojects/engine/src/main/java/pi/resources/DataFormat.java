/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/resources/DataFormat.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
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
package pi.resources;

import java.util.ArrayList;

import pi.util.FileUtil;

/**
 * Enthält gemeinsame Hilfsmethoden für verschiedene Klassen zur Verarbeitung
 * von Dateiformaten, zum Beispiel {@code SoundFormat} und {@code ImageFormat}.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 */
public final class DataFormat
{
    /**
     * Dieser private Konstruktor dient dazu, den öffentlichen Konstruktor zu
     * verbergen. Dadurch ist es nicht möglich, Instanzen dieser Klasse zu
     * erstellen.
     *
     * @throws UnsupportedOperationException Falls eine Instanz der Klasse
     *     erzeugt wird.
     */
    private DataFormat()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Ermittelt aus einer Dateiendung den entsprechenden Enum-Wert.
     *
     * <p>
     * Führende Punkte in der Dateiendung werden ignoriert, ebenso die Groß-
     * oder Kleinschreibung.
     * </p>
     *
     * @param extension Der zu prüfende Format-String.
     * @param values Alle erlaubten Enum-Werte.
     * @param defaultValue Der Rückgabewert für unbekannte oder leere Formate.
     * @param <T> Der Typ der Aufzählung.
     *
     * @return Der passende Enum-Wert oder {@code defaultValue}, wenn kein
     *     passendes Format gefunden wird.
     */
    public static <T extends Enum<T>> T get(String extension, T[] values,
            T defaultValue)
    {
        if (extension == null || extension.isEmpty())
        {
            return defaultValue;
        }
        String stripedImageFormat = extension;
        if (stripedImageFormat.startsWith("."))
        {
            stripedImageFormat = extension.substring(1);
        }
        for (T val : values)
        {
            if (stripedImageFormat.equalsIgnoreCase(val.toString()))
            {
                return val;
            }
        }
        return defaultValue;
    }

    /**
     * Prüft, ob die Dateiendung eines Dateinamens in den unterstützten Formaten
     * enthalten ist.
     *
     * @param fileName Der zu prüfende Dateiname.
     * @param values Alle erlaubten Enum-Werte.
     * @param defaultValue Der Enum-Wert, der nicht als unterstützt gilt.
     * @param <T> Der Typ des Format-Enums.
     *
     * @return {@code true}, wenn die Dateiendung unterstützt wird, andernfalls
     *     {@code false}.
     */
    public static <T extends Enum<T>> boolean isSupported(String fileName,
            T[] values, T defaultValue)
    {
        String extension = FileUtil.getExtension(fileName);
        if (extension == null || extension.isEmpty())
        {
            return false;
        }
        for (String supported : getAllExtensions(values, defaultValue))
        {
            if (extension.equalsIgnoreCase(supported))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt alle unterstützten Dateiendungen zurück, ohne den als nicht
     * unterstützt markierten Standardwert.
     *
     * @param values Alle erlaubten Enum-Werte.
     * @param defaultValue Der Enum-Wert, der aus dem Ergebnis ausgeschlossen
     *     wird.
     * @param <T> Der Typ des Format-Enums.
     *
     * @return Ein Array mit allen unterstützten Dateiendungen.
     */
    public static <T extends Enum<T>> String[] getAllExtensions(T[] values,
            T defaultValue)
    {
        ArrayList<String> arrList = new ArrayList<>();
        for (T format : values)
        {
            if (format != defaultValue)
            {
                arrList.add(format.toString());
            }
        }
        return arrList.toArray(new String[arrList.size()]);
    }
}
