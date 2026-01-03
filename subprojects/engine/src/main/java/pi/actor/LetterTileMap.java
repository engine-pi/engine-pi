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
package pi.actor;

import java.util.HashMap;
import java.util.HashSet;

import pi.annotations.Getter;
import pi.annotations.Setter;

/**
 * Ein Kachelsatz (tile map), bei dem die einzelnen Kacheln (tile) durch
 * Buchstaben (letter) repräsentiert sind.
 *
 * Die Größe der Kachel wird auf 1 x 1 Pixelmeter und die linke oberen Ecke an
 * die Position -0.5 x 0.5 im Engine-Alpha-Koordinatensystem gesetzt, sodass zum
 * Beispiel (0,0) die Mitte der ersten Kachel (links oben) adressiert.
 *
 * @author Josef Friedrich
 *
 * @since 0.26.0
 */
abstract class LetterTileMap
{
    HashMap<Character, Tile> tiles;

    public TileRegistration container;

    /**
     * x-Koordinate im Engine-Alpha Koordinatensystem. Bezieht sich auf die
     * Mitte der linken oberen Kachel.
     */
    int x;

    /**
     * y-Koordinate im Engine-Alpha Koordinatensystem. Bezieht sich auf die
     * Mitte der linken oberen Kachel.
     */
    int y;

    /**
     * Die Breite des Kachelsatzes, d. h. die Anzahl der Kacheln in der
     * x-Richtung.
     */
    public int width;

    /**
     * Die Höhe des Kachelsatzes, d. h. die Anzahl der Kacheln in der
     * y-Richtung.
     */
    public int height;

    protected char[][] letterMap;

    /**
     * Um doppelte Buchstaben zu verhindern.
     */
    protected HashSet<Character> letters;

    /**
     * Ein Speicher für einprägsamere Namen für eine Kachel als nur der
     * Buchstabe.
     */
    protected HashMap<Character, String> names;

    protected HashMap<String, Character> namesToLetter;

    protected HashSet<Character> obstacles;

    protected String pathPrefix;

    protected String extension;

    public LetterTileMap(int width, int height)
    {
        this(width, height, "", null);
    }

    public LetterTileMap(int width, int height, String pathPrefix)
    {
        this(width, height, pathPrefix, null);
    }

    /**
     * @param width Die Breite des Kachelsatzes bzw. die Anzahl an Kacheln in
     *     x-Richtung.
     * @param height Die Höhe des Kachelsatzes bzw. die Anzahl an Kacheln in
     *     y-Richtung.
     * @param pathPrefix
     * @param extension Die Dateiendung der Bild-Dateien, die als Kacheln
     *     verwendet werden.
     */
    public LetterTileMap(int width, int height, String pathPrefix,
            String extension)
    {
        this.width = width;
        this.height = height;
        letters = new HashSet<>();
        names = new HashMap<>();
        namesToLetter = new HashMap<>();
        letterMap = new char[width][height];
        this.pathPrefix = pathPrefix;
        this.extension = extension;
    }

    protected String assembleFilePath(String filePath)
    {
        String extension;
        if (this.extension != null)
        {
            extension = "." + this.extension;
        }
        else
        {
            extension = "";
        }
        if (!pathPrefix.isEmpty())
        {
            char last = pathPrefix.charAt(pathPrefix.length() - 1);
            if (last != '/')
            {
                pathPrefix = pathPrefix + "/";
            }
        }
        return pathPrefix + filePath + extension;
    }

    protected void createTile(char letter, String filePath)
    {
        tiles.put(letter, TileMap.createFromImage(assembleFilePath(filePath)));
    }

    /**
     * @param x Die x-Position im Kachelgitter. 0 adressiert die erste, (ganz am
     *     linken Rand gelegene) Spalte.
     * @param y Die y-Position im Kachelgitter. 0 adressiert die erste,
     *     (oberste) Zeile.
     */
    @Getter
    protected Tile tileFromCache(int x, int y)
    {
        return tiles.get(letter(x, y));
    }

    @Setter
    protected final void name(char letter, String name)
    {
        if (namesToLetter.get(name) != null)
        {
            throw new IllegalArgumentException(String.format(
                    "Eine Kachel mit dem Namen „%s“ existiert bereits!", name));
        }
        namesToLetter.put(name, letter);
        names.put(letter, name);
    }

