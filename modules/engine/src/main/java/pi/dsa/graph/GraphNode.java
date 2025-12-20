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

import pi.Vector;

/**
 * Stellt einen <b>Knoten</b> in einem Graphen dar.
 *
 * <p>
 * Bei dieser Klasse handelt es sich um eine reine Datenklasse. Mit Hilfe der
 * Klasse {@link pi.actor.LabeledNode} kann ein Knoten grafisch dargestellt
 * werden.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class GraphNode
{
    /**
     * Die <b>Bezeichnung</b> bzw. der Name des Knotens.
     */
    private String label;

    /**
     * Die <b>x-Koordinate</b> des Knotens in Meter.
     */
    private double x;

    /**
     * Die <b>y-Koordinate</b> des Knotens in Meter.
     */
    private double y;

    /**
     * Erstellt einen neuen Knoten durch Angabe einer <b>Bezeichnung</b>.
     *
     * @param label Die <b>Bezeichnung</b> bzw. der Name des Knotens.
     */
    public GraphNode(String label)
    {
        this.label = label;
    }

    /**
     * Erstellt einen neuen Knoten durch Angabe einer <b>Bezeichnung</b> und
     * durch Angabe einer <b>Koordinate</b>.
     *
     * @param label Die <b>Bezeichnung</b> bzw. der Name des Knotens.
     * @param x Die <b>x-Koordinate</b> des Knotens in Meter.
     * @param y Die <b>y-Koordinate</b> des Knotens in Meter.
     */
    public GraphNode(String label, double x, double y)
    {
        this.label = label;
        this.x = x;
        this.y = y;
    }

    /**
     * Gibt den Bezeichner des Knoten zurück.
     *
     * @return Der Bezeichner des Knotens.
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * Setzt den Bezeichner des Knotens.
     *
     * @param label Der Bezeichner des Knotens.
     */
    public void setLabel(String label)
    {
        this.label = label;
    }

    /**
     * Gibt die <b>x-Koordinate</b> des Knotens in Meter zurück.
     *
     * @return Die <b>x-Koordinate</b> des Knotens in Meter.
     */
    public double getX()
    {
        return x;
    }

    /**
     * Gibt die <b>y-Koordinate</b> des Knotens in Meter zurück.
     *
     * @return Die <b>y-Koordinate</b> des Knotens in Meter.
     */
    public double getY()
    {
        return y;
    }

    public Vector getPosition()
    {
        return new Vector(x, y);
    }

    /**
     * Gibt den <b>Bezeichner</b> des Knotenobjekts <b>formatiert</b> zurück.
     *
     * <p>
     * Der Bezeichner wird auf die angegebene Länge abgeschnitten bzw. mit
     * Leerzeichen aufgefüllt.
     * </p>
     *
     * @param width Anzahl der Zeichen auf die der Bezeichner formatiert wird.
     *     Maximal 15 Zeichen.
     *
     * @return formatierter Bezeichner
     */
    public String getFormattedLabel(int width)
    {
        return (label + "               ").substring(0, width);
    }

    /**
     * Exportiert den Knoten, indem eine Zeichenkette generiert wird, die als
     * Java-Code verwendet werden kann.
     *
     * @since 0.37.0
     */
    public String generateJavaCode()
    {
        return String.format("g.addNode(\"%s\", %.2f, %.2f);", label, x, y);
    }
}
