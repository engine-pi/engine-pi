package de.pirckheimer_gymnasium.pacman.actors;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.actor.StatefulImagesAnimation;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;

public abstract class Ghost extends StatefulImagesAnimation<GhostState>
        implements FrameUpdateListener
{
    private final String name;

    private final Color color;

    private final Color fromColor = new Color(255, 0, 0);

    public Ghost(String name, Color color)
    {
        super(2, 2, 0.1);
        this.name = name;
        this.color = color;
        for (GhostState state : GhostState.values())
        {
            addGhostState(state);
        }
        makeDynamic();
        lockRotation();
    }

    private void addGhostState(GhostState state)
    {
        if (state == GhostState.STAND)
        {
            addState(state, getImage(state, 1));
        }
        else
        {
            addState(state, getImage(state, 1), getImage(state, 2));
        }
    }

    public BufferedImage getImage(GhostState state, int no)
    {
        String realPath = state.getImagePath(no);
        String virtualPath = realPath + "__" + name;
        if (Resources.IMAGES.contains(virtualPath))
        {
            return Resources.IMAGES.get(virtualPath);
        }
        return ImageUtil.replaceColor(Resources.IMAGES.get(realPath), fromColor,
                color);
    }

    public void reverse()
    {
        switch (getState())
        {
        case STAND -> {
        }
        case DOWN -> setState(GhostState.UP);
        case UP -> setState(GhostState.DOWN);
        case LEFT -> setState(GhostState.RIGHT);
        case RIGHT -> setState(GhostState.LEFT);
        }
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        switch (getState())
        {
        case STAND -> {
        }
        case DOWN -> moveBy(0, -0.05);
        case UP -> moveBy(0, 0.05);
        case LEFT -> moveBy(-0.05, 0);
        case RIGHT -> moveBy(0.05, 0);
        }
    }

    public static Ghost createGhost(Class<? extends Ghost> clazz)
    {
        {
            Ghost ghost;
            try
            {
                ghost = clazz.getDeclaredConstructor().newInstance();
                return ghost;
            }
            catch (InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public String getName()
    {
        return name;
    }
}
