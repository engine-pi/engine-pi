package pi.actor;

import static pi.Controller.colorScheme;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.graphics.boxes.TextBox;
import pi.graphics.boxes.TextLineBox;
import pi.physics.FixtureBuilder;
import pi.resources.color.ColorContainer;
import pi.resources.font.FontStyle;
import static pi.Controller.colors;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/TextRandomDemo.java

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

    /* color */

    /**
     * Setzt die <b>Farbe</b> der Figur auf eine bestimmte Farbe.
     *
     * @param color Die neue <b>Farbe</b>.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code actor.color(..).postion(..)}.
     */
    @API
    @Setter
    public TextNg color(Color color)
    {
        super.color(color);
        box.color(color);
        return this;
    }

    /**
     * Setzt die <b>Farbe</b> der Figur auf eine bestimmte Farbe, die als
     * <b>Zeichenkette</b> angegeben werden kann.
     *
     * @param color Ein Farbname, ein Farbalias ({@link ColorContainer siehe
     *     Auflistung}) oder eine Farbe in hexadezimaler Codierung (z. B.
     *     {@code #ff0000}).
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code actor.color(..).postion(..)}.
     *
     * @see pi.resources.color.ColorContainer#get(String)
     */
    @API
    @Setter
    public TextNg color(String color)
    {
        return color(colors.get(color));
    }

    /* font */

    /**
     * Setzt eine neue <b>Schriftart</b> durch Angabe des <b>Names</b>.
     *
     * @param fontName Der <b>Name</b> der Schriftart, falls es sich um eine
     *     Systemschriftart handelt, oder der <b>Pfad</b> zu einer Schriftdatei.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code actor.color(..).postion(..)}.
     *
     * @since 0.42.0
     */
    @API
    @Setter
    public TextNg font(String fontName)
    {
        box.font(fontName);
        syncAttributes();
        return this;
    }

    /**
     * Setzt die <b>Schriftart</b>, in der der Inhalt dargestellt werden soll.
     *
     * @param font Die <b>Schriftart</b>, in der der Inhalt dargestellt werden
     *     soll.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code actor.color(..).postion(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    public TextNg font(Font font)
    {
        box.font(font);
        syncAttributes();
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
