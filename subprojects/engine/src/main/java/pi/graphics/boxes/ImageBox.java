/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package pi.graphics.boxes;

import static pi.Controller.images;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import pi.annotations.Setter;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/ImageBoxDemo.java

public class ImageBox extends LeafBox
{
    BufferedImage image;

    public ImageBox(BufferedImage image)
    {
        this.image = image;
    }

    public ImageBox(String image)
    {
        this(images.get(image));
    }

    /**
     * Setzt die <b>Breite</b> des Bildes in Pixel.
     *
     * @param width Die <b>Breite</b> des Bildes in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz des Bildes, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften des Bildes durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code image.width(..).height(..)}.
     */
    @Setter
    public ImageBox width(int width)
    {
        definedWidth = width;
        return this;
    }

    /**
     * Setzt die <b>Höhe</b> des Bildes in Pixel.
     *
     * @param height Die <b>Höhe</b> des Bildes in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz des Bildes, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften des Bildes durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code image.width(..).height(..)}.
     */
    @Setter
    public ImageBox height(int height)
    {
        definedHeight = height;
        return this;
    }

    /**
     * Gibt an, ob das Objekt horizontal gespiegelt ist.
     */
    boolean flippedHorizontally = false;

    @Setter
    public ImageBox flippedHorizontally(boolean flippedHorizontally)
    {
        this.flippedHorizontally = flippedHorizontally;
        return this;
    }

    @Setter
    public ImageBox flippedHorizontally()
    {
        return flippedHorizontally(true);
    }

    /**
     * Gibt an, ob das Objekt vertikal gespiegelt ist.
     */
    boolean flippedVertically = false;

    @Setter
    public ImageBox flippedVertically(boolean flippedVertically)
    {
        this.flippedVertically = flippedVertically;
        return this;
    }

    @Setter
    public ImageBox flippedVertically()
    {
        return flippedVertically(true);
    }

    @Override
    protected void calculateDimension()
    {
        if (definedWidth > 0)
        {
            width = definedWidth;
        }
        else
        {
            width = image.getWidth();
        }
        if (definedHeight > 0)
        {
            height = definedHeight;
        }
        else
        {
            height = image.getHeight();
        }
    }

    @Override
    void draw(Graphics2D g)
    {
        AffineTransform oldTransfer = g.getTransform();
        g.drawImage(image,
            flippedHorizontally ? x + width : x,
            flippedVertically ? y + height : y,
            (flippedHorizontally ? -1 : 1) * width,
            (flippedVertically ? -1 : 1) * height,
            null);
        g.setTransform(oldTransfer);
    }

    @Override
    public String toString()
    {
        var formatter = toStringFormatter();
        if (flippedHorizontally)
        {
            formatter.prepend("flippedHorizontally", true);
        }
        if (flippedVertically)
        {
            formatter.prepend("flippedVertically", true);
        }
        return formatter.format();
    }
}
