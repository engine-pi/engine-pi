/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/configuration/DebugConfiguration.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2025 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pi.configuration;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Setter;

/**
 * Verwaltet die Einstellmöglichkeiten mit Bezug zum Entwicklungsmodus.
 *
 * @author Josef Friedrich
 * @author Steffen Wilke
 * @author Matthias Wilke
 *
 * @since 0.42.0
 */
@ConfigurationGroupInfo(prefix = "debug_", debug = true)
public class DebugConfiguration extends ConfigurationGroup
{
    DebugConfiguration()
    {
        super();
        // Der Konstruktor sollte nicht auf „public“ gesetzt werden, sondern
        // „package private“ bleiben, damit die Konfigurationsgruppe nur in
        // diesem Paket instanziert werden kann.
    }

    /* enabled */

    /**
     * Ob der Entwicklungsmodus aktiviert werden soll.
     */
    private boolean enabled = false;

    /**
     * Gibt zurück, ob der Entwicklungsmodus <b>aktiviert</b> ist oder nicht.
     *
     * @return Ob der Entwicklungsmodus <b>aktiviert</b> ist oder nicht.
     *
     * @since 0.42.0
     */
    @Getter
    @API
    public boolean enabled()
    {
        return enabled;
    }

    /**
     * <b>Aktiviert</b> bzw. <b>deaktiviert</b> den Entwicklungsmodus.
     *
     * @param enabled Ob der Entwicklungsmodus <b>aktiviert</b> werden soll oder
     *     nicht.
     *
     * @return Eine Referenz auf die eigene Instanz der Konfigurationsgruppe,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Konfigurationsgruppe durch aneinander gekettete Setter festgelegt
     *     werden können, z. B. {@code debug.enabled(..).renderActors(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public DebugConfiguration enabled(boolean enabled)
    {
        set("enabled", enabled);
        return this;
    }

    /* verbose */

    /**
     * Wird dieses Attribut auf <code>true</code> gesetzt, so werden
     * ausführliche Log-Ausgaben gemacht.
     *
     * <p>
     * Dies betrifft unter anderem Informationen über das Verhalten auf Ebene
     * von Einzelbildern arbeitenden Threads. Hierfür wurde diese Variable
     * eingeführt.
     * </p>
     */
    private boolean verbose;

    /**
     * Gibt die <b>Ausführlichkeit</b> der Log-Ausgaben zurück.
     *
     * @return Die <b>Ausführlichkeit</b> der Log-Ausgaben.
     *
     * @since 0.42.0
     */
    @Getter
    @API
    public boolean verbose()
    {
        return verbose;
    }

    /**
     * Setzt die <b>Ausführlichkeit</b> der Log-Ausgaben.
     *
     * @param verbose Die <b>Ausführlichkeit</b> der Log-Ausgaben.
     *
     * @return Eine Referenz auf die eigene Instanz der Konfigurationsgruppe,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Konfigurationsgruppe durch aneinander gekettete Setter festgelegt
     *     werden können, z. B. {@code debug.enabled(..).renderActors(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public DebugConfiguration verbose(boolean verbose)
    {
        set("verbose", verbose);
        return this;
    }

    /* renderActors */

    /**
     * Gibt an, ob die <b>Figuren</b> <b>gezeichnet</b> werden sollen.
     *
     * <p>
     * Ist dieses Attribut auf {@code false} gesetzt, werden keine Figuren
     * gezeichnet. Diese Einstellung macht nur im aktivierten Debug-Modus Sinn,
     * denn dann werden die Umrisse gezeichnet, jedoch nicht die Füllung.
     * </p>
     */
    private boolean renderActors = true;

    /**
     * Gibt an, ob die <b>Figuren</b> <b>gezeichnet</b> werden sollen.
     *
     * @return Ob die <b>Figuren</b> <b>gezeichnet</b> werden sollen.
     *
     * @since 0.42.0
     */
    @Getter
    @API
    public boolean renderActors()
    {
        return renderActors;
    }

