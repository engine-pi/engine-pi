/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/animation/KeyFrame.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
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
package pi.animation;

import pi.animation.interpolation.EaseInOutDouble;
import pi.animation.interpolation.LinearDouble;
import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;

/**
 * Beschreibt einen Keyframe.
 *
 * @param <Value> Werttyp, der animiert werden soll.
 *
 * @author Michael Andonie
 */
public class KeyFrame<Value extends Number>
        implements Comparable<KeyFrame<Value>>
{
    /**
     * Der Typ des Keyframes. Bestimmt die Interpolationsart <b>hin zum nächsten
     * Keyframe</b>.
     */
    private Type type;

    /**
     * Der Zeitpunkt, zu dem der Keyframe den angegebenen Wert markiert.
     */
    private double timecode;

    /**
     * Der Wert, den dieser Keyframe markiert.
     */
    private Value value;

    /**
     * Referenz auf den Nachfolgenden Keyframe, falls vorhanden.
     */
    private KeyFrame<Value> next = null;

    /**
     * Erstellt einen Keyframe
     *
     * @param type Der Typ des Keyframes
     * @param timecode Der Timecode des Keyframes
     */
    @API
    public KeyFrame(Value value, Type type, double timecode)
    {
        this.value = value;
        this.type = type;
        timecode(timecode);
    }

    @API
    @Setter
    public void value(Value value)
    {
        this.value = value;
    }

    @API
    @Getter
    public Value value()
    {
        return value;
    }

    @API
    @Setter
    public void type(Type type)
    {
        this.type = type;
    }

    @API
    @Getter
    public Type type()
    {
        return type;
    }

    @API
    @Setter
    public void timecode(double timecode)
    {
        if (timecode < 0)
        {
            throw new IllegalArgumentException(
                    "Der Timecode eines Keyframes kann nicht kleiner als 0 sein. Er war: "
                            + timecode);
        }
        this.timecode = timecode;
    }

    @API
    @Getter
    public double timecode()
    {
        return timecode;
    }

    /**
     * @hidden
     */
    @Internal
    @Setter
    void next(KeyFrame<Value> next)
    {
        this.next = next;
    }

    @Override
    public int compareTo(KeyFrame<Value> o)
    {
        return (int) ((this.timecode() - o.timecode()) * 1000);
    }

    /**
     * @hidden
     */
    @Internal
    Interpolator<Double> generateInterpolator(Value destinationValue)
    {
        switch (type)
        {
        case LINEAR:
            return new LinearDouble(value.doubleValue(),
                    destinationValue.doubleValue());

        case SMOOTHED_SIN:
            return new EaseInOutDouble(value.doubleValue(),
                    destinationValue.doubleValue());
        }
        throw new RuntimeException("The impossible happened.");
    }

    /**
     * @hidden
     */
    @Internal
    boolean hasNext()
    {
        return next != null;
    }

    /**
     * @hidden
     */
    @Internal
    @Getter
    KeyFrame<Value> next()
    {
        return next;
    }

    /**
     * Aufzählung der verschiedenen Typen von Keyframes.
     * <ul>
     * <li>linear: Der Keyframe interpoliert linear zum nachfolgenden
     * Keyframe</li>
     * <li>smooth: Der Keyframe interpoliert mit einer Sinuskurve zum nächsten
     * Keyframe</li>
     * </ul>
     */
    @API
    public enum Type
    {
        SMOOTHED_SIN,
        LINEAR;
    }
}
