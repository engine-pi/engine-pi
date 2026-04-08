/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/actor/Image.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2014 Michael Andonie and contributors.
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
import static pi.Controller.images;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import pi.Controller;
import pi.Scene;
import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
import pi.physics.FixtureBuilder;
import pi.resources.color.ColorUtil;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/image.md

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/ImageDemo.java

/**
 * Ein <b>Bild</b> als grafische Repräsentation einer Bilddatei, die gezeichnet
 * werden kann.
 *
 * @author Michael Andonie
 * @author Josef Friedrich
 */
public class Image extends Actor
{
    /**
     * Erzeugt ein Bild durch Angabe des <b>Verzeichnispfads</b>.
     *
     * @param image Der <b>Verzeichnispfad</b> des Bilds, das geladen werden
     *     soll.
     */
    @API
    public Image(String image)
    {
        this(images.get(image));
    }

    /**
     * Erzeugt ein Bild durch Angabe eines bereits im Speicher vorhandenen Bilds
     * vom Datentyp {@link BufferedImage}.
     *
     * @param image Ein bereits im Speicher vorhandenes Bild vom Datentyp
     *     {@link BufferedImage}.
     */
    @API
    public Image(BufferedImage image)
    {
        super(null);
        this.image = image;
        update();
    }

    /* image */

    /**
     * Das {@link BufferedImage}, das dieses <b>Bild</b> darstellt.
     */
    private BufferedImage image;

    /**
     * Gibt das {@link BufferedImage}, das dieses <b>Bild</b> darstellt.
     *
     * @return Das {@link BufferedImage}, das dieses Bild darstellt.
     */
    @API
    @Getter
    public BufferedImage image()
    {
        return image;
    }

    /**
     * Setzt oder ersetzt das <b>Bild</b>.
     *
     * @param image Ein bereits im Speicher vorhandenes Bild vom Datentyp
     *     {@link BufferedImage}.
     */
    @API
    @Setter
    @ChainableMethod
    public Image image(BufferedImage image)
    {
        this.image = image;
        update();
        return this;
    }

    /**
     * Setzt oder ersetzt das <b>Bild</b>.
     *
     * @param image Der <b>Verzeichnispfad</b> des Bildes, das geladen werden
     *     soll.
     */
    @API
    @Setter
    @ChainableMethod
    public Image image(String image)
    {
        image(images.get(image));
        return this;
    }

    /* width */

    /**
     * Die <b>Breite</b> des Bilds in Meter.
     */
    private double width;

    private double definedWidth;

    /**
     * Gibt die <b>Breite</b> des Bilds in Meter.
     *
     * @return Die <b>Breite</b> des Bilds in Meter.
     */
    @API
    @Getter
    public double width()
    {
        return width;
    }

    /**
     * Setzt die <b>Breite</b> des Bilds in Meter.
     *
     * <p>
     * Der vorher durch die Methode {@link pi.actor.Image#pixelPerMeter(double)}
     * gesetzte Umrechnungsfaktor von Pixel zu Meter wird durch diese Methode
     * zurückgesetzt und ist somit unwirksam.
     * </p>
     *
     * @param width Die <b>Breite</b> des Bilds in Meter.
     */
    @API
    @Setter
    @ChainableMethod
    public Image width(double width)
    {
        if (width <= 0)
        {
            throw new IllegalArgumentException(
                    "Die Breite des Bilds muss größer als 0 sein.");
        }
        definedWidth = width;
        pixelPerMeter = 0;
        update();
        return this;
    }

    /* height */

    /**
     * Die <b>Höhe</b> des Bilds in Meter.
     */
    private double height;

    private double definedHeight;

    /**
     * Gibt die <b>Höhe</b> des Bilds in Meter.
     *
     * @return Die <b>Höhe</b> des Bilds in Meter.
     */
    @API
    @Getter
    public double height()
    {
        return height;
    }

