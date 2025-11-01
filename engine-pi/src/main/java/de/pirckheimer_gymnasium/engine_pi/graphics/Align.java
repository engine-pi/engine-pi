/*
 * https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/Align.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2025 Gurkenlabs
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
package de.pirckheimer_gymnasium.engine_pi.graphics;

/**
 * The enum {@code Align} defines a range of horizontal alignments.
 */
public enum Align
{
    /**
     * Center alignment with a portion value of 0.5.
     */
    CENTER(0.5f),

    /**
     * Left alignment with a portion value of 0.0.
     */
    LEFT(0f),

    /**
     * Right alignment with a portion value of 1.0.
     */
    RIGHT(1f),

    /**
     * Center-left alignment with a portion value of 0.25.
     */
    CENTER_LEFT(0.25f),

    /**
     * Center-right alignment with a portion value of 0.75.
     */
    CENTER_RIGHT(0.75f);

    private final float portion;

    Align(float portion)
    {
        this.portion = portion;
    }

    /**
     * Gets the align enumeration value for the specified string.
     *
     * @param alignString The string representing the enum value.
     *
     * @return The enum value represented by the specified string or
     *     {@link Align#CENTER} if the specified string is invalid.
     */
    public static Align get(final String alignString)
    {
        if (alignString == null || alignString.isEmpty())
        {
            return Align.CENTER;
        }

        try
        {
            return Align.valueOf(alignString.toUpperCase());
        }
        catch (final IllegalArgumentException iae)
        {
            return Align.CENTER;
        }
    }

    /**
     * Gets the proportional value of this instance.
     *
     * @param width The width to calculate the relative value from.
     *
     * @return The proportional value for the specified height.
     */
    public float getValue(float width)
    {
        return width * this.portion;
    }

    /**
     * Gets the proportional value of this instance.
     *
     * @param width The width to calculate the relative value from.
     *
     * @return The proportional value for the specified height.
     */
    public double getValue(double width)
    {
        return width * this.portion;
    }

    /**
     * Gets the proportional value of this instance.
     *
     * @param width The width to calculate the relative value from.
     *
     * @return The proportional value for the specified height.
     */
    public int getValue(int width)
    {
        return (int) (width * this.portion);
    }

    /**
     * Gets the location for the specified object width to be horizontally
     * aligned relatively to the bounds of the specified width.<br>
     * Suitable for <b>entity</b> alignment. The return value might be negative
     * or exceed the right boundary which is <i>{@code width} -
     * {@code objectWidth}</i>. <br>
     * For <b>text</b> alignment {@link #getLocation(double, double, boolean)}
     * should be used with <i>{@code preventOverflow}</i> set to {@code true}.
     *
     * @param width The width, limiting the horizontal alignment.
     * @param objectWidth The width of the object for which to calculate the
     *     horizontally aligned location.
     *
     * @return The x-coordinate for the location of the object with the
     *     specified width.
     */
    public double getLocation(final double width, final double objectWidth)
    {
        return getLocation(width, objectWidth, false);
    }

    /**
     * Gets the location for the specified object width to be horizontally
     * aligned relatively to the bounds of the specified width. An overflow
     * behavior (whenever <i>{@code objectWidth} > {@code width}</i>) can be
     * controlled using <b>{@code preventOverflow}</b> parameter:
     * <ul>
     * <li><i>false</i>: the return value might be negative or exceed the right
     * boundary which is <i>{@code width} - {@code objectWidth}</i> (good for
     * <b>entity</b> alignment).</li>
     * <li><i>true</i>: the return value will always be clamped within the
     * bounds (good for <b>text</b> alignment).</li>
     * </ul>
     *
     * @param width The width, limiting the horizontal alignment.
     * @param objectWidth The width of the object for which to calculate the
     *     horizontally aligned location.
     * @param preventOverflow A flag indicating whether the return value should
     *     be clamped to keep it within the bounds (prevent values that are
     *     negative or beyond the right boundary).
     *
     * @return The x-coordinate for the location of the object with the
     *     specified width.
     */
    public double getLocation(final double width, final double objectWidth,
            final boolean preventOverflow)
    {
        double value = this.getValue(width);
        double location = value - objectWidth / 2.0;
        if (objectWidth > width && !preventOverflow)
        {
            return location;
        }

        return Math.max(0, Math.min(width - objectWidth, location));
    }

    /**
     * Gets the portion value of the alignment.
     *
     * @return The portion value of the alignment.
     */
    public float getPortion()
    {
        return portion;
    }
}
