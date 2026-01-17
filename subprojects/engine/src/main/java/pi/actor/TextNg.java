package pi.actor;

import static pi.Controller.colorScheme;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.graphics.boxes.TextLineBox;
import pi.physics.FixtureBuilder;
import pi.resources.font.FontStyle;

public class TextNg extends Geometry
{
    private TextLineBox box;

    /**
     * Der Skalierungsfaktor in x-Richtung.
     *
     * <p>
     * Wir erzeugen eine Box der Standardschriftgröße. Mithilfe dieses
     * Skalierungsfaktors wird die Box dann auf die gewünschte Größe skaliert.
     * Damit die Abmessungen einer Zeichenkette nicht bei jedem Einzelbild
     * erneut bestimmt werden müssen, dient dieses Attribut als Cache.
     * </p>
     */
    private double scaleFactorX;

    /**
     * Der Skalierungsfaktor in x-Richtung.
     *
     * <p>
     * Wir erzeugen eine Box der Standardschriftgröße. Mithilfe dieses
     * Skalierungsfaktors wird die Box dann auf die gewünschte Größe skaliert.
     * Damit die Abmessungen einer Zeichenkette nicht bei jedem Einzelbild
     * erneut bestimmt werden müssen, dient dieses Attribut als Cache.
     * </p>
     */
    private double scaleFactorY;

    @API
    public TextNg(Object content)
    {
        super(null);
        box = new TextLineBox(content);
        Color color = colorScheme.get().white();
        box.color(color);
        color(color);
        syncAttributes();
    }

    /* width */

    /**
     * Die <b>gesetzte Breite</b> in Meter.
     */
    private double definedWidth = 0;

    /**
     * Die <b>berechnete Breite</b> in Meter.
     */
    private double width = 0;

    @API
    @Getter
    public double width()
    {
        return definedWidth;
    }

    @API
    @Setter
    public TextNg width(double width)
    {
        if (definedWidth != width)
        {
            definedWidth = width;
            syncAttributes();
        }
        return this;
    }

    /* height */

    /**
     * Die <b>gesetzte Höhe</b> in Meter.
     */
    private double definedHeight = 0;

    /**
     * Die <b>berechnete Höhe</b> in Meter.
     */
    private double height = 0;

    @API
    @Getter
    public double height()
    {
        return definedHeight;
    }

    @API
    @Setter
    public TextNg height(double height)
    {
        if (definedHeight != height)
        {
            definedHeight = height;
            syncAttributes();
        }
        return this;
    }

    /* style */

    /**
     * Setzt den <b>Stil</b> der Schriftart als <b>Aufzählungstyp</b>.
     *
     * @param style Der <b>Stil</b> der Schriftart (<i>fett</i>, <i>kursiv</i>
     *     oder <i>fett und kursiv</i>) als Aufzählungstyp.
     *
     *     <ul>
     *     <li>{@link FontStyle#PLAIN} — normaler Text ({@code 0})</li>
     *     <li>{@link FontStyle#BOLD} — fetter Text ({@code 1})</li>
     *     <li>{@link FontStyle#ITALIC} — kursiver Text ({@code 2})</li>
     *     <li>{@link FontStyle#BOLD_ITALIC} — fett und kursiv kombiniert
     *     ({@code 3})</li>
     *     </ul>
     *
     * @return Eine Instanz dieser Textfigur, damit mehrere Setter durch die
     *     Punktschreibweise aneinander gekettet werden können.
     */
    @API
    @Setter
    public TextNg style(FontStyle style)
    {
        box.fontStyle(style);
        syncAttributes();
        return this;
    }

    /**
     * Setzt den <b>Stil</b> der Schriftart als <b>Ganzzahl</b>.
     *
     * @param style Der <b>Stil</b> der Schriftart (<i>fett</i>, <i>kursiv</i>
     *     oder <i>fett und kursiv</i>) als Ganzzahl.
     *
     *     <ul>
     *     <li>{@code 0}: Normaler Text</li>
     *     <li>{@code 1}: Fett</li>
     *     <li>{@code 2}: Kursiv</li>
     *     <li>{@code 3}: Fett und Kursiv</li>
     *     </ul>
     *
     * @return Eine Instanz dieser Textfigur, damit mehrere Setter durch die
     *     Punktschreibweise aneinander gekettet werden können.
     */
    @API
    @Setter
    public TextNg style(int style)
    {
        box.fontStyle(style);
        syncAttributes();
        return this;
    }

    /**
     * Gibt den <b>Stil</b> der Schriftart als <b>Aufzählungstyp</b> zurück.
     *
     * @return Der <b>Stil</b> der Schriftart (<i>fett</i>, <i>kursiv</i> oder
     *     <i>fett und kursiv</i>) als Aufzählungstyp.
     *
     *     <ul>
     *     <li>{@link FontStyle#PLAIN} — normaler Text ({@code 0})</li>
     *     <li>{@link FontStyle#BOLD} — fetter Text ({@code 1})</li>
     *     <li>{@link FontStyle#ITALIC} — kursiver Text ({@code 2})</li>
     *     <li>{@link FontStyle#BOLD_ITALIC} — fett und kursiv kombiniert
     *     ({@code 3})</li>
     *     </ul>
     *
     * @since 0.42.0
     */
    @API
    @Getter
    public FontStyle style()
    {
        return box.fontStyle();
    }

    /**
     * @hidden
     */
    @Internal
    private void syncAttributes()
    {
        box.measure();

        if (definedWidth == 0 && definedHeight == 0)
        {
            height = 1;
            width = box.width() * height / box.height();
        }
        else if (definedWidth == 0)
        {
            width = box.width() * definedHeight / box.height();
            height = definedHeight;
        }
        else if (definedHeight == 0)
        {
            width = definedWidth;
            height = box.height() * definedWidth / box.width();
        }
        else
        {
            width = definedWidth;
            height = definedHeight;
        }

        scaleFactorX = width / (double) box.width();
        scaleFactorY = height / (double) box.height();
        fixture(() -> FixtureBuilder.rectangle(width, height));
    }

    /**
     * @hidden
     */
    @Override
    @Internal
    public void render(Graphics2D g, double pixelPerMeter)
    {
        AffineTransform oldTransform = g.getTransform();
        g.translate(0, -height * pixelPerMeter);
        g.scale(scaleFactorX * pixelPerMeter, scaleFactorY * pixelPerMeter);
        box.render(g);
        g.setTransform(oldTransform);
    }
}
