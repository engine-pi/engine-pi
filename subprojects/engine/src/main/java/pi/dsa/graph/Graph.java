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

import java.util.ArrayList;

import pi.annotations.Getter;

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
        for (GraphNode node : graph.Nodes())
        {
            addNode(node);
        }

        for (GraphEdge edge : graph.edges())
        {
            addEdge(edge);
        }
    }

    /**
     * Gibt die <b>Anzahl der Knoten</b> des Graphen zurück.
     *
     * @return Die <b>Anzahl der Knoten</b>.
     */
    @Getter
    public int nodeCount()
    {
        return nodes.size();
    }

    /**
     * Gibt die <b>Anzahl der Kanten</b> des Graphen zurück.
     *
     * @return Die <b>Anzahl der Kanten</b>.
     */
    @Getter
    public int edgeCount()
    {
        return edges.size();
    }

    /**
     * Gibt den Index bzw {@literal .} die interne Nummer des Knotens zurück.
     *
     * @param label Der <b>Bezeichner</b> des Knotens, der gesucht wird.
     *
     * @return Die Indexnummer des Knotens im Knotenarray;
     *     {@code 0 &lt;= x &lt;= anzahl-1}
     *
     * @throws RuntimeException Falls kein Knoten über den gegeben Bezeichner
     *     gefunden werden konnte.
     */
    @Getter
    public int nodeIndex(String label)
    {
        for (int i = 0; i < nodes.size(); i++)
        {
            if (nodes.get(i).label().equals(label))
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
    @Getter
    public int nodeIndex(GraphNode node)
    {
        return nodeIndex(node.label());
    }

    /**
     * Gibt den Index bzw{@literal .} die interne Nummer des Knotens zurück oder
     * {@code -1} falls der Knoten nicht gefunden werden konnte.
     *
     * @param label Der <b>Bezeichner</b> des Knotens, der gesucht wird.
     *
     * @return Die Indexnummer des Knotens im Knotenarray;
     *     {@code 0 &lt;= x &lt;= anzahl-1} bzw. {@code -1}
     */
    @Getter
    public int nodeIndexSafe(String label)
    {
        int index = -1;
        try
        {
            index = nodeIndex(label);
        }
        catch (Exception e)
        {
            // ignore
        }
        return index;
    }

    @Getter
    public GraphNode node(String label)
    {
        return nodes.get(nodeIndex(label));
    }

    @Getter
    public GraphNode node(int index)
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
     * @return Der Bezeichner des Knotens.
     */
    @Getter
    public String nodeLabel(int index)
    {
        return nodes.get(index).label();
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

        if (nodeIndexSafe(label) > -1)
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
        addNode(node.label(), node.x(), node.y());
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
        edges.add(new GraphEdge(node(from), node(to), weight, directed));
        addEdgeIntoDataStructure(from, to, weight, directed);
    }

    /**
     * <b>Einfügen</b> einer Kante in den Graphen.
     *
     * <p>
     * Die Kante ist durch einen <b>Anfangsknoten</b>, einen <b>Endknoten</b>
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
        addEdge(edge.from().label(), edge.to().label(), edge.weight(),
                edge.isDirected());
    }

    @Getter
    public ArrayList<GraphNode> Nodes()
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
    @Getter
    public GraphEdge edge(int index)
    {
        return edges.get(index);
    }

    /**
     * Gibt die Liste aller Kanten dieses Graphen zurück.
     *
     * @return Die Liste aller Kanten dieses Graphen.
     */
    @Getter
    public ArrayList<GraphEdge> edges()
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
        if (nodeCount() > 0)
        {
            lines.add("// Anlegen der Knoten");

            for (GraphNode node : Nodes())
            {
                lines.add(node.generateJavaCode());
            }
        }
        if (edgeCount() > 0)
        {
            lines.add("// Anlegen der Kanten");
            for (GraphEdge edge : edges())
            {
                lines.add(edge.generateJavaCode());
            }
        }
        return String.join(System.lineSeparator(), lines);
    }
}
