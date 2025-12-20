package pi.graphics.boxes;

/**
 * Eine Box, die keine weiteren Kinder hat.
 *
 * <p>
 * Im Boxenbaum handelt es sich um einen Blattknoten.
 * </p>
 */
public abstract class LeafBox extends Box
{

    public int numberOfChilds()
    {
        return 0;
    }

    @Override
    protected void calculateAnchors()
    {

    }
}
