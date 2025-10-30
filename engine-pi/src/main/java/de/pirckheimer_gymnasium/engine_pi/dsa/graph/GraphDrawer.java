package de.pirckheimer_gymnasium.engine_pi.dsa.graph;

import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.event.MouseButton;
import de.pirckheimer_gymnasium.engine_pi.event.MouseClickListener;

public class GraphDrawer implements MouseClickListener
{
    public void onMouseDown(Vector vector, MouseButton button)
    {
        System.err.println("x: %s, y: %s, button %s".formatted(vector.getX(),
                vector.getY(), button));
    }
}
