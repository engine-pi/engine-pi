package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.awt.Graphics2D;

/**
 * Eine leere Abschlussbox, damit keine Null-Checks erforderlich sind.
 *
 * @since 0.40.0
 */
public class EmptyBox extends LeafBox
{
    @Override
    protected void calculateDimension()
    {
    }

    @Override
    void draw(Graphics2D g)
    {
    }
}
