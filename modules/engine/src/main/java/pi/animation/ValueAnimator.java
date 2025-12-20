/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/animation/ValueAnimator.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2018 Michael Andonie and contributors.
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

import java.util.function.Consumer;

import pi.annotations.API;
import pi.event.EventListeners;
import pi.event.FrameUpdateListener;
import pi.event.FrameUpdateListenerRegistration;

/**
 * Ein Animator, der Werte über eine bestimmte Zeitspanne interpoliert und dabei
 * einen {@link Consumer} mit den berechneten Zwischenwerten aufruft.
 *
 * <p>
 * Der ValueAnimator ermöglicht es, Animationen zu erstellen, indem er
 * kontinuierlich Werte zwischen einem Start- und Endwert berechnet und diese an
 * eine Consumer-Funktion übergibt. Die Berechnung erfolgt durch einen
 * {@link Interpolator}, der die konkreten Zwischenwerte bestimmt.
 * </p>
 *
 * <p>
 * Die Klasse unterstützt verschiedene Animationsmodi:
 * </p>
 * <ul>
 * <li>{@link AnimationMode#SINGLE SINGLE} - Die Animation wird einmal
 * ausgeführt und beendet sich dann.</li>
 * <li>{@link AnimationMode#REPEATED REPEATED} - Die Animation wird
 * kontinuierlich wiederholt.</li>
 * <li>{@link AnimationMode#PING_PONG PING_PONG} - Die Animation läuft vorwärts
 * und rückwärts in einer Schleife.</li>
 * </ul>
 *
 * <p>
 * Zusätzlich kann die Animation pausiert, der Fortschritt manuell gesetzt und
 * Completion-Listener registriert werden, die beim Abschluss der Animation (nur
 * im {@link AnimationMode#SINGLE SINGLE}-Modus) aufgerufen werden.
 * </p>
 *
 * @param <Value> Der Typ der Werte, die animiert werden, z. B. {@link Double}
 *
 * @see Interpolator
 * @see AnimationMode
 * @see FrameUpdateListener
 */
public class ValueAnimator<Value> implements FrameUpdateListener
{
    /**
     * Die <b>Dauer</b> der Animation in Sekunden.
     */
    private final Consumer<Value> consumer;

    /**
     * Die Funktion, die bei jedem Einzelbild mit dem interpolierten Wert
     * aufgerufen wird.
     */
    private final Interpolator<Value> interpolator;

    /**
     * Der Animationsmodus (z.B. {@link AnimationMode#SINGLE SINGLE} für
     * einmalige Ausführung).
     */
    private final AnimationMode mode;

    private double currentTime = 0;

    /**
     * Die <b>Dauer</b> der Animation in Sekunden.
     */
    private final double duration;

    private boolean complete = false;

    private boolean paused = false;

    /**
     * Hilfsvariable für {@link AnimationMode#PING_PONG PING_PONG}-Modus.
     */
    private boolean goingBackwards = false;

    private EventListeners<Consumer<Value>> completionListeners = new EventListeners<>();

    /**
     * @param duration Die <b>Dauer</b> der Animation in Sekunden.
     * @param consumer Die Funktion, die bei jedem Einzelbild mit dem
     *     interpolierten Wert aufgerufen wird.
     * @param interpolator Der <b>Interpolator</b>, der die Werte zwischen Start
     *     und Ende berechnet.
     * @param mode Der Animationsmodus (z.B. {@link AnimationMode#SINGLE SINGLE}
     *     für einmalige Ausführung).
     * @param parent Die Figur, Szene oder Ebene, die diesen Animator über die
     *     Methode
     *     {@link FrameUpdateListenerRegistration#addFrameUpdateListener(FrameUpdateListener)}
     *     registriert, damit der Animator bei Abschluss der Animation abmeldet
     *     werden kann.
     */
    public ValueAnimator(double duration, Consumer<Value> consumer,
            Interpolator<Value> interpolator, AnimationMode mode,
            FrameUpdateListenerRegistration parent)
    {
        this.duration = duration;
        this.consumer = consumer;
        this.interpolator = interpolator;
        this.mode = mode;
        if (mode == AnimationMode.SINGLE)
        {
            addCompletionListener(
                    (v) -> parent.removeFrameUpdateListener(this));
        }
    }

