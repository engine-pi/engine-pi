/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/actor/StatefulAnimation.java
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
package pi.actor;

import java.awt.Graphics2D;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pi.animation.AnimationFrame;
import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.physics.FixtureBuilder;

/**
 * Ein animierter {@link Actor}, der mehrere Zustände haben kann (laufen
 * (links/rechts), stehen (links/rechts), springen (links/rechts), etc.).
 *
 * <h2>Anwendungsbeispiel</h2>
 *
 * <pre>
 * {@code
 * StatefulAnimation sf = new StatefulAnimation();
 * sf.addState(Animation.createFromAnimatedGif("running.gif",
 * "running");
 * sf.addState(Animation.createFromAnimatedGif("jumping.gif", "jumping");
 * scene.add(sf);
 * sf.state("running");
 * }</pre>
 *
 * @param <State> Typ der Zustände, zwischen denen in der Animation gewechselt
 *     werden soll.
 *
 * @author Michael Andonie
 *
 * @see Animation
 */
public class StatefulAnimation<State> extends Actor
{
    /**
     * Speichert die Einzelbilder (= "Animation") zu jedem Zustand.
     */
    private final Map<State, AnimationFrame[]> states = new ConcurrentHashMap<>();

    /**
     * Speichert den Übergang zum Folgezustand von jedem Zustand. Ordnet
     * standardmäßig jedem Zustand sich selbst als Folgezustand zu ("loop").
     * Kann jedoch über {@link #stateTransition(State, State)} angepasst werden.
     */
    private final Map<State, State> stateTransitions = new ConcurrentHashMap<>();

    /**
     * Der aktuelle Zustand.
     */
    private State state = null;

    /**
     * Die aktuelle Animation.
     */
    private AnimationFrame[] animation = null;

    /**
     * Die aktuelle Zeit.
     */
    private double time = 0;

    /**
     * Der aktuelle Index
     */
    private int index = 0;

    /**
     * Die Breite in Meter.
     */
    protected double width;

    /**
     * Die Höhe in Meter.
     */
    protected double height;

    private boolean flipHorizontal = false;

    private boolean flipVertical = false;

    private boolean animationPaused = false;

    protected double frameDuration;

    /**
     * @param width Die Breite in Meter der animierten Figur.
     * @param height Die Höhe in Meter der animierten Figur.
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *     bleiben.
     */
    public StatefulAnimation(double width, double height, double frameDuration)
    {
        super(() -> FixtureBuilder.rectangle(width, height));
        this.width = width;
        this.height = height;
        this.frameDuration = frameDuration;
        addFrameUpdateListener(this::internalOnFrameUpdate);
    }

    /**
     * @param width Die Breite in Meter der animierten Figur.
     * @param height Die Höhe in Meter der animierten Figur.
     */
    public StatefulAnimation(double width, double height)
    {
        this(width, height, -1);
    }

    /**
     * Gibt die Breite in Meter der animierten Figur zurück.
     *
     * @return Die Breite in Meter der animierten Figur.
     */
    @API
    @Getter
    public double width()
    {
        return width;
    }

    /**
     * Gibt die Höhe in Meter der animierten Figur zurück.
     *
     * @return Die Höhe in Meter der animierten Figur.
     */
    @API
    @Getter
    public double height()
    {
        return height;
    }

    /**
     * Fügt dieser Animation einen neuen Zustand zu hinzu.
     *
     * @param state Der Zustand, unter dem die Animation gespeichert wird.
     * @param stateAnimation Die Animation für diesen Zustand. Kann normal
     *     eingeladen werden, allerdings sollte das übergebene Objekt <b>nicht
     *     selbst in einer Scene angemeldet sein</b>.
     *
     * @see Animation
     */
    @API
    public void addState(State state, Animation stateAnimation)
    {
        if (states.containsKey(state))
        {
            throw new RuntimeException(
                    "Zustandsname wird bereits in diesem Objekt genutzt: "
                            + state);
        }
        AnimationFrame[] frames = stateAnimation.frames();
        states.put(state, frames);
        // Add default loop transition rule for state
        stateTransitions.put(state, state);
        if (this.state == null)
        {
            this.state = state;
            animation = frames;
        }
    }

