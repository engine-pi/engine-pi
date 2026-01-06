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
package pi.dsa.graph;

import static pi.dsa.graph.GraphEdge.NOT_REACHABLE_WEIGHT;

import java.util.ArrayList;

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
     * Erzeugt einen neuen Graphen unter Angabe der <b>Größe der Matrix</b>. Die
     * Matrix wird automatisch vergrößert.
     *
     * @param matrixSize Die <b>Größe der Matrix</b>.
     */
    public GraphArrayMatrix(int matrixSize)
    {
        super();
        matrix = new int[matrixSize][matrixSize];
    }

    /**
     * Erstellt eine Adjazenz-Matrix mit der Größe von {@code 42}. Die Matrix
     * wird automatisch vergrößert.
     */
    public GraphArrayMatrix()
    {
        this(42);
    }

    /**
     * Vergrößert die aktuelle Adjazenzmatrix, um mehr Knoten im Graphen zu
     * ermöglichen.
     *
     * <p>
     * Die neue Matrix wird um 10 Zeilen und Spalten erweitert. Alle neuen
     * Einträge werden mit dem Standardwert {@code NOT_REACHABLE_WEIGHT}
     * initialisiert. Bestehende Werte aus der alten Matrix werden in die neue
     * Matrix kopiert.
     * </p>
     */
    private void enlargeMatrix()
    {
        int newSize = matrix.length + 10;
        int[][] newMatrix = new int[newSize][newSize];

        // Initialisiert die neue Matrix mit dem „nicht
        // erreichbar“-Standard-Wert
        for (int i = 0; i < newSize; i++)
        {
            for (int j = 0; j < newSize; j++)
            {
                newMatrix[i][j] = NOT_REACHABLE_WEIGHT;
            }
        }

        // Kopiert die bestehenden Gewichte in die neue Matrix
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix.length; j++)
            {
                newMatrix[i][j] = matrix[i][j];
            }
        }

        matrix = newMatrix;
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
        return matrix[nodeIndex(from)][nodeIndex(to)];
    }

    protected void addNodeIntoDataStructure(String label, double x, double y)
    {
        int index = nodeCount() - 1;
        if (index >= matrix.length)
        {
            enlargeMatrix();
        }
        matrix[index][index] = 0;
        for (int i = 0; i < index; i++)
        {
            // Der neu eingefügte Knoten hat noch keine Kanten zu den bereits
            // bestehenden Knoten.
            matrix[index][i] = NOT_REACHABLE_WEIGHT;
            matrix[i][index] = NOT_REACHABLE_WEIGHT;
        }
    }

    protected void addEdgeIntoDataStructure(String from, String to, int weight,
            boolean directed)
    {
        int fromIndex = nodeIndex(from);
        int toIndex = nodeIndex(to);
        if (fromIndex != toIndex)
        {
            matrix[fromIndex][toIndex] = weight;
            if (!directed)
            {
                matrix[toIndex][fromIndex] = weight;
            }
        }
    }

    public ArrayList<EdgesOfNodePair> getAllEdgesOfNodePairs()
    {
        ArrayList<EdgesOfNodePair> all = new ArrayList<>();
        for (int nodeIndex = 0; nodeIndex < nodeCount(); nodeIndex++)
        {
            for (int i = 0; i < nodeIndex; i++)
            {
                EdgesOfNodePair pair = new EdgesOfNodePair(this);
                if (matrix[nodeIndex][i] > 0)
                {
                    pair.addDirectedEdge(nodeIndex, i, matrix[nodeIndex][i]);
                }
                if (matrix[i][nodeIndex] > 0)
                {
                    pair.addDirectedEdge(i, nodeIndex, matrix[i][nodeIndex]);
                }
                if (!pair.isEmpty())
                {
                    all.add(pair);
                }
            }
        }
        return all;
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
        for (int i = 0; i < nodeCount(); i++)
        {
            System.out.print(node(i).formattedLabel(width));
        }
        System.out.println();
        for (int i = 0; i < nodeCount(); i++)
        {
            System.out.print(node(i).formattedLabel(width));
            for (int j = 0; j < nodeCount(); j++)
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
