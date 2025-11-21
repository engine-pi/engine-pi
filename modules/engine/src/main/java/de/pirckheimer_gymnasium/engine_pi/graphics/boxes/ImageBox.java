package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.images;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/ImageBoxDemo.java

public class ImageBox extends LeafBox
{
    BufferedImage image;

    /**
     * Gibt an, ob das Objekt vertikal gespiegelt ist.
     */
    boolean flippedVertically = false;

    /**
     * Gibt an, ob das Objekt horizontal gespiegelt ist.
     */
    boolean flippedHorizontally = false;

    public ImageBox(BufferedImage image)
    {
        this.image = image;
    }

    public ImageBox(String image)
    {
        this(images.get(image));
    }

    public ImageBox width(int width)
    {
        definedWidth = width;
        return this;
    }

    public ImageBox height(int height)
    {
        definedHeight = height;
        return this;
    }

    public ImageBox flippedVertically(boolean flippedVertically)
    {
        this.flippedVertically = flippedVertically;
        return this;
    }

    public ImageBox flippedVertically()
    {
        return flippedVertically(true);
    }

    public ImageBox flippedHorizontally(boolean flippedHorizontally)
    {
        this.flippedHorizontally = flippedHorizontally;
        return this;
    }

    public ImageBox flippedHorizontally()
    {
        return flippedHorizontally(true);
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
        g.drawImage(image, flippedHorizontally ? x + width : x,
                flippedVertically ? y + height : y,
                (flippedHorizontally ? -1 : 1) * width,
                (flippedVertically ? -1 : 1) * height, null);
        g.setTransform(oldTransfer);
    }
}
