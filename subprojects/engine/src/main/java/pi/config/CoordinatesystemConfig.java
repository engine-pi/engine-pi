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
package pi.config;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Setter;

/**
 * Verwaltet die Einstellungsmöglichkeiten, wie das <b>Koordinatensystem</b> im
 * Entwicklungsmodus gezeichnet werden soll.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
@ConfigGroupInfo(prefix = "coordinatesystem_")
public class CoordinatesystemConfig extends ConfigGroup
{
    CoordinatesystemConfig()
    {
        super();
        // Der Konstruktor sollte nicht auf „public“ gesetzt werden, sondern
        // „package private“ bleiben, damit die Konfigurationsgruppe nur in
        // diesem Paket instanziert werden kann.
        linesNMeter(-1);
        labelsOnIntersections(false);
    }

    /* linesNMeter */

    /**
     * Zeichnet unabhängig vom Zoomfaktor jede <b>n-ten Meter</b> eine
     * <b>Linie</b> in das Koordinatensystem.
     */
    private int linesNMeter;

    /**
     * Gibt an, auf welchen <b>n-ten Meter</b> eine <b>Linie</b> in das
     * Koordinatensystem gezeichnet werden soll.
     *
     * @return Auf welchen <b>n-ten Meter</b> eine <b>Linie</b> in das
     *     Koordinatensystem gezeichnet werden soll.
     *
     * @since 0.42.0
     */
    @Getter
    @API
    public int linesNMeter()
    {
        return linesNMeter;
    }

    /**
     * Setzt auf welchen <b>n-ten Meter</b> eine <b>Linie</b> in das
     * Koordinatensystem gezeichnet werden soll.
     *
     * @param linesNMeter Auf welchen <b>n-ten Meter</b> eine <b>Linie</b> in
     *     das Koordinatensystem gezeichnet werden soll.
     *
     * @return Eine Referenz auf die eigene Instanz der Konfigurationsgruppe,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Konfigurationsgruppe durch aneinander gekettete Setter festgelegt
     *     werden können, z. B.
     *     {@code coordinateSystem().linesNMeter(..).labelsOnIntersections(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public CoordinatesystemConfig linesNMeter(int linesNMeter)
    {
        set("linesNMeter", linesNMeter);
        return this;
    }

    /* labelsOnIntersections */

    /**
     * Ob Koordinatenbeschriftungen bei jeder <b>Überschneidung</b> der
     * <b>Gitterlinien</b> eingezeichnet werden sollen.
     */
    private boolean labelsOnIntersections;

    /**
     * Gibt zurück, ob Koordinatenbeschriftungen bei jeder <b>Überschneidung</b>
     * der <b>Gitterlinien</b> eingezeichnet werden sollen.
     *
     * @return Ob Koordinatenbeschriftungen bei jeder <b>Überschneidung</b> der
     *     <b>Gitterlinien</b> eingezeichnet werden sollen.
     *
     * @since 0.42.0
     */
    @Getter
    @API
    public boolean labelsOnIntersections()
    {
        return labelsOnIntersections;
    }

    /**
     * Setzt ob Koordinatenbeschriftungen bei jeder <b>Überschneidung</b> der
     * <b>Gitterlinien</b> eingezeichnet werden sollen.
     *
     * @param labelsOnIntersections Ob Koordinatenbeschriftungen bei jeder
     *     <b>Überschneidung</b> der <b>Gitterlinien</b> eingezeichnet werden
     *     sollen.
     *
     * @return Eine Referenz auf die eigene Instanz der Konfigurationsgruppe,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Konfigurationsgruppe durch aneinander gekettete Setter festgelegt
     *     werden können, z. B.
     *     {@code coordinateSystem().linesNMeter(..).labelsOnIntersections(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public CoordinatesystemConfig labelsOnIntersections(
            boolean labelsOnIntersections)
    {
        set("labelsOnIntersections", labelsOnIntersections);
        return this;
    }
}
