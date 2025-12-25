/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/tweening/Tweenable.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pi.tweening;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Tweenable interface allows modifying an object's attributes smoothly over
 * time using {@code
 * Tween} instances managed by the {@code TweenEngine}.
 */
public interface Tweenable
{
    /**
     * Gets one or many values from the target object associated to the given
     * tween type. It is used by the Tween Engine to determine starting values.
     *
     * @param tweenType The tween type of this interpolation, determining which
     *     values are modified.
     *
     * @return The array of current tween values.
     */
    default float[] getTweenValues(final TweenType tweenType)
    {
        Logger.getLogger(this.getClass().getName()).log(Level.WARNING,
                () -> String.format(
                        "TweenType %s unsupported for Tweenable '%s'",
                        tweenType.name(), this.getClass().getName()));
        return new float[0];
    }

    /**
     * This method is called in a Tween's update() method to set the new
     * interpolated values.
     *
     * @param tweenType The tween type of this interpolation, determining which
     *     values are modified.
     * @param newValues The new values determined by the tween equation.
     */
    default void setTweenValues(final TweenType tweenType,
            final float[] newValues)
    {
        Logger.getLogger(this.getClass().getName()).log(Level.WARNING,
                () -> String.format(
                        "TweenType %s unsupported for Tweenable '%s'",
                        tweenType.name(), this.getClass().getName()));
    }
}
