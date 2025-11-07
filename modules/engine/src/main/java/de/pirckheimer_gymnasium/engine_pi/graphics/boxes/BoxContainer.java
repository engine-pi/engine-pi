package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.util.ArrayList;

/**
 * Eine Box, die weitere untergeordnete Boxen enthält.
 */
public abstract class BoxContainer extends Box
{
    protected ArrayList<Box> childs;
}
