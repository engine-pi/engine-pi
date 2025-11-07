package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Eine Box, die weitere untergeordnete Boxen enthält.
 */
public abstract class MultipleChildBoxContainer extends Box
{
    protected ArrayList<Box> childs;

    public MultipleChildBoxContainer(Box... childs)
    {
        this.childs = new ArrayList<Box>();
        for (Box box : childs)
        {
            this.childs.add(box);
            box.parent = this;
        }
    }

    @Override
    public void calculateAnchors()
    {
        for (Box box : childs)
        {
            box.calculateAnchors();
        }
    }

    @Override
    public void render(Graphics2D g)
    {
        calculateAnchors();
        for (Box box : childs)
        {
            box.render(g);
        }
    }
}
