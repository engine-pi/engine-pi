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

import pi.annotations.Getter;

/**
 * Stellt eine <b>Kante</b> in einem Graphen dar.
 *
 * <p>
 * Wird ein Graph über eine Adjazenz-Matrix oder -Liste implementiert, ist diese
 * Klasse eigentlich nicht nötig. Beim Einfügen von Kanten in den Graphen wird
 * momentan zusätzlich zu den oben beschriebenen Datenstrukturen auch ein Objekt
 * dieser Klasse erzeugt. Dadurch entstehen Datendoppelungen. Jedoch wird das
 * Zeichnen von Kanten durch diese Klasse vereinfacht.
 * </p>
 *
 * <p>
 * Bei dieser Klasse handelt es sich um eine reine Datenklasse. Mithilfe der
 * Klasse {@link pi.actor.LabeledEdge} kann eine Kante grafisch dargestellt
 * werden.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class GraphEdge
{

    public static int NOT_REACHABLE_WEIGHT = -1;

    /**
     * Der <b>Startknoten</b>.
     */
    private final GraphNode from;

    /**
     * Der <b>Endknoten</b>.
     */
    private final GraphNode to;

    /**
     * Die <b>Gewichtung</b> der Kante.
     */
    private final int weight;

    /**
     * Wahr, falls die Kante <b>gerichtet</b> ist.
     */
    private final boolean directed;

    /**
     * Erzeugt eine Kante durch Angabe des <b>Startknotens</b>, des
     * <b>Endknoten</b>, der <b>Gewichtung</b> und der Information, ob die Kante
     * <b>gerichtet</b> ist.
     *
     * @param from Der <b>Startknoten</b>.
     * @param to Der <b>Endknoten</b>.
     * @param weight Die <b>Gewichtung</b> der Kante.
     * @param directed Wahr, falls die Kante <b>gerichtet</b> ist.
     */
    public GraphEdge(GraphNode from, GraphNode to, int weight, boolean directed)
    {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.directed = directed;
    }

    /**
     * Erzeugt eine ungerichtete Kante durch Angabe des <b>Startknotens</b>, des
     * <b>Endknoten</b> und der <b>Gewichtung</b>.
     *
     * @param from Der <b>Startknoten</b>.
     * @param to Der <b>Endknoten</b>.
     * @param weight Die <b>Gewichtung</b> der Kante.
     *
     * @since 0.37.0
     */
    public GraphEdge(GraphNode from, GraphNode to, int weight)
    {
        this(from, to, weight, false);
    }

    /**
     * Erzeugt eine ungerichtete Kante mit der Gewichtung von 1 durch Angabe des
     * <b>Startknotens</b> und des <b>Endknotens</b>.
     *
     * @param from Der <b>Startknoten</b>.
     * @param to Der <b>Endknoten</b>.
     *
     * @since 0.37.0
     */
    public GraphEdge(GraphNode from, GraphNode to)
    {
        this(from, to, 1);
    }

    /**
     * Gibt dem <b>Startknoten</b> zurück.
     *
     * @return Der <b>Startknoten</b>.
     */
    @Getter
    public GraphNode from()
    {
        return from;
    }

    /**
     * Gibt dem <b>Endknoten</b> zurück.
     *
     * @return Der <b>Endknoten</b>.
     */
    @Getter
    public GraphNode to()
    {
        return to;
    }

    /**
     * Gibt die <b>Gewichtung</b> der Kante zurück.
     *
     * @return Die <b>Gewichtung</b> der Kante.
     */
    @Getter
    public int weight()
    {
        return weight;
    }

    /**
     * Gibt wahr zurück, falls die Kante <b>gerichtet</b> ist.
     *
     * @return Wahr, falls die Kante <b>gerichtet</b> ist.
     */
    public boolean isDirected()
    {
        return directed;
    }

    private String generateJavaCodeToAndFrom(String additionalArguments)
    {
        if (additionalArguments == null)
        {
            additionalArguments = "";
        }
        else
        {
            additionalArguments = ", " + additionalArguments;
        }
        return String.format("g.addEdge(\"%s\", \"%s\"%s);",
            to.label(),
            from.label(),
            additionalArguments);
    }

    /**
     * Exportiert die Kante, indem eine Zeichenkette generiert wird, die als
     * Java-Code verwendet werden kann.
     *
     * @since 0.37.0
     */
    public String generateJavaCode()
    {
        if (!directed && weight != 1)
        {
            return generateJavaCodeToAndFrom(weight + "");
        }
        if (!directed && weight == 1)
        {
            return generateJavaCodeToAndFrom(null);
        }
        return generateJavaCodeToAndFrom(weight + ", " + directed);
    }
}
