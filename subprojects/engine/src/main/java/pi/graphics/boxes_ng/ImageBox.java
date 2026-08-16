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
package pi.graphics.boxes_ng;

import static pi.Controller.images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pi.annotations.Setter;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/boxes_ng/ImageBoxDemo.java

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
    @Override
    public ImageBox width(double width)
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
    @Override
    public ImageBox height(double height)
    {
        definedHeight = height;
        return this;
    }

    /**
     * Gibt an, ob das Objekt horizontal gespiegelt ist.
     */
    boolean hFlip = false;

    @Setter
    public ImageBox hFlip(boolean hFlip)
    {
        this.hFlip = hFlip;
        return this;
    }

    @Setter
    public ImageBox hFlip()
    {
        return hFlip(true);
    }

    /**
     * Gibt an, ob das Objekt vertikal gespiegelt ist.
     */
    boolean vFlip = false;

    @Setter
    public ImageBox vFlip(boolean vFlip)
    {
        this.vFlip = vFlip;
        return this;
    }

    @Setter
    public ImageBox vFlip()
    {
        return vFlip(true);
    }

    @Override
    protected void calculateDimension()
    {
        if (definedWidth == 0 && definedHeight == 0)
        {
            width = image.getWidth();
            height = image.getHeight();
        }
        else if (definedWidth > 0 && definedHeight == 0)
        {
            width = definedWidth;
            height = (double) image.getHeight() / image.getWidth()
                    * definedWidth;
        }
        else if (definedWidth == 0 && definedHeight > 0)
        {
            width = (double) image.getWidth() / image.getHeight()
                    * definedHeight;
            height = definedHeight;
        }
        else
        {
            width = definedWidth;
            height = definedHeight;
        }
    }

    @Override
    void draw(Graphics2D g)
    {
        g.drawImage(image,
            hFlip ? x() + width() : x(),
            vFlip ? y() : yTop(),
            (hFlip ? -1 : 1) * width(),
            (vFlip ? -1 : 1) * height(),
            null);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        var formatter = toStringFormatter();
        if (hFlip)
        {
            formatter.prepend("hFlip", true);
        }
        if (vFlip)
        {
            formatter.prepend("vFlip", true);
        }
        return formatter.format();
    }
}
