/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024, 2025 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pacman.actors;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

import pi.Resources;
import pi.actor.StatefulImagesAnimation;
import pi.event.FrameUpdateListener;
import pi.util.ImageUtil;

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
        if (Resources.images.contains(virtualPath))
        {
            return Resources.images.get(virtualPath);
        }
        return ImageUtil.replaceColor(Resources.images.get(realPath), fromColor,
                color);
    }

    public void reverse()
    {
        switch (state())
        {
        case STAND -> {
        }
        case DOWN -> state(GhostState.UP);
        case UP -> state(GhostState.DOWN);
        case LEFT -> state(GhostState.RIGHT);
        case RIGHT -> state(GhostState.LEFT);
        }
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        switch (state())
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
