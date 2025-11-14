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

    /**
     * Die gesetzte Breite in Pixel. Im Gegensatz zu {@link Box#width} wird
     * dieses Attribut gesetzt und nicht durch {@link Box#calculateDimension()}
     * berechnet.
     */
    int setWidth;

    /**
     * Die gesetzte Höhe in Pixel. Im Gegensatz zu {@link Box#height} wird
     * dieses Attribut gesetzt und nicht durch {@link Box#calculateDimension()}
     * berechnet.
     */
    int setHeight;

    public DimensionBox(Box child)
    {
        super(child);
    }

    public DimensionBox width(int width)
    {
        setWidth = width;
        return this;
    }

    public DimensionBox height(int height)
    {
        setHeight = height;
        return this;
    }

    @Override
    protected void calculateDimension()
    {
        child.calculateDimension();
        width = setWidth > 0 ? setWidth : child.width;
        height = setHeight > 0 ? setHeight : child.height;;
    }

    @Override
    protected void calculateAnchors()
    {
        child.x = x;
        child.y = y;
        child.calculateAnchors();
    }
}
