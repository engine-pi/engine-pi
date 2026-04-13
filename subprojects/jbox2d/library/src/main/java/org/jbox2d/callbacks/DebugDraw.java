/*
 * Copyright (c) 2013, Daniel Murphy
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 	* Redistributions of source code must retain the above copyright notice,
 * 	  this list of conditions and the following disclaimer.
 * 	* Redistributions in binary form must reproduce the above copyright notice,
 * 	  this list of conditions and the following disclaimer in the documentation
 * 	  and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jbox2d.callbacks;

import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.particle.ParticleColor;

/**
 * Implement this abstract class to allow JBox2d to automatically draw your
 * physics for debugging purposes. Not intended to replace your own custom
 * rendering routines!
 *
 * @author Daniel Murphy
 */
public abstract class DebugDraw
{
    /**
     * Draw shapes
     */
    public static final int shapeBit = 1 << 1;

    /**
     * Draw joint connections
     */
    public static final int jointBit = 1 << 2;

    /**
     * Draw axis aligned bounding boxes
     */
    public static final int aabbBit = 1 << 3;

    /**
     * Draw pairs of connected objects
     */
    public static final int pairBit = 1 << 4;

    /**
     * Draw center of mass frame
     */
    public static final int centerOfMassBit = 1 << 5;

    /**
     * Draw dynamic tree
     */
    public static final int dynamicTreeBit = 1 << 6;

    /**
     * Draw only the wireframe for drawing performance
     */
    public static final int wireframeDrawingBit = 1 << 7;

    protected int drawFlags;

    protected IViewportTransform viewportTransform;

    public DebugDraw()
    {
        this(null);
    }

    public DebugDraw(IViewportTransform viewport)
    {
        drawFlags = 0;
        viewportTransform = viewport;
    }

    public void setViewportTransform(IViewportTransform viewportTransform)
    {
        this.viewportTransform = viewportTransform;
    }

    public void setFlags(int flags)
    {
        drawFlags = flags;
    }

    public int getFlags()
    {
        return drawFlags;
    }

    public void appendFlags(int flags)
    {
        drawFlags |= flags;
    }

    public void clearFlags(int flags)
    {
        drawFlags &= ~flags;
    }

    /**
     * Draw a closed polygon provided in CCW order. This implementation uses
     * {@link #drawSegment(Vec2, Vec2, Color3f)} to draw each side of the
     * polygon.
     */
    public void drawPolygon(Vec2[] vertices, int vertexCount, Color3f color)
    {
        if (vertexCount == 1)
        {
            drawSegment(vertices[0], vertices[0], color);
            return;
        }
        for (int i = 0; i < vertexCount - 1; i += 1)
        {
            drawSegment(vertices[i], vertices[i + 1], color);
        }
        if (vertexCount > 2)
        {
            drawSegment(vertices[vertexCount - 1], vertices[0], color);
        }
    }

    public abstract void drawPoint(Vec2 argPoint, float argRadiusOnScreen,
            Color3f argColor);

    /**
     * Draw a solid closed polygon provided in CCW order.
     */
    public abstract void drawSolidPolygon(Vec2[] vertices, int vertexCount,
            Color3f color);

    /**
     * Draw a circle.
     */
    public abstract void drawCircle(Vec2 center, float radius, Color3f color);

    /** Draws a circle with an axis */
    public void drawCircle(Vec2 center, float radius, Vec2 axis, Color3f color)
    {
        drawCircle(center, radius, color);
    }

    /**
     * Draw a solid circle.
     */
    public abstract void drawSolidCircle(Vec2 center, float radius, Vec2 axis,
            Color3f color);

    /**
     * Draw a line segment.
     */
    public abstract void drawSegment(Vec2 p1, Vec2 p2, Color3f color);

    /**
     * Draw a transform. Choose your own length scale
     */
    public abstract void drawTransform(Transform xf);

    /**
     * Draw a string.
     */
    public abstract void drawString(float x, float y, String s, Color3f color);

    /**
     * Draw a particle array
     *
     * @param colors Can be null
     */
    public abstract void drawParticles(Vec2[] centers, float radius,
            ParticleColor[] colors, int count);

    /**
     * Draw a particle array
     *
     * @param colors Can be null
     */
    public abstract void drawParticlesWireframe(Vec2[] centers, float radius,
            ParticleColor[] colors, int count);

    /**
     * Called at the end of drawing a world
     */
    public void flush()
    {
    }

    public void drawString(Vec2 pos, String s, Color3f color)
    {
        drawString(pos.x, pos.y, s, color);
    }

    public IViewportTransform getViewportTransform()
    {
        return viewportTransform;
    }

    /**
     * @deprecated use the viewport transform in {@link #getViewportTransform()}
     */
    public void setCamera(float x, float y, float scale)
    {
        viewportTransform.setCamera(x, y, scale);
    }

    public void getScreenToWorldToOut(Vec2 argScreen, Vec2 argWorld)
    {
        viewportTransform.getScreenToWorld(argScreen, argWorld);
    }

    public void getWorldToScreenToOut(Vec2 argWorld, Vec2 argScreen)
    {
        viewportTransform.getWorldToScreen(argWorld, argScreen);
    }

    /**
     * Takes the world coordinates and puts the corresponding screen coordinates
     * in argScreen.
     */
    public void getWorldToScreenToOut(float worldX, float worldY,
            Vec2 argScreen)
    {
        argScreen.set(worldX, worldY);
        viewportTransform.getWorldToScreen(argScreen, argScreen);
    }

    /**
     * takes the world coordinate (argWorld) and returns the screen coordinates.
     */
    public Vec2 getWorldToScreen(Vec2 argWorld)
    {
        Vec2 screen = new Vec2();
        viewportTransform.getWorldToScreen(argWorld, screen);
        return screen;
    }

    /**
     * Takes the world coordinates and returns the screen coordinates.
     */
    public Vec2 getWorldToScreen(float worldX, float worldY)
    {
        Vec2 argScreen = new Vec2(worldX, worldY);
        viewportTransform.getWorldToScreen(argScreen, argScreen);
        return argScreen;
    }

    /**
     * takes the screen coordinates and puts the corresponding world coordinates
     * in argWorld.
     */
    public void getScreenToWorldToOut(float screenX, float screenY,
            Vec2 argWorld)
    {
        argWorld.set(screenX, screenY);
        viewportTransform.getScreenToWorld(argWorld, argWorld);
    }

    /**
     * takes the screen coordinates (argScreen) and returns the world
     * coordinates
     */
    public Vec2 getScreenToWorld(Vec2 argScreen)
    {
        Vec2 world = new Vec2();
        viewportTransform.getScreenToWorld(argScreen, world);
        return world;
    }

    /**
     * takes the screen coordinates and returns the world coordinates.
     */
    public Vec2 getScreenToWorld(float screenX, float screenY)
    {
        Vec2 screen = new Vec2(screenX, screenY);
        viewportTransform.getScreenToWorld(screen, screen);
        return screen;
    }
}
