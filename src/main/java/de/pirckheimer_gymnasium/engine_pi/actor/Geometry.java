/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/actor/Geometry.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi.actor;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;
import java.util.function.Supplier;

import de.pirckheimer_gymnasium.engine_pi.animation.AnimationMode;
import de.pirckheimer_gymnasium.engine_pi.animation.ValueAnimator;
import de.pirckheimer_gymnasium.engine_pi.animation.interpolation.LinearDouble;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureData;
import de.pirckheimer_gymnasium.engine_pi.util.ColorUtil;

/**
 * Ein Objekt, das aus n primitiven geometrischen Formen - <b>Dreiecken</b> -
 * besteht.
 *
 * @author Michael Andonie
 */
@API
public abstract class Geometry extends Actor
{
    /**
     * Konstruktor.
     */
    @API
    public Geometry(Supplier<FixtureData> fixtureSupplier)
    {
        super(fixtureSupplier);
        color = colors.get("green");
    }

    /**
     * Gibt die Farbe aus.
     *
     * @return Die Farbe des Objekts.
     */
    @API
    public Color getColor()
    {
        return color;
    }

    /**
     * Animiert die Farbe des aktuellen Objekts.
     *
     * @param duration Dauer der Animation in Sekunden
     * @param color    Neue Farbe des Objekts
     * @return Animations-Objekt, das die weitere Steuerung der Animation
     *         erlaubt
     */
    @API
    public ValueAnimator<Double> animateColor(double duration, Color color)
    {
        Color originalColor = getColor();
        ValueAnimator<Double> animator = new ValueAnimator<>(duration,
                progress -> setColor(
                        ColorUtil.interpolate(originalColor, color, progress)),
                new LinearDouble(0, 1), AnimationMode.SINGLE, this);
        addFrameUpdateListener(animator);
        return animator;
    }
}
