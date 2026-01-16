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

public class TextNg extends Geometry
{
    private TextLineBox box;

    /**
     * Wir erzeugen eine Box mit einer zu großen Schriftart. Mithilfe dieses
     * Skalierungsfaktors wird die Box dann auf die gewünschte Größe skaliert.
     * Damit die Abmessungen einer Zeichenkette nicht bei jedem Einzelbild
     * erneut bestimmt werden müssen, dient dieses Attribut als Cache.
     */
    private double scaleFactorX;

    /**
     * Wir erzeugen eine Box mit einer zu großen Schriftart. Mithilfe dieses
     * Skalierungsfaktors wird die Box dann auf die gewünschte Größe skaliert.
     * Damit die Abmessungen einer Zeichenkette nicht bei jedem Einzelbild
     * erneut bestimmt werden müssen, dient dieses Attribut als Cache.
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