    /**
     * Setzt die <b>Höhe</b> des Bilds in Meter.
     *
     * <p>
     * Der vorher durch die Methode {@link pi.actor.Image#pixelPerMeter(double)}
     * gesetzte Umrechnungsfaktor von Pixel zu Meter wird durch diese Methode
     * zurückgesetzt und ist somit unwirksam.
     * </p>
     *
     * @param height Die <b>Höhe</b> des Bilds in Meter.
     */
    @API
    @Setter
    @ChainableMethod
    public Image height(double height)
    {
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
    public Image pixelPerMeter(double pixelPerMeter)
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

    /* flippedHorizontally */

    /**
     * Gibt an, ob das Bild <b>horizontal gespiegelt</b> ist.
     */
    private boolean flippedHorizontally = false;

    /**
     * Gibt an, ob das Bild <b>horizontal gespiegelt</b>, das heißt an der
     * verticalen Achse gespiegelt ist.
     *
     * @return <code>true</code>, wenn das Objekt gerade <b>horizontal
     *     gespiegelt</b> ist. Sonst <code>false</code>.
     */
    @API
    @Getter
    public boolean flippedHorizontally()
    {
        return flippedHorizontally;
    }

    /**
     * Setzt, ob dieses Bild <b>horizontal gespiegelt</b> werden soll.
     *
     * <p>
     * Hiermit lassen sich zum Beispiel Bewegungsrichtungen (links/rechts)
     * einfach umsetzen.
     * </p>
     *
     * @param flippedHorizontally Ob das Bild <b>horizontal gespiegelt</b>
     *     dargestellt werden soll.
     *
     * @see #flippedVertically(boolean)
     */
    @API
    @Setter
    @ChainableMethod
    public Image flippedHorizontally(boolean flippedHorizontally)
    {
        this.flippedHorizontally = flippedHorizontally;
        return this;
    }

    /**
     * <b>Spiegelt</b> das Bild in <b>horizontaler</b> Richtung.
     *
     * <p>
     * Es wird an der vertikalen Achse gespiegelt.
     * </p>
     *
     * @return Wahr, wenn es <b>horizontale gespiegelt</b> wurde, falsch, wenn
     *     es im Originalzustand angezeigt wird.
     *
     * @since 0.24.0
     */
    @API
    public boolean toggleFlipHorizontally()
    {
        flippedHorizontally(!flippedHorizontally());
        return flippedHorizontally();
    }

    /* flippedVertically */

    /**
     * Gibt an, ob das Bild <b>vertikal gespiegelt</b> ist.
     */
    private boolean flippedVertically = false;

    /**
     * Gibt an, ob das Bild <b>vertikal gespiegelt</b> ist, das heißt an der
     * horizontaler Achse gespiegelt ist.
     *
     * @return <code>true</code>, wenn das Objekt gerade vertikal gekippt ist.
     *     Sonst <code>false</code>.
     */
    @API
    @Getter
    public boolean flippedVertically()
    {
        return flippedVertically;
    }

    /**
     * Setzt, ob das Bild <b>vertikal gespiegelt</b> werden sollen.
     *
     * @param flippedVertically Ob die Animation horizontal geflippt dargestellt
     *     werden soll.
     *
     * @see #flippedVertically(boolean)
     */
    @API
    @Setter
    @ChainableMethod
    public Image flippedVertically(boolean flippedVertically)
    {
        this.flippedVertically = flippedVertically;
        return this;
    }

    /**
     * <b>Spiegelt</b> das Bild in <b>vertikaler</b> Richtung.
     *
     * <p>
     * Es wird an der horizontaler Achse gespiegelt.
     * </p>
     *
     * @return Wahr, wenn es in horizontaler Richtung gekippt wurde, falsch,
     *     wenn es im Originalzustand angezeigt wird.
     *
     * @since 0.24.0
     */
    @API
    public boolean toggleFlipVertically()
    {
        flippedVertically(!flippedVertically());
        return flippedVertically();
    }

    /* End getter setter */

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
    public Image size(double width, double height)
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
     * Setzt alle Bildeinstellungen auf ihre <b>Standardwerte zurück</b>.
     */
    @API
    @ChainableMethod
    public Image reset()
    {
        definedWidth = 0;
        definedHeight = 0;
        pixelPerMeter = 0;
        flippedHorizontally = false;
        flippedVertically = false;
        update();
        return this;
    }

    /**
     * @return Die Größe des Bildes in Pixeln.
     *
     * @hidden
     */
    @Internal
    @Getter
    public Dimension sizeInPx()
    {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    /**
     * @hidden
     */
    @Override
    @Internal
    public void update()
    {
        if (image == null)
        {
            throw new RuntimeException("Kein Bild gesetzt.");
        }

        double widthPx = (double) image.getWidth();
        double heightPx = (double) image.getHeight();
        color = ColorUtil.calculateAverage(image);

        if (definedWidth == 0 && definedHeight == 0)
        {
            width = widthPx / pixelPerMeter();
            height = heightPx / pixelPerMeter();
        }
        else if (definedWidth > 0 && definedHeight == 0)
        {
            width = definedWidth;
            height = heightPx / widthPx * definedWidth;
        }
        else if (definedWidth == 0 && definedHeight > 0)
        {
            width = widthPx / heightPx * definedHeight;
            height = definedHeight;
        }
        else
        {
            width = definedWidth;
            height = definedHeight;
        }
        fixture(() -> FixtureBuilder.rectangle(width, height));
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        AffineTransform pre = g.getTransform();
        int imageH = image.getHeight();
        int imageW = image.getWidth();
        g.scale(width * pixelPerMeter / imageW,
            height * pixelPerMeter / imageH);
        g.drawImage(image,
            flippedHorizontally ? imageW : 0,
            -imageH + (flippedVertically ? imageH : 0),
            (flippedHorizontally ? -1 : 1) * imageW,
            (flippedVertically ? -1 : 1) * imageH,
            null);
        g.setTransform(pre);
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter(this);
        formatter.append("width", width, "m");
        formatter.append("height", height, "m");
        formatter.append("imageWidth", image.getWidth(), "px");
        formatter.append("imageHeight", image.getHeight(), "px");
        if (pixelPerMeter > 0)
        {
            formatter.append("pixelPerMeter", pixelPerMeter);
        }
        if (flippedHorizontally())
        {
            formatter.append("flippedHorizontally", true);
        }
        if (flippedVertically())
        {
            formatter.append("flippedVertically", true);
        }
        return formatter.format();
    }

    /**
     * @hidden
     */
    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new Scene()
        {
            {
                // Erzeugen mit Hilfe der createImage()-Methode.
                Image image = new Image("logo/logo.png").pixelPerMeter(40);
                image.center(0, 0);
                add(image);
                addKeyStrokeListener((event -> {
                    switch (event.getKeyCode())
                    {
                    case KeyEvent.VK_V ->
                        System.out.println(image.toggleFlipVertically());
                    case KeyEvent.VK_H ->
                        System.out.println(image.toggleFlipHorizontally());
                    }
                    System.out.println(image);
                }));
            }
        });
    }
}
