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
package org.jbox2d.common;

import java.lang.reflect.Array;

/**
 * @author Daniel Murphy
 */
public class BufferUtils
{
    /**
     * Reallocate a buffer.
     */
    public static <T> T[] reallocateBuffer(Class<T> klass, T[] oldBuffer,
            int oldCapacity, int newCapacity)
    {
        assert (newCapacity > oldCapacity);
        @SuppressWarnings("unchecked")
        T[] newBuffer = (T[]) Array.newInstance(klass, newCapacity);
        if (oldBuffer != null)
        {
            System.arraycopy(oldBuffer, 0, newBuffer, 0, oldCapacity);
        }
        for (int i = oldCapacity; i < newCapacity; i++)
        {
            try
            {
                newBuffer[i] = klass.getDeclaredConstructor().newInstance();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
        return newBuffer;
    }

    /**
     * Reallocate a buffer.
     */
    public static int[] reallocateBuffer(int[] oldBuffer, int oldCapacity,
            int newCapacity)
    {
        assert (newCapacity > oldCapacity);
        int[] newBuffer = new int[newCapacity];
        if (oldBuffer != null)
        {
            System.arraycopy(oldBuffer, 0, newBuffer, 0, oldCapacity);
        }
        return newBuffer;
    }

    /**
     * Reallocate a buffer.
     */
    public static float[] reallocateBuffer(float[] oldBuffer, int oldCapacity,
            int newCapacity)
    {
        assert (newCapacity > oldCapacity);
        float[] newBuffer = new float[newCapacity];
        if (oldBuffer != null)
        {
            System.arraycopy(oldBuffer, 0, newBuffer, 0, oldCapacity);
        }
        return newBuffer;
    }

    /**
     * Reallocate a buffer. A 'deferred' buffer is reallocated only if it is not
     * NULL. If 'userSuppliedCapacity' is not zero, buffer is user supplied and
     * must be kept.
     */
    public static <T> T[] reallocateBuffer(Class<T> klass, T[] buffer,
            int userSuppliedCapacity, int oldCapacity, int newCapacity,
            boolean deferred)
    {
        assert (newCapacity > oldCapacity);
        assert (userSuppliedCapacity == 0
                || newCapacity <= userSuppliedCapacity);
        if ((!deferred || buffer != null) && userSuppliedCapacity == 0)
        {
            buffer = reallocateBuffer(klass, buffer, oldCapacity, newCapacity);
        }
        return buffer;
    }

    /**
     * Reallocate an int buffer. A 'deferred' buffer is reallocated only if it
     * is not NULL. If 'userSuppliedCapacity' is not zero, buffer is user
     * supplied and must be kept.
     */
    public static int[] reallocateBuffer(int[] buffer, int userSuppliedCapacity,
            int oldCapacity, int newCapacity, boolean deferred)
    {
        assert (newCapacity > oldCapacity);
        assert (userSuppliedCapacity == 0
                || newCapacity <= userSuppliedCapacity);
        if ((!deferred || buffer != null) && userSuppliedCapacity == 0)
        {
            buffer = reallocateBuffer(buffer, oldCapacity, newCapacity);
        }
        return buffer;
    }

    /**
     * Reallocate a float buffer. A 'deferred' buffer is reallocated only if it
     * is not NULL. If 'userSuppliedCapacity' is not zero, buffer is user
     * supplied and must be kept.
     */
    public static float[] reallocateBuffer(float[] buffer,
            int userSuppliedCapacity, int oldCapacity, int newCapacity,
            boolean deferred)
    {
        assert (newCapacity > oldCapacity);
        assert (userSuppliedCapacity == 0
                || newCapacity <= userSuppliedCapacity);
        if ((!deferred || buffer != null) && userSuppliedCapacity == 0)
        {
            buffer = reallocateBuffer(buffer, oldCapacity, newCapacity);
        }
        return buffer;
    }

    /**
     * Rotate an array, see std::rotate
     */
    public static <T> void rotate(T[] ray, int first, int new_first, int last)
    {
        int next = new_first;
        while (next != first)
        {
            T temp = ray[first];
            ray[first] = ray[next];
            ray[next] = temp;
            first++;
            next++;
            if (next == last)
            {
                next = new_first;
            }
            else if (first == new_first)
            {
                new_first = next;
            }
        }
    }

    /**
     * Rotate an array, see std::rotate
     */
    public static void rotate(int[] ray, int first, int new_first, int last)
    {
        int next = new_first;
        while (next != first)
        {
            int temp = ray[first];
            ray[first] = ray[next];
            ray[next] = temp;
            first++;
            next++;
            if (next == last)
            {
                next = new_first;
            }
            else if (first == new_first)
            {
                new_first = next;
            }
        }
    }

    /**
     * Rotate an array, see std::rotate
     */
    public static void rotate(float[] ray, int first, int new_first, int last)
    {
        int next = new_first;
        while (next != first)
        {
            float temp = ray[first];
            ray[first] = ray[next];
            ray[next] = temp;
            first++;
            next++;
            if (next == last)
            {
                next = new_first;
            }
            else if (first == new_first)
            {
                new_first = next;
            }
        }
    }
}
