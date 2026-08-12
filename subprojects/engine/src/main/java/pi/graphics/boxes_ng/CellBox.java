package pi.graphics.boxes_ng;

import java.awt.Graphics2D;

import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
import static pi.util.MathUtil.round;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/boxes/CellBoxDemo.java

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
public class CellBox extends ChildBox
{
    HAlign hAlign = HAlign.LEFT;

    VAlign vAlign = VAlign.TOP;

    public CellBox()
    {
        supportsDefinedDimension = true;
    }

    public CellBox(Box child)
    {
        this();
        addChild(child);
    }

    @Getter
    public int childWidth()
    {
        if (child != null)
        {
            return round(child.width);
        }
        return 0;
    }

    @Getter
    public int childHeight()
    {
        if (child != null)
        {
            return round(child.height);
        }
        return 0;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     */
    @Setter
    @Override
    public CellBox width(double width)
    {
        definedWidth = width;
        return this;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     */
    @Setter
    @Override
    public CellBox height(double height)
    {
        definedHeight = height;
        return this;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     */
    @Setter
    public CellBox hAlign(HAlign hAlign)
    {
        this.hAlign = hAlign;
        return this;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code box.x(..).y(..)}.
     */
    @Setter
    public CellBox vAlign(VAlign vAlign)
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
        // Die Größe des horizontalen Freiraums
        double freeH = width - child.width;

        // Die Größe des vertikalen Freiraums
        double freeV = height - child.height;

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
            child.y = y - freeV;
            break;

        case MIDDLE:
            child.y = y - freeV / 2;

            break;

        case BOTTOM:
            child.y = y;
            break;
        }
    }

    @Override
    void draw(Graphics2D g)
    {
        // do nothing
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = toStringFormatter();

        if (vAlign != VAlign.TOP)
        {
            formatter.prepend("vAlign", vAlign);
        }

        if (hAlign != HAlign.LEFT)
        {
            formatter.prepend("hAlign", hAlign);
        }
        return formatter.format();
    }
}
