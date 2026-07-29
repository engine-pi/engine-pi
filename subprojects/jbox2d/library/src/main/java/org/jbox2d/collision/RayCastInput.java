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

import org.jbox2d.common.Vec2;

/**
 * Ray-cast query input data.
 *
 * <p>
 * A ray is a directed line segment used to test whether it intersects a
 * fixture.
 * </p>
 *
 * <p>
 * The ray starts at {@code p1} and extends toward {@code p2}. The tested
 * segment is {@code p1 + t * (p2 - p1)} for {@code 0 <= t <= maxFraction}.
 * </p>
 *
 * https://www.iforce2d.net/b2dtut/raycasting
 *
 * @author Daniel Murphy
 */
public class RayCastInput
{
    /**
     * Start point of the ray.
     */
    public final Vec2 p1;

    /**
     * End point used to define the ray direction.
     *
     * <p>
     * The direction vector is {@code (p2 - p1)}.
     * </p>
     */
    public final Vec2 p2;

    /**
     * Maximum normalized distance along the ray to test for intersection.
     *
     * <p>
     * {@code 1} tests the full segment from {@code p1} to {@code p2}; smaller
     * values shorten the tested segment.
     * </p>
     */
    public float maxFraction;

    /**
     * Creates an empty ray-cast input.
     */
    public RayCastInput()
    {
        p1 = new Vec2();
        p2 = new Vec2();
        maxFraction = 0;
    }

    /**
     * Copies all values from another ray-cast input.
     *
     * @param rayCastInput The source input to copy from.
     */
    public void set(final RayCastInput rayCastInput)
    {
        p1.set(rayCastInput.p1);
        p2.set(rayCastInput.p2);
        maxFraction = rayCastInput.maxFraction;
    }
}
