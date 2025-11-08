package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.awt.Graphics2D;

/**
 * Eine Box, die nur einen einzige Box enthält.
 */
public abstract class SingleChildBoxContainer extends Box
{

    protected Box child;

    public SingleChildBoxContainer(Box child)
    {
        this.child = child;
        child.parent = this;
    }

    @Override
    public void calculateAnchors()
    {
        child.calculateAnchors();
    }

    @Override
    public void render(Graphics2D g)
    {
        calculateAnchors();
        child.render(g);
    }

}
