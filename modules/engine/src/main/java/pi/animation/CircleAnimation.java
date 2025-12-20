/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/animation/CircleAnimation.java
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

import pi.Vector;
import pi.actor.Actor;
import pi.animation.interpolation.CosinusDouble;
import pi.animation.interpolation.LinearDouble;
import pi.animation.interpolation.SinusDouble;
import pi.annotations.API;
import pi.event.AggregateFrameUpdateListener;

/**
 * Animiert einen Actor in einem Kreis.
 *
 * @author Michael Andonie
 */
public class CircleAnimation extends AggregateFrameUpdateListener
{
    /**
     * Erstellt eine Circle-Animation. Animiert ein {@link Actor}-Objekt anhand
     * seines Mittelpunkts um einen Drehungsmittelpunkt.
     *
     * @param actor Der zu animierende Actor.
     * @param rotationCenter Das Zentrum der Drehung.
     * @param durationInSeconds Die Dauer einer ganzen Umdrehung in
     *     Millisekunden.
     * @param circleClockwise <code>true</code>: Drehung im Uhrzeigersinn.
     *     <code>false</code>: Drehung entgegen des Uhrzeigersinns.
     * @param rotateActor <code>true</code>: Der Actor rotiert auch.
     *     <code>false</code>: Die Rotation des Actors bleibt fix. Nur seine
     *     Position verändert sich durch die Animation.
     */
    @API
    public CircleAnimation(Actor actor, Vector rotationCenter,
            double durationInSeconds, boolean circleClockwise,
            boolean rotateActor)
    {
        Vector currentActorCenter = actor.getCenter();
        double radius = new Vector(rotationCenter, currentActorCenter)
                .getLength();
        Vector rightPoint = rotationCenter.add(new Vector(radius, 0));
        ValueAnimator<Double> aX = new ValueAnimator<>(durationInSeconds,
                x -> actor.setCenter(x, actor.getCenter().getY()),
                new CosinusDouble(rightPoint.getX(), radius),
                AnimationMode.REPEATED, this);
        ValueAnimator<Double> aY = new ValueAnimator<>(durationInSeconds,
                y -> actor.setCenter(actor.getCenter().getX(), y),
                new SinusDouble(rotationCenter.getY(),
                        circleClockwise ? -radius : radius),
                AnimationMode.REPEATED, this);
        // Winkel zwischen gewünschtem Startpunkt und aktueller Actor-Position
        // (immer in [0;PI])
        double angle = rotationCenter.negate().add(rightPoint)
                .getAngle(rotationCenter.negate().add(currentActorCenter));
        if (circleClockwise && currentActorCenter.getY() > rotationCenter.getY()
                || !circleClockwise
                        && currentActorCenter.getY() < rotationCenter.getY())
        {
            // Gedrehter Winkel ist bereits über die Hälfte
            angle = 360 - angle;
        }
        double actualProgress = angle / 360;
        aX.setProgress(actualProgress);
        aY.setProgress(actualProgress);
        addFrameUpdateListener(aX);
        addFrameUpdateListener(aY);
        if (rotateActor)
        {
            double rotationAngle = circleClockwise ? angle : -angle;
            ValueAnimator<Double> aR = new ValueAnimator<>(durationInSeconds,
                    actor::setRotation,
                    new LinearDouble(-rotationAngle,
                            -rotationAngle + 360 * (circleClockwise ? -1 : 1)),
                    AnimationMode.REPEATED, actor);
            aR.setProgress(actualProgress);
            addFrameUpdateListener(aR);
        }
    }
}
