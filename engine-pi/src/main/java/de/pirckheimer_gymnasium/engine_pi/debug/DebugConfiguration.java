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
package de.pirckheimer_gymnasium.engine_pi.debug;

/**
 * Verwaltet die Einstellmöglichkeiten mit Bezug zum Entwicklungsmodus.
 *
 * @author Josef Friedrich
 *
 * @since 0.18.0
 */
public class DebugConfiguration
{
    /**
     * Ob der Entwicklungsmodus aktiviert werden soll.
     */
    public static boolean enableDebugMode = false;

    /**
     * Wird dieses Attribut auf <code>true</code> gesetzt, so werden äußerst
     * ausführliche Log-Ausgaben gemacht. Dies betrifft unter anderem
     * Informationen über das Verhalten auf Ebene von Einzelbildern arbeitenden
     * Threads. Hierfür wurde diese Variable eingeführt.
     */
    public static boolean verbose;

    /**
     * Gibt an, ob die Figuren gezeichnet werden sollen. Ist dieses Attribut auf
     * {@code false} gesetzt, werden keine Figuren gezeichnet. Diese Einstellung
     * macht nur im aktivierten Debug-Modus Sinn, denn dann werden die Umrisse
     * gezeichnet, jedoch nicht die Füllung.
     */
    public static boolean renderActors = true;

    /**
     * Ob die Ankerpunkte der Figuren gezeichnet werden sollen.
     */
    public static boolean showPositions = false;

    /**
     * Zeichnet unabhängig vom Zoomfaktor jede n-te Linie in das
     * Koordinatensystem.
     */
    public static int coordinateSystemLinesEveryNMeter = -1;

    /**
     * Schaltet die Einstellung, ob die Ankerpunkte der Figuren gezeichnet
     * werden sollen, ein oder aus.
     *
     * @return Die Einstellung, ob die Ankerpunkte der Figuren gezeichnet werden
     *         sollen, nach der Veränderung.
     */
    public static boolean toogleShowPositions()
    {
        showPositions = !showPositions;
        return showPositions;
    }
}
