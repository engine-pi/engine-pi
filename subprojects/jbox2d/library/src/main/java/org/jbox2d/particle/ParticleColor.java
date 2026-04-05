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
package org.jbox2d.particle;

import org.jbox2d.common.Color3f;

/**
 * Small color object for each particle
 *
 * @author Daniel Murphy
 *
 * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2Particle.h#L80-L266
 */
public class ParticleColor
{
    public byte r, g, b, a;

    public ParticleColor()
    {
        r = (byte) 127;
        g = (byte) 127;
        b = (byte) 127;
        a = (byte) 50;
    }

    /**
     * Constructor with four elements: r (red), g (green), b (blue), and a
     * (opacity). Each element can be specified 0 to 255.
     *
     * @param r The color red.
     * @param g The color green.
     * @param b The color blue.
     * @param a The alpha channel (opacity).
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2Particle.h#L84-L91
     */
    public ParticleColor(byte r, byte g, byte b, byte a)
    {
        set(r, g, b, a);
    }

    public ParticleColor(Color3f color)
    {
        set(color);
    }

    public void set(Color3f color)
    {
        r = (byte) (255 * color.x);
        g = (byte) (255 * color.y);
        b = (byte) (255 * color.z);
        a = (byte) 255;
    }

    public void set(ParticleColor color)
    {
        r = color.r;
        g = color.g;
        b = color.b;
        a = color.a;
    }

    /**
     * True when all four color elements equal 0. When true, a particle color
     * buffer isn't allocated.
     *
     * @return True when all four color elements equal 0.
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2Particle.h#L97-L103
     */
    public boolean isZero()
    {
        return r == 0 && g == 0 && b == 0 && a == 0;
    }

    /**
     * Sets color for current object using the four elements described above.
     *
     * @param r The color red.
     * @param g The color green.
     * @param b The color blue.
     * @param a The alpha channel (opacity).
     *
     * @repolink https://github.com/google/liquidfun/blob/7f20402173fd143a3988c921bc384459c6a858f2/liquidfun/Box2D/Box2D/Particle/b2Particle.h#L109-L117
     */
    public void set(byte r, byte g, byte b, byte a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
}
