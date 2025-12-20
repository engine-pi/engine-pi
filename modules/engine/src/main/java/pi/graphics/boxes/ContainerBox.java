package pi.graphics.boxes;

import java.awt.Graphics2D;

import pi.debug.ToStringFormatter;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/ContainerBoxDemo.java

/**
 * Eine äußere (größere) <b>Behälter</b>-Box, die eine kleinere (innere) Box
 * enthält.
 *
 * <p>
 * Die Methoden legen ({@link #height(int)} und {@link #width(int)}) die
 * Abmessung der äußeren Box fest. Ist die innere Box jedoch größer als die
 * äußere, so nimmt die äußere Box die Größe der inneren Box an. Die innere Box
 * kann horizontal und vertikal ausgerichtet werden.
 * </p>
 */
public class ContainerBox extends ChildBox
{
    HAlign hAlign = HAlign.LEFT;

    VAlign vAlign = VAlign.TOP;

    public ContainerBox(Box child)
    {
        super(child);
        supportsDefinedDimension = true;
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

    public ContainerBox width(int width)
    {
        definedWidth = width;
        return this;
    }

    public ContainerBox height(int height)
    {
        definedHeight = height;
        return this;
    }

    public ContainerBox hAlign(HAlign hAlign)
    {
        this.hAlign = hAlign;
        return this;
    }

    public ContainerBox vAlign(VAlign vAlign)
    {
        this.vAlign = vAlign;
        return this;
    }

    @Override
    protected void calculateDimension()
    {
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
    }

    @Override
    void draw(Graphics2D g)
    {
        // do nothing
    }

    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("ContainerBox");

        if (definedWidth > 0)
        {
            formatter.add("width", definedWidth);
        }

        if (definedHeight > 0)
        {
            formatter.add("height", definedHeight);
        }

        if (vAlign != VAlign.TOP)
        {
            formatter.add("vAlign", vAlign);
        }

        if (hAlign != HAlign.LEFT)
        {
            formatter.add("hAlign", hAlign);
        }
        return formatter.format();
    }
}
