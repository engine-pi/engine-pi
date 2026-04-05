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
package org.jbox2d.collision;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.pooling.WorldPool;
import org.jbox2d.pooling.normal.DefaultWorldPool;

/**
 * An axis-aligned bounding box.
 *
 * @author Daniel Murphy
 */
public class AABB
{
    /**
     * The bottom left vertex of the bounding box.
     */
    public final Vec2 lowerBound;

    /**
     * The top right vertex of the bounding box.
     */
    public final Vec2 upperBound;

    /**
     * Creates the default object, with vertices at 0,0 and 0,0.
     */
    public AABB()
    {
        lowerBound = new Vec2();
        upperBound = new Vec2();
    }

    /**
     * Copies from the given object.
     *
     * @param copy The object to copy from.
     */
    public AABB(final AABB copy)
    {
        this(copy.lowerBound, copy.upperBound);
    }

    /**
     * Creates an AABB object using the given bounding vertices.
     *
     * @param lowerVertex The bottom left vertex of the bounding box.
     * @param upperVertex The top right vertex of the bounding box.
     */
    public AABB(final Vec2 lowerVertex, final Vec2 upperVertex)
    {
        this.lowerBound = lowerVertex.clone(); // clone to be safe
        this.upperBound = upperVertex.clone();
    }

    /**
     * Sets this object from the given object.
     *
     * @param aabb The object to copy from.
     */
    public final void set(final AABB aabb)
    {
        Vec2 v = aabb.lowerBound;
        lowerBound.x = v.x;
        lowerBound.y = v.y;
        Vec2 v1 = aabb.upperBound;
        upperBound.x = v1.x;
        upperBound.y = v1.y;
    }

    /**
     * Verify that the bounds are sorted.
     */
    public final boolean isValid()
    {
        final float dx = upperBound.x - lowerBound.x;
        if (dx < 0f)
        {
            return false;
        }
        final float dy = upperBound.y - lowerBound.y;
        if (dy < 0)
        {
            return false;
        }
        return lowerBound.isValid() && upperBound.isValid();
    }

    /**
     * Get the center of the AABB.
     */
    public final Vec2 getCenter()
    {
        final Vec2 center = new Vec2(lowerBound);
        center.addLocal(upperBound);
        center.mulLocal(.5f);
        return center;
    }

    public final void getCenterToOut(final Vec2 out)
    {
        out.x = (lowerBound.x + upperBound.x) * .5f;
        out.y = (lowerBound.y + upperBound.y) * .5f;
    }

    /**
     * Get the extents of the AABB (half-widths).
     */
    public final Vec2 getExtents()
    {
        final Vec2 center = new Vec2(upperBound);
        center.subLocal(lowerBound);
        center.mulLocal(.5f);
        return center;
    }

    public final void getExtentsToOut(final Vec2 out)
    {
        out.x = (upperBound.x - lowerBound.x) * .5f;
        out.y = (upperBound.y - lowerBound.y) * .5f; // thanks FDN1
    }

    public final void getVertices(Vec2[] argRay)
    {
        argRay[0].set(lowerBound);
        argRay[1].set(lowerBound);
        argRay[1].x += upperBound.x - lowerBound.x;
        argRay[2].set(upperBound);
        argRay[3].set(upperBound);
        argRay[3].x -= upperBound.x - lowerBound.x;
    }

    /**
     * Combine two AABBs into this one.
     */
    public final void combine(final AABB aabb1, final AABB aab)
    {
        lowerBound.x = Math.min(aabb1.lowerBound.x, aab.lowerBound.x);
        lowerBound.y = Math.min(aabb1.lowerBound.y, aab.lowerBound.y);
        upperBound.x = Math.max(aabb1.upperBound.x, aab.upperBound.x);
        upperBound.y = Math.max(aabb1.upperBound.y, aab.upperBound.y);
    }

    /**
     * Gets the perimeter length.
     */
    public final float getPerimeter()
    {
        return 2.0f
                * (upperBound.x - lowerBound.x + upperBound.y - lowerBound.y);
    }

    /**
     * Combines another aabb with this one.
     */
    public final void combine(final AABB aabb)
    {
        lowerBound.x = Math.min(lowerBound.x, aabb.lowerBound.x);
        lowerBound.y = Math.min(lowerBound.y, aabb.lowerBound.y);
        upperBound.x = Math.max(upperBound.x, aabb.upperBound.x);
        upperBound.y = Math.max(upperBound.y, aabb.upperBound.y);
    }

