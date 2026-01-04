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

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import pi.Game;
import pi.Resources;
import pi.Scene;
import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
import pi.physics.FixtureBuilder;
import pi.resources.color.ColorUtil;

/**
 * Ein <b>Bild</b> als grafische Repräsentation einer Bilddatei, die gezeichnet
 * werden kann.
 *
 * @author Michael Andonie
 */
public class Image extends Actor
{
    /**
     * Das {@link BufferedImage}, das dieses Bild darstellt.
     */
    private BufferedImage image;

    /**
     * Die Breite des Bilds in Meter.
     */
    private double width;

    /**
     * Die Höhe des Bilds in Meter.
     */
    private double height;

    private double pixelPerMeter;

    /**
     * Gibt an, ob das Objekt vertikal gespiegelt ist.
     */
    private boolean flippedVertically = false;

    /**
     * Gibt an, ob das Objekt horizontal gespiegelt ist.
     */
    private boolean flippedHorizontally = false;

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
     */
    @API
    public Image(String filepath, final double pixelPerMeter)
    {
        super(null);
        image(filepath, pixelPerMeter);
    }

    /**
     * Konstruktor für ein Bildobjekt.
     *
     * @param image Ein bereits im Speicher vorhandenes Bild vom Datentyp
     *     {@link BufferedImage}.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @API
    public Image(BufferedImage image, final double pixelPerMeter)
    {
        super(null);
        image(image, pixelPerMeter);
    }

    @API
    @Getter
    public double width()
    {
        return width;
    }

    @API
    @Getter
    public double height()
    {
        return height;
    }

    /**
     * @return Die Größe des Bildes in Pixeln.
     *
     * @hidden
     */
    @Internal
    @Getter
    public Dimension imageSizeInPx()
    {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    /**
     * @return AWT-Repräsentation des Bildes
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
     */
    @Setter
    public void image(String filepath, double width, double height)
    {
        image(Resources.images.get(filepath), width, height);
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
     */
    @Setter
    public void image(BufferedImage image, double pixelPerMeter)
    {
        assertViablePPM(pixelPerMeter);
        this.pixelPerMeter = pixelPerMeter;
        image(image, image.getWidth() / pixelPerMeter,
                image.getHeight() / pixelPerMeter);
    }

    /**
     * Setzt oder ersetzt das Bild.
     *
     * @param filepath Der Verzeichnispfad des Bildes, das geladen werden soll.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @Setter
    public void image(String filepath, double pixelPerMeter)
    {
        image(Resources.images.get(filepath), pixelPerMeter);
    }

    /**
     * Setzt die Größe des Bildes innerhalb der Physik neu. Ändert die
     * physikalischen Eigenschaften. Das Bild füllt die neuen Maße und wird ggf.
     * verzerrt.
     *
     * @param width Die neue Breite des Bilds in Meter.
     * @param height Die neue Höhe des Bild in Meter.
     *
     * @see #imageSize(double)
     */
    @Setter
    public void imageSize(double width, double height)
    {
        assertViableSizes(width, height);
        this.width = width;
        this.height = height;
        fixture(() -> FixtureBuilder.rectangle(width, height));
    }

    /**
     * Ändert die Größe des Bildobjektes, sodass es dem angegebenen
     * Umrechnungsfaktor entspricht. Ändert auch die physikalischen
     * Eigenschaften des Bildes.
     *
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     *
     * @see #imageSize(double, double)
     */
    @Setter
    public void imageSize(double pixelPerMeter)
    {
        assertViablePPM(pixelPerMeter);
        imageSize(image.getWidth() / pixelPerMeter,
                image.getHeight() / pixelPerMeter);
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
     * Gibt an, ob das Bild in vertikaler Richtung gekippt, das heißt an
     * horizontaler Achse gespiegelt ist.
     *
     * @return <code>true</code>, wenn das Objekt gerade vertikal gekippt ist.
     *     Sonst <code>false</code>.
     */
    @API
    public boolean isFlippedVertically()
    {
        return flippedVertically;
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
    public boolean flipVertically()
    {
        flippedVertically(!isFlippedVertically());
        return isFlippedVertically();
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
    public void flippedVertically(boolean flippedVertically)
    {
        this.flippedVertically = flippedVertically;
    }

    /**
     * Gibt an, ob das Bild in horizontaler Richtung gekippt, das heißt an der
     * vertikalen Achse gespiegelt ist.
     *
     * @return <code>true</code>, wenn das Objekt gerade horizontal gespiegelt
     *     ist. Sonst <code>false</code>.
     */
    @API
    public boolean isFlippedHorizontally()
    {
        return flippedHorizontally;
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
    public boolean flipHorizontally()
    {
        flippedHorizontally(!isFlippedHorizontally());
        return isFlippedHorizontally();
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
    public void flippedHorizontally(boolean flippedHorizontally)
    {
        this.flippedHorizontally = flippedHorizontally;
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
        g.drawImage(image, flippedHorizontally ? imageW : 0,
                -imageH + (flippedVertically ? imageH : 0),
                (flippedHorizontally ? -1 : 1) * imageW,
                (flippedVertically ? -1 : 1) * imageH, null);
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
        if (isFlippedHorizontally())
        {
            formatter.append("flippedHorizontally", true);
        }
        if (isFlippedVertically())
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
        Game.instantMode(false);
        Game.start(new Scene()
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
                        System.out.println(image.flipVertically());
                    case KeyEvent.VK_H ->
                        System.out.println(image.flipHorizontally());
                    }
                    System.out.println(image);
                }));
            }
        });
    }
}
