/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package pi.actor;

import static pi.Controller.config;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.graphics.boxes.Box;
import pi.physics.FixtureBuilder;

/**
 * Eine Figur, die eine {@link Box} enthält und diese in der Größe anpassen
 * kann.
 */
public class SizeableBoxActor<T extends Box> extends Actor
{
    protected T box;

    /**
     * Der Skalierungsfaktor in x-Richtung.
     *
     * <p>
     * Dieses Attribut dient als Cache, damit die Abmessungen der Box nicht bei
     * jedem Einzelbild erneut bestimmt werden müssen. Mithilfe dieses
     * Skalierungsfaktors wird die Box auf die gewünschte Größe skaliert.
     * </p>
     */
    protected double scaleFactorX;

    /**
     * Der Skalierungsfaktor in x-Richtung.
     *
     * <p>
     * Dieses Attribut dient als Cache, damit die Abmessungen der Box nicht bei
     * jedem Einzelbild erneut bestimmt werden müssen. Mithilfe dieses
     * Skalierungsfaktors wird die Box auf die gewünschte Größe skaliert.
     * </p>
     */
    protected double scaleFactorY;

    public SizeableBoxActor(T box)
    {
        super(null);
        this.box = box;
        update();
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

    /**
     * Gibt die <b>Breite</b> des Texts in Meter zurück.
     *
     * @return Die <b>Breite</b> des Texts in Meter zurück.
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public double width()
    {
        return definedWidth;
    }

    /**
     * Setzt die <b>Breite</b> des Texts in Meter.
     *
     * @param width Die <b>Breite</b> des Texts in Meter.
     *
     * @return Eine Referenz auf die eigene Instanz des Textes, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften des Textes durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).height(..)}.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public SizeableBoxActor<T> width(double width)
    {
        definedHeight = 0;
        if (definedWidth != width)
        {
            definedWidth = width;
            update();
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

    /**
     * Gibt die <b>Höhe</b> in Meter zurück.
     *
     * @return Die <b>Höhe</b> in Meter.
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public double height()
    {
        return definedHeight;
    }

    /**
     * Setzt die <b>Höhe</b> in Meter.
     *
     * @param height Die <b>Höhe</b> in Meter.
     *
     * @return Eine Referenz auf die eigene Instanz des Textes, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften des Textes durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code text.content(..).height(..)}.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public SizeableBoxActor<T> height(double height)
    {
        definedWidth = 0;
        if (definedHeight != height)
        {
            definedHeight = height;
            update();
        }
        return this;
    }

    /* pixelPerMeter */

    private double pixelPerMeter;

    /**
     * Gibt den Umrechnungsfaktor von <b>Pixel zu Meter</b> zurück.
     *
     * <p>
     * Ist der Umrechnungsfaktor bisher noch nicht gesetzt worden, wird der
     * konfigurierte Wert {@link pi.config.GraphicsConfig#pixelPerMeter()}
     * verwenden.
     * </p>
     *
     * @return
     */
    @API
    @Getter
    public double pixelPerMeter()
    {
        double ppm;
        if (pixelPerMeter > 0)
        {
            ppm = pixelPerMeter;
        }
        else
        {
            ppm = config.graphics.pixelPerMeter();
        }

        if (ppm <= 0)
        {
            throw new RuntimeException(
                    "Pixel per Meter darf nicht kleiner gleich 0 sein.");
        }
        return ppm;
    }

    /**
     * Setzt den Umrechnungsfaktor von <b>Pixel zu Meter</b>.
     *
     * Wenn das Bild beispielsweise {@code 300} Pixel breit und {@code 200}
     * Pixel hoch ist und der Umrechnungsfaktor {@code 100} Pixel pro Meter
     * beträgt, wird es mit einer Größe von {@code 3 x 2} Metern dargestellt.
     *
     * <p>
     * Die Größe des Bildobjekts wird so geändert, dass sie dem angegebenen
     * Umrechnungsfaktor entspricht. Dies hat auch Auswirkungen auf die
     * physikalischen Eigenschaften des Bildes.
     * </p>
     *
     * <p>
     * Die vorher durch die Methoden {@link pi.actor.Image#width(double)}
     * {@link pi.actor.Image#height(double)} oder
     * {@link pi.actor.Image#size(double, double)} gesetzte Breite und Höhe des
     * Bilds werden durch diese Methode zurückgesetzt und sind somit unwirksam.
     * </p>
     *
     * @param pixelPerMeter Wie viele <b>Pixel ein Meter</b> misst.
     */
    @API
    @Setter
    @ChainableMethod
    public SizeableBoxActor<T> pixelPerMeter(double pixelPerMeter)
    {
        if (pixelPerMeter <= 0)
        {
            throw new IllegalArgumentException(
                    "Die Umrechnungszahl für Pixel pro Meter darf nicht negativ sein. War "
                            + pixelPerMeter);
        }
        this.pixelPerMeter = pixelPerMeter;
        this.definedWidth = 0;
        this.definedHeight = 0;
        update();
        return this;
    }

    /**
     * Setzt die <b>Größe</b> des Bildes innerhalb der Physik neu.
     *
     * <p>
     * Ändert die physikalischen Eigenschaften. Das Bild füllt die neuen Maße
     * und wird ggf. verzerrt.
     * </p>
     *
     * <p>
     * <b>Entsprechen die Eingabeparameter für Breite und Höhe nicht den
     * Abmessungen des Bildes, dann wird das Bild verzerrt dargestellt.</b>
     * </p>
     *
     * *
     * <p>
     * Der vorher durch die Methode {@link pi.actor.Image#pixelPerMeter(double)}
     * gesetzte Umrechnungsfaktor von Pixel zu Meter wird durch diese Methode
     * zurückgesetzt und ist somit unwirksam.
     * </p>
     *
     * @param width Die neue Breite des Bilds in Meter.
     * @param height Die neue Höhe des Bild in Meter.
     *
     * @see #pixelPerMeter(double)
     */
    @API
    @Setter
    @ChainableMethod
    public SizeableBoxActor<T> size(double width, double height)
    {
        if (width <= 0)
        {
            throw new IllegalArgumentException(
                    "Die Breite des Bilds muss größer als 0 sein.");
        }
        definedWidth = width;
        if (height <= 0)
        {
            throw new IllegalArgumentException(
                    "Die Höhe des Bilds müssen größer als 0 sein.");
        }
        definedHeight = height;
        pixelPerMeter = 0;
        update();
        return this;
    }

    /**
     * Berechnet das <b>Seitenverhältnis</b> des Bildes.
     *
     * @return Das Seitenverhältnis als Quotient von Breite und Höhe
     *
     * @throws IllegalArgumentException wenn die Breite oder Höhe kleiner oder
     *     gleich 0 ist
     */
    @API
    public double aspectRatio()
    {
        if (width <= 0 || height <= 0)
        {
            throw new IllegalArgumentException(
                    "Höhe und Breite müssen größer als 0 sein.");
        }
        return width / height;
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public void update()
    {
        box.measure();

        double widthPx = (double) box.width();
        double heightPx = (double) box.height();

        if (definedWidth == 0 && definedHeight == 0)
        {
            height = 1;
            width = widthPx * height / heightPx;
        }
        else if (definedWidth == 0)
        {
            width = widthPx * definedHeight / heightPx;
            height = definedHeight;
        }
        else if (definedHeight == 0)
        {
            width = definedWidth;
            height = heightPx * definedWidth / widthPx;
        }
        else
        {
            width = definedWidth;
            height = definedHeight;
        }

        scaleFactorX = width / widthPx;
        scaleFactorY = height / heightPx;
        fixture(() -> FixtureBuilder.rectangle(width, height));
    }

    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        AffineTransform oldTransform = g.getTransform();
        g.translate(0, -height * pixelPerMeter);
        g.scale(scaleFactorX * pixelPerMeter, scaleFactorY * pixelPerMeter);
        box.render(g);
        g.setTransform(oldTransform);
    }
}
