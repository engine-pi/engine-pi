package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/AlignBoxDemo.java

/**
 * Eine größere Box, in die eine kleiner Boxen eingesetzt werden kann.
 */
public class AlignBox extends DimensionBox
{
    HAlignment hAlign = HAlignment.LEFT;

    VAlignment vAlign = VAlignment.TOP;

    public AlignBox(Box child)
    {
        super(child);
    }

    public AlignBox h(HAlignment hAlign)
    {
        this.hAlign = hAlign;
        return this;
    }

    public AlignBox v(VAlignment vAlign)
    {
        this.vAlign = vAlign;
        return this;
    }

    @Override
    protected void calculateAnchors()
    {
        int freeH = width - child.width;
        int freeV = height - child.height;

        switch (hAlign)
        {
        case LEFT:
            child.x = x;
            break;

        case CENTER:
            child.x = x + freeH / 2;

            break;

        case RIGHT:
            child.x = x + freeH;
            break;
        }

        switch (vAlign)
        {
        case TOP:
            child.y = y;
            break;

        case MIDDLE:
            child.y = y + freeV / 2;

            break;

        case BOTTOM:
            child.y = y + freeV;
            break;
        }

        child.calculateAnchors();
    }

}