    /**
     * Setzt den Zustand der Animation. Die Animation des neuen Zustands beginnt
     * in jedem Fall von vorne.
     *
     * @param state Der Name des Zustands, der gesetzt werden soll.
     *
     * @see #changeState(State)
     */
    @API
    @Setter
    public void state(State state)
    {
        if (!states.containsKey(state))
        {
            throw new RuntimeException(
                    "Zustand nicht nicht vorhanden: " + state);
        }
        index = 0;
        this.state = state;
        time = 0;
        animation = states.get(state);
    }

    /**
     * * Eine animierte Figur mit mehreren Zuständen, deren Animationen durch
     * Angabe
     *
     * Ändert den Zustand der Animation. Die Animation des neuen Zustands
     * beginnt nur von vorne, wenn der gesetzte Zustand <b>nicht derselbe ist,
     * wie der aktuelle Zustand</b>.
     *
     * @param state Der Name des Zustands, der gesetzt werden soll.
     *
     * @see #state(State)
     */
    @API
    public void changeState(State state)
    {
        if (!state.equals(this.state))
        {
            state(state);
        }
    }

    /**
     * Gibt an, ob ein bestimmter Zustandsname bereits in dieser Animation
     * genutzt wird.
     *
     * @param state Der zu testende State.
     *
     * @return <code>true</code>: Diese Animation hat einen Zustand mit dem
     *     Namen <code>stateName</code>. <br />
     *     <code>false</code>: Diese Animation hat keinen Zustand mit dem Namen
     *     <code>stateName</code>.
     */
    @API
    public boolean hasState(State state)
    {
        return states.containsKey(state);
    }

    /**
     * Gibt den aktuellen Zustand der Animation aus.
     *
     * @return Der aktuelle Zustand der Animation. Dies ist der {@link String},
     *     der beim Hinzufügen der aktuell aktiven Animation als State-Name
     *     angegeben wurde. Ist <code>null</code>, wenn die Animation noch keine
     *     Zustände hat.
     */
    @API
    @Getter
    public State state()
    {
        return state;
    }

    /**
     * Setzt, ob alle Animationen horizontal gespiegelt dargestellt werden
     * sollen. Hiermit lassen sich zum Beispiel Bewegungsrichtungen
     * (links/rechts) einfach umsetzen.
     *
     * @param flipHorizontal Ob die Animation horizontal geflippt dargestellt
     *     werden soll.
     *
     * @see #flipVertical(boolean)
     */
    @API
    @Setter
    public void flipHorizontal(boolean flipHorizontal)
    {
        this.flipHorizontal = flipHorizontal;
    }

    /**
     * Setzt, ob alle Animationen vertikal gespiegelt dargestellt werden sollen.
     *
     * @param flipVertical Ob die Animation horizontal geflippt dargestellt
     *     werden soll.
     *
     * @see #flipVertical(boolean)
     */
    @API
    @Setter
    public void flipVertical(boolean flipVertical)
    {
        this.flipVertical = flipVertical;
    }

    /**
     * Gibt an, ob das Objekt horizontal gespiegelt ist.
     *
     * @return <code>true</code>, wenn das Objekt gerade horizontal gespiegelt
     *     ist. Sonst <code>false</code>.
     */
    @API
    public boolean isFlipHorizontal()
    {
        return flipHorizontal;
    }

    /**
     * Gibt an, ob das Objekt vertikal gespiegelt ist.
     *
     * @return <code>true</code>, wenn das Objekt gerade vertikal gespiegelt
     *     ist. Sonst <code>false</code>.
     */
    @API
    public boolean isFlipVertical()
    {
        return flipVertical;
    }

    /**
     * Setzt, ob diese <code>StatefulAnimation</code> animiert werden soll.
     *
     * @param animationPaused Ist dieser Wert <code>true</code>, so läuft die
     *     Animation normal weiter. Ist dieser Wert <code>false</code>, so läuft
     *     die Animation nicht und keine automatischen Zustandsübergänge
     *     passieren.
     *
     * @see #isAnimationPaused()
     */
    @API
    @Setter
    public void animationPaused(boolean animationPaused)
    {
        this.animationPaused = animationPaused;
    }

