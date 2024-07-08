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
package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;
import de.pirckheimer_gymnasium.engine_pi.util.ColorUtil;

/**
 * Ein Bild als grafische Repräsentation einer Bilddatei, die gezeichnet werden
 * kann.
 *
 * @author Michael Andonie
 */
public class Image extends Actor
{
    /**
     * Das {@link BufferedImage}, das dieses Bild darstellt.
     */
    private BufferedImage image;

    private double width;

    private double height;

    private boolean flipVertical = false;

    private boolean flipHorizontal = false;

    /**
     * Erzeugt ein Bild durch Angabe des <b>Verzeichnispfads</b> und der
     * <b>Abmessungen</b> in <b>Meter</b>.
     *
     * <p>
     * <b>Entsprechen die Eingabeparameter für Breite und Höhe nicht den
     * Abmessungen des Bildes, dann wird das Bild verzerrt dargestellt.</b>
     * </p>
     *
     * @param filepath Der Verzeichnispfad des Bildes, das geladen werden soll.
     * @param width    Die Breite des Bilds in Meter.
     * @param height   Die Höhe des Bilds in Meter.
     */
    @API
    public Image(String filepath, double width, double height)
    {
        super(() -> FixtureBuilder.rectangle(width, height));
        assertViableSizes(width, height);
        image = Resources.IMAGES.get(filepath);
        color = ColorUtil.calculateAverage(image);
        this.width = width;
        this.height = height;
    }

    /**
     * Konstruktor für ein Bildobjekt.
     *
     * @param filepath      Der Verzeichnispfad des Bildes, das geladen werden
     *                      soll.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @API
    public Image(String filepath, final double pixelPerMeter)
    {
        super(() -> FixtureBuilder.rectangle(
                Resources.IMAGES.get(filepath).getWidth() / pixelPerMeter,
                Resources.IMAGES.get(filepath).getHeight() / pixelPerMeter));
        assertViablePPM(pixelPerMeter);
        image = Resources.IMAGES.get(filepath);
        color = ColorUtil.calculateAverage(image);
        width = image.getWidth() / pixelPerMeter;
        height = image.getHeight() / pixelPerMeter;
    }

    /**
     * Konstruktor für ein Bildobjekt.
     *
     * @param image         Ein bereits im Speicher vorhandenes Bild vom
     *                      Datentyp BufferedImage.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @API
    public Image(BufferedImage image, final double pixelPerMeter)
    {
        super(() -> FixtureBuilder.rectangle(image.getWidth() / pixelPerMeter,
                image.getHeight() / pixelPerMeter));
        assertViablePPM(pixelPerMeter);
        this.image = image;
        color = ColorUtil.calculateAverage(this.image);
        width = image.getWidth() / pixelPerMeter;
        height = image.getHeight() / pixelPerMeter;
    }

    /**
     * @return Größe des Bildes in Pixeln
     */
    @Internal
    public Dimension getImageSizeInPx()
    {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    /**
     * @return AWT-Repräsentation des Bildes
     */
    @API
    public BufferedImage getImage()
    {
        return image;
    }

    /**
     * Setzt die Größe des Bildes innerhalb der Physik neu. Ändert die
     * physikalischen Eigenschaften. Das Bild füllt die neuen Maße und wird ggf.
     * verzerrt.
     *
     * @param width  Die neue Breite des Bilds in Meter.
     * @param height Die neue Höhe des Bild in Meter.
     *
     * @see #resetPixelPerMeter(double)
     */
    public void resetImageSize(double width, double height)
    {
        assertViableSizes(width, height);
        this.width = width;
        this.height = height;
        this.setFixture(() -> FixtureBuilder.rectangle(width, height));
    }

    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

    /**
     * Ändert die Größe des Bildobjektes, sodass es dem angegebenen
     * Umrechnungsfaktor entspricht. Ändert auch die physikalischen
     * Eigenschaften des Bildes.
     *
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     *
     * @see #resetImageSize(double, double)
     */
    public void resetPixelPerMeter(double pixelPerMeter)
    {
        assertViablePPM(pixelPerMeter);
        resetImageSize(image.getWidth() / pixelPerMeter,
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
     * Setzt, ob dieses Bild horizontal gespiegelt dargestellt werden sollen.
     * Hiermit lassen sich zum Beispiel Bewegungsrichtungen (links/rechts)
     * einfach umsetzen.
     *
     * @param flipHorizontal Ob das Bild horizontal geflippt dargestellt werden
     *                       soll.
     *
     * @see #setFlipVertical(boolean)
     */
    @API
    public void setFlipHorizontal(boolean flipHorizontal)
    {
        this.flipHorizontal = flipHorizontal;
    }

    /**
     * Setzt, ob das Bild vertikal gespiegelt dargestellt werden sollen.
     *
     * @param flipVertical Ob die Animation horizontal geflippt dargestellt
     *                     werden soll.
     *
     * @see #setFlipVertical(boolean)
     */
    @API
    public void setFlipVertical(boolean flipVertical)
    {
        this.flipVertical = flipVertical;
    }

    /**
     * Gibt an, ob das Objekt horizontal gespiegelt ist.
     *
     * @return <code>true</code>, wenn das Objekt gerade horizontal gespiegelt
     *         ist. Sonst <code>false</code>.
     */
    @API
    public boolean isFlipHorizontal()
    {
        return flipHorizontal;
    }

    /**
     * Gibt an, ob das Objekt vertikal gespiegelt ist.
     *
     * @return <code>true</code>, wenn das Objekt gerade vertikal gespiegelt
     *         ist. Sonst <code>false</code>.
     */
    @API
    public boolean isFlipVertical()
    {
        return flipVertical;
    }

    /**
     * Zeichnet die Figur an der Position {@code (0|0)} mit der Rotation
     * {@code 0}.
     *
     * @param g             Das {@link Graphics2D}-Objekt, in das gezeichnet
     *                      werden soll.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        AffineTransform pre = g.getTransform();
        int imageH = image.getHeight();
        int imageW = image.getWidth();
        g.scale(width * pixelPerMeter / imageW,
                height * pixelPerMeter / imageH);
        g.drawImage(image, flipHorizontal ? imageW : 0,
                -imageH + (flipVertical ? imageH : 0),
                (flipHorizontal ? -1 : 1) * imageW,
                (flipVertical ? -1 : 1) * imageH, null);
        g.setTransform(pre);
    }

    public static void main(String[] args)
    {
        Game.start(new Scene()
        {
            {
                // Erzeugen mit Hilfe der createImage()-Methode.
                Image i1 = addImage("logo/logo.png", 40);
                i1.setCenter(0, 0);
            }
        });
    }
}
