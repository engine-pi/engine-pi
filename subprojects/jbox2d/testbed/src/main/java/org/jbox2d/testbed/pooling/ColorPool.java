/*
 * Copyright (c) 2013, Daniel Murphy All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met: * Redistributions of source code must retain the
 * above copyright notice, this list of conditions and the following disclaimer. * Redistributions
 * in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jbox2d.testbed.pooling;

import java.util.HashMap;

/**
 * Sun just HAD to make {@link java.awt.Color} immutable, so now I have to make
 * another stupid pool, and now I'm all hot and bothered. Also, this pool isn't
 * thread safe!
 *
 * @author Daniel Murphy
 */
public abstract class ColorPool<C>
{
    private final HashMap<ColorKey, C> colorMap = new HashMap<>();

    private final ColorKey queryKey = new ColorKey();

    public C getColor(float r, float g, float b, float alpha)
    {
        queryKey.set(r, g, b, alpha);
        if (colorMap.containsKey(queryKey))
        {
            return colorMap.get(queryKey);
        }
        else
        {
            C c = newColor(r, g, b, alpha);
            ColorKey ck = new ColorKey();
            ck.set(r, g, b, alpha);
            colorMap.put(ck, c);
            return c;
        }
    }

    protected abstract C newColor(float r, float g, float b, float alpha);

    public C getColor(float r, float g, float b)
    {
        return getColor(r, g, b, 1);
    }
}

class ColorKey
{
    float r, g, b, a;

    public void set(float argR, float argG, float argB)
    {
        set(argR, argG, argB, 1);
    }

    public void set(float argR, float argG, float argB, float argA)
    {
        r = argR;
        g = argG;
        b = argB;
        a = argA;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(a);
        result = prime * result + Float.floatToIntBits(b);
        result = prime * result + Float.floatToIntBits(g);
        result = prime * result + Float.floatToIntBits(r);
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ColorKey other = (ColorKey) obj;
        if (Float.floatToIntBits(a) != Float.floatToIntBits(other.a))
            return false;
        if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b))
            return false;
        if (Float.floatToIntBits(g) != Float.floatToIntBits(other.g))
            return false;
        return Float.floatToIntBits(r) == Float.floatToIntBits(other.r);
    }
}