    /**
     * Gibt an, ob die Animation derzeit pausiert ist.
     *
     * @return <code>true</code>, wenn die Animation gerade pausiert ist. Sonst
     *     <code>false</code>.
     *
     * @see #animationPaused(boolean)
     */
    @API
    public boolean isAnimationPaused()
    {
        return animationPaused;
    }

    /**
     * Setzt eine neue Übergangsregel für die Zustände der Animation.<br>
     * Jedes Mal, wenn die Animation vom <b>Von-Zustand</b> einmal durchlaufen
     * wurde, geht die Animation automatisch in den <b>Ziel-Zustand</b> über.
     * <br>
     * Per Default gilt: Ein zugefügter Zustand geht nach Abschluss seiner
     * Animation "in sich selbst" über. Also
     * <code>Von-Zustand = Ziel-Zustand</code>. Effektiv ist das ein Loop.<br>
     * Diese Methode überschreibt die bisherige Übergangsregel für den
     * entsprechenden Von-Zustand.
     *
     * @param stateFrom Der Von-Zustand.
     * @param stateTo Der Ziel-Zustand.
     */
    @API
    @Setter
    public void stateTransition(State stateFrom, State stateTo)
    {
        if (!states.containsKey(stateFrom))
        {
            throw new RuntimeException(
                    "Der Von-Zustand ist nicht in dieser Animation eingepflegt: "
                            + stateFrom);
        }
        if (!states.containsKey(stateTo))
        {
            throw new RuntimeException(
                    "Der To-Zustand ist nicht in dieser Animation eingepflegt: "
                            + stateTo);
        }
        // Remove old transition rule
        stateTransitions.remove(stateFrom);
        // Add new transition rule
        stateTransitions.put(stateFrom, stateTo);
    }

    /**
     * Setzt die Dauer, die ein Frame einer bestimmten Animation verweilt.
     *
     * @param state Der State, für den die Frame-Dauer neu gesetzt werden soll.
     * @param frameDuration Die Zeit (in Sekunden), die jeder einzelne Frame der
     *     Animation des entsprechenden States verweilen soll, bis der Frame
     *     gewechselt wird.
     */
    @API
    @Setter
    public void frameDuration(State state, double frameDuration)
    {
        if (!states.containsKey(state))
        {
            throw new RuntimeException(
                    "Der Zustand ist nicht bekannt: " + state);
        }
        for (AnimationFrame frame : states.get(state))
        {
            frame.duration(frameDuration);
        }
    }

    /**
     * Methode pro Einzelbild über einen anonymen Beobachter aufgerufen.
     *
     * @hidden
     */
    @Internal
    private void internalOnFrameUpdate(double frameDuration)
    {
        if (animation == null || animation.length == 0 || animationPaused)
        {
            return; // we don't have a state yet - or the animation is paused
        }
        time += frameDuration;
        AnimationFrame currentFrame = animation[index];
        while (time > currentFrame.duration())
        {
            time -= currentFrame.duration();
            if (index + 1 == animation.length)
            {
                // Animation cycle has ended. -> Transition to next state
                index = 0;
                State nextState = stateTransitions.get(state);
                AnimationFrame[] nextAnimation = states.get(nextState);
                state = nextState;
                animation = nextAnimation;
            }
            else
            {
                // Animation cycle has not ended -> simply move on to next frame
                index++;
            }
        }
    }

    /**
     * Setzt die Höhe und Breite der Animation neu. Ändert die physikalischen
     * Eigenschaften (Masse etc.).
     *
     * @param width Neue Breite für das Rechteck.
     * @param height Neue Höhe für das Rechteck.
     */
    @API
    @Setter
    public void size(double width, double height)
    {
        assertPositiveWidthAndHeight(width, height);
        this.width = width;
        this.height = height;
        this.fixture(() -> FixtureBuilder.rectangle(width, height));
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        if (animation == null || animation.length == 0)
        {
            return; // we don't have a state yet
        }
        animation[index].render(g, width * pixelPerMeter,
                height * pixelPerMeter, flipHorizontal, flipVertical);
    }
}
