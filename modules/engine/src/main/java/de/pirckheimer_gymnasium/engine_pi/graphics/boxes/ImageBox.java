package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.images;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/ImageBoxDemo.java

public class ImageBox extends LeafBox implements FixedSizeSetter
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

    @Override
    protected void calculateDimension()
    {
        if (fixedSize != null)
        {
            width = fixedSize.width;
            height = fixedSize.height;
        }
        else
        {
            width = image.getWidth();
            height = image.getHeight();
        }
    }

    @Override
    void draw(Graphics2D g)
    {
        AffineTransform pre = g.getTransform();
        // g.scale(1, 1);
        // g.drawImage(image, flippedHorizontally ? width : 0,
        // -height + (flippedVertically ? height : 0),
        // (flippedHorizontally ? -1 : 1) * width,
        // (flippedVertically ? -1 : 1) * height, null);

        g.drawImage(image, x, y, width, height, null);
        g.setTransform(pre);
    }
}
