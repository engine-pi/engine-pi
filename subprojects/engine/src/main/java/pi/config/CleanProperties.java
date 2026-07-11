/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/configuration/CleanProperties.java
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
package pi.config;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serial;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;

/**
 * Eine benutzerdefinierte Implementierung der Klasse {@link Properties}, die
 * die Schlüssel sortiert und beim Speichern die erste Kommentarzeile entfernt.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
class CleanProperties extends Properties
{
    @Serial
    private static final long serialVersionUID = 7567765340218227372L;

    /**
     * Gibt eine Enumeration der Schlüssel in dieser Property-Liste zurück,
     * aufsteigend sortiert.
     *
     * @return Eine Enumeration der Schlüssel in dieser Property-Liste.
     */
    @Override
    public synchronized Enumeration<Object> keys()
    {
        return Collections.enumeration(new TreeSet<>(super.keySet()));
    }

    /**
     * Schreibt diese Property-Liste (Schlüssel-Wert-Paare) dieser
     * {@link Properties}-Tabelle in einem Format in den Ausgabestrom, das mit
     * der Methode {@code load} wieder in eine {@link Properties}-Tabelle
     * geladen werden kann. Diese Implementierung entfernt die erste
     * Kommentarzeile.
     *
     * @param out ein Ausgabestrom.
     * @param comments eine Beschreibung der Property-Liste.
     *
     * @throws IOException falls ein I/O-Fehler auftritt.
     */
    @Override
    public void store(final OutputStream out, final String comments)
            throws IOException
    {
        super.store(new StripFirstLineStream(out), null);
    }

    /**
     * Ein benutzerdefinierter {@link FilterOutputStream}, der die erste
     * Ausgabezeile entfernt.
     */
    private static class StripFirstLineStream extends FilterOutputStream
    {
        private boolean firstlineseen = false;

        /**
         * Erzeugt einen neuen {@link StripFirstLineStream}.
         *
         * @param out Der zugrunde liegende Ausgabestrom.
         */
        public StripFirstLineStream(final OutputStream out)
        {
            super(out);
        }

        /**
         * Schreibt das angegebene Byte in diesen Ausgabestrom. Diese
         * Implementierung überspringt die erste Zeile.
         *
         * @param b Das zu schreibende Byte.
         *
         * @throws IOException Falls ein I/O-Fehler auftritt.
         */
        @Override
        public void write(final int b) throws IOException
        {
            if (firstlineseen)
            {
                out.write(b);
            }
            else if (b == '\n')
            {
                firstlineseen = true;
            }
        }

        /**
         * Schreibt {@code len} Bytes aus dem angegebenen Byte-Array beginnend
         * bei Offset {@code off} in diesen Ausgabestrom. Diese Implementierung
         * überspringt die erste Zeile.
         *
         * @param b Die Daten.
         * @param off Der Start-Offset in den Daten.
         * @param len Die Anzahl der zu schreibenden Bytes.
         *
         * @throws IOException Falls ein I/O-Fehler auftritt.
         */
        @Override
        public void write(byte[] b, int off, int len) throws IOException
        {
            while (!firstlineseen)
            {
                if (b[off++] == '\n')
                {
                    firstlineseen = true;
                }
                if (--len == 0)
                {
                    return;
                }
            }
            out.write(b, off, len);
        }
    }
}
