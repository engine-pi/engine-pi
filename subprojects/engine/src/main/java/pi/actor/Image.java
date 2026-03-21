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
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
import pi.physics.FixtureBuilder;
import pi.resources.color.ColorUtil;

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
     * Erzeugt ein Bild durch Angabe des <b>Verzeichnispfads</b> und der
     * <b>Abmessungen</b> in <b>Meter</b>.
     *
     * <p>
     * <b>Entsprechen die Eingabeparameter für Breite und Höhe nicht den
     * Abmessungen des Bildes, dann wird das Bild verzerrt dargestellt.</b>
     * </p>
     *
     * @param filepath Der Verzeichnispfad des Bilds, das geladen werden soll.
     * @param width Die Breite des Bilds in Meter.
     * @param height Die Höhe des Bilds in Meter.
     *
     * @deprecated
     */
    @API
    public Image(String filepath, double width, double height)
    {
        super(null);
        image(filepath, width, height);
    }

    /**
     * Konstruktor für ein Bildobjekt.
     *
     * @param filepath Der Verzeichnispfad des Bildes, das geladen werden soll.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     *
     * @deprecated
     */
    @API
    public Image(String filepath, final double pixelPerMeter)
    {
        super(null);
        image(filepath, pixelPerMeter);
    }

    /**
     * Erzeugt ein Bild durch Angabe eines bereits im Speicher vorhandenen Bilds
     * vom Datentyp {@link BufferedImage}.
     *
     * @param image Ein bereits im Speicher vorhandenes Bild vom Datentyp
     *     {@link BufferedImage}.
     */
    public Image(BufferedImage image)
    {
        super(null);
        image(image);
    }

    /**
     * Erzeugt ein Bild durch Angabe des <b>Verzeichnispfads</b>.
     *
     * @param image Der Verzeichnispfad des Bilds, das geladen werden soll.
     */
    public Image(String image)
    {
        super(null);
        image(images.get(image));
    }

    /**
     * Konstruktor für ein Bildobjekt.
     *
     * @param image Ein bereits im Speicher vorhandenes Bild vom Datentyp
     *     {@link BufferedImage}.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     *
     * @deprecated
     */
    @API
    public Image(BufferedImage image, final double pixelPerMeter)
    {
        super(null);
        image(image, pixelPerMeter);
    }

    /* image */

    /**
     * Das {@link BufferedImage}, das dieses Bild darstellt.
     */
    private BufferedImage image;

    /**
     * Gibt das {@link BufferedImage}, das dieses Bild darstellt.
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
     * Setzt oder ersetzt das Bild.
     *
     * @param image Ein bereits im Speicher vorhandenes Bild vom Datentyp
     *     {@link BufferedImage}.
     * @param width Die Breite des Bilds in Meter.
     * @param height Die Höhe des Bilds in Meter.
     *
     * @deprecated
     */
    @Setter
    public void image(BufferedImage image, double width, double height)
    {
        assertViableSizes(width, height);
        this.image = image;
        this.width = width;
        this.height = height;
        color = ColorUtil.calculateAverage(image);
        fixture(() -> FixtureBuilder.rectangle(width, height));
    }

    /**
     * Setzt oder ersetzt das Bild.
     *
     * @param filepath Der Verzeichnispfad des Bilds, das geladen werden soll.
     * @param width Die Breite des Bilds in Meter.
     * @param height Die Höhe des Bilds in Meter.
     *
     * @deprecated
     */
    @Setter
    public void image(String filepath, double width, double height)
    {
        image(images.get(filepath), width, height);
    }

    /**
     * Setzt oder ersetzt das Bild.
     *
     * @param image Ein bereits im Speicher vorhandenes Bild vom Datentyp
     *     {@link BufferedImage}.
     */
    @Setter
    public void image(BufferedImage image)
    {
        if (pixelPerMeter > 0)
        {
            image(image, pixelPerMeter);
        }
        else
        {
            this.image = image;
        }
    }

    /**
     * Setzt oder ersetzt das Bild.
     *
     * @param image Ein bereits im Speicher vorhandenes Bild vom Datentyp
     *     {@link BufferedImage}.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     *
     * @deprecated
     */
    @Setter
    public void image(BufferedImage image, double pixelPerMeter)
    {
        assertViablePPM(pixelPerMeter);
        this.pixelPerMeter = pixelPerMeter;
        image(image,
            image.getWidth() / pixelPerMeter,
            image.getHeight() / pixelPerMeter);
    }

    /**
     * Setzt oder ersetzt das Bild.
     *
     * @param filepath Der Verzeichnispfad des Bildes, das geladen werden soll.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     *
     * @deprecated
     */
    @Setter
    public void image(String filepath, double pixelPerMeter)
    {
        image(images.get(filepath), pixelPerMeter);
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
     * @param width Die <b>Breite</b> des Bilds in Meter.
     */
    @API
    @Setter
    public Image width(double width)
    {
        if (width <= 0)
        {
            throw new IllegalArgumentException(
                    "Die Breite des Bilds muss größer als 0 sein.");
        }
        definedWidth = width;
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
     * @param height Die <b>Höhe</b> des Bilds in Meter.
     */
    @API
    @Setter
    public Image height(double height)
    {
        if (height <= 0)
        {
            throw new IllegalArgumentException(
                    "Die Höhe des Bilds müssen größer als 0 sein.");
        }
        definedHeight = height;
        update();
        return this;
    }

    /* pixelPerMeter */

    private double pixelPerMeter;

    /**
     * Ändert die Größe des Bildobjektes, sodass es dem angegebenen
     * Umrechnungsfaktor entspricht. Ändert auch die physikalischen
     * Eigenschaften des Bildes.
     *
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     *
     * @see #size(double, double)
     */
    @API
    @Setter
    public Image pixelPerMeter(double pixelPerMeter)
    {
        if (pixelPerMeter <= 0)
        {
            throw new IllegalArgumentException(
                    "Die Umrechnungszahl für Pixel pro Meter darf nicht negativ sein. War "
                            + pixelPerMeter);
        }
        this.pixelPerMeter = pixelPerMeter;
        update();
        return this;
    }

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

    /* flippedHorizontally */

    /**
     * Gibt an, ob das Objekt horizontal gespiegelt ist.
     */
    private boolean flippedHorizontally = false;

    /**
     * Gibt an, ob das Bild in horizontaler Richtung gekippt, das heißt an der
     * vertikalen Achse gespiegelt ist.
     *
     * @return <code>true</code>, wenn das Objekt gerade horizontal gespiegelt
     *     ist. Sonst <code>false</code>.
     */
    @API
    @Getter
    public boolean flippedHorizontally()
    {
        return flippedHorizontally;
    }

    /**
     * Setzt, ob dieses Bild horizontaler Richtung umgedreht, das heißt an der
     * vertikalen Achse gespiegelt werden soll. Hiermit lassen sich zum Beispiel
     * Bewegungsrichtungen (links/rechts) einfach umsetzen.
     *
     * @param flippedHorizontally Ob das Bild horizontal gespiegelt dargestellt
     *     werden soll.
     *
     * @see #flippedVertically(boolean)
     */
    @API
    @Setter
    public Image flippedHorizontally(boolean flippedHorizontally)
    {
        this.flippedHorizontally = flippedHorizontally;
        return this;
    }

    /**
     * Kippt das Bild in horizontaler Richtung. Es wird an der vertikalen Achse
     * gespiegelt.
     *
     * @return Wahr, wenn es in horizontaler Richtung gekippt wurde, falsch,
     *     wenn es im Originalzustand angezeigt wird.
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
     * Gibt an, ob das Objekt vertikal gespiegelt ist.
     */
    private boolean flippedVertically = false;

    /**
     * Gibt an, ob das Bild in vertikaler Richtung gekippt, das heißt an
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
     * Setzt, ob das Bild vertikal gespiegelt dargestellt werden sollen.
     *
     * @param flippedVertically Ob die Animation horizontal geflippt dargestellt
     *     werden soll.
     *
     * @see #flippedVertically(boolean)
     */
    @API
    @Setter
    public Image flippedVertically(boolean flippedVertically)
    {
        this.flippedVertically = flippedVertically;
        return this;
    }

    /**
     * Kippt das Bild in vertikaler Richtung. Es wird an der horizontalen Achse
     * gespiegelt.
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
     * Setzt die Größe des Bildes innerhalb der Physik neu. Ändert die
     * physikalischen Eigenschaften. Das Bild füllt die neuen Maße und wird ggf.
     * verzerrt.
     *
     * @param width Die neue Breite des Bilds in Meter.
     * @param height Die neue Höhe des Bild in Meter.
     *
     * @see #pixelPerMeter(double)
     */
    @Setter
    public Image size(double width, double height)
    {
        if (width <= 0)
        {
            throw new IllegalArgumentException(
                    "Die Breite des Bilds muss größer als 0 sein.");
        }
        this.definedWidth = width;
        if (height <= 0)
        {
            throw new IllegalArgumentException(
                    "Die Höhe des Bilds müssen größer als 0 sein.");
        }
        this.definedHeight = height;
        update();
        return this;
    }

    @Override
    public void update()
    {
        if (image == null)
        {
            throw new RuntimeException("Kein Bild gesetzt.");
        }

        int widthPx = image.getWidth();
        int heightPx = image.getHeight();
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

    public double aspectRatio()
    {
        if (width <= 0 || height <= 0)
        {
            throw new IllegalArgumentException(
                    "Höhe und Breite müssen größer als 0 sein.");
        }

        return width / height;
    }

    public void reset()
    {
        definedWidth = 0;
        definedHeight = 0;
        pixelPerMeter = 0;
        flippedHorizontally = false;
        flippedVertically = false;
        update();
    }

    private void assertViableSizes(double width, double height)
    {
        if (width <= 0 || height <= 0)
        {
            throw new IllegalArgumentException(
                    "Bildhöhe und Breite müssen größer als 0 sein.");
        }
    }

    /**
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    private void assertViablePPM(double pixelPerMeter)
    {
        if (pixelPerMeter <= 0)
        {
            throw new IllegalArgumentException(
                    "Die Umrechnungszahl für Pixel pro Meter darf nicht negativ sein. War "
                            + pixelPerMeter);
        }
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
        ToStringFormatter formatter = new ToStringFormatter("Image");
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
                Image image = new Image("logo/logo.png", 40);
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
