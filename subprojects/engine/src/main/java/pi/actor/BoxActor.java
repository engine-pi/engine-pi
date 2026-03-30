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

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/BoxActorPhysicsDemo.java
// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/BoxActorFlipDemo.java

/**
 * Eine Figur, die eine {@link Box} enthält und diese in der Größe anpassen
 * kann.
 */
public class BoxActor<T extends Box> extends Actor
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
     *
     * @since 0.45.0
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
     *
     * @since 0.45.0
     */
    protected double scaleFactorY;

    public BoxActor(T box)
    {
        super(null);
        this.box = box;
        update();
    }

    /* width */

    /**
     * Die <b>gesetzte Breite</b> in Meter.
     *
     * @since 0.45.0
     */
    private double definedWidth = 0;

    /**
     * Die <b>berechnete Breite</b> in Meter.
     *
     * @since 0.45.0
     */
    private double width = 0;

    /**
     * Gibt die <b>Breite</b> in Meter zurück.
     *
     * @return Die <b>Breite</b> in Meter zurück.
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
     * Setzt die <b>Breite</b> in Meter.
     *
     * <p>
     * Diese Methode setzt die Höhe zurück. Um sowohl die Breite als auch die
     * Höhe zu setzen, steht die Methode {@link #size(double, double)} zur
     * Verfügung.
     * </p>
     *
     * @param width Die <b>Breite</b> in Meter.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code actor.content(..).height(..)}.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public BoxActor<T> width(double width)
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
     *
     * @since 0.45.0
     */
    private double definedHeight = 0;

    /**
     * Die <b>berechnete Höhe</b> in Meter.
     *
     * @since 0.45.0
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
     * <p>
     * Diese Methode setzt die Breite zurück. Um sowohl die Breite als auch die
     * Höhe zu setzen, steht die Methode {@link #size(double, double)} zur
     * Verfügung.
     * </p>
     *
     * @param height Die <b>Höhe</b> in Meter.
     *
     * @return Eine Referenz auf die eigene Instanz der Figur, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Figur durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code actor.content(..).height(..)}.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public BoxActor<T> height(double height)
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
     * @since 0.45.0
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
     * Wenn die Figur beispielsweise {@code 300} Pixel breit und {@code 200}
     * Pixel hoch ist und der Umrechnungsfaktor {@code 100} Pixel pro Meter
     * beträgt, wird sie mit einer Größe von {@code 3 x 2} Metern dargestellt.
     *
     * <p>
     * Die Größe der Figur wird so geändert, dass sie dem angegebenen
     * Umrechnungsfaktor entspricht. Dies hat auch Auswirkungen auf die
     * physikalischen Eigenschaften der Figur.
     * </p>
     *
     * <p>
     * Die durch die Methoden {@link #width(double)} {@link #height(double)}
     * oder {@link #size(double, double)} gesetzte Breite und Höhe des Figur
     * werden durch diese Methode zurückgesetzt und sind somit unwirksam.
     * </p>
     *
     * @param pixelPerMeter Wie viele <b>Pixel ein Meter</b> misst.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public BoxActor<T> pixelPerMeter(double pixelPerMeter)
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
     * Setzt die <b>Größe</b> innerhalb der Physik neu.
     *
     * <p>
     * Ändert die physikalischen Eigenschaften. Das Figur füllt die neuen Maße
     * und wird ggf. verzerrt.
     * </p>
     *
     * <p>
     * Der durch die Methode {@link #pixelPerMeter(double)} gesetzte
     * Umrechnungsfaktor von Pixel zu Meter wird durch diese Methode
     * zurückgesetzt und ist somit unwirksam.
     * </p>
     *
     * @param width Die neue <b>Breite</b> in Meter.
     * @param height Die neue <b>Höhe</b> in Meter.
     *
     * @see #pixelPerMeter(double)
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public BoxActor<T> size(double width, double height)
    {
        if (width <= 0)
        {
            throw new IllegalArgumentException(
                    "Die Breite der Box muss größer als 0 sein.");
        }
        definedWidth = width;
        if (height <= 0)
        {
            throw new IllegalArgumentException(
                    "Die Höhe der Box muss größer als 0 sein.");
        }
        definedHeight = height;
        pixelPerMeter = 0;
        update();
        return this;
    }

    /**
     * Berechnet das <b>Seitenverhältnis</b> der Figur.
     *
     * @return Das Seitenverhältnis als Quotient von Breite und Höhe
     *
     * @throws IllegalArgumentException wenn die Breite oder Höhe kleiner oder
     *     gleich 0 ist
     *
     * @since 0.45.0
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

    /* flippedHorizontally */

    /**
     * Gibt an, ob die Box <b>horizontal gespiegelt</b> ist.
     */
    private boolean hFlip = false;

    /**
     * Gibt an, ob die Box <b>horizontal gespiegelt</b>, das heißt an der
     * verticalen Achse gespiegelt ist.
     *
     * @return <code>true</code>, wenn das Objekt gerade <b>horizontal
     *     gespiegelt</b> ist. Sonst <code>false</code>.
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public boolean hFlip()
    {
        return hFlip;
    }

    /**
     * Setzt, ob die Box <b>horizontal gespiegelt</b> werden soll.
     *
     * <p>
     * Hiermit lassen sich zum Beispiel Bewegungsrichtungen (links/rechts)
     * einfach umsetzen.
     * </p>
     *
     * @param hFlip Ob die Box <b>horizontal gespiegelt</b> dargestellt werden
     *     soll.
     *
     * @see #vFlip(boolean)
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public BoxActor<T> hFlip(boolean hFlip)
    {
        this.hFlip = hFlip;
        return this;
    }

    /**
     * <b>Spiegelt</b> die Box in <b>horizontaler</b> Richtung.
     *
     * <p>
     * Es wird an der vertikalen Achse gespiegelt.
     * </p>
     *
     * @return Wahr, wenn es <b>horizontale gespiegelt</b> wurde, falsch, wenn
     *     es im Originalzustand angezeigt wird.
     *
     * @since 0.45.0
     */
    @API
    public boolean toggleHFlip()
    {
        hFlip(!hFlip());
        return hFlip();
    }

    /* flippedVertically */

    /**
     * Gibt an, ob die Box <b>vertikal gespiegelt</b> ist.
     */
    private boolean vFlip = false;

    /**
     * Gibt an, ob die Box<b>vertikal gespiegelt</b> ist, das heißt an der
     * horizontaler Achse gespiegelt ist.
     *
     * @return <code>true</code>, wenn das Objekt gerade vertikal gekippt ist.
     *     Sonst <code>false</code>.
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public boolean vFlip()
    {
        return vFlip;
    }

    /**
     * Setzt, ob die Box <b>vertikal gespiegelt</b> werden sollen.
     *
     * @param vFlip Ob die Animation horizontal geflippt dargestellt werden
     *     soll.
     *
     * @see #vFlip(boolean)
     */
    @API
    @Setter
    @ChainableMethod
    public BoxActor<T> vFlip(boolean vFlip)
    {
        this.vFlip = vFlip;
        return this;
    }

    /**
     * <b>Spiegelt</b> die Box in <b>vertikaler</b> Richtung.
     *
     * <p>
     * Es wird an der horizontaler Achse gespiegelt.
     * </p>
     *
     * @return Wahr, wenn es in horizontaler Richtung gekippt wurde, falsch,
     *     wenn es im Originalzustand angezeigt wird.
     *
     * @since 0.45.0
     */
    @API
    public boolean toggleVFlip()
    {
        vFlip(!vFlip());
        return vFlip();
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

    /**
     * @hidden
     */
    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        AffineTransform oldTransform = g.getTransform();
        g.translate(0, -height * pixelPerMeter);
        if (hFlip || vFlip)
        {
            g.scale(hFlip ? -1 : 1, vFlip ? -1 : 1);
            g.translate(hFlip ? -width * pixelPerMeter : 0,
                vFlip ? -height * pixelPerMeter : 0);
        }
        g.scale(scaleFactorX * pixelPerMeter, scaleFactorY * pixelPerMeter);
        box.render(g);
        g.setTransform(oldTransform);
    }
}
