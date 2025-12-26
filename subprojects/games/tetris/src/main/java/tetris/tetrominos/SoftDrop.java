/*
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
package tetris.tetrominos;

/**
 * Ein <b>Soft Drop</b> ist eine Bewegung, bei dem ein Tetromino seine
 * Abwärtsbewegung beschleunigt. Die Anzahl der Punkte entspricht dabei der
 * Zeilenanzahl der kontinuierlichen, d. h. nicht unterbrochenen
 * Soft-Drop-Bewegung.
 *
 * @author Josef Friedrich
 */
public class SoftDrop
{
    /**
     * Die y-Koordinate, bei der die Soft-Drop-Bewegung gestartet wurde.
     */
    private final int y;

    private final Tetromino tetromino;

    public SoftDrop(Tetromino tetromino)
    {
        y = tetromino.getY();
        this.tetromino = tetromino;
    }

    /**
     * Gibt die Distanz in der Zeilenanzahl an, wie weit das Tetromino mittels
     * Soft-Drop nach unten bewegt wurde.
     *
     * @return Die Distanz in Zeilen der Soft-Drop-Bewegung.
     */
    public int getDistance()
    {
        int result = y - tetromino.getY();
        assert result > -1;
        return result;
    }
}
