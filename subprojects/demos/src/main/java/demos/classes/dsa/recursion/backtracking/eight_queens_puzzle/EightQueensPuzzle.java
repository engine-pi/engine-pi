/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025, 2026 Josef Friedrich and contributors.
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
package demos.classes.dsa.recursion.backtracking.eight_queens_puzzle;

import pi.Controller;
import pi.Scene;
import pi.actor.Counter;

/**
 * Löst das Problem der N Damen.
 *
 * <p>
 * Der ursprünglich deutsche Name dieser Klasse war {@code Damen}.
 * </p>
 *
 * @author Albert Wiedemann
 * @author Josef Friedrich (englische Übersetzung, Array statt Arraylist)
 *
 * @version 1.0
 *
 * @since 0.33.0
 */
public class EightQueensPuzzle
{
    /**
     * Speichert die bisher gefundenen Lösungen.
     */
    private Counter foundSolutions;

    /**
     * <p>
     * Der ursprünglich deutsche Name dieses Attributes war {@code schachbrett}.
     * </p>
     */
    private boolean[][] queenPositions;

    private Chessboard chessboard;

    /**
     * Legt das Schachbrett an.
     *
     * @param numberOfQueens Die Anzahl der Damen (und damit auch die Größe des
     *     Schachbretts.
     */
    public EightQueensPuzzle(int numberOfQueens)
    {
        queenPositions = new boolean[numberOfQueens][numberOfQueens];
        Scene scene = new Scene();
        scene.meter(50);
        scene.camera().focus(4, 4);
        chessboard = new Chessboard(scene, numberOfQueens);
        // chessboard.disableHighlighting();
        foundSolutions = new Counter();
        foundSolutions.anchor(10, 4);
        foundSolutions.color("white");
        scene.add(foundSolutions);
        Controller.start(scene);
        makeStep(0);
    }

    /**
     * Testet, ob das angegebene Element des Schachbretts durch eine der bisher
     * aufgestellten Damen bedroht ist.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code IstFeldBedroht}.
     * </p>
     *
     * @param row Die Reihen- bzw. Zeilennummer eines Felds. {@code 0} ist die
     *     untereste Reihe. Laut englischer Schachfachsprache müsste der
     *     Parameter eigentlich {@code rank} heißen. Der ursprünglich deutsche
     *     Name dieses Parameters war {@code zeile}.
     * @param column Die Linien- bzw. Spaltennummer eines Felds. {@code 0} ist
     *     die Linie ganz links. Laut englischer Schachfachsprache müsste der
     *     Parameter eigentlich {@code file} heißen. Der ursprünglich deutsche
     *     Name dieses Parameters war {@code spalte}.
     */
    boolean isSquareThreatened(int row, int column)
    {
        // aktuelle Reihe bzw. Zeile
        int r = row;
        // aktuelle Linie bzw. Spalte
        int c = column - 1;

        if (c >= 0 && r >= 0)
        {
            chessboard.highlightSquare(r, c);
            chessboard.highlightSquare(r, c);
            chessboard.highlightSquare(r, c);
        }

        // Überprüfen der aktuellen waagrechten Reihe bzw. Zeile nach links
        while (c >= 0)
        {
            chessboard.highlightSquare(r, c);
            if (queenPositions[r][c])
            {
                return true;
            }
            c -= 1;
        }

        // Überprüfen der Diagonalen nach links unten
        c = column - 1;
        r = row - 1;
        while (c >= 0 && r >= 0)
        {
            chessboard.highlightSquare(r, c);
            if (queenPositions[r][c])
            {
                return true;
            }
            c -= 1;
            r -= 1;
        }

        // Überprüfen der Diagonalen nach links oben
        c = column - 1;
        r = row + 1;
        while (c >= 0 && r < queenPositions.length)
        {
            chessboard.highlightSquare(r, c);
            if (queenPositions[r][c])
            {
                return true;
            }
            c -= 1;
            r += 1;
        }
        return false;
    }

    /**
     * Gibt die gefundene Lösung aus.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war {@code LösungAusgeben}.
     * </p>
     */
    void printSolution()
    {
        System.out.println("Lösung:");
        for (int column = 0; column < queenPositions.length; column += 1)
        {
            for (int row = 0; row < queenPositions.length; row += 1)
            {
                if (queenPositions[row][column])
                {
                    System.out.print(" " + row);
                }
            }
        }
        System.out.println();
    }

    /**
     * Macht einen Rekursionsschritt auf dem Weg zur Lösung.
     *
     * <p>
     * Der ursprünglich deutsche Name dieser Methode war
     * {@code SchrittAusführen}.
     * </p>
     *
     * @param column Die Linien- bzw. Spaltennummer eines Felds. {@code 0} ist
     *     die Linie ganz links. Laut englischer Schachfachsprache müsste der
     *     Parameter eigentlich {@code file} heißen. Der ursprünglich deutsche
     *     Name dieses Parameters war {@code spalte}.
     */
    void makeStep(int column)
    {
        for (int row = 0; row < queenPositions.length; row++)
        {
            if (!isSquareThreatened(row, column))
            {
                queenPositions[row][column] = true;
                if (column == queenPositions.length - 1)
                {
                    foundSolutions.increase();
                    printSolution();
                    chessboard.setQueens(queenPositions, 2000);
                }
                else
                {
                    chessboard.setQueens(queenPositions, 120);
                    makeStep(column + 1);
                }
                queenPositions[row][column] = false;
            }
        }
    }

    public static void main(String[] args)
    {
        new EightQueensPuzzle(8);
    }
}
