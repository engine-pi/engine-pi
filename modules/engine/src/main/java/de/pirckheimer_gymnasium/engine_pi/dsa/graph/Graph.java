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

import java.util.ArrayList;

/**
 * Ein <b>Graph</b>.
 *
 * <p>
 * Eine abstrakte Basisklasse als Grundlage zum Vererben für unterschiedliche
 * Graph-Implementationen, z. B. eine Implementation eines Graphen durch eine
 * Adjazenzmatrix oder durch eine Adjazenzliste. Diese Klasse wird auch als
 * Datenbasis zum grafischen Zeichnen des Graphen verwendet.
 * </p>
 *
 * @see <a href=
 *     "https://github.com/bschlangaul-sammlung/java-fuer-examens-aufgaben/blob/main/src/main/java/org/bschlangaul/graph/Graph.java">Bschlangaul-Sammlung:
 *     Graph.java</a>
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public abstract class Graph
{
    /**
     * Alle Knoten des Graphen
     */
    private ArrayList<GraphNode> nodes;

    /**
     * Alle Kanten des Graphen.
     */
    private ArrayList<GraphEdge> edges;

    public Graph()
    {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    /**
     * Importiert alle Knoten und Kanten eines anderen Graphen.
     *
     * @param graph Der Graph der importiert werden soll.
     */
    public void importGraph(Graph graph)
    {
        for (GraphNode node : graph.getNodes())
        {
            addNode(node);
        }

        for (GraphEdge edge : graph.getEdges())
        {
            addEdge(edge);
        }
    }

    /**
     * Gibt die <b>Anzahl der Knoten</b> des Graphen zurück.
     *
     * @return Die <b>Anzahl der Knoten</b>.
     */
    public int getNodeCount()
    {
        return nodes.size();
    }

    /**
     * Gibt die <b>Anzahl der Kanten</b> des Graphen zurück.
     *
     * @return Die <b>Anzahl der Kanten</b>.
     */
    public int getEdgeCount()
    {
        return edges.size();
    }

    /**
     * Gibt den Index bzw {@literal .} die interne Nummer des Knoten zurück.
     *
     * @param label Der <b>Bezeichner</b> des Knoten, der gesucht wird.
     *
     * @return Die Indexnummer des Knotens im Knotenarray;
     *     {@code 0 &lt;= x &lt;= anzahl-1}
     *
     * @throws RuntimeException Falls kein Knoten über den gegeben Bezeichner
     *     gefunden werden konnte.
     */
    public int getNodeIndex(String label)
    {
        for (int i = 0; i < nodes.size(); i++)
        {
            if (nodes.get(i).getLabel().equals(label))
            {
                return i;
            }
        }
        throw new RuntimeException(String.format(
                "Es konnte kein Knoten mit dem Bezeichner „%s“ gefunden werden.",
                label));
    }

    /**
     * @since 0.37.0
     */
    public int getNodeIndex(GraphNode node)
    {
        return getNodeIndex(node.getLabel());
    }

    /**
     * Gibt den Index bzw{@literal .} die interne Nummer des Knoten zurück oder
     * {@code -1} falls der Knoten nicht gefunden werden konnte.
     *
     * @param label Der <b>Bezeichner</b> des Knoten, der gesucht wird.
     *
     * @return Die Indexnummer des Knotens im Knotenarray;
     *     {@code 0 &lt;= x &lt;= anzahl-1} bzw. {@code -1}
     */
    public int getNodeIndexSafe(String label)
    {
        int index = -1;
        try
        {
            index = getNodeIndex(label);
        }
        catch (Exception e)
        {
            // ignore
        }
        return index;
    }

    public GraphNode getNode(String label)
    {
        return nodes.get(getNodeIndex(label));
    }

    public GraphNode getNode(int index)
    {
        return nodes.get(index);
    }

    /**
     * Gibt die Bezeichnung eines Knotens mit der internen Knotennummer.
     *
     * @param index Indexnummer des Knotens im Knotenarray;
     *     {@code 0 &lt;= x &lt;=
     *     anzahl-1}
     *
     * @return Der Bezeichner des Knoten.
     */
    public String getNodeLabel(int index)
    {
        return nodes.get(index).getLabel();
    }

    /**
     * <b>Fügt</b> einen neuen Knoten in den Datenstruktur (z. B. Matrix oder
     * Liste) der aktuellen Graph-Implementation <b>ein</b>.
     *
     * <p>
     * Diese Methode wird aufgerufen, wenn bereits ein neuer Knoten vom Datentyp
     * {@link GraphNode} erzeugt wurde. Außerdem ist schon überprüft worden, ob
     * der Bezeichner des Knotens eindeutig ist.
     * </p>
     *
     * @param label Der <b>Bezeichner</b> des neuen Knotens, der dem Graphen
     *     hinzugefügt wird.
     * @param x Die <b>x-Koordinate</b> des Knotens in Meter.
     * @param y Die <b>y-Koordinate</b> des Knotens in Meter.
     */
    protected abstract void addNodeIntoDataStructure(String label, double x,
            double y);

    /**
     * <b>Fügt</b> einen neuen Knoten in den Graphen <b>ein</b>.
     *
     * @param label Der <b>Bezeichner</b> des neuen Knotens, der dem Graphen
     *     hinzugefügt wird.
     * @param x Die <b>x-Koordinate</b> des Knotens in Meter.
     * @param y Die <b>y-Koordinate</b> des Knotens in Meter.
     *
     * @throws RuntimeException Wenn die Bezeichnung des Knotens bereits
     *     existiert.
     */
    public void addNode(String label, double x, double y)
    {

        if (getNodeIndexSafe(label) > -1)
        {
            throw new RuntimeException("Ein Knoten mit der Bezeichnung " + label
                    + " existiert bereits.");
        }
        nodes.add(new GraphNode(label, x, y));
        addNodeIntoDataStructure(label, x, y);
    }

    /**
     * <b>Fügt</b> einen neuen Knoten in den Graphen <b>ein</b>.
     *
     * @param label Der <b>Bezeichner</b> des neuen Knotens, der dem Graphen
     *     hinzugefügt wird.
     *
     * @throws RuntimeException Wenn die Bezeichnung des Knotens bereits
     *     existiert.
     */
    public void addNode(String label)
    {
        addNode(label, 0, 0);
    }

    /**
     * Fügt eine Kopie des übergebenen {@link GraphNode}-Objekt dem Graphen
     * hinzu.
     *
     * @param node der einzufügende Knoten; darf nicht {@code null} sein
     *
     * @throws NullPointerException wenn {@code node} {@code null} ist
     *
     * @see #addNode(String, int, int)
     */
    public void addNode(GraphNode node)
    {
        addNode(node.getLabel(), node.getX(), node.getY());
    }

    /**
     * <b>Einfügen</b> einer Kante in den Graphen.
     *
     * <p>
     * Die Kante ist durch einen <b>Anfangsknoten</b> und einen <b>Endknoten</b>
     * festgelegt, hat eine <b>Gewichtung</b> und kann <b>gerichtet</b> sein.
     * </p>
     *
     * @param from Der <b>Bezeichner</b> des Anfangsknotens.
     * @param to Der <b>Bezeichner</b> des Endknotens.
     * @param weight Die <b>Gewichtung</b> der Kante.
     * @param directed Ist die Kante <b>gerichtet</b>?
     */
    protected abstract void addEdgeIntoDataStructure(String from, String to,
            int weight, boolean directed);

    /**
     * <b>Einfügen</b> einer Kante in den Graphen.
     *
     * <p>
     * Die Kante ist durch einen <b>Anfangsknoten</b> und einen <b>Endknoten</b>
     * festgelegt, hat eine <b>Gewichtung</b> und kann <b>gerichtet</b> sein.
     * </p>
     *
     * @param from Der <b>Bezeichner</b> des Anfangsknotens.
     * @param to Der <b>Bezeichner</b> des Endknotens.
     * @param weight Die <b>Gewichtung</b> der Kante.
     * @param directed Ist die Kante <b>gerichtet</b>?
     */
    public void addEdge(String from, String to, int weight, boolean directed)
    {
        edges.add(new GraphEdge(getNode(from), getNode(to), weight, directed));
        addEdgeIntoDataStructure(from, to, weight, directed);
    }

    /**
     * <b>Einfügen</b> einer Kante in den Graphen.
     *
     * <p>
     * Die Kante ist durch einen <b>Anfangsknoten</b> und einen <b>Endknoten</b>
     * festgelegt, hat eine <b>Gewichtung</b> und ist <b>ungerichtet</b>.
     * </p>
     *
     * @param from Der <b>Bezeichner</b> des Anfangsknotens.
     * @param to Der <b>Bezeichner</b> des Endknotens.
     * @param weight Die <b>Gewichtung</b> der Kante.
     */
    public void addEdge(String from, String to, int weight)
    {
        addEdge(from, to, weight, false);
    }

    /**
     * <b>Einfügen</b> einer Kante in den Graphen.
     *
     * <p>
     * Die Kante ist durch einen <b>Anfangsknoten</b> und einen <b>Endknoten</b>
     * festgelegt, hat eine <b>Gewichtung von 1</b> und ist <b>ungerichtet</b>.
     * </p>
     *
     * @param from Der <b>Bezeichner</b> des Anfangsknotens.
     * @param to Der <b>Bezeichner</b> des Endknotens.
     */
    public void addEdge(String from, String to)
    {
        addEdge(from, to, 1);
    }

    public void addEdge(GraphEdge edge)
    {
        addEdge(edge.getFrom().getLabel(), edge.getTo().getLabel(),
                edge.getWeight(), edge.isDirected());
    }

    public ArrayList<GraphNode> getNodes()
    {
        return nodes;
    }

    /**
     * Gibt die <b>Kante</b> anhand des Indexes zurück.
     *
     * @param index Der 0-basierte Index der gewünschten Kante.
     *
     * @return Die Kante
     *
     * @throws IndexOutOfBoundsException wenn der Index außerhalb des gültigen
     *     Bereichs liegt
     */
    public GraphEdge getEdge(int index)
    {
        return edges.get(index);
    }

    /**
     * Gibt die Liste aller Kanten dieses Graphen zurück.
     *
     * @return Die Liste aller Kanten dieses Graphen.
     */
    public ArrayList<GraphEdge> getEdges()
    {
        return edges;
    }

    /**
     * Exportiert den Graphen, indem eine Zeichenkette generiert wird, die als
     * Java-Code verwendet werden kann.
     *
     * @since 0.37.0
     */
    public String generateJavaCode()
    {
        ArrayList<String> lines = new ArrayList<>();
        if (getNodeCount() > 0)
        {
            lines.add("// Anlegen der Knoten");

            for (GraphNode node : getNodes())
            {
                lines.add(node.generateJavaCode());
            }
        }
        if (getEdgeCount() > 0)
        {
            lines.add("// Anlegen der Kanten");
            for (GraphEdge edge : getEdges())
            {
                lines.add(edge.generateJavaCode());
            }
        }
        return String.join(System.lineSeparator(), lines);
    }
}