    protected final void checkLetterUnset(char letter)
    {
        if (letters.contains(letter))
        {
            throw new IllegalArgumentException(String.format(
                    "Eine Kachel mit dem Buchstaben „%s“ existiert bereits!",
                    letter));
        }
    }

    public final void registerImage(char letter, String filePath, String name)
    {
        if (name == null)
        {
            name = filePath;
        }
        name(letter, name);
        checkLetterUnset(letter);
        letters.add(letter);
        createTile(letter, filePath);
    }

    public final void registerImage(char letter, String filePath)
    {
        registerImage(letter, filePath, null);
    }

    /**
     * @param x Die x-Position im Kachelgitter. 0 adressiert die erste, (ganz am
     *     linken Rand gelegene) Spalte.
     * @param y Die y-Position im Kachelgitter. 0 adressiert die erste,
     *     (oberste) Zeile.
     */
    @Getter
    public final char letter(int x, int y)
    {
        return letterMap[x][y];
    }

    /**
     * Überprüfe, ob ein Buchstabe, der eine Kachel repräsentiert, bereits
     * registiert ist.
     *
     * @param tile Der Buchstabe, der für ein bestimmtes Kachelbild registiert
     *     wurde.
     */
    protected final boolean existsTile(char tile)
    {
        if (tile == ' ')
        {
            return true;
        }
        return names.get(tile) != null;
    }

    /**
     * @throws IllegalArgumentException
     */
    protected final void checkWidth(String row)
    {
        if (row.length() > width)
        {
            throw new IllegalArgumentException(String.format(
                    "Anzahl der Zeichen in einer Reihe (%s) muss kleiner gleich numX (%s) sein!",
                    row, width));
        }
    }

    /**
     * @throws IllegalArgumentException
     */
    protected final void checkHeight(String[] rows)
    {
        if (rows.length > height)
        {
            throw new IllegalArgumentException(String.format(
                    "Anzahl der Reihen (%s) muss kleiner gleich numY (%s) sein!",
                    rows.length, height));
        }
    }

    /**
     * @throws IllegalArgumentException
     */
    protected final void checkLetter(char letter)
    {
        if (!existsTile(letter))
        {
            throw new IllegalArgumentException(String.format(
                    "Unbekannte Kachel mit dem Buchstaben “%s”!", letter));
        }
    }

    public final void parseMap(String[] rows)
    {
        checkHeight(rows);
        int currentRow = 0;
        for (String row : rows)
        {
            row(currentRow, row);
            currentRow++;
        }
    }

    /**
     * Setze ein und dieselbe Kachel auf alle Positionen im Kachelgitter.
     *
     * @param tile Der Buchstabe, der für ein bestimmtes Kachelbild registiert
     *     wurde.
     */
    public final void fill(char tile)
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                tile(x, y, tile);
            }
        }
    }

    /**
     * @param x Die x-Position im Kachelgitter. 0 adressiert die erste, (ganz am
     *     linken Rand gelegene) Spalte.
     * @param y Die y-Position im Kachelgitter. 0 adressiert die erste,
     *     (oberste) Zeile.
     * @param tile Der Buchstabe, der für ein bestimmtes Kachelbild registiert
     *     wurde.
     */
    @Setter
    public void tile(int x, int y, char tile)
    {
        checkLetter(tile);
        letterMap[x][y] = tile;
        container.setTile(x, y, tileFromCache(x, y));
    }

    /**
     * Setzt die Kacheln in einer Zeile von links nach rechts.
     *
     * @param y Die y-Position im Kachelgitter. 0 adressiert die erste,
     *     (oberste) Zeile.
     */
    @Setter
    public final void row(int y, String row)
    {
        checkWidth(row);
        for (int x = 0; x < row.length(); x++)
        {
            tile(x, y, row.charAt(x));
        }
    }

    /**
     * Setzt die Kacheln in einer Spalte von oben nach unten.
     *
     * @param x Die x-Position im Kachelgitter. 0 adressiert die erste, (ganz am
     *     linken Rand gelegene) Spalte.
     */
    @Setter
    public final void column(int x, String column)
    {
        for (int y = 0; y < column.length(); y++)
        {
            tile(x, y, column.charAt(y));
        }
    }

    @Setter
    public void position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