    /**
     * Does this aabb contain the provided AABB.
     *
     */
    public final boolean contains(final AABB aabb)
    {
        /*
         * boolean result = true; result = result && lowerBound.x <=
         * aabb.lowerBound.x; result = result && lowerBound.y <=
         * aabb.lowerBound.y; result = result && aabb.upperBound.x <=
         * upperBound.x; result = result && aabb.upperBound.y <= upperBound.y;
         * return result;
         */
        // djm: faster putting all of them together, as if one is false we leave
        // the logic
        // early
        return lowerBound.x <= aabb.lowerBound.x
                && lowerBound.y <= aabb.lowerBound.y
                && aabb.upperBound.x <= upperBound.x
                && aabb.upperBound.y <= upperBound.y;
    }

    /**
     * @deprecated please use
     *     {@link #raycast(RayCastOutput, RayCastInput, WorldPool)} for better
     *     performance
     */
    public final boolean raycast(final RayCastOutput output,
            final RayCastInput input)
    {
        return raycast(output, input, new DefaultWorldPool(4, 4));
    }

    /**
     * From Real-time Collision Detection, p179.
     */
    public final boolean raycast(final RayCastOutput output,
            final RayCastInput input, WorldPool argPool)
    {
        float tMin = -Float.MAX_VALUE;
        float tMax = Float.MAX_VALUE;
        final Vec2 p = argPool.popVec2();
        final Vec2 d = argPool.popVec2();
        final Vec2 absD = argPool.popVec2();
        final Vec2 normal = argPool.popVec2();
        p.set(input.p1);
        d.set(input.p2).subLocal(input.p1);
        Vec2.absToOut(d, absD);
        // x then y
        if (absD.x < Settings.EPSILON)
        {
            // Parallel.
            if (p.x < lowerBound.x || upperBound.x < p.x)
            {
                argPool.pushVec2(4);
                return false;
            }
        }
        else
        {
            final float inv_d = 1.0f / d.x;
            float t1 = (lowerBound.x - p.x) * inv_d;
            float t2 = (upperBound.x - p.x) * inv_d;
            // Sign of the normal vector.
            float s = -1.0f;
            if (t1 > t2)
            {
                final float temp = t1;
                t1 = t2;
                t2 = temp;
                s = 1.0f;
            }
            // Push the min up
            if (t1 > tMin)
            {
                normal.setZero();
                normal.x = s;
                tMin = t1;
            }
            // Pull the max down
            tMax = MathUtils.min(tMax, t2);
            if (tMin > tMax)
            {
                argPool.pushVec2(4);
                return false;
            }
        }
        if (absD.y < Settings.EPSILON)
        {
            // Parallel.
            if (p.y < lowerBound.y || upperBound.y < p.y)
            {
                argPool.pushVec2(4);
                return false;
            }
        }
        else
        {
            final float inv_d = 1.0f / d.y;
            float t1 = (lowerBound.y - p.y) * inv_d;
            float t2 = (upperBound.y - p.y) * inv_d;
            // Sign of the normal vector.
            float s = -1.0f;
            if (t1 > t2)
            {
                final float temp = t1;
                t1 = t2;
                t2 = temp;
                s = 1.0f;
            }
            // Push the min up
            if (t1 > tMin)
            {
                normal.setZero();
                normal.y = s;
                tMin = t1;
            }
            // Pull the max down
            tMax = MathUtils.min(tMax, t2);
            if (tMin > tMax)
            {
                argPool.pushVec2(4);
                return false;
            }
        }
        // Does the ray start inside the box?
        // Does the ray intersect beyond the max fraction?
        if (tMin < 0.0f || input.maxFraction < tMin)
        {
            argPool.pushVec2(4);
            return false;
        }
        // Intersection.
        output.fraction = tMin;
        output.normal.x = normal.x;
        output.normal.y = normal.y;
        argPool.pushVec2(4);
        return true;
    }

    public static boolean testOverlap(final AABB a, final AABB b)
    {
        if (b.lowerBound.x - a.upperBound.x > 0.0f
                || b.lowerBound.y - a.upperBound.y > 0.0f)
        {
            return false;
        }
        return !(a.lowerBound.x - b.upperBound.x > 0.0f)
                && !(a.lowerBound.y - b.upperBound.y > 0.0f);
    }

    @Override
    public final String toString()
    {
        return "AABB[" + lowerBound + " . " + upperBound + "]";
    }
}
