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
package pi.debug;

import pi.annotations.API;

/**
 * https://github.com/bschlangaul-sammlung/java-fuer-examens-aufgaben/blob/main/src/main/java/org/bschlangaul/helfer/Farbe.java
 *
 * Bessere Alternative: Jansi
 *
 * unmaintained: https://github.com/fusesource/jansi -> https://central.sonatype.com/artifact/org.fusesource.jansi/jansi
 * Nachfolger: https://github.com/jline/jline3/tree/master/jansi-core/src/main/java/org/jline/jansi -> https://central.sonatype.com/artifact/org.jline/jansi
 */

/**
 * Hilfsklasse zum <b>Einfärben</b> von Texten mit <b>ANSI</b>-Escapesequenzen
 * für die Konsole.
 *
 * <p>
 * Die statischen Methoden umschließen einen beliebigen Wert mit einem
 * Farbpräfix und setzen die Farbe anschließend mit {@link #RESET} zurück.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class AnsiColor
{
    // https://github.com/fusesource/jansi/blob/70186aa41bef6877a83e1a0c3ed7ebe87185f377/src/main/java/org/fusesource/jansi/Ansi.java#L27-L78

    /**
     * ANSI-Sequenz zum Zurücksetzen aller Farben und Formatierungen.
     */
    public static final String RESET = "\u001B[0m";

    /**
     * ANSI-Sequenz für schwarze Schrift.
     */
    public static final String BLACK = "\u001B[30m";

    /**
     * ANSI-Sequenz für rote Schrift.
     */
    public static final String RED = "\u001B[31m";

    /**
     * ANSI-Sequenz für grüne Schrift.
     */
    public static final String GREEN = "\u001B[32m";

    /**
     * ANSI-Sequenz für gelbe Schrift.
     */
    public static final String YELLOW = "\u001B[33m";

    /**
     * ANSI-Sequenz für blaue Schrift.
     */
    public static final String BLUE = "\u001B[34m";

    /**
     * ANSI-Sequenz für magentafarbene Schrift.
     */
    public static final String MAGENTA = "\u001B[35m";

    /**
     * ANSI-Sequenz für cyanfarbene Schrift.
     */
    public static final String CYAN = "\u001B[36m";

    /**
     * ANSI-Sequenz für weiße Schrift.
     */
    public static final String WHITE = "\u001B[37m";

    /**
     * Wandelt ein Objekt in einen <b>schwarzen</b> Text um.
     *
     * @param object Das zu färbende Objekt.
     *
     * @return Die farbig formatierte Zeichenkette.
     */
    @API
    public static String black(Object object)
    {
        return BLACK + object.toString() + RESET;
    }

    /**
     * Wandelt ein Objekt in einen <b>roten</b> Text um.
     *
     * @param object Das zu färbende Objekt.
     *
     * @return Die farbig formatierte Zeichenkette.
     */
    @API
    public static String red(Object object)
    {
        return RED + object.toString() + RESET;
    }

    /**
     * Wandelt ein Objekt in einen <b>grünen</b> Text um.
     *
     * @param object Das zu färbende Objekt.
     *
     * @return Die farbig formatierte Zeichenkette.
     */
    @API
    public static String green(Object object)
    {
        return GREEN + object.toString() + RESET;
    }

    /**
     * Wandelt ein Objekt in einen <b>gelben</b> Text um.
     *
     * @param object Das zu färbende Objekt.
     *
     * @return Die farbig formatierte Zeichenkette.
     */
    @API
    public static String yellow(Object object)
    {
        return YELLOW + object.toString() + RESET;
    }

    /**
     * Wandelt ein Objekt in einen <b>blauen</b> Text um.
     *
     * @param object Das zu färbende Objekt.
     *
     * @return Die farbig formatierte Zeichenkette.
     */
    @API
    public static String blue(Object object)
    {
        return BLUE + object.toString() + RESET;
    }

    /**
     * Wandelt ein Objekt in einen <b>magentafarbenen</b> Text um.
     *
     * @param object Das zu färbende Objekt.
     *
     * @return Die farbig formatierte Zeichenkette.
     */
    @API
    public static String magenta(Object object)
    {
        return MAGENTA + object.toString() + RESET;
    }

    /**
     * Wandelt ein Objekt in einen <b>cyanfarbenen</b> Text um.
     *
     * @param object Das zu färbende Objekt.
     *
     * @return Die farbig formatierte Zeichenkette.
     */
    @API
    public static String cyan(Object object)
    {
        return CYAN + object.toString() + RESET;
    }

    /**
     * Wandelt ein Objekt in einen <b>weißen</b> Text um.
     *
     * @param object Das zu färbende Objekt.
     *
     * @return Die farbig formatierte Zeichenkette.
     */
    @API
    public static String white(Object object)
    {
        return WHITE + object.toString() + RESET;
    }

    /**
     * <b>Entfernt</b> die ANSI-Farben wieder.
     *
     * @param colored Eine Zeichenkette mit ANSI-Escapesequenzen.
     *
     * @return Eine Zeichenkette ohne Farbe.
     *
     * @since 0.42.0
     */
    @API
    public static String remove(String colored)
    {
        return colored.replaceAll("\u001B\\[\\d+m", "");
    }
}
