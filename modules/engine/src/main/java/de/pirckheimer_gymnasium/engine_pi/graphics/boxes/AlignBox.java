package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/AlignBoxDemo.java

/**
 * Eine größere Box, in die eine kleiner Box eingesetzt und <b>ausgerichtet</b>
 * (vertikal und horizontal) werden kann.
 */
public class AlignBox extends DimensionBox
{
    HAlign hAlign = HAlign.LEFT;

    VAlign vAlign = VAlign.TOP;

    public AlignBox(Box child)
    {
        super(child);
    }

    public int childWidth()
    {
        if (child != null)
        {
            return child.width;
        }
        return 0;
    }

    public int childHeight()
    {
        if (child != null)
        {
            return child.height;
        }
        return 0;
    }

    public AlignBox hAlign(HAlign hAlign)
    {
        this.hAlign = hAlign;
        return this;
    }

    public AlignBox vAlign(VAlign vAlign)
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
