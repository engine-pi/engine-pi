/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/resources/Images.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.pirckheimer_gymnasium.engine_pi.resources;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;

/**
 * Ein Speicher für <b>Bilder</b> vom Datentyp {@link BufferedImage}.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 * @author Josef Friedrich
 */
public final class ImageContainer extends ResourcesContainer<BufferedImage>
{
    /**
     * Loads the image by the specified resourceName. This method supports both
     * loading images from a folder and loading them from the resources.
     *
     * @param name Der Dateipfad des Bilds.
     *
     * @return Ein Bild vom Datentyp {@link BufferedImage}.
     */
    @Override
    protected BufferedImage load(URL name) throws IOException
    {
        BufferedImage img = ImageIO.read(name);
        if (img == null)
        {
            return null;
        }
        return ImageUtil.toCompatibleImage(img);
    }

    @Override
    public BufferedImage get(String name)
    {
        BufferedImage image = super.get(name);
        if (Game.getPixelMultiplication() > 1)
        {
            image = ImageUtil.multiplyPixel(image, Game.getPixelMultiplication());
        }
        return image;
    }

    /**
     * Ruft ein Bild auf, vergrößert es, indem seine <b>Pixel vervielfältigt</b>
     * werden und <b>färbt</b> es neu.
     *
     * @param name                Der <b>Name</b> oder <b>Dateipfad</b> des
     *                            Bilds.
     * @param pixelMultiplication Wie oft ein <b>Pixel vervielfältigt</b> werden
     *                            soll. Beispielsweise verwandelt die Zahl
     *                            {@code 3} ein Pixel in {@code 9} Pixel der
     *                            Abmessung {@code 3x3}.
     * @param fromColors          Ein Feld, das die <b>Quellfarben</b> enthält.
     * @param toColors            Ein Feld, das die <b>Zielfarben</b> enthält.
     *
     * @return Ein Bild vom Datentyp {@link BufferedImage}.
     *
     * @since 0.23.0
     */
    public BufferedImage get(String name, int pixelMultiplication,
            Color[] fromColors, Color[] toColors)
    {
        BufferedImage image = get(name);
        if (pixelMultiplication > 1)
        {
            image = ImageUtil.multiplyPixel(image, pixelMultiplication);
        }
        if (fromColors != null && toColors != null)
        {
            image = ImageUtil.replaceColors(image, fromColors, toColors);
        }
        return image;
    }

    /**
     * Ruft ein Bild auf und vergrößert es, indem seine <b>Pixel
     * vervielfältigt</b> werden.
     *
     * @param name                Der <b>Name</b> oder <b>Dateipfad</b> des
     *                            Bilds.
     * @param pixelMultiplication Wie oft ein <b>Pixel vervielfältigt</b> werden
     *                            soll. Beispielsweise verwandelt die Zahl
     *                            {@code 3} ein Pixel in {@code 9} Pixel der
     *                            Abmessung {@code 3x3}.
     *
     * @return Ein Bild vom Datentyp {@link BufferedImage}.
     *
     * @since 0.23.0
     */
    public BufferedImage get(String name, int pixelMultiplication)
    {
        return get(name, pixelMultiplication, null, null);
    }

    /**
     * Ruft ein Bild auf und <b>färbt</b> es neu.
     *
     * @param name       Der <b>Name</b> oder <b>Dateipfad</b> des Bilds.
     *
     * @param fromColors Ein Feld, das die <b>Quellfarben</b> enthält.
     * @param toColors   Ein Feld, das die <b>Zielfarben</b> enthält.
     *
     * @return Ein Bild vom Datentyp {@link BufferedImage}.
     *
     * @since 0.23.0
     */
    public BufferedImage get(String name, Color[] fromColors, Color[] toColors)
    {
        return get(name, Game.getPixelMultiplication(), fromColors, toColors);
    }
}
