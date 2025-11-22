package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.util.ArrayList;
import java.util.List;

/**
 * Eine Anordung und Aufreihung mehrerer äußerer {@link AlignBox}en, die
 * verschiedene Kinder-{@link Box}en unterschiedlicher Größe enthalten können.
 */
public class AlignBoxOrder
{
    private List<AlignBox> alignBoxes;

    public AlignBoxOrder()
    {
        this.alignBoxes = new ArrayList<AlignBox>();
    }

    public AlignBox addInner(Box inner)
    {
        AlignBox align = new AlignBox(inner);
        this.alignBoxes.add(align);
        return align;
    }

    public AlignBoxOrder hAlign(HAlign hAlign)
    {
        for (AlignBox box : alignBoxes)
        {
            box.hAlign(hAlign);
        }
        return this;
    }

    public AlignBoxOrder vAlign(VAlign vAlign)
    {
        for (AlignBox box : alignBoxes)
        {
            box.vAlign(vAlign);
        }
        return this;
    }

    /**
     * Ermittelt die maximale Breite aller inneren Kind-Boxen.
     */
    private int getMaxChildWidth()
    {
        int maxWidth = 0;
        for (AlignBox box : alignBoxes)
        {
            if (box.childWidth() > maxWidth)
            {
                maxWidth = box.childWidth();
            }
        }
        return maxWidth;
    }

    /**
     * Ermittelt die maximale Höhe aller inneren Kind-Boxen.
     */
    private int getMaxChildHeight()
    {
        int maxHeight = 0;
        for (AlignBox box : alignBoxes)
        {
            if (box.childHeight() > maxHeight)
            {
                maxHeight = box.childHeight();
            }
        }
        return maxHeight;
    }

    /* setter */

    public void alignV(VAlign vAlign)
    {
        for (AlignBox box : alignBoxes)
        {
            box.vAlign(vAlign);
        }
    }

    public void alignH(HAlign hAlign)
    {
        for (AlignBox box : alignBoxes)
        {
            box.hAlign(hAlign);
        }
    }

    public void width(int width)
    {
        for (AlignBox box : alignBoxes)
        {
            box.width = width;
        }
    }

    public void height(int height)
    {
        for (AlignBox box : alignBoxes)
        {
            box.height = height;
        }
    }

    /**
     * Setzt alle äußeren {@link AlignBox}en auf die maximale innere Breite.
     */
    public void maxChildWidth()
    {
        width(getMaxChildWidth());
    }

    /**
     * Setzt alle äußeren {@link AlignBox}en auf die maximale innere Höhe.
     */
    public void maxChildHeight()
    {
        height(getMaxChildHeight());
    }

}
