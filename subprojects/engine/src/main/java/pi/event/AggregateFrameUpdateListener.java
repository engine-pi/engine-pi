/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/event/AggregateFrameUpdateListener.java
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
package pi.event;

import pi.annotations.API;

/**
 * Aggregiert mehrere {@link FrameUpdateListener}, um sie gemeinsam pausieren zu
 * können.
 *
 * @author Niklas Keller
 */
public abstract class AggregateFrameUpdateListener
        implements FrameUpdateListener, FrameUpdateListenerRegistration
{
    private final EventListeners<FrameUpdateListener> listeners = new EventListeners<>();

    private boolean paused = false;

    @API
    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }

    @API
    public boolean isPaused()
    {
        return paused;
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        if (!paused)
        {
            listeners.invoke(listener -> listener.onFrameUpdate(pastTime));
        }
    }

    @Override
    public EventListeners<FrameUpdateListener> frameUpdateListeners()
    {
        return listeners;
    }
}
