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
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
     * @param width    Die Breite des Bilds in Meter.
     * @param height   Die Höhe des Bilds in Meter.
     */
    @API
    public Image(String filepath, double width, double height)
    {
        super(null);
        setImage(filepath, width, height);
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
        super(null);
        setImage(filepath, pixelPerMeter);
    }

    /**
     * Konstruktor für ein Bildobjekt.
     *
     * @param image         Ein bereits im Speicher vorhandenes Bild vom
     *                      Datentyp {@link BufferedImage}.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @API
    public Image(BufferedImage image, final double pixelPerMeter)
    {
        super(null);
        setImage(image, pixelPerMeter);
    }

    /**
     * @return Die Größe des Bildes in Pixeln.
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
     * Setzt oder ersetzt das Bild.
     *
     * @param image  Ein bereits im Speicher vorhandenes Bild vom Datentyp
     *               {@link BufferedImage}.
     * @param width  Die Breite des Bilds in Meter.
     * @param height Die Höhe des Bilds in Meter.
     */
    public void setImage(BufferedImage image, double width, double height)
    {
        assertViableSizes(width, height);
        this.image = image;
        this.width = width;
        this.height = height;
        color = ColorUtil.calculateAverage(image);
        setFixture(() -> FixtureBuilder.rectangle(width, height));
    }

    /**
     * Setzt oder ersetzt das Bild.
     *
     * @param filepath Der Verzeichnispfad des Bilds, das geladen werden soll.
     * @param width    Die Breite des Bilds in Meter.
     * @param height   Die Höhe des Bilds in Meter.
     */
    public void setImage(String filepath, double width, double height)
    {
        setImage(Resources.IMAGES.get(filepath), width, height);
    }

    /**
     * Setzt oder ersetzt das Bild.
     *
     * @param image Ein bereits im Speicher vorhandenes Bild vom Datentyp
     *              {@link BufferedImage}.
     */
    public void setImage(BufferedImage image)
    {
        if (pixelPerMeter > 0)
        {
            setImage(image, pixelPerMeter);
        }
        else
        {
            this.image = image;
        }
    }

    /**
     * Setzt oder ersetzt das Bild.
     *
     * @param image         Ein bereits im Speicher vorhandenes Bild vom
     *                      Datentyp {@link BufferedImage}.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    public void setImage(BufferedImage image, double pixelPerMeter)
    {
        assertViablePPM(pixelPerMeter);
        this.pixelPerMeter = pixelPerMeter;
        setImage(image, image.getWidth() / pixelPerMeter,
                image.getHeight() / pixelPerMeter);
    }

    /**
     * Setzt oder ersetzt das Bild.
     *
     * @param filepath      Der Verzeichnispfad des Bildes, das geladen werden
     *                      soll.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    public void setImage(String filepath, double pixelPerMeter)
    {
        setImage(Resources.IMAGES.get(filepath), pixelPerMeter);
    }

    /**
     * Setzt die Größe des Bildes innerhalb der Physik neu. Ändert die
     * physikalischen Eigenschaften. Das Bild füllt die neuen Maße und wird ggf.
     * verzerrt.
     *
     * @param width  Die neue Breite des Bilds in Meter.
     * @param height Die neue Höhe des Bild in Meter.
     *
     * @see #setImageSize(double)
     */
    public void setImageSize(double width, double height)
    {
        assertViableSizes(width, height);
        this.width = width;
        this.height = height;
        setFixture(() -> FixtureBuilder.rectangle(width, height));
    }

    /**
     * Ändert die Größe des Bildobjektes, sodass es dem angegebenen
     * Umrechnungsfaktor entspricht. Ändert auch die physikalischen
     * Eigenschaften des Bildes.
     *
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     *
     * @see #setImageSize(double, double)
     */
    public void setImageSize(double pixelPerMeter)
    {
        assertViablePPM(pixelPerMeter);
        setImageSize(image.getWidth() / pixelPerMeter,
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
     *         Sonst <code>false</code>.
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
     *         wenn es im Originalzustand angezeigt wird.
     *
     * @since 0.24.0
     */
    public boolean flipVertically()
    {
        setFlippedVertically(!isFlippedVertically());
        return isFlippedVertically();
    }

    /**
     * Setzt, ob das Bild vertikal gespiegelt dargestellt werden sollen.
     *
     * @param flippedVertically Ob die Animation horizontal geflippt dargestellt
     *                          werden soll.
     *
     * @see #setFlippedVertically(boolean)
     */
    @API
    public void setFlippedVertically(boolean flippedVertically)
    {
        this.flippedVertically = flippedVertically;
    }

    /**
     * Gibt an, ob das Bild in horizontaler Richtung gekippt, das heißt an der
     * vertikalen Achse gespiegelt ist.
     *
     * @return <code>true</code>, wenn das Objekt gerade horizontal gespiegelt
     *         ist. Sonst <code>false</code>.
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
     *         wenn es im Originalzustand angezeigt wird.
     *
     * @since 0.24.0
     */
    public boolean flipHorizontally()
    {
        setFlippedHorizontally(!isFlippedHorizontally());
        return isFlippedHorizontally();
    }

    /**
     * Setzt, ob dieses Bild horizontaler Richtung umgedreht, das heißt an der
     * vertikalen Achse gespiegelt werden soll. Hiermit lassen sich zum Beispiel
     * Bewegungsrichtungen (links/rechts) einfach umsetzen.
     *
     * @param flippedHorizontally Ob das Bild horizontal gespiegelt dargestellt
     *                            werden soll.
     *
     * @see #setFlippedVertically(boolean)
     */
    @API
    public void setFlippedHorizontally(boolean flippedHorizontally)
    {
        this.flippedHorizontally = flippedHorizontally;
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
        g.drawImage(image, flippedHorizontally ? imageW : 0,
                -imageH + (flippedVertically ? imageH : 0),
                (flippedHorizontally ? -1 : 1) * imageW,
                (flippedVertically ? -1 : 1) * imageH, null);
        g.setTransform(pre);
    }

    @Override
    public String toString()
    {
        ArrayList<String> additional = new ArrayList<>();
        if (pixelPerMeter > 0)
        {
            additional.add("pixelPerMeter=" + pixelPerMeter);
        }
        if (isFlippedHorizontally())
        {
            additional.add("flippedHorizontally=true");
        }
        if (isFlippedVertically())
        {
            additional.add("flippedVertically=true");
        }
        String fillIn = "";
        if (!additional.isEmpty())
        {
            fillIn = ", " + String.join(", ", additional);
        }
        return String.format(
                "Image [width=%sm, height=%sm, imageWidth=%spx, imageHeight=%spx%s]",
                width, height, image.getWidth(), image.getHeight(), fillIn);
    }

    public static void main(String[] args)
    {
        Game.start(new Scene()
        {
            {
                // Erzeugen mit Hilfe der createImage()-Methode.
                Image image = addImage("logo/logo.png", 40);
                image.setCenter(0, 0);
                addKeyStrokeListener((event -> {
                    switch (event.getKeyCode())
                    {
                    case KeyEvent.VK_V ->
                        System.out.println(image.flipVertically());
                    case KeyEvent.VK_H ->
                        System.out.println(image.flipHorizontally());
                    }
                }));
            }
        });
    }
}
