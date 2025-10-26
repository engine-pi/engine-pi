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
package de.pirckheimer_gymnasium.engine_pi.dsa.graph;

/**
 * Ein Graph, der über ein zweidimensionales Feld / Array implementiert ist.
 *
 * @see <a href=
 *     "https://github.com/bschlangaul-sammlung/java-fuer-examens-aufgaben/blob/main/src/main/java/org/bschlangaul/graph/GraphAdjazenzMatrix.java">Bschlangaul-Sammlung:
 *     GraphAdjazenzMatrix.java</a>
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class GraphArrayMatrix extends Graph
{
    /**
     * Das 2-dimensionale Feld der Adjazenzmatrix.
     */
    protected int[][] matrix;

    /**
     * Erzeugt einen neuen Graphen durch Angabe der <b>maximale Anzahl der
     * Knoten</b>.
     *
     * @param maxNodes Die <b>Anzahl der maximal möglichen Knoten</b>.
     */
    public GraphArrayMatrix(int maxNodes)
    {
        super();
        matrix = new int[maxNodes][maxNodes];
    }

    /**
     * Erstelle eine Adjazenz-Matrix die <b>100 Knoten</b> aufnehmen kann.
     */
    public GraphArrayMatrix()
    {
        this(100);
    }

    /**
     * Gibt die <b>Gewichtung</b> einer Kante zurück.
     *
     * <p>
     * Die Kante ist durch einen Anfangsknoten und einen Endknoten festgelegt.
     * </p>
     *
     * @param from Der Bezeichner des <b>Anfangsknotens</b>.
     * @param to Der Bezeichner des <b>Endknotens</b>.
     *
     * @return Die <b>Gewichtung</b> der Kante.
     */
    public int getEdgeWeight(String from, String to)
    {
        int fromIndex = getNodeIndex(from);
        int toIndex = getNodeIndex(to);
        if (fromIndex != -1 && toIndex != -1)
        {
            return matrix[fromIndex][toIndex];
        }
        return -1;
    }

    protected void addNodeIntoDataStructure(String label, double x, double y)
    {
        int index = getNodesCount() - 1;
        if (index >= matrix.length)
        {
            throw new RuntimeException(
                    "Die maximale Anzahl an Knoten wurde überschritten: "
                            + getNodesCount());
        }
        matrix[index][index] = 0;
        for (int i = 0; i < index; i++)
        {
            // Symmetrie, da ungerichteter Graph
            matrix[index][i] = -1;
            matrix[i][index] = -1;
        }
    }

    public void addEdgeIntoDataStructure(String from, String to, int weight,
            boolean directed)
    {
        int fromIndex = getNodeIndex(from);
        int toIndex = getNodeIndex(to);
        if (fromIndex != -1 && toIndex != -1 && fromIndex != toIndex)
        {
            matrix[fromIndex][toIndex] = weight;
            if (!directed)
            {
                matrix[toIndex][fromIndex] = weight;
            }
        }
    }

    /**
     * Gibt die Adjazenzmatrix des Graphen nach Zeilen und Spalten formatiert in
     * der Konsole aus. Als Spaltenbreite wurde hier 4 Zeichen gewählt.
     */
    public void print()
    {
        int width = 4;
        String whiteSpace = " ".repeat(4);
        // Kopfzeile
        System.out.print(whiteSpace);
        for (int i = 0; i < getNodesCount(); i++)
        {
            System.out.print(getNode(i).getFormattedLabel(width));
        }
        System.out.println();
        for (int i = 0; i < getNodesCount(); i++)
        {
            System.out.print(getNode(i).getFormattedLabel(width));
            for (int j = 0; j < getNodesCount(); j++)
            {
                if (matrix[i][j] != -1)
                {
                    System.out.print(
                            (matrix[i][j] + whiteSpace).substring(0, width));
                }
                else
                {
                    System.out.print(whiteSpace);
                }
            }
            System.out.println();
        }
    }
}
