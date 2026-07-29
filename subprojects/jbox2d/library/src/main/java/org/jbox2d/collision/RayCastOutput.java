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
 * Ray-cast result data.
 *
 * <p>
 * If an intersection is found, the hit position can be reconstructed as
 * {@code p1 + fraction * (p2 - p1)}, where {@code p1} and {@code p2} come from
 * {@link RayCastInput}.
 * </p>
 *
 * <p>
 * This object stores only the hit fraction and surface normal. It does not
 * store the hit point directly.
 * </p>
 *
 * https://www.iforce2d.net/b2dtut/raycasting
 *
 * @author Daniel Murphy
 */
public class RayCastOutput
{
    /**
     * Surface normal at the intersection point.
     *
     * <p>
     * This vector points away from the hit surface.
     * </p>
     */
    public final Vec2 normal;

    /**
     * Fraction along the input ray where the intersection occurs.
     *
     * <p>
     * A value of {@code 0} is at {@code p1}; a value of {@code 1} is at
     * {@code p2} when {@code maxFraction == 1}.
     * </p>
     */
    public float fraction;

    /**
     * Creates an empty ray-cast result.
     */
    public RayCastOutput()
    {
        normal = new Vec2();
        fraction = 0;
    }

    /**
     * Copies all values from another ray-cast result.
     *
     * @param rayCastOutput The source result to copy from.
     */
    public void set(final RayCastOutput rayCastOutput)
    {
        normal.set(rayCastOutput.normal);
        fraction = rayCastOutput.fraction;
    }
}