    /**
     * Setzt, ob die <b>Figuren</b> <b>gezeichnet</b> werden sollen.
     *
     * @param renderActors Ob die <b>Figuren</b> <b>gezeichnet</b> werden
     *     sollen.
     *
     * @return Eine Referenz auf die eigene Instanz der Konfigurationsgruppe,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Konfigurationsgruppe durch aneinander gekettete Setter festgelegt
     *     werden können, z. B. {@code debug.enabled(..).renderActors(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public DebugConfiguration renderActors(boolean renderActors)
    {
        set("renderActors", renderActors);
        return this;
    }

    /* showPositions */

    /**
     * Ob die Ankerpunkte der Figuren gezeichnet werden sollen.
     */
    private boolean showPositions = false;

    /**
     * Gibt
     *
     * @return
     *
     * @since 0.42.0
     */
    @Getter
    @API
    public boolean showPositions()
    {
        return showPositions;
    }

    /**
     * Schaltet die Einstellung, ob die Ankerpunkte der Figuren gezeichnet
     * werden sollen, ein oder aus.
     *
     * @return Die Einstellung, ob die Ankerpunkte der Figuren gezeichnet werden
     *     sollen, nach der Veränderung.
     */
    public boolean toogleShowPositions()
    {
        showPositions = !showPositions;
        return showPositions;
    }

    /* renderActors */

    /**
     * Setzt
     *
     * @param showPositions
     *
     * @return Eine Referenz auf die eigene Instanz der Konfigurationsgruppe,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Konfigurationsgruppe durch aneinander gekettete Setter festgelegt
     *     werden können, z. B. {@code debug.enabled(..).renderActors(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public DebugConfiguration showPositions(boolean showPositions)
    {
        set("showPositions", showPositions);
        return this;
    }

    /* coordinateSystemLinesEveryNMeter */

    /**
     * Zeichnet unabhängig vom Zoomfaktor jede n-te Linie in das
     * Koordinatensystem.
     */
    private int coordinateSystemLinesEveryNMeter = -1;

    /**
     * Gibt
     *
     * @return
     *
     * @since 0.42.0
     */
    @Getter
    @API
    public int coordinateSystemLinesEveryNMeter()
    {
        return coordinateSystemLinesEveryNMeter;
    }

    /**
     * Setzt
     *
     * @param coordinateSystemLinesEveryNMeter
     *
     * @return Eine Referenz auf die eigene Instanz der Konfigurationsgruppe,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Konfigurationsgruppe durch aneinander gekettete Setter festgelegt
     *     werden können, z. B. {@code debug.enabled(..).renderActors(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public DebugConfiguration coordinateSystemLinesEveryNMeter(
            int coordinateSystemLinesEveryNMeter)
    {
        set("coordinateSystemLinesEveryNMeter",
                coordinateSystemLinesEveryNMeter);
        return this;
    }

    /* coordinateSystemLabelsEachIntersectionGridLines */

    /**
     * Zeichne Koordinatenbeschriftungen bei jeder Überschneidung der
     * Gitterlinien ein.
     */
    private boolean coordinateSystemLabelsEachIntersectionGridLines = false;

    /**
     * Gibt
     *
     * @return
     *
     * @since 0.42.0
     */
    @Getter
    @API
    public boolean coordinateSystemLabelsEachIntersectionGridLines()
    {
        return coordinateSystemLabelsEachIntersectionGridLines;
    }

    /**
     * Setzt
     *
     * @param coordinateSystemLabelsEachIntersectionGridLines
     *
     * @return Eine Referenz auf die eigene Instanz der Konfigurationsgruppe,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Konfigurationsgruppe durch aneinander gekettete Setter festgelegt
     *     werden können, z. B. {@code debug.enabled(..).renderActors(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public DebugConfiguration coordinateSystemLabelsEachIntersectionGridLines(
            boolean coordinateSystemLabelsEachIntersectionGridLines)
    {
        set("coordinateSystemLabelsEachIntersectionGridLines",
                coordinateSystemLabelsEachIntersectionGridLines);
        return this;
    }
}