    /**
     * @param duration Die <b>Dauer</b> der Animation in Sekunden.
     * @param consumer Die Funktion, die bei jedem Einzelbild mit dem
     *     interpolierten Wert aufgerufen wird.
     * @param interpolator Der <b>Interpolator</b>, der die Werte zwischen Start
     *     und Ende berechnet.
     * @param parent Die Figur, Szene oder Ebene, die diesen Animator über die
     *     Methode
     *     {@link FrameUpdateListenerRegistration#addFrameUpdateListener(FrameUpdateListener)}
     *     registriert, damit der Animator bei Abschluss der Animation abmeldet
     *     werden kann.
     */
    public ValueAnimator(double duration, Consumer<Value> consumer,
            Interpolator<Value> interpolator,
            FrameUpdateListenerRegistration parent)
    {
        this(duration, consumer, interpolator, AnimationMode.SINGLE, parent);
    }

    /**
     * Setzt, ob die ValueAnimation pausiert werden soll.
     *
     * @param paused <code>true</code>: Die Animation wird unterbrochen, bis das
     *     flag umgesetzt wird. <code>false</code>: Die Animation wird wieder
     *     aufgenommen (sollte sie unterbrochen worden sein)
     *
     * @see #isPaused()
     */
    @API
    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }

    /**
     * Gibt an, ob der Animator pausiert ist.
     *
     * @return Ob der Animator pausiert ist.
     *
     * @see #setPaused(boolean)
     */
    @API
    public boolean isPaused()
    {
        return paused;
    }

    /**
     * Setzt den aktuellen Fortschritt des Animators händisch.
     *
     * @param progress Der Fortschritt, zu dem der Animator gesetzt werden soll.
     *     <code>0</code> ist <b>Anfang der Animation</b>, <code>1</code> ist
     *     <b>Ende der Animation</b>. Werte kleiner 0 bzw. größer als 1 sind
     *     nicht erlaubt.
     */
    @API
    public void setProgress(double progress)
    {
        if (progress < 0 || progress > 1)
        {
            throw new IllegalArgumentException(
                    "Der eingegebene Progess muss zwischen 0 und 1 liegen. War "
                            + progress);
        }
        goingBackwards = false;
        currentTime = duration * progress;
        interpolator.interpolate(progress);
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        if (paused)
        {
            return;
        }
        double progress;
        if (!goingBackwards)
        {
            this.currentTime += pastTime;
            if (this.currentTime > this.duration)
            {
                switch (this.mode)
                {
                case REPEATED:
                    this.currentTime %= this.duration;
                    progress = this.currentTime / this.duration;
                    break;

                case SINGLE:
                    this.currentTime = this.duration;
                    progress = 1;
                    complete = true;
                    Value finalValue = this.interpolator.interpolate(1);
                    completionListeners
                            .invoke(listener -> listener.accept(finalValue));
                    break;

                case PING_PONG:
                    // Ging bisher vorwärts -> Jetzt Rückwärts
                    goingBackwards = true;
                    progress = 1;
                    break;

                default:
                    progress = -1;
                    break;
                }
            }
            else
            {
                progress = this.currentTime / this.duration;
            }
        }
        else
        {
            // Ping-Pong-Backwards Strategy
            this.currentTime -= pastTime;
            if (this.currentTime < 0)
            {
                // PINGPONG backwards ist fertig -> Jetzt wieder vorwärts
                goingBackwards = false;
                progress = 0;
            }
            else
            {
                progress = this.currentTime / this.duration;
            }
        }
        this.consumer.accept(interpolator.interpolate(progress));
    }

    public ValueAnimator<Value> addCompletionListener(Consumer<Value> listener)
    {
        if (this.complete)
        {
            listener.accept(this.interpolator.interpolate(1));
        }
        else
        {
            this.completionListeners.add(listener);
        }
        return this;
    }
}
