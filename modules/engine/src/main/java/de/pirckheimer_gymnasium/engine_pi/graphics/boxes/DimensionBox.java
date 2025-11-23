package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

/**
 * Eine Box, dessen <b>Abmessung</b> (Breite und Höhe) gesetzt und nicht
 * rekursiv berechnet wird.
 *
 * <p>
 * Wird die Abmessung nicht gesetzt, so wird die Breite und Höhe von der
 * Kinder-Box übernommen.
 * </p>
 *
 * @since 0.40.0
 */
public class DimensionBox extends ChildBox
{
    public DimensionBox(Box child)
    {
        super(child);
    }

    public DimensionBox width(int width)
    {
        definedWidth = width;
        return this;
    }

    public DimensionBox height(int height)
    {
        definedHeight = height;
        return this;
    }

    @Override
    protected void calculateDimension()
    {
        child.calculateDimension();

        if (definedWidth > 0 && definedWidth > child.width)
        {
            width = definedWidth;
        }
        else
        {
            width = child.width;
        }

        if (definedHeight > 0 && definedHeight > child.height)
        {
            height = definedHeight;
        }
        else
        {
            height = child.height;
        }
    }

    @Override
    protected void calculateAnchors()
    {
        child.x = x;
        child.y = y;
        child.calculateAnchors();
    }
}
